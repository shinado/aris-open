package indi.shinado.piping.saas;

public interface ISucceedCallback {
    void onSucceed(String key);
    void onFail(String msg);
}
