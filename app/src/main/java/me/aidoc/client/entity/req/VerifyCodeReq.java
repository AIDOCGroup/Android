package me.aidoc.client.entity.req;

/**
 * Created by lijianqiang on 2018/1/8.
 */

public class VerifyCodeReq {

    /**
     * phone :
     * type : 0
     */

    private String phone;
    private int type; //0，注册，1，修改密码，2，修改账号
    private String country_code; //（0：注册，1：修改密码）

    public VerifyCodeReq(String phone, int type, String country_code) {
        this.phone = phone;
        this.type = type;
        this.country_code = country_code;
    }

    public String getCountry_code() {
        return country_code;
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
