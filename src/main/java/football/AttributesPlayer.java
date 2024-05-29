package football;

public class AttributesPlayer {
    private final int side;
    private final int shooting;
    private final int dribbling;
    private final int passing;
    private final int defending;
    private final int aggression;
    private final int intelligence;
    public AttributesPlayer(int side, int shooting, int dribbling, int passing, int defending, int aggression,int intelligence){
        this.side = side;
        this.shooting = shooting;
        this.dribbling = dribbling;
        this.passing = passing;
        this.defending = defending;
        this.aggression = aggression;
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
    public int getPassing(){
        return passing;
    }
    public int getDefending(){
        return defending;
    }
    public int getAggression(){
        return aggression;
    }
    public int getIntelligence(){
        return intelligence;
    }
}