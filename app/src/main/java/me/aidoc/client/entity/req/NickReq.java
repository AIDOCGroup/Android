package me.aidoc.client.entity.req;

public class NickReq {
    public NickReq(String nickname) {
        this.nickname = nickname;
    }

    /**
     * nickname :
     */

    private String nickname;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
