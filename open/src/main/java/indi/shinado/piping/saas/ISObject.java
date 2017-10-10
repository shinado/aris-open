package indi.shinado.piping.saas;

public interface ISObject {

    void setName(String name);
    String getObjectId();
    void setObjectId(String id);
    void put(String key, Object value);
    void save(ISucceedCallback callback);

    String getString(String key);
    int getInt(String key);
    double getLong(String key);

    ISObject getObject(String key);
}
