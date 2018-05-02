package me.aidoc.client.entity.req;

import me.aidoc.client.entity.BaseResp;

public class LanguageReq extends BaseResp {

    public LanguageReq(int language) {
        this.language = language;
    }

    /**
     * language : 0
     */

    private int language;

    public int getLanguage() {
        return language;
    }

    public void setLanguage(int language) {
        this.language = language;
    }
}
