package me.aidoc.client.entity.req;

public class LoginReq {


    /**
     * username :
     * password :
     */

    private String username;
    private String password;
    private String country_code;

    public LoginReq(String username, String password, String country_code) {
        this.username = username;
        this.password = password;
        this.country_code = country_code;
    }

    public String getCountry_code() {
        return country_code;
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
