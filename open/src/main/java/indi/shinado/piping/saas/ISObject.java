package indi.shinado.piping.saas;

import java.util.Date;

public interface ISObject {

    void setName(String name);
    String getObjectId();
    void setObjectId(String id);
    void put(String key, Object value);
    void save(ISucceedCallback callback);
    void increment(String key);

    String getString(String key);
    int getInt(String key);
    long getLong(String key);
    Date getDate(String key);

    ISObject getObject(String key);
}