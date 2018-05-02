package me.aidoc.client.entity.req;


public class SportsLikedReq {
    private String sportsLiked;

    public SportsLikedReq(String sportsLiked) {
        this.sportsLiked = sportsLiked;
    }

    public String getSportsLiked() {
        return sportsLiked;
    }

    public void setSportsLiked(String sportsLiked) {
        this.sportsLiked = sportsLiked;
    }
}
