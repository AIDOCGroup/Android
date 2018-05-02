package me.aidoc.client.entity.req;

public class RegisterReq {

    /**
     * password :
     * phone :
     * phone_verify :
     */

    private String password;
    private String phone;
    private String phone_verify;
    private String country_code;

    public RegisterReq( String phone, String password,String phone_verify, String country_code) {
        this.password = password;
        this.phone = phone;
        this.phone_verify = phone_verify;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone_verify() {
        return phone_verify;
    }

    public void setPhone_verify(String phone_verify) {
        this.phone_verify = phone_verify;
    }
}
