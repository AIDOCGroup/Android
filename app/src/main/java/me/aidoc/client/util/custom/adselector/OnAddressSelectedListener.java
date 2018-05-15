package me.aidoc.client.util.custom.adselector;


import me.aidoc.client.entity.bean.AreaData;

public interface OnAddressSelectedListener {
    void onAddressSelected(AreaData province, AreaData city, AreaData county);

    void proviceItemClick(int position);

    void cityItemClick(int position);

}
