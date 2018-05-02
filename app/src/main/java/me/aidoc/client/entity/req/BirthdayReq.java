package me.aidoc.client.entity.req;

public class BirthdayReq {

    private String birthday;

    public BirthdayReq(String birthday) {
        this.birthday = birthday;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
}
