package me.aidoc.client.entity.resp;

import me.aidoc.client.entity.BaseResp;

import xiaofei.library.datastorage.annotation.ClassId;
import xiaofei.library.datastorage.annotation.ObjectId;

@ClassId("SportsProposeResp")
public class SportsProposeResp extends BaseResp {
    @ObjectId
    private String id;
    private int sportsState;
    private int sportsGoal;
    private int sportDay;
    private int sportsHour;
    private int weightInfo;
    private int shape;
    private int wristLength;
    private String sportsLiked;
    private String sportsFieild ;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getSportsState() {
        return sportsState;
    }

    public void setSportsState(int sportsState) {
        this.sportsState = sportsState;
    }

    public int getSportsGoal() {
        return sportsGoal;
    }

    public void setSportsGoal(int sportsGoal) {
        this.sportsGoal = sportsGoal;
    }

    public int getSportDay() {
        return sportDay;
    }

    public void setSportDay(int sportDay) {
        this.sportDay = sportDay;
    }

    public int getSportsHour() {
        return sportsHour;
    }

    public void setSportsHour(int sportsHour) {
        this.sportsHour = sportsHour;
    }

    public int getWeightInfo() {
        return weightInfo;
    }

    public void setWeightInfo(int weightInfo) {
        this.weightInfo = weightInfo;
    }

    public int getShape() {
        return shape;
    }

    public void setShape(int shape) {
        this.shape = shape;
    }

    public int getWristLength() {
        return wristLength;
    }

    public void setWristLength(int wristLength) {
        this.wristLength = wristLength;
    }

    public String getSportsLiked() {
        return sportsLiked;
    }

    public void setSportsLiked(String sportsLiked) {
        this.sportsLiked = sportsLiked;
    }

    public String getSportsFieild() {
        return sportsFieild;
    }

    public void setSportsFieild(String sportsFieild) {
        this.sportsFieild = sportsFieild;
    }
}
