package pipes.directory;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.FileObserver;
import android.support.v4.content.FileProvider;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

import com.ss.aris.open.array.PipeArray;
import com.ss.aris.open.w.CharacterInputCallback;
import com.ss.aris.open.w.impl.DeviceConsole;
import com.ss.aris.open.w.impl.PermissionCallback;
import com.ss.aris.open.pipes.entity.Instruction;
import com.ss.aris.open.pipes.entity.Keys;
import com.ss.aris.open.pipes.entity.Pipe;
import com.ss.aris.open.pipes.entity.SearchableName;
import com.ss.aris.open.pipes.impl.ShareIntent;
import com.ss.aris.open.pipes.impl.interfaces.Helpable;
import com.ss.aris.open.pipes.search.FullSearchActionPipe;
import com.ss.aris.open.pipes.search.translator.AbsTranslator;
import com.ss.aris.open.pri.PRI;
import com.ss.aris.open.util.IntentUtil;
import com.ss.aris.open.util.JsonUtil;

//TODO in folder, clear connection operator

/**
 * for a preview version
 * cd download
 * ls
 * filename.gmail
 * <p>
 * for full function
 * cd download/folder
 * cd -> download
 * cd -> download/folder
 * qq.apk.cd download/folder
 * cd download/filename.mv.folder
 * cd download/folder.latest
 */
@TargetApi(1061)
public class DirectoryPipe extends FullSearchActionPipe implements Helpable {

    public static final String HEAD = "file";

    private static final String TAG = "DirectoryPipe";
    private AbsTranslator mTranslator;
    private Pipe current = null;
    private Pipe startPipe;
    private Pipe cdPipe;
    private Pipe homePipe;
    private Pipe lsPipe;
    private Pipe rmPipe;
    private boolean doExecute = false;
    private Pipe latestPipe;
    private HashMap<String, Pipe> mPipeMap = new HashMap<>();
    private FileObserver mFileObserver;

    public DirectoryPipe(int id) {
        super(id);
        startPipe = new Pipe(getId(), "$cd", new SearchableName("cd"), "$#cd");

        latestPipe = new Pipe(getId(), "latest", new SearchableName("latest"), "$#latest");
        lsPipe = new Pipe(getId(), "ls", new SearchableName("ls"), "$#ls");
        cdPipe = new Pipe(getId(), "cd..", new SearchableName("cd"), "$#cd");
        homePipe = new Pipe(getId(), "cd~", new SearchableName("root"), "$#root");

        rmPipe = new Pipe(getId(), "rm", new SearchableName("rm"), "$#rm");
        rmPipe.setAcceptableParams(
                new Pipe(getId(), "-today"),
                new Pipe(getId(), "-suffix"),
                new Pipe(getId(), "-within"));
        setBasePipe(startPipe, cdPipe, rmPipe, lsPipe, homePipe, latestPipe);
    }

    private void setBasePipe(Pipe... pipes) {
        for (Pipe p : pipes) {
            p.setBasePipe(this);
        }
    }

    @Override
    protected void startAsSelected() {
        start(false);
    }

    @Override
    public void justStart() {
        start(true);
    }

    private void start(final boolean justStart) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
            ((DeviceConsole) getConsole()).requestPermission(
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    new PermissionCallback() {
                        @Override
                        public void onPermissionResult(boolean granted, boolean first) {
                            if (granted) {
                                onPermissionGranted(justStart);
                                if (first) {
                                    getConsole().showInputMethod();
                                }
                            } else {
                                getConsole().display("\n Permission not granted, abort. ");
                                end();
                            }
                        }
                    }
            );
        } else {
            onPermissionGranted(justStart);
        }
    }

    private void onPermissionGranted(boolean justStart) {
        if (justStart) {
            super.justStart();
        } else {
            super.startAsSelected();
        }

        putItemInMap(latestPipe);
        putItemInMap(rmPipe);
        putItemInMap(lsPipe);

        if (justStart) {
            getConsole().display("\n Console is enabled for searching files and directories now.\n" +
                    "Please use " + defaultExitPipe.getSearchableName().toString() + " to exit. ");
        }

        File root = Environment.getExternalStorageDirectory();
        String rootPath = root.getAbsolutePath();
        String exe = new PRI(HEAD, rootPath).toString();
        homePipe.setExecutable(exe);
        add(new Pipe(getId(), "/", new SearchableName(""), exe), true);
    }

    @Override
    public void reset() {

    }

    @Override
    public void onDestroy() {
        if (mFileObserver != null) {
            mFileObserver.stopWatching();
        }
    }

    private String getDirectory(Pipe rs) {
        PRI pri = PRI.parse(rs.getExecutable());
        if (pri == null) {
            return "";
        }
        return pri.value;
    }

    private void getLatestFor(Pipe rs, OutputCallback callback) {
        String directory = getDirectory(rs);
        File file = new File(directory);
        if (file.isDirectory()) {
            long latestTime = 0L;
            String latest = null;
            String[] children = file.list();
            for (String child : children) {
                File f = new File(directory + File.separator + child);
                long lastModified = f.lastModified();
                if (lastModified > latestTime) {
                    latest = directory + File.separator + child;
                    latestTime = f.lastModified();
                }
            }
            if (latest != null) {
                Pipe target = new Pipe(getId());
                target.setExecutable(new PRI(HEAD, latest).toString());
                doExecute(target, callback);
            } else {
                callback.onOutput("Empty directory. ");
            }
        } else {
            callback.onOutput(directory + " is not a directory. ");
        }
    }

    @Override
    public void doAcceptInput(Pipe result, String input, Pipe.PreviousPipes previous, OutputCallback callback) {
        if (result.equals(latestPipe)) {
            Pipe rs = previous.get();
            getLatestFor(rs, callback);
        } else if (result.equals(rmPipe)) {
            if (previous != null) {
                Pipe prev = previous.get();
                if (prev != null) {
                    try {
                        //TODO
                        final PipeArray array = JsonUtil.fromJson(prev.getExecutable(), PipeArray.class);
                        StringBuilder sb = new StringBuilder();
                        for (Pipe p : array.data) {
                            sb.append(getDirectory(p)).append("\n");
                        }

                        String msg = "<font color='#7E0009'>Warning: </font>";
                        msg += "The following files will be deleted: \n" + sb.toString()
                                + "Press 'y' to continue, any key to abort";
                        getConsole().display(msg);
                        getConsole().waitForCharacterInput(new CharacterInputCallback() {
                            @Override
                            public void onCharacterInput(String character) {
                                if (character.equals("y")) {
                                    for (Pipe p : array.data) {
                                        File f = new File(getDirectory(p));
                                        deleteRecursive(f, true);
                                    }
                                } else {
                                    getConsole().display("Abort. ");
                                }
                            }
                        });

                        return;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    final File file = new File(getDirectory(prev));
                    if (file.exists()) {
                        String prefix;
                        if (file.isDirectory()) {
                            prefix = "directory ";
                        } else {
                            prefix = "file ";
                        }

                        String msg = "<font color='#7E0009'>Warning: </font>";
                        msg += "You are going to delete " + prefix + file.getAbsolutePath() + ".\n"
                                + "Press 'y' to continue, any key to abort";
                        getConsole().display(msg);
                        getConsole().waitForCharacterInput(new CharacterInputCallback() {
                            @Override
                            public void onCharacterInput(String character) {
                                if (character.equals("y")) {
                                    deleteRecursive(file, true);
                                } else {
                                    getConsole().display("Abort. ");
                                }
                            }
                        });
                    }
                }
            }
        } else {
            try {
                ShareIntent intent = JsonUtil.fromJson(input, ShareIntent.class);
                if (Intent.ACTION_SEND.equals(intent.action)) {
                    if (intent.extras.containsKey(Intent.EXTRA_STREAM)) {
                        String path = intent.extras.get(Intent.EXTRA_STREAM);
                        Instruction instruction = result.getInstruction();
                        copy(path, getDirectory(result),
                                !instruction.isParamsEmpty()
                                        && "d".endsWith(instruction.params[0]));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                callback.onOutput("Error to parse intent. ");
            }
        }
    }

    private void deleteRecursive(File fileOrDirectory, boolean singleFile) {
        if (fileOrDirectory.isDirectory())
            for (File child : fileOrDirectory.listFiles())
                deleteRecursive(child, false);

        boolean b = fileOrDirectory.delete();
        if (b) {
            String path = fileOrDirectory.getAbsolutePath();
            removeItemInMap(new Pipe(path));
            if (singleFile) {
                getConsole().input("File " + path + " has been removed. ");
            }
        }
    }

    @Override
    protected void putItemInMap(Pipe vo) {
        super.putItemInMap(vo);
        mPipeMap.put(vo.getExecutable(), vo);
    }

    @Override
    protected void removeItemInMap(Pipe vo) {
        super.removeItemInMap(vo);
        mPipeMap.remove(vo.getExecutable());
    }

    @Override
    protected void doExecute(Pipe rs, OutputCallback outputCallback) {
        doExecute = true;
        if (rs.equals(lsPipe)) {
            String directory = getDirectory(current);
            if (current != null) {
                ls(directory, outputCallback);
            }
        } else if (rs.equals(latestPipe)) {
            if (current != null) {
                getLatestFor(current, outputCallback);
            }
        } else if (rs.equals(rmPipe)) {
            outputCallback.onOutput("rm must take input. ");
        } else {
            String directory = getDirectory(rs);
            if (directory.isEmpty()) {
                outputCallback.onOutput("Empty folder. ");
            } else {
                if (outputCallback == getConsoleCallback()) {
                    File file = new File(directory);
                    if (file.isDirectory()) {
                        cd(rs);
                    } else {
                        executeFile(rs);
                    }
                } else {
                    ShareIntent shareIntent = new ShareIntent();
                    shareIntent.action = Intent.ACTION_SEND;
                    shareIntent.type = IntentUtil.getMIMEType(directory);

//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                        Uri uri = FileProvider.getUriForFile(context,
//                                context.getApplicationContext()
//                                        .getPackageName() + ".FILE_PROVIDER",
//                                new File(directory));
//                        Log.d(TAG, "shareURI: " + uri.toString());
//                        shareIntent.extras.put(Intent.EXTRA_STREAM, uri.toString());
//                    } else {
                        shareIntent.extras.put(Intent.EXTRA_STREAM, "file://" + directory);
//                    }

                    outputCallback.onOutput(shareIntent.toString());
                }
            }
        }
    }

    private void executeFile(Pipe rs) {
        String directory = getDirectory(rs);
        if (directory.endsWith(".pip")) {
            //TODO
        } else {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);

            Uri uri = FileProvider.getUriForFile(
                    context, context.getApplicationContext().getPackageName() + ".FILE_PROVIDER",
                    new File(directory));

            String type = IntentUtil.getMIMEType(directory);
            intent.setDataAndType(uri, type);

            List<ResolveInfo> resInfoList = context.getPackageManager().queryIntentActivities(
                    intent, PackageManager.MATCH_DEFAULT_ONLY);
            for (ResolveInfo resolveInfo : resInfoList) {
                String packageName = resolveInfo.activityInfo.packageName;
                context.grantUriPermission(packageName, uri,
                        Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            getContext().startActivity(intent);
        }

        clear();
        end();
    }

    @Override
    protected void end() {
        super.end();
        doExecute = false;
    }

    private TreeSet<Pipe> cd(Pipe rs) {
        clear();
        TreeSet<Pipe> result = add(rs, false);

        String executable = getDirectory(rs);
        int index = executable.lastIndexOf(File.separator);
        String displayName = index >= 0 ? executable.substring(index) : "";

        getConsole().setIndicator(displayName);
//        ls(rs.getExecutable(), callback);

        mFileObserver = new FileObserver(executable) {
            @Override
            public void onEvent(int i, String s) {
                Log.d(TAG, "onEvent: " + i + ", " + s);
                switch (i) {
                    case FileObserver.CREATE:
                        addFile(new File(s));
                        break;
                    case FileObserver.DELETE:
                        removeFile(s);
                        break;
                }
            }
        };
        mFileObserver.startWatching();

        return result;
    }

    private void ls(String dir, OutputCallback callback) {
        File file = new File(dir);
        File[] files = file.listFiles();
        StringBuilder sb = new StringBuilder();
        for (File f : files) {
            sb.append(f.getName()).append("\n");
        }
        if (callback == getConsoleCallback()) {
            getConsole().display(sb.toString());
        } else {
            callback.onOutput(sb.toString());
        }
    }

    private TreeSet<Pipe> add(Pipe rs, boolean isRoot) {
        TreeSet<Pipe> result;
        current = rs;

        PRI pri = PRI.parse(rs.getExecutable());
        if (pri == null) return new TreeSet<>();

        File file = new File(pri.value);
        if (file.isDirectory()) {
            result = addFiles(file);
        } else {
            result = new TreeSet<>();
        }

        if (!isRoot) {
            cdPipe.setExecutable(file.getParent());
            putItemInMap(cdPipe);
            putItemInMap(homePipe);
        } else {
            removeItemInMap(cdPipe);
            removeItemInMap(homePipe);
        }

        return result;
    }

    private Pipe addFile(File file) {
        String path = file.getPath();
        int index = path.lastIndexOf('/');
        String displayName = path.substring(index + 1, path.length());
//        if (mTranslator == null) {
//            mTranslator = new ChineseTranslator(getContext());
//        }
        Pipe pipe = new Pipe(getId(),
                displayName + (file.isDirectory() ? File.separator : ""),
                mTranslator == null ? new SearchableName(displayName):
                mTranslator.getName(displayName),
                new PRI(HEAD, path).toString());
        putItemInMap(pipe);
        return pipe;
    }

    private TreeSet<Pipe> addFiles(File dir) {
        TreeSet<Pipe> result = new TreeSet<>();
        File files[] = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                result.add(addFile(file));
            }
        }

        return result;
    }

    private void removeFile(String path) {
        Pipe pipe = mPipeMap.get(path);
        removeItemInMap(pipe);
    }

    @Override
    public void load(final AbsTranslator translator, final OnItemsLoadedListener listener, final int total) {
        //nothing to search unless cd is handled
        listener.onItemsLoaded(this, total);
    }

    @Override
    public Pipe getDefaultPipe() {
        return startPipe;
    }

    @Override
    protected TreeSet<Pipe> search(Instruction input) {
        if (input.input.contains(Keys.DIVIDER)) {
            String[] split = input.input.split(Keys.DIVIDER);

            if (input.input.endsWith(Keys.DIVIDER)) {
                if (hasStarted) {
                    TreeSet<Pipe> results = super.search(
                            new Instruction(input.input.replace(Keys.DIVIDER, "")));

                    if (results.size() > 0) {
                        //ls files
                        return cd((Pipe) results.toArray()[0]);
                    }
                }
            } else {
                String trueInput = split[split.length - 1];
                return super.search(new Instruction(trueInput));
            }
        }

        return super.search(input);
    }

    @Override
    public void onSelectedAsPrevious(Pipe result) {
        super.onSelectedAsPrevious(result);
        //so that can apps can be found
        pipeManager.reenableSearchAll();
        //keep searching for directories and files
        //for cd download.latest thing
        endAsSelected();
    }

    @Override
    public void onPreviousDeselected(Pipe result) {
        super.onPreviousDeselected(result);

        pipeManager.searchAction(this);

        //do not call this when in directory mode
        if (doExecute){
            startedAsSelected = true;
        }
    }

    //clear directory only
    private void clear() {
        for (String key : resultMap.keySet()) {
            TreeSet<Pipe> set = resultMap.get(key);
            TreeSet<Pipe> newSet = new TreeSet<>();
            for (Pipe p : set) {
                if (p.getExecutable().startsWith("$#")) {
                    newSet.add(p);
                }
            }
            resultMap.put(key, newSet);
        }

        if (mFileObserver != null) {
            mFileObserver.stopWatching();
        }
    }

    private void copy(String from, String to, boolean delete) {
        getConsole().display("Copying file " + from + " to " + to);
        new FileTask().execute(from, to);
    }

    @Override
    public String getHelp() {
        return new PRI("admin.ss.web",
                "http://arislauncher.com/aris/guide/2017/07/25/cd/").toString();
    }

    class FileTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... strings) {
            if (strings.length == 2) {
                String from = strings[0];
                String to = strings[1];
                int lastIndex = from.lastIndexOf(File.separator);
                if (lastIndex > 0) {
                    String fileName = from.substring(lastIndex);
                    try {
                        //create output directory if it doesn't exist
//                        File dir = new File(to + File.separator + fileName);
//                        if (!dir.exists()) {
//                            dir.mkdirs();
//                        }

                        InputStream in = new FileInputStream(from);
                        OutputStream out = new FileOutputStream(to + File.separator + fileName);

                        byte[] buffer = new byte[1024];
                        int read;
                        while ((read = in.read(buffer)) != -1) {
                            out.write(buffer, 0, read);
                        }
                        in.close();

                        // write the output file
                        out.flush();
                        out.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                        return "Error: " + e.getMessage();
                    }
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (getConsole() != null) {
                if (s == null) {
                    getConsole().display("...Done");
                } else {
                    getConsole().display(s);
                }
            }
        }
    }

    @Override
    public Pipe getByValue(String value) {
        PRI pri = PRI.parse(value);
        if (pri != null) {
            if (HEAD.equals(pri.head)) {
                Pipe pipe = new Pipe(getId(), "", new SearchableName(""), value);
                pipe.setBasePipe(this);
                return pipe;
            }
        } else {
            if (value.equals(latestPipe.getExecutable())) {
                return latestPipe;
            }
        }
        return super.getByValue(value);
    }

    public static Pipe ofFilePath(String path) {
        return new Pipe(new PRI(HEAD, path).toString());
    }

}

