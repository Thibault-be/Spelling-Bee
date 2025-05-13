package be.thibault.spellingbee.domain.enums;

public enum Ranking {

    BEGINNER (0, "Beginner"),
    GOOD_START (2, "Good Start"),
    MOVING_UP(5, "Moving Up"),
    GOOD(8, "Good"),
    SOLID(15, "Solid"),
    NICE(25, "Nice"),
    GREAT(40, "Great"),
    AMAZING(51, "Amazing"),
    GENIUS(71, "Genius"),
    QUEEN_BEE(100, "Queen Bee");

    private double threshold;
    private String frontName;

    private Ranking(double threshold, String frontName){
        this.threshold = threshold;
        this.frontName = frontName;
    }

    public String getFrontName(){
        return this.frontName;
    }

}
