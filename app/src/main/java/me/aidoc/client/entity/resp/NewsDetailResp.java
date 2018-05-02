package me.aidoc.client.entity.resp;

import me.aidoc.client.entity.BaseResp;

public class NewsDetailResp extends BaseResp {
    int id;
    String title;
    //标题
    String cover;
    //封面
    String content;
    // 内容
    String created_at;
    //创建时间
    int watch_num;
    //观看次数
    int forward_num;
    //分享次数


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public int getWatch_num() {
        return watch_num;
    }

    public void setWatch_num(int watch_num) {
        this.watch_num = watch_num;
    }

    public int getForward_num() {
        return forward_num;
    }

    public void setForward_num(int forward_num) {
        this.forward_num = forward_num;
    }
}
