package indi.shinado.piping.saas.abs;

import indi.shinado.piping.saas.IFoundCallback;
import indi.shinado.piping.saas.IFoundMetaCallback;
import indi.shinado.piping.saas.ISQuery;


public class AbsQuery implements ISQuery{

    @Override
    public void setName(String name) {

    }

    @Override
    public ISQuery equalTo(String key, Object value) {
        return new AbsQuery();
    }

    @Override
    public ISQuery noEqualTo(String key, Object value) {
        return new AbsQuery();
    }

    @Override
    public ISQuery lessThan(String key, Object value) {
        return null;
    }

    @Override
    public ISQuery greaterThan(String key, Object value) {
        return null;
    }

    @Override
    public void find(IFoundCallback callback) {
        callback.onFailed();
    }

    @Override
    public void find(IFoundMetaCallback callback) {
        callback.onFailed();
    }
}
