package com.esilva.equiposunidos.util;

import static com.esilva.equiposunidos.util.Constantes.IMAGE_FIRMA;
import static com.esilva.equiposunidos.util.Constantes.PACKAGE_FILE;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Environment;
import android.util.AttributeSet;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * Created by esilva.
 */

public class SignLayout {

    private LinearLayout signCanvas;
    private Signature mSignature;
    private Bitmap mBitmap = null;
    private Context context;

    public SignLayout(LinearLayout signCanvas, Context context) {
        this.signCanvas = signCanvas;
        this.context = context;
        addSignLayoutToCanvas();
    }

    /**
     * Adds a signature canvas to the given layout in signCanvas
     */
    private void addSignLayoutToCanvas() {
        mSignature = new Signature(context, signCanvas, null);
        mSignature.setBackgroundColor(Color.WHITE);
        signCanvas.addView(mSignature, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
    }
    /**
     * Clears signature canvas
     */
    public void eraseCanvas() {
        mSignature.clear();
    }

    /**
     * Returns true or false if the canvas has been marked
     *
     * @return TRUE if the canvas has been painted, FALSE if the canvas has not been touched
     *
     */
    public boolean isSignWritten() {
        return mSignature.isCanvasUsed();
    }

    /**
     * Encodes the given file
     * @param fileName File name of the signature picture
     * @return TRUE if the process ended successfully, FALSE otherwise
     */
    public boolean encodeSign(){

        File signatureFile = new File(context.getFilesDir(), IMAGE_FIRMA);

        if(signatureFile.exists()) {
            signatureFile.delete();
        }

        try {
            signatureFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        signCanvas.setDrawingCacheEnabled(true);
        Bitmap signBitmap = Bitmap.createBitmap(signCanvas.getWidth(), signCanvas.getHeight(), Bitmap.Config.RGB_565);

        Canvas canvas = new Canvas(signBitmap);
        signCanvas.draw(canvas);

        //Codificar el bitmap
        FileOutputStream byteArrayOutputStream = null;
        try {
            byteArrayOutputStream = new FileOutputStream(signatureFile);
            signBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byteArrayOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }finally {

        }

        return true;
    }

    /**
     * Class tasked with handling signature drawing and saving
     *
     * @author oamezquita
     *
     */
    private class Signature extends View {
        private static final float STROKE_WIDTH = 3f;
        private static final float HALF_STROKE_WIDTH = STROKE_WIDTH / 2;
        private final RectF dirtyRect = new RectF();
        private Paint paint = new Paint();
        private Path path = new Path();
        private float lastTouchX;
        private float lastTouchY;
        private Object filename;
        private boolean canvasUsed = false;
        LinearLayout linearLayout;

        /**
         * Constructor de la clase, en este se definen los datos basicos del
         * signCanvas como el color, el estilo de la linea y el alcho de la lÃ­nea
         *
         * @param context
         *            Context de la aplicaciÃ³n
         * @param attrs
         *            Set de atributos del constructor
         */
        public Signature(Context context,LinearLayout linearLayout, AttributeSet attrs) {
            super(context, attrs);
            this.linearLayout = linearLayout;
            paint.setAntiAlias(true);
            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeJoin(Paint.Join.ROUND);
            paint.setStrokeWidth(STROKE_WIDTH);
        }

        /**
         * ConversiÃ³n del signCanvas para ser almacenado en un bitmap
         *
         * @param v
         *            View actual a almacenar
         * @param fname
         *            Nombre del archivo
         * @param format
         *            formato de la imagen
         * @return String con la ubicacion del archivo
         */
        public String save(View v, File fname, Bitmap.CompressFormat format) {
            Log.v("log_tag", "Width: " + v.getWidth());
            Log.v("log_tag", "Height: " + v.getHeight());

            // File name = fname;
            File name = context.getApplicationContext().getDir("imagenesAMF", context.getApplicationContext().MODE_PRIVATE);

            String filename = name.getPath() + File.separator + fname.getName();

            if (mBitmap == null) {
                mBitmap = Bitmap.createBitmap(v.getWidth(), v.getHeight(),
                        Bitmap.Config.RGB_565);
            }
            Canvas canvas = new Canvas(mBitmap);
            FileOutputStream mFileOutStream=null;
            try {
                mFileOutStream = new FileOutputStream(
                        fname.getPath());
                v.draw(canvas);
                mBitmap.compress(format, 95, mFileOutStream);
                mFileOutStream.flush();


            } catch (Exception e) {
                Log.v("log_tag", e.toString());
            }finally {
                try {
                    mFileOutStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return filename;
        }

        public void clear() {
            canvasUsed = false;
            path.reset();
            invalidate();
        }

        public boolean isCanvasUsed() {
            return canvasUsed;
        }

        public void setCanvasUsed(boolean canvasUsed) {
            this.canvasUsed = canvasUsed;
        }

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawPath(path, paint);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            linearLayout.getParent().getParent().getParent().getParent().getParent()
                    .requestDisallowInterceptTouchEvent(true);
            float eventX = event.getX();
            float eventY = event.getY();
            canvasUsed = true;
            // mGetSign.setEnabled(true);

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    path.moveTo(eventX, eventY);
                    lastTouchX = eventX;
                    lastTouchY = eventY;
                    return true;

                case MotionEvent.ACTION_MOVE:

                case MotionEvent.ACTION_UP:

                    resetDirtyRect(eventX, eventY);
                    int historySize = event.getHistorySize();
                    for (int i = 0; i < historySize; i++) {
                        float historicalX = event.getHistoricalX(i);
                        float historicalY = event.getHistoricalY(i);
                        expandDirtyRect(historicalX, historicalY);
                        path.lineTo(historicalX, historicalY);
                    }
                    path.lineTo(eventX, eventY);
                    break;

                default:
                    debug("Ignored touch event: " + event.toString());
                    return false;
            }

            invalidate((int) (dirtyRect.left - HALF_STROKE_WIDTH),
                    (int) (dirtyRect.top - HALF_STROKE_WIDTH),
                    (int) (dirtyRect.right + HALF_STROKE_WIDTH),
                    (int) (dirtyRect.bottom + HALF_STROKE_WIDTH));

            lastTouchX = eventX;
            lastTouchY = eventY;

            return true;
        }

        private void debug(String string) {
        }

        private void expandDirtyRect(float historicalX, float historicalY) {
            if (historicalX < dirtyRect.left) {
                dirtyRect.left = historicalX;
            } else if (historicalX > dirtyRect.right) {
                dirtyRect.right = historicalX;
            }

            if (historicalY < dirtyRect.top) {
                dirtyRect.top = historicalY;
            } else if (historicalY > dirtyRect.bottom) {
                dirtyRect.bottom = historicalY;
            }
        }

        private void resetDirtyRect(float eventX, float eventY) {
            dirtyRect.left = Math.min(lastTouchX, eventX);
            dirtyRect.right = Math.max(lastTouchX, eventX);
            dirtyRect.top = Math.min(lastTouchY, eventY);
            dirtyRect.bottom = Math.max(lastTouchY, eventY);
        }
    }
}
