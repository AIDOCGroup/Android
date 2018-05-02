package me.aidoc.client.entity.req;

public class SignatureReq {
    public SignatureReq(String signature) {
        this.signature = signature;
    }

    /**
     * signature :
     */

    private String signature;

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
