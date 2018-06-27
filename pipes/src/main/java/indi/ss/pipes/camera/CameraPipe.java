package indi.ss.pipes.camera;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import com.ss.aris.open.console.impl.DeviceConsole;
import com.ss.aris.open.console.impl.PermissionCallback;
import com.ss.aris.open.console.impl.ResultCallback;
import com.ss.aris.open.pipes.action.SimpleActionPipe;
import com.ss.aris.open.pipes.entity.Instruction;
import com.ss.aris.open.pipes.entity.Pipe;
import com.ss.aris.open.pipes.impl.ShareIntent;
import com.ss.aris.open.util.IntentUtil;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import static android.app.Activity.RESULT_OK;

public class CameraPipe extends SimpleActionPipe {

    public CameraPipe(int id) {
        super(id);
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        return image;
    }

    @Override
    protected void doExecute(Pipe rs, final OutputCallback callback) {
        final Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            final File photoFile = createImageFile();

            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = Uri.fromFile(photoFile);

                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                Instruction ins = rs.getInstruction();
                if (!ins.isParamsEmpty() && "f".equals(ins.params[0])){
                    takePictureIntent.putExtra("android.intent.extras.CAMERA_FACING", 1);
                }

                if (takePictureIntent.resolveActivity(context.getPackageManager()) != null) {
                    final DeviceConsole console = (DeviceConsole) getConsole();
                    console.requestPermission(new String[]{Manifest.permission.CAMERA,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE}, new PermissionCallback() {
                        @Override
                        public void onPermissionResult(boolean granted, boolean firstTime) {
                            if (granted) {
                                console.requestResult(takePictureIntent, new ResultCallback() {
                                    @Override
                                    public void onActivityResult(int resultCode, Intent data) {
                                        if (resultCode == RESULT_OK) {
                                            String file = photoFile.getAbsolutePath();
                                            ShareIntent shareIntent = new ShareIntent();
                                            shareIntent.setAction(Intent.ACTION_SEND);
                                            shareIntent.setType(IntentUtil.getMIMEType(file));

                                            shareIntent.putExtra(Intent.EXTRA_STREAM, "file://" + file);

                                            callback.onOutput(shareIntent.toString());
                                        }
                                    }
                                });
                            }else {
                                callback.onOutput("Permission denied. ");
                            }
                        }
                    });
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public String getDisplayName() {
        return "CAM";
    }

}