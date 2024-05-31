package football;

public class AttributesPlayer {
    private final int side;
    private int shooting;
    private int dribbling;
    private int passing;
    private int defending;
    private final int aggression;
    private final int goalkeeping;
    public AttributesPlayer(int side, int shooting, int dribbling, int passing, int defending, int aggression,int goalkeeping){
        this.side = side;
        this.shooting = shooting;
        this.dribbling = dribbling;
        this.passing = passing;
        this.defending = defending;
        this.aggression = aggression;
        this.goalkeeping = goalkeeping;
    }

    public int getSide(){
        return side;
    }
    public int getShooting(){
        return shooting;
    }
    public void setShooting(int shooting){
        this.shooting = shooting;
    }
    public void setDribbling(int dribbling){
        this.dribbling = dribbling;
    }
    public void setPassing(int passing){
        this.passing = passing;
    }
    public void setDefending(int defending){
        this.defending = defending;
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
    public int getGoalkeeping(){
        return goalkeeping;
    }
}
