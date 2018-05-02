package me.aidoc.client.entity.resp;

import me.aidoc.client.entity.BaseResp;

public class RegisterResp extends BaseResp {
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
