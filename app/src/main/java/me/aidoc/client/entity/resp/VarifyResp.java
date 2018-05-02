package me.aidoc.client.entity.resp;

import me.aidoc.client.entity.BaseResp;

public class VarifyResp extends BaseResp {

    /**
     * phone :手机号
     * type : 0 类型（0：注册，1：修改密码）
     */

    private String phone;
    private int type;

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
