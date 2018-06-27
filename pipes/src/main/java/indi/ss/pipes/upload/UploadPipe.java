package indi.ss.pipes.upload;

import android.content.Intent;
import com.ss.aris.open.pipes.action.SimpleActionPipe;
import com.ss.aris.open.pipes.entity.Pipe;
import com.ss.aris.open.pipes.impl.ShareIntent;
import com.ss.aris.open.util.FileUtil;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import indi.shinado.piping.saas.IProgressCallback;
import indi.shinado.piping.saas.ISFile;
import indi.shinado.piping.saas.ISucceedCallback;
import indi.shinado.piping.saas.SaasFactory;

public class UploadPipe extends SimpleActionPipe{

    public UploadPipe(int id) {
        super(id);
    }

    @Override
    public String getDisplayName() {
        return "upload";
    }

    @Override
    public void acceptInput(Pipe result, String input, Pipe.PreviousPipes previous, final OutputCallback callback) {
        ShareIntent intent = ShareIntent.from(input);
        if (intent != null) {
            if (Intent.ACTION_SEND.equals(intent.getAction())) {
                if (intent.containsKey(Intent.EXTRA_STREAM)) {
                    String path = intent.getStringExtra(Intent.EXTRA_STREAM);
                    File file = new File(path.replaceFirst("file://", ""));
                    if (file.exists() && file.isFile()) {
                        try {
                            ISFile f = SaasFactory.getFile(context, FileUtil.getName(path), read(file));
                            f.save(new ISucceedCallback() {
                                @Override
                                public void onSucceed(String key) {
                                    callback.onOutput(key);
                                }
                                @Override
                                public void onFail(String msg) {
                                    getConsole().input("Abort since " + msg);
                                }
                            }, new IProgressCallback() {

                                long GAP = 400;
                                long lastUpdateTime = 0;

                                @Override
                                public void onProgress(int p) {
                                    long time = System.currentTimeMillis();
                                    if (time - lastUpdateTime > GAP){
                                        getConsole().input("uploading...." + p + "%");
                                        lastUpdateTime = time;
                                    }
                                }
                            });
                            getConsole().input("uploading....");
                        } catch (IOException e) {
                            e.printStackTrace();
                            getConsole().input("Abort since " + e.getMessage());
                        }

                        return;
                    } else {
                        callback.onOutput("File not exists. ");
                    }
                }
            }
        }

        callback.onOutput("Nothing to upload. ");
    }

    public byte[] read(File file) throws IOException {
        ByteArrayOutputStream ous = null;
        InputStream ios = null;
        try {
            byte[] buffer = new byte[4096];
            ous = new ByteArrayOutputStream();
            ios = new FileInputStream(file);
            int read;
            while ((read = ios.read(buffer)) != -1) {
                ous.write(buffer, 0, read);
            }
        } finally {
            try {
                if (ous != null) ous.close();
            } catch (IOException e) {
            }

            try {
                if (ios != null) ios.close();
            } catch (IOException e) {
            }
        }

        return ous.toByteArray();
    }

    @Override
    protected void doExecute(Pipe rs, OutputCallback callback) {
        callback.onOutput(getDisplayName());
    }

}
