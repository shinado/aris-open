package indi.shinado.piping.saas;

public interface ISQuery {

    public static String SERVER_DATE_TIME = ".server_date_time";

    void setName(String name);
    ISQuery equalTo(String key, Object value);
    ISQuery noEqualTo(String key, Object value);
    ISQuery lessThan(String key, Object value);
    ISQuery greaterThan(String key, Object value);

    void find(IFoundCallback callback);
    void find(IFoundMetaCallback callback);
}
