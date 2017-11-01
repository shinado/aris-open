package indi.ss.pipes.flash;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.Log;
import com.ss.aris.open.pipes.action.SimpleActionPipe;
import com.ss.aris.open.pipes.entity.Pipe;

public class FlashPipe extends SimpleActionPipe {

    private Camera mCam;
    private boolean torchEnabeld = false;

    public FlashPipe(int id) {
        super(id);
    }

    @Override
    protected void doExecute(Pipe rs, OutputCallback callback) {
        if (callback == getConsoleCallback()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                try {
                    @SuppressLint("WrongConstant") CameraManager cameraManager = (CameraManager)
                            context.getApplicationContext().getSystemService(
                                    Context.CAMERA_SERVICE);
                    if (cameraManager != null) {
                        for (String id : cameraManager.getCameraIdList()) {
                            CameraCharacteristics characteristics = cameraManager.getCameraCharacteristics(id);
                            //noinspection ConstantConditions

                            if (characteristics.get(CameraCharacteristics.FLASH_INFO_AVAILABLE)){
                                torchEnabeld = !torchEnabeld;
                                Log.d("Torching", "doExecute: " + torchEnabeld);
                                cameraManager.setTorchMode(id, torchEnabeld);
                                getConsole().input("Torch is " + (torchEnabeld ? "on" : "off"));
                                return;
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    if (mCam == null) {
                        mCam = Camera.open();
                        Camera.Parameters p = mCam.getParameters();
                        p.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                        mCam.setParameters(p);
                        SurfaceTexture mPreviewTexture = new SurfaceTexture(0);
                        mCam.setPreviewTexture(mPreviewTexture);
                        mCam.startPreview();
                    }else {
                        mCam.stopPreview();
                        mCam.release();
                        mCam = null;
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    getConsole().input(ex.toString());
                }
            }
        }else {
            callback.onOutput("torch");
        }
    }

    @Override
    public String getDisplayName() {
        return "torch";
    }

    @Override
    public void acceptInput(Pipe result, String input, Pipe.PreviousPipes previous, OutputCallback callback) {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            registerCallback();
        }
    }

    @android.support.annotation.RequiresApi(api = Build.VERSION_CODES.M)
    private void registerCallback() {
        try {
            @SuppressLint("WrongConstant") CameraManager cameraManager = (CameraManager)
                    context.getApplicationContext().getSystemService(
                            Context.CAMERA_SERVICE);
            if (cameraManager != null) {
                for (String id : cameraManager.getCameraIdList()) {
                    // Turn on the flash if camera has one
                    CameraCharacteristics characteristics = cameraManager.getCameraCharacteristics(id);
                    //noinspection ConstantConditions
                    if (characteristics.get(CameraCharacteristics.FLASH_INFO_AVAILABLE)) {
                        cameraManager.registerTorchCallback(new CameraManager.TorchCallback() {
                            @Override
                            public void onTorchModeUnavailable(@NonNull String cameraId) {
                                super.onTorchModeUnavailable(cameraId);
                            }

                            @Override
                            public void onTorchModeChanged(@NonNull String cameraId, boolean enabled) {
                                super.onTorchModeChanged(cameraId, enabled);
                                torchEnabeld = enabled;
                            }
                        }, new Handler());
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
