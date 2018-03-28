package indi.shinado.piping.saas;

public interface ISQuery {

    void setName(String name);
    ISQuery equalTo(String key, Object value);
    ISQuery lessThan(String key, Object value);
    ISQuery greaterThan(String key, Object value);
    ISQuery orderByDescending(String key);

    void find(IFoundCallback callback);
}
