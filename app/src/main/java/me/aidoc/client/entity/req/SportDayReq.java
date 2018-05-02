package me.aidoc.client.entity.req;


public class SportDayReq {
    public SportDayReq(int sportDay) {
        this.sportDay = sportDay;
    }

    /**
     * sportDay : 0
     */

    private int sportDay;

    public int getSportDay() {
        return sportDay;
    }

    public void setSportDay(int sportDay) {
        this.sportDay = sportDay;
    }
}
