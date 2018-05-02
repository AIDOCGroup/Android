package me.aidoc.client.entity.req;

public class SportsGoalReq {
    private int sportsGoal;

    public SportsGoalReq(int sportsGoal) {
        this.sportsGoal = sportsGoal;
    }

    public int getSportsGoal() {
        return sportsGoal;
    }

    public void setSportsGoal(int sportsGoal) {
        this.sportsGoal = sportsGoal;
    }
}
