package me.aidoc.client.entity.req;

public class SportsHourReq {
    public SportsHourReq(int sportsHour) {
        this.sportsHour = sportsHour;
    }

    /**
     * sportsHour : 0
     */

    private int sportsHour;

    public int getSportsHour() {
        return sportsHour;
    }

    public void setSportsHour(int sportsHour) {
        this.sportsHour = sportsHour;
    }
}
