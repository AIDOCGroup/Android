package me.aidoc.client.entity.resp;

import me.aidoc.client.entity.BaseResp;

import xiaofei.library.datastorage.annotation.ClassId;
import xiaofei.library.datastorage.annotation.ObjectId;

@ClassId("UserResp")
public class UserResp extends BaseResp {
    //    userId
// true number
//            用户id
//    username
// true string
//            用户账号
//    nickname
// false string
//            用户昵称
//    token
// true string
//            token
//    type
// false string
//            用户类型
//    msg_count
// false string
//            消息为读数
//    avatar
// false string
//            头像

    @ObjectId
    private String username;
    private int id;
    private String nickname;
    private String token;
    private String type;
    private String msg_count;
    private String avatar;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMsg_count() {
        return msg_count;
    }

    public void setMsg_count(String msg_count) {
        this.msg_count = msg_count;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
