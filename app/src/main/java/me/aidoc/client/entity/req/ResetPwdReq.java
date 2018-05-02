package me.aidoc.client.entity.req;

public class ResetPwdReq {

    /**
     * password :
     * phone_verify_code :
     * phone :
     */

    private String password;
    private String phone_verify_code;
    private String phone;
    private String country_code;

    public ResetPwdReq( String phone, String password,String phone_verify_code, String country_code) {
        this.password = password;
        this.phone_verify_code = phone_verify_code;
        this.phone = phone;
        this.country_code = country_code;
    }

    public String getCountry_code() {
        return country_code;
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
