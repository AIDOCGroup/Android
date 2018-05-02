package me.aidoc.client.entity.req;

/**
 * Created by lijianqiang on 2018/1/8.
 */

public class AvatarReq {

    /**
     * avatar :
     */

    private String avatar;

    public AvatarReq(String avatar) {
        this.avatar = avatar;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
