package me.aidoc.client.entity.req;

public class ResetPhoneReq {

    private String phone;
    private String password;
    private String phone_verify_code;

    public ResetPhoneReq(String phone, String password, String phone_verify_code) {
        this.phone = phone;
        this.password = password;
        this.phone_verify_code = phone_verify_code;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone_verify_code() {
        return phone_verify_code;
    }

    public void setPhone_verify_code(String phone_verify_code) {
        this.phone_verify_code = phone_verify_code;
    }
}
