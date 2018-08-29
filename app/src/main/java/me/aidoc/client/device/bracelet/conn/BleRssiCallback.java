package me.aidoc.client.device.bracelet.conn;

public abstract class BleRssiCallback extends BleCallback {
    public abstract void onSuccess(int rssi);
}