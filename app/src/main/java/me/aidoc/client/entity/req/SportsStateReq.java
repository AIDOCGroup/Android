package me.aidoc.client.entity.req;

/**
 * Created by lijianqiang on 2018/1/9.
 */

public class SportsStateReq {
    public SportsStateReq(int sportsState) {
        this.sportsState = sportsState;
    }

    /**
     * sportsState : 0
     */

    private int sportsState;

    public int getSportsState() {
        return sportsState;
    }

    public void setSportsState(int sportsState) {
        this.sportsState = sportsState;
    }
}
