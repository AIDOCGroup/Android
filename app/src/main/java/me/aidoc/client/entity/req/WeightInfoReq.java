package me.aidoc.client.entity.req;

public class WeightInfoReq {

    public WeightInfoReq(int weightInfo) {
        this.weightInfo = weightInfo;
    }

    public int getWeightInfo() {
        return weightInfo;
    }

    public void setWeightInfo(int weightInfo) {
        this.weightInfo = weightInfo;
    }

    private int weightInfo;

}
