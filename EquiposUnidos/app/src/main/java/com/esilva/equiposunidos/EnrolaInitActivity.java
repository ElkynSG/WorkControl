package com.esilva.equiposunidos;

import static com.esilva.equiposunidos.util.Constantes.*;
import static com.esilva.equiposunidos.util.Util.getAlphaBitmap;
import static com.esilva.equiposunidos.util.Util.replaceBitmapColor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.esilva.equiposunidos.Dialog.CustumerDialog;
import com.esilva.equiposunidos.db.AdminBaseDatos;
import com.esilva.equiposunidos.db.models.User;
import com.suprema.BioMiniFactory;
import com.suprema.CaptureResponder;
import com.suprema.IBioMiniDevice;
import com.suprema.IUsbEventHandler;
import com.suprema.util.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


public class EnrolaInitActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tvLog;
    private Button btLog,btVerifica;
    private ImageView huellaVerif;
    private CustumerDialog custumerDialog;

    public UsbDevice mUsbDevice;
    private UsbManager mUsbManager;
    private PowerManager.WakeLock mWakeLock = null;
    private static final int REQUEST_WRITE_PERMISSION = 786;
    private BioMiniFactory mBioMiniFactory;
    public IBioMiniDevice mCurrentDevice;
    private IBioMiniDevice.TransferMode mTransferMode;
    private IBioMiniDevice.TemplateData mTemplateData;
    private IBioMiniDevice.CaptureOption mCaptureOption = new IBioMiniDevice.CaptureOption();
    private ViewPager2 mViewPager;
    private int mDetect_core = 0;
    private int mTemplateQualityEx = 0;

    private List<User> userEnrroled;
    private User userVerify;
    private Boolean isConected;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enrola_init);
        isConected = false;
        setView();
        AdminBaseDatos adminBaseDatos = new AdminBaseDatos(this);
        userEnrroled = adminBaseDatos.usu_getAll_Enrroled();
        adminBaseDatos.closeBaseDtos();

        if (mUsbManager == null)
            mUsbManager = (UsbManager) getSystemService(Context.USB_SERVICE);

        addDeviceToUsbDeviceList();
    }

    private void setView() {

        huellaVerif = findViewById(R.id.huellaVerif);
        btVerifica =findViewById(R.id.verificaUser);
        btVerifica.setOnClickListener(this);
        tvLog = findViewById(R.id.tvLogVerif);
        tvLog.setMovementMethod(new ScrollingMovementMethod());
    }

    @Override
    protected void onResume() {
        super.onResume();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(isConected) {
                    doVerify();
                }
            }
        },1000);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.verificaUser:
                if (mCurrentDevice != null && mCurrentDevice.isCapturing() == true) {
                   Toast.makeText(this,"En proceso de captura",Toast.LENGTH_SHORT).show();
                    return;
                }
/*
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        userVerified("calos");
                    }
                }).start();
*/
                doVerify();
                break;
            default:
                break;
        }
    }

    private void userVerified(String name){

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                custumerDialog = new CustumerDialog(EnrolaInitActivity.this,"SUCCESS!","Bienvenido !  "+name,false);
                custumerDialog.show();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        custumerDialog.dismiss();
                        goNext();
                    }
                },3000);
            }
        });
    }

    private void userNotVerified(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                custumerDialog = new CustumerDialog(EnrolaInitActivity.this,"FAIL!","Usuario NO verificado\nPruebe con otro dedo",true);
                custumerDialog.show();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        custumerDialog.dismiss();
                    }
                },3000);
            }
        });

    }

    private void goNext(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(EnrolaInitActivity.this,MenuActivity.class));
                finish();
            }
        });

    }

    private void doVerify() {
        scrollBottom("DoVerify");
        if (userEnrroled.size() == 0) {
            scrollBottom("There is no enrolled data");
            return;
        }
        mTemplateData = null;
        mCaptureOption.captureFuntion = IBioMiniDevice.CaptureFuntion.VERIFY;
        mCaptureOption.extractParam.captureTemplate = true;

        //setLogInTextView(getResources().getString(R.string.toast_viewpager_capturing));
        if (mCurrentDevice != null) {
            boolean result = mCurrentDevice.captureSingle(
                    mCaptureOption,
                    mCaptureCallBack,
                    true);
        }
    }

    private void addDeviceToUsbDeviceList() {
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

    private int setTransferMode(boolean _value, boolean _prefchanged) {
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
                isConected = true;
                scrollBottom("conectado");

            } else {
                scrollBottom("mCurrentDevice es null");

            }

        } else {
            scrollBottom("adicionar dispositivo falla");
        }
        //mBioMiniFactory.setTransferMode(IBioMiniDevice.TransferMode.MODE2);
    }

    private void handleDevChange(IUsbEventHandler.DeviceChangeEvent event, Object dev) {
        //Logger.d("START!");
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

            if (option.captureFuntion == IBioMiniDevice.CaptureFuntion.VERIFY && mTemplateData != null) {
                sendMsgToHandler(SET_TEXT_LOGVIEW,"imagen creada");

                sendMsgToHandler(SHOW_CAPTURE_IMAGE_DEVICE, capturedImage);

                sendMsgToHandler(SET_TEXT_LOGVIEW,"Template guardado CALIDAD "+mTemplateData.quality);
                sendMsgToHandler(SET_TEXT_LOGVIEW,"Verificando Usuario");
                Boolean isMached=false;
                for (User user:userEnrroled) {
                    isMached = mCurrentDevice.verify(mTemplateData.data,mTemplateData.data.length,
                                        user.getHuellaPulgar(),user.getHuellaPulgar().length);
                    sendMsgToHandler(SET_TEXT_LOGVIEW,"verificando pulgar "+user.getNombre()+" "+isMached);

                    if(isMached){
                        userVerify = user;
                        break;
                    }
                    isMached = mCurrentDevice.verify(mTemplateData.data,mTemplateData.data.length,
                            user.getHuellaIndice(),user.getHuellaIndice().length);
                    sendMsgToHandler(SET_TEXT_LOGVIEW,"verificando indice"+user.getNombre()+" "+isMached);

                    if(isMached){
                        userVerify = user;
                        break;
                    }
                    sendMsgToHandler(SET_TEXT_LOGVIEW,"verificando medio"+user.getNombre()+" "+isMached);

                    isMached = mCurrentDevice.verify(mTemplateData.data,mTemplateData.data.length,
                            user.getHuellaMedio(),user.getHuellaMedio().length);
                    if(isMached){
                        userVerify = user;
                        break;
                    }
                }
                sendMsgToHandler(SET_TEXT_LOGVIEW,"Usuario "+userVerify.getNombre());

                if(isMached){
                    userVerified(userVerify.getNombre());
                }else{
                    userNotVerified();
                }

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

                    huellaVerif.setImageBitmap(_captureImgDev);

                    break;
            }
        }
    };

    private void scrollBottom(String _log) {
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

}

