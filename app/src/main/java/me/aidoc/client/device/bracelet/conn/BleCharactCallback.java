package me.aidoc.client.device.bracelet.conn;

import android.bluetooth.BluetoothGattCharacteristic;

public abstract class BleCharactCallback extends BleCallback {
    public abstract void onSuccess(BluetoothGattCharacteristic characteristic);
}