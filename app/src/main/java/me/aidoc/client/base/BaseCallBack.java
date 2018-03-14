package me.aidoc.client.base;

public interface BaseCallBack {
    void onSuccess(String msg);
    void onError(Throwable error, String msg);
}
