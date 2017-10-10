package indi.shinado.piping.saas;

import java.util.List;

public interface IFoundCallback {
    void found(List<? extends ISObject> list);
    void onFailed();
}
