package me.aidoc.client.entity.resp;

import xiaofei.library.datastorage.annotation.ClassId;
import xiaofei.library.datastorage.annotation.ObjectId;

@ClassId("CurrentResp")
public class CurrentResp {
    /**
     * userId : 0
     * username :
     * nickname :
     * token :
     * avatar :
     * signature :
     * city_id :
     * city_name :
     * isSign : 0
     */

    private String userId;
    @ObjectId
    private String username;
    private String nickname;
    private String token;
    private String avatar;
    private String signature;
    private String city_id;
    private String city_name;
    private String isSign;

    private String yesterdayAidoc;
    private String birthday;
    private String sex;
    private String totalAidoc;
    private int language;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getIsSign() {
        return isSign;
    }

    public void setIsSign(String isSign) {
        this.isSign = isSign;
    }

    public String getYesterdayAidoc() {
        return yesterdayAidoc;
    }

    public void setYesterdayAidoc(String yesterdayAidoc) {
        this.yesterdayAidoc = yesterdayAidoc;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getTotalAidoc() {
        return totalAidoc;
    }

    public void setTotalAidoc(String totalAidoc) {
        this.totalAidoc = totalAidoc;
    }

    public int getLanguage() {
        return language;
    }

    public void setLanguage(int language) {
        this.language = language;
    }
}
