package com.example.cameraxdemo;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraX;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureConfig;
import androidx.camera.core.Preview;
import androidx.camera.core.PreviewConfig;
import androidx.lifecycle.LifecycleOwner;

//import android.opengl.Matrix;
import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.util.Rational;
import android.util.Size;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class CameraActivity extends AppCompatActivity {

    private static final int MY_REQUEST_CODE = 10;
    TextureView view_finder;
    ImageButton imgCapture;
    ImageButton imgGallery;
    ArrayList<String> paths;
    private static final int PICK_IMAGE_REQUEST = 1;

    private ActivityResultLauncher<Intent> mActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();

                        if (data.getClipData() != null) {
                            int count = data.getClipData().getItemCount(); //evaluate the count before the for loop --- otherwise, the count is evaluated every loop.
                            for (int i = 0; i < count; i++) {
                                Uri uri = data.getClipData().getItemAt(i).getUri();
                                String path = RealPathUtil.getRealPath(getApplicationContext(), uri);
                                paths.add(path + "");
                            }
                        } else if (data.getData() != null) {
                            Uri uri = data.getData();
                            String path = RealPathUtil.getRealPath(getApplicationContext(), uri);
                            paths.add(path + "");
                        }
                    }
                    
                    Intent intent = new Intent(CameraActivity.this, ShowPhotoActivity.class);
                    intent.putExtra("paths", paths);
                    intent.putExtra("isGallery", "1");
                    startActivity(intent);
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        getSupportActionBar().hide();

        view_finder = (TextureView) findViewById(R.id.view_finder);
        imgCapture = (ImageButton) findViewById(R.id.imgCapture);
        imgGallery = (ImageButton) findViewById(R.id.imgGallery);
        startCamera();
    }

    public void startCamera() {
        CameraX.unbindAll();
        Rational aspectRatio = new Rational(view_finder.getWidth(), view_finder.getHeight());
        Size screen = new Size(view_finder.getWidth(), view_finder.getHeight());

        PreviewConfig pConfig = new PreviewConfig.Builder().setTargetAspectRatio(aspectRatio).setTargetResolution(screen).build();
        Preview preview = new Preview(pConfig);
        preview.setOnPreviewOutputUpdateListener(new Preview.OnPreviewOutputUpdateListener() {
            @Override
            public void onUpdated(Preview.PreviewOutput output) {
                ViewGroup parent = (ViewGroup) view_finder.getParent();
                parent.removeView(view_finder);
                parent.addView(view_finder, 0);

                view_finder.setSurfaceTexture(output.getSurfaceTexture());
                updateTransform();
            }
        });

        ImageCaptureConfig imageCaptureConfig = new ImageCaptureConfig.Builder().setCaptureMode(ImageCapture.CaptureMode.MIN_LATENCY)
                .setTargetRotation(getWindowManager().getDefaultDisplay().getRotation()).build();

        final ImageCapture imgCap = new ImageCapture(imageCaptureConfig);

        imgCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File file = new File(Environment.getExternalStorageDirectory() + "/" + System.currentTimeMillis() + ".jpg");
                imgCap.takePicture(file, new ImageCapture.OnImageSavedListener() {
                    @Override
                    public void onImageSaved(@NonNull File file) {
                        paths = new ArrayList<String>();
                        paths.add(file.getAbsoluteFile() + "");
                        Intent intent = new Intent(CameraActivity.this, ShowPhotoActivity.class);
                        intent.putExtra("paths", paths);
                        intent.putExtra("isGallery", "0");
                        startActivity(intent);
                    }

                    @Override
                    public void onError(@NonNull ImageCapture.UseCaseError useCaseError, @NonNull String message, @Nullable Throwable cause) {
                        String msg = "Unable to capture photo" + message;
                        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();

                        if (cause != null) {
                            cause.printStackTrace();
                        }
                    }
                });
            }
        });

        imgGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT == Build.VERSION_CODES.M) {
                    openGallery();
                }

                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    openGallery();
                } else {
                    String[] permission = {Manifest.permission.READ_EXTERNAL_STORAGE};
                    requestPermissions(permission, MY_REQUEST_CODE);
                }
            }
        });
        CameraX.bindToLifecycle((LifecycleOwner) this, preview, imgCap);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            }
        }
    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        mActivityResultLauncher.launch((Intent.createChooser(intent, "Select picture")));
    }

    public String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = getContentResolver().query(contentUri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, MediaStore.Images.Media._ID
                + " = ? ", new String[]{document_id}, null);

        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();

        return path;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        paths = new ArrayList<String>();
        switch (requestCode) {
            case PICK_IMAGE_REQUEST:
                if (resultCode == RESULT_OK) {
                    try {
                        if (data.getClipData() != null) {
                            int count = data.getClipData().getItemCount(); //evaluate the count before the for loop --- otherwise, the count is evaluated every loop.
                            for (int i = 0; i < count; i++) {
                                Uri uri = data.getClipData().getItemAt(i).getUri();
                                String path = getRealPathFromURI(getApplicationContext(), uri);
                                paths.add(path + "");
                            }
                        } else if (data.getData() != null) {
                            Uri uri = data.getData();
                            String path = getRealPathFromURI(getApplicationContext(), uri);
                            paths.add(path + "");
                        }
                        Intent intent = new Intent(CameraActivity.this, ShowPhotoActivity.class);
                        intent.putExtra("paths", paths);
                        intent.putExtra("isGallery", "1");
                        startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }

    public void updateTransform() {
        Matrix mx = new Matrix();
        float h = view_finder.getMeasuredHeight();
        float w = view_finder.getMeasuredWidth();

        float cx = w / 2f;
        float cy = h / 2f;

        int rotationDegree = 90;
        int rotation = (int) view_finder.getRotation();
        switch (rotation) {
            case Surface
                    .ROTATION_0:
                rotationDegree = 0;
                break;
            case Surface
                    .ROTATION_90:
                rotationDegree = 90;
                break;
            case Surface
                    .ROTATION_180:
                rotationDegree = 180;
                break;
            case Surface
                    .ROTATION_270:
                rotationDegree = 270;
                break;
            default:
                return;
        }

        mx.postRotate((float) rotationDegree, cx, cy);

        view_finder.setTransform(mx);
    }
}