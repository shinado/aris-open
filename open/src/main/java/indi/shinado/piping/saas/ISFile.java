package indi.shinado.piping.saas;

public interface ISFile {
    void setup(String name, byte[] bytes);
    String getUrl();
    void save(ISucceedCallback callback, IProgressCallback progressCallback);
}
