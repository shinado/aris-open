package indi.shinado.piping.saas.abs;


import java.util.Date;

import indi.shinado.piping.saas.ISObject;
import indi.shinado.piping.saas.ISucceedCallback;

public class AbsObject implements ISObject{

    @Override
    public void setName(String name) {

    }

    @Override
    public String getObjectId() {
        return "";
    }

    @Override
    public void setObjectId(String id) {

    }

    @Override
    public void put(String key, Object value) {

    }

    @Override
    public void save(ISucceedCallback callback) {
        callback.onFail("AbsObject");
    }

    @Override
    public void increment(String key) {

    }

    @Override
    public String getString(String key) {
        return "";
    }

    @Override
    public int getInt(String key) {
        return 0;
    }

    @Override
    public long getLong(String key) {
        return 0;
    }

    @Override
    public Date getDate(String key) {
        return null;
    }

    @Override
    public ISObject getObject(String key) {
        return new AbsObject();
    }
}
