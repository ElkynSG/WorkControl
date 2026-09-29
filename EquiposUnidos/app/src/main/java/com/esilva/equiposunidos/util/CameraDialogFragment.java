package com.esilva.equiposunidos.util;


import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static androidx.constraintlayout.widget.Constraints.TAG;

import static com.esilva.equiposunidos.util.Constantes.IMAGE_FIRMA;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import com.esilva.equiposunidos.R;
import com.esilva.equiposunidos.databinding.DialogCameraBinding;
import com.google.common.util.concurrent.ListenableFuture;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CameraDialogFragment extends DialogFragment {

    private DialogCameraBinding binding;
    // Interface para devolver la ruta de la foto a la Activity
    public interface OnPhotoCapturedListener {
        void onPhotoCaptured(File photoFile, String comment);
    }

    private OnPhotoCapturedListener listener;
    private ImageCapture imageCapture;
    private ExecutorService cameraExecutor;
    private String nameFhoto;
    private boolean isCaptua;
    private String comentario;
    private File photoFile;

    public void setOnPhotoCapturedListener(OnPhotoCapturedListener listener) {
        this.listener = listener;
    }
    public void setNameFhoto(String nameFhoto) {
        this.nameFhoto = nameFhoto;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DialogCameraBinding.inflate(inflater, container, false);
        isCaptua = false;
        comentario = "";
        cameraExecutor = Executors.newSingleThreadExecutor();


        binding.btnCaptureDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isCaptua)
                    guardar();
                else
                    takePhoto();
            }
        });
        binding.btnCloseDialog.setOnClickListener(v -> dismiss());
        startCamera();

        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        // Ajustar el diálogo a pantalla casi completa
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setLayout(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.MATCH_PARENT
            );
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.BLACK));
        }
    }

    private void startCamera() {
        ListenableFuture<ProcessCameraProvider> cameraProviderFuture =
                ProcessCameraProvider.getInstance(requireContext());

        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();

                Preview preview = new Preview.Builder().build();
                preview.setSurfaceProvider(binding.dialogViewFinder.getSurfaceProvider());

                imageCapture = new ImageCapture.Builder().build();
                CameraSelector cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA;

                cameraProvider.unbindAll();
                cameraProvider.bindToLifecycle(
                        getViewLifecycleOwner(), cameraSelector, preview, imageCapture
                );

            } catch (ExecutionException | InterruptedException e) {
                Log.e(TAG, "Error al iniciar la cámara en el diálogo", e);
            }
        }, ContextCompat.getMainExecutor(requireContext()));
    }

    private void takePhoto() {
        if (imageCapture == null) return;

        File photoDir = new File(requireContext().getFilesDir(),"");
        if (!photoDir.exists()) {
            photoDir.mkdirs();
        }

        photoFile = new File(photoDir, nameFhoto);


        ImageCapture.OutputFileOptions outputOptions =
                new ImageCapture.OutputFileOptions.Builder(photoFile).build();

        imageCapture.takePicture(
                outputOptions,
                ContextCompat.getMainExecutor(requireContext()),
                new ImageCapture.OnImageSavedCallback() {
                    @Override
                    public void onImageSaved(@NonNull ImageCapture.OutputFileResults output) {
                        Toast.makeText(requireContext(), "Foto capturada", Toast.LENGTH_SHORT).show();

                        binding.lyCamera.setVisibility(VISIBLE);
                        binding.dialogViewFinder.setVisibility(GONE);
                        binding.btnCaptureDialog.setText("GUARDAR");
                        binding.edCommentDialog.setText("");

                        if (cameraExecutor != null) {
                            cameraExecutor.shutdown();
                            cameraExecutor = null;
                        }

                        isCaptua = true;

                    }

                    @Override
                    public void onError(@NonNull ImageCaptureException exception) {
                        Log.e(TAG, "Error al capturar foto", exception);
                    }
                }
        );
    }

    private void guardar(){
        comentario = binding.edCommentDialog.getText().toString();
        if (listener != null) {
            listener.onPhotoCaptured(photoFile,comentario);
        }
        dismiss();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (cameraExecutor != null) {
            cameraExecutor.shutdown();
            cameraExecutor = null;
        }
    }
}