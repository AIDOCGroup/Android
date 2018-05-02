package me.aidoc.client.entity.resp;

import me.aidoc.client.entity.BaseResp;

public class ModifyDataResp extends BaseResp {
    private int sex;
    private String birthday;

    public ModifyDataResp(int sex, String birthday) {
        this.sex = sex;
        this.birthday = birthday;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
}
