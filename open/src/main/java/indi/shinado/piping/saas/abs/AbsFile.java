package indi.shinado.piping.saas.abs;


import indi.shinado.piping.saas.IProgressCallback;
import indi.shinado.piping.saas.ISFile;
import indi.shinado.piping.saas.ISucceedCallback;

public class AbsFile implements ISFile {

    @Override
    public void setup(String name, byte[] bytes) {

    }

    @Override
    public String getUrl() {
        return "";
    }

    @Override
    public void save(ISucceedCallback callback, IProgressCallback progressCallback) {
        callback.onFail("absFile");
    }

}
