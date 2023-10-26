package com.esilva.equiposunidos.Setting;

import static com.esilva.equiposunidos.util.Constantes.ACTIVATE_USB_DEVICE;
import static com.esilva.equiposunidos.util.Constantes.ADD_DEVICE;
import static com.esilva.equiposunidos.util.Constantes.CLEAR_VIEW_FOR_CAPTURE;
import static com.esilva.equiposunidos.util.Constantes.FILE_IMAGE;
import static com.esilva.equiposunidos.util.Constantes.MAKE_DELAY_1SEC;
import static com.esilva.equiposunidos.util.Constantes.MAKE_TOAST;
import static com.esilva.equiposunidos.util.Constantes.REMOVE_USB_DEVICE;
import static com.esilva.equiposunidos.util.Constantes.REQUEST_USB_PERMISSION;
import static com.esilva.equiposunidos.util.Constantes.SET_TEXT_LOGVIEW;
import static com.esilva.equiposunidos.util.Constantes.SHOW_CAPTURE_IMAGE_DEVICE;
import static com.esilva.equiposunidos.util.Constantes.UPDATE_DEVICE_INFO;
import static com.esilva.equiposunidos.util.Util.getAlphaBitmap;
import static com.esilva.equiposunidos.util.Util.replaceBitmapColor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.esilva.equiposunidos.Dialog.CustumerDialog;
import com.esilva.equiposunidos.EnrolaInitActivity;
import com.esilva.equiposunidos.R;
import com.esilva.equiposunidos.db.AdminBaseDatos;
import com.esilva.equiposunidos.db.models.User;
import com.suprema.BioMiniFactory;
import com.suprema.CaptureResponder;
import com.suprema.IBioMiniDevice;
import com.suprema.IUsbEventHandler;
import com.suprema.util.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class EnrolaUserActivity extends AppCompatActivity implements View.OnClickListener {

    private final int FINGER_PULDAR = 1;
    private final int FINGER_INDICE = 2;
    private final int FINGER_MEDIO = 3;
    private Button btPulgar,btIndice,btMedio,btRegresar,btEnrolar;
    private TextView tvCedula,tvCargo,tvPerfil,tvLog;
    private ImageView imageUser,imagePulgar,imageIndice,imageMedio;
    private Spinner spinnerUser;
    private List<String> nombres;
    private List<User> users;
    private int idUser;

    private int idFinger;
    private UsbDevice mUsbDevice;
    private UsbManager mUsbManager;
    private BioMiniFactory mBioMiniFactory;
    private IBioMiniDevice mCurrentDevice;
    private IBioMiniDevice.TransferMode mTransferMode;
    private IBioMiniDevice.TemplateData mTemplateData;
    private IBioMiniDevice.TemplateData mTemplatePulgar = null;
    private IBioMiniDevice.TemplateData mTemplateIndice = null;
    private IBioMiniDevice.TemplateData mTemplateMedio = null;
    private IBioMiniDevice.CaptureOption mCaptureOption = new IBioMiniDevice.CaptureOption();
    private int mTemplateQualityEx = 0;
    private int mDetect_core = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enrola_user);

        setView();
        if (mUsbManager == null)
            mUsbManager = (UsbManager) getSystemService(Context.USB_SERVICE);

        addDeviceToUsbDeviceList();

    }

    private void setView() {
        btPulgar = findViewById(R.id.btPulgar);
        btPulgar.setOnClickListener(this);
        btIndice = findViewById(R.id.btIndice);
        btIndice.setOnClickListener(this);
        btMedio = findViewById(R.id.btMedio);
        btMedio.setOnClickListener(this);
        btRegresar = findViewById(R.id.btRegresar);
        btRegresar.setOnClickListener(this);
        btEnrolar = findViewById(R.id.btEnrolar);
        btEnrolar.setOnClickListener(this);
        tvCedula = findViewById(R.id.tvCedula);
        tvCargo = findViewById(R.id.tvCargo);
        tvPerfil = findViewById(R.id.tvPerfil);
        tvLog = findViewById(R.id.tvLog2);
        tvLog.setMovementMethod(new ScrollingMovementMethod());
        imageUser = findViewById(R.id.imageUser);
        imagePulgar = findViewById(R.id.imagePulga);
        imageIndice = findViewById(R.id.imageIndice);
        imageMedio = findViewById(R.id.imageMedio);
        spinnerUser = findViewById(R.id.sppinerUser);
        AdminBaseDatos adminBaseDatos = new AdminBaseDatos(this);
        users = adminBaseDatos.usu_getAll();
        if(users == null)
            return;
        nombres = new ArrayList<>();
        for (User user:users) {
            nombres.add(user.getNombre());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, nombres);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUser.setAdapter(adapter);
        spinnerUser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                setViewDataUser(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void scrollBottom(String _log) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (tvLog == null)
                    return;
                if (tvLog.getLayout() == null)
                    return;
                tvLog.append(_log + "\n");
                final int scrollAmount = tvLog.getLayout().getLineTop(tvLog.getLineCount()) - tvLog.getHeight();
                if (scrollAmount > 0)
                    tvLog.scrollTo(0, scrollAmount);
                else
                    tvLog.scrollTo(0, 0);
            }
        });
        Logger.d(_log);

    }

    private void setViewDataUser(int pos){
        idUser = pos;
        tvCedula.setText(String.valueOf(users.get(pos).getCedula()));
        tvCargo.setText(users.get(pos).getCargo());
        tvPerfil.setText(String.valueOf(users.get(pos).getPerfil()));
        Uri uri = Uri.parse(Environment.getExternalStorageDirectory()+"/"+FILE_IMAGE+"/"+users.get(pos).getFoto());
        File pathImage = new File(Environment.getExternalStorageDirectory()+"/"+FILE_IMAGE, users.get(pos).getFoto());
        if(pathImage.exists() == false)
            uri = Uri.parse(Environment.getExternalStorageDirectory()+"/"+FILE_IMAGE+"/fotico.png");

        if(users.get(pos).getIsEnrolado() == 1)
            imageUser.setBackground(getResources().getDrawable(R.drawable.shape_image_azul));
        else
            imageUser.setBackground(getResources().getDrawable(R.drawable.shape_image_rojo));

        imageUser.setImageURI(uri);
        tvLog.setText(null);
        imageMedio.setImageBitmap(null);
        imagePulgar.setImageBitmap(null);
        imageIndice.setImageBitmap(null);
        mTemplateIndice = null;
        mTemplateMedio = null;
        mTemplatePulgar = null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btPulgar:
                idFinger = FINGER_PULDAR;
                tvLog.setText(null);
                doEnrollment();
                break;
            case R.id.btIndice:
                idFinger = FINGER_INDICE;
                tvLog.setText(null);
                doEnrollment();
                break;
            case R.id.btMedio:
                idFinger = FINGER_MEDIO;
                tvLog.setText(null);
                doEnrollment();
                break;
            case R.id.btRegresar:
                finish();
                break;
            case R.id.btEnrolar:
                SaveUser();
                break;
            default:
                break;
        }
    }

    private void SaveUser() {
        CustumerDialog custumerDialog;
        if(mTemplateMedio != null && mTemplateIndice != null && mTemplatePulgar != null) {

            AdminBaseDatos adminBaseDatos = new AdminBaseDatos(EnrolaUserActivity.this);
            User user = users.get(idUser);
            user.setHuellaMedio(mTemplateMedio.data);
            user.setHuellaIndice(mTemplateIndice.data);
            user.setHuellaPulgar(mTemplatePulgar.data);
            user.setIsEnrolado(1);
            Boolean result = adminBaseDatos.usu_update(user);
            if (result) {
                users.set(idUser,user);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        imageUser.setBackground(getResources().getDrawable(R.drawable.shape_image_azul));
                    }
                });

                custumerDialog = new CustumerDialog(this, "SUCCESS!", "Usuario enrolado con exito", false);
            } else {
                custumerDialog = new CustumerDialog(this, "FAIL!", "Usuario NO enrolado con exito", true);
            }
            custumerDialog.show();
            adminBaseDatos.closeBaseDtos();
        }else{
            new CustumerDialog(this, "SUCCESS!", "Hullas NO capturadas", true).show();
        }
    }

    ///////////////////////////////     biometria    ///////////////////////////////////////////////////
    private void addDeviceToUsbDeviceList() {
        Logger.d("start!");
        if (mUsbManager == null) {
            scrollBottom("mUsbManager is null");
            return;
        }
        if (mUsbDevice != null) {
            scrollBottom("usbdevice is not null!");
            return;
        }

        HashMap<String, UsbDevice> deviceList = mUsbManager.getDeviceList();
        Iterator<UsbDevice> deviceIter = deviceList.values().iterator();
        while (deviceIter.hasNext()) {
            UsbDevice _device = deviceIter.next();
            if (_device.getVendorId() == 0x16d1) {
                scrollBottom("Dispositivo encontrado");
                mUsbDevice = _device;
                if (mUsbManager.hasPermission(mUsbDevice) == false) {
                    scrollBottom("Dispositivo sin permisos");
                    mHandler.sendEmptyMessage(REQUEST_USB_PERMISSION);
                } else {
                    scrollBottom("Este dispositivo ya tiene permiso USB. Por favor, actívelo.");
                    mHandler.sendEmptyMessage(ACTIVATE_USB_DEVICE);
                }
            } else {
                scrollBottom("Dispositivo no encontrado!  : " + _device.getVendorId());
            }
        }
    }

    private void createBioMiniDevice() {
        Logger.d("START!");
        if (mUsbDevice == null) {
            scrollBottom("error conectando mUsbDevice null");

            return;
        }
        if (mBioMiniFactory != null) {
            mBioMiniFactory.close();
        }

        scrollBottom("new BioMiniFactory( )");

        mBioMiniFactory = new BioMiniFactory(this, mUsbManager) { //for android sample
            @Override
            public void onDeviceChange(DeviceChangeEvent event, Object dev) {
                Logger.d("onDeviceChange : " + event);
                handleDevChange(event, dev);
            }
        };
        scrollBottom("new BioMiniFactory( ) : " + mBioMiniFactory);
        setTransferMode(true, false);

        boolean _result = mBioMiniFactory.addDevice(mUsbDevice);

        if (_result == true) {
            mCurrentDevice = mBioMiniFactory.getDevice(0);
            if (mCurrentDevice != null) {
                scrollBottom("conectado");

            } else {
                scrollBottom("mCurrentDevice es null");

            }

        } else {
            scrollBottom("adicionar dispositivo falla");
            Logger.d("addDevice is fail!");
        }
        //mBioMiniFactory.setTransferMode(IBioMiniDevice.TransferMode.MODE2);
    }

    public int setTransferMode(boolean _value, boolean _prefchanged) {
        Logger.d("setTransferMode : " + _value + " _prefchanged : " + _prefchanged);
        int result = 0;
        if (_value == true) {
            mTransferMode = IBioMiniDevice.TransferMode.MODE2;
        } else {
            mTransferMode = IBioMiniDevice.TransferMode.MODE1;
        }
        result = mBioMiniFactory.setTransferMode(mTransferMode);
        if (result != IBioMiniDevice.ErrorCode.OK.value()) {
            if (result == IBioMiniDevice.ErrorCode.ERR_NOT_SUPPORTED.value()) {
                //mSettingFragment.setUseNativeUsbModePref(false);
            }
            return IBioMiniDevice.ErrorCode.ERR_NOT_SUPPORTED.value();
        }

        if (_prefchanged == true)
            recreate();

        return result;
    }

    private void handleDevChange(IUsbEventHandler.DeviceChangeEvent event, Object dev) {
        Logger.d("START!");
    }

    Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case ACTIVATE_USB_DEVICE:
                    if (mUsbDevice != null)
                        scrollBottom("ACTIVATE_USB_DEVICE : " + mUsbDevice.getDeviceName());
                    createBioMiniDevice();
                    break;
                case REMOVE_USB_DEVICE:
                    scrollBottom("REMOVE_USB_DEVICE");
                    // removeDevice();
                    break;
                case UPDATE_DEVICE_INFO:
                    scrollBottom("UPDATE_DEVICE_INFO");
                    break;
                case REQUEST_USB_PERMISSION:
                    scrollBottom("REQUEST_USB_PERMISSION");
                    //mPermissionIntent = PendingIntent.getBroadcast(mContext, 0, new Intent(ACTION_USB_PERMISSION), 0);
                    //mUsbManager.requestPermission(mUsbDevice, mPermissionIntent);
                    break;
                case MAKE_DELAY_1SEC:
                    scrollBottom("MAKE_DELAY_1SEC");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;
                case ADD_DEVICE:
                    scrollBottom("ADD_DEVICE");
                    //addDeviceToUsbDeviceList();
                    break;
                case CLEAR_VIEW_FOR_CAPTURE:
                    scrollBottom("CLEAR_VIEW_FOR_CAPTURE");
                    //cleareViewForCapture();
                    break;
                case SET_TEXT_LOGVIEW:
                    String _log = (String) msg.obj;
                    scrollBottom(_log);
                    break;
                case MAKE_TOAST:
                    scrollBottom("MAKE_TOAST");
                    Logger.d("MAKE_TOAST : " + (String) msg.obj);
                    //Toast.makeText(mContext, (String) msg.obj, Toast.LENGTH_SHORT).show();
                    break;
                case SHOW_CAPTURE_IMAGE_DEVICE:
                    scrollBottom("SHOW_CAPTURE_IMAGE_DEVICE");
                    Bitmap _captureImgDev = (Bitmap) msg.obj;
                    _captureImgDev = replaceBitmapColor(_captureImgDev, Color.argb(0, 233, 10, 10));
                    _captureImgDev = getAlphaBitmap(_captureImgDev, Color.BLACK);
                    if(idFinger == FINGER_PULDAR)
                        imagePulgar.setImageBitmap(_captureImgDev);
                    else if(idFinger == FINGER_INDICE)
                        imageIndice.setImageBitmap(_captureImgDev);
                    else
                        imageMedio.setImageBitmap(_captureImgDev);
                    break;
            }
        }
    };


    private void doEnrollment() {
        mTemplateData = null;
        sendMsgToHandler(SET_TEXT_LOGVIEW,"doEnrollment");

        if (mCurrentDevice != null) {

            sendMsgToHandler(SET_TEXT_LOGVIEW,"doEnrollment 2");

            mCaptureOption.captureFuntion = IBioMiniDevice.CaptureFuntion.ENROLLMENT;
            mCaptureOption.extractParam.captureTemplate = true;

            sendMsgToHandler(SET_TEXT_LOGVIEW,"Se inicia la captura. La página se bloquea hasta que finaliza la captura.");

            if (mCurrentDevice != null) {
                boolean result = mCurrentDevice.captureSingle(
                        mCaptureOption,
                        mCaptureCallBack,
                        true);
            }
        }
    }

    CaptureResponder mCaptureCallBack = new CaptureResponder() {
        @Override
        public void onCapture(Object context, IBioMiniDevice.FingerState fingerState) {
            super.onCapture(context, fingerState);
        }

        @Override
        public boolean onCaptureEx(Object context, IBioMiniDevice.CaptureOption option, final Bitmap capturedImage, IBioMiniDevice.TemplateData capturedTemplate, IBioMiniDevice.FingerState fingerState) {

            sendMsgToHandler(SET_TEXT_LOGVIEW,"START! : " + mCaptureOption.captureFuntion.toString());

            if (capturedTemplate != null) {
                sendMsgToHandler(SET_TEXT_LOGVIEW,"TemplateData is not null!");

                mTemplateData = capturedTemplate;
            }

            if (option.captureFuntion == IBioMiniDevice.CaptureFuntion.ENROLLMENT && mTemplateData != null) {
                sendMsgToHandler(SET_TEXT_LOGVIEW,"register user template data.");

                if(idFinger == FINGER_PULDAR)
                   mTemplatePulgar =  mTemplateData;
                else if(idFinger == FINGER_INDICE)
                    mTemplateIndice =  mTemplateData;
                else
                    mTemplateMedio =  mTemplateData;


                Boolean result = true;
                if (result == true) {
                    sendMsgToHandler(SET_TEXT_LOGVIEW,"Template guardado CALIDAD "+mTemplateData.quality);
                } else
                    sendMsgToHandler(SET_TEXT_LOGVIEW,"Template no guardado");

            }

            if (capturedTemplate != null) {
                sendMsgToHandler(SET_TEXT_LOGVIEW,"Template guardado 2");

                if (mCurrentDevice != null && mCurrentDevice.getLfdLevel() > 0) {
                    sendMsgToHandler(SET_TEXT_LOGVIEW,"LFD SCORE : " + mCurrentDevice.getLfdScoreFromCapture());
                }
                if (mDetect_core == 1) {
                    int[] _coord = mCurrentDevice.getCoreCoordinate();
                    sendMsgToHandler(SET_TEXT_LOGVIEW,"Core Coordinate X : " + _coord[0] + " Y : " + _coord[1]);
                }
                if (mTemplateQualityEx == 1) {
                    int _templateQualityExValue = mCurrentDevice.getTemplateQualityExValue();
                    sendMsgToHandler(SET_TEXT_LOGVIEW,"template Quality : " + _templateQualityExValue);
                }
            }

            IBioMiniDevice.FpQualityMode nqsModeDefault = IBioMiniDevice.FpQualityMode.NQS_MODE_DEFAULT;
            //fpquality example
            if (mCurrentDevice != null) {
                /*byte[] imageData = mCurrentDevice.getCaptureImageAsRAW_8();
                if (imageData != null) {
                    IBioMiniDevice.FpQualityMode mode = IBioMiniDevice.FpQualityMode.NQS_MODE_DEFAULT;
                    int _fpquality = mCurrentDevice.getFPQuality(imageData, mCurrentDevice.getImageWidth(), mCurrentDevice.getImageHeight(), mode.value());
                    Logger.d("_fpquality : " + _fpquality);
                }*/
            }


            sendMsgToHandler(SET_TEXT_LOGVIEW,"imagen creada");
            sendMsgToHandler(SHOW_CAPTURE_IMAGE_DEVICE, capturedImage);

            if (option.captureFuntion == IBioMiniDevice.CaptureFuntion.CAPTURE_SINGLE ||
                    option.captureFuntion == IBioMiniDevice.CaptureFuntion.ENROLLMENT ||
                    option.captureFuntion == IBioMiniDevice.CaptureFuntion.VERIFY) {
                sendMsgToHandler(SET_TEXT_LOGVIEW,"set ui event is available.");

            }
            return true;

        }

        @Override
        public void onCaptureError(Object context, int errorCode, String error) {
            if (errorCode == IBioMiniDevice.ErrorCode.CTRL_ERR_IS_CAPTURING.value()) {
                scrollBottom("Other capture function is running. abort capture function first!");
            } else if (errorCode == IBioMiniDevice.ErrorCode.CTRL_ERR_CAPTURE_ABORTED.value()) {
                Logger.d("CTRL_ERR_CAPTURE_ABORTED occured.");
            } else if (errorCode == IBioMiniDevice.ErrorCode.CTRL_ERR_FAKE_FINGER.value()) {
                scrollBottom("Fake Finger Detected");
                if (mCurrentDevice != null && mCurrentDevice.getLfdLevel() > 0) {
                    scrollBottom("LFD SCORE : " + mCurrentDevice.getLfdScoreFromCapture());
                }
            } else {
                scrollBottom(mCaptureOption.captureFuntion.name() + " is fail by " + error);
                scrollBottom("Please try again.");
            }
            // mViewPager.setUserInputEnabled(true);
            //setUiClickable(true);
        }
    };

    private void sendMsgToHandler(int what, Object objToSend) {
        Message msg = new Message();
        msg.what = what;
        msg.obj = objToSend;
        mHandler.sendMessage(msg);
    }

    private void sendMsgToHandler(int what, String msgToSend) {
        Message msg = new Message();
        msg.what = what;
        msg.obj = (String) msgToSend;
        mHandler.sendMessage(msg);
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////

}