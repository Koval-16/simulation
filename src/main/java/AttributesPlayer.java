public class AttributesPlayer {
    private final int side;
    private final int shooting;
    private final int dribbling;
    private final int speed;
    private final int passing;
    private final int defending;
    private final int heading;
    private final int aggression;
    private final int risk_taking;
    private final int intelligence;
    public AttributesPlayer(int side, int shooting, int dribbling, int speed, int passing, int defending,
                            int heading, int aggression, int risk_taking, int intelligence){
        this.side = side;
        this.shooting = shooting;
        this.dribbling = dribbling;
        this.speed = speed;
        this.passing = passing;
        this.defending = defending;
        this.heading = heading;
        this.aggression = aggression;
        this.risk_taking = risk_taking;
        this.intelligence = intelligence;
    }

    public int getSide(){
        return side;
    }
    public int getShooting(){
        return shooting;
    }
    public int getDribbling(){
        return dribbling;
    }
    public int getSpeed(){
        return speed;
    }
    public int getPassing(){
        return passing;
    }
    public int getDefending(){
        return defending;
    }
    public int getHeading(){
        return heading;
    }
    public int getAggression(){
        return aggression;
    }
    public int getRisk_taking(){
        return risk_taking;
    }
    public int getIntelligence(){
        return intelligence;
    }
}
