package me.aidoc.client.entity.resp;

import me.aidoc.client.entity.BaseResp;


public class UploadFileResp  extends BaseResp {
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
