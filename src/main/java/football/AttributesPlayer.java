package football;

/**
 * Class which stores attributes of a {@link Player}, such as: shooting, passing etc.
 * This class provides setters and getters to access the attributes
 * <p>Attributes:</p>
 * <ul>
 *     <li>side: the side of the player; left(1), center(2) or right(3)</li>
 *     <li>shooting: the shooting ability of the player</li>
 *     <li>dribbling: the dribbling ability of the player</li>
 *     <li>passing: the passing ability of the player</li>
 *     <li>defending: the defending ability of the player</li>
 *     <li>aggression: the aggression of the player</li>
 *     <li>goalkeeping: the goalkeeping ability of the player</li>
 * </ul>
 */
public class AttributesPlayer {
    private final int side;
    private int shooting;
    private int dribbling;
    private int passing;
    private int defending;
    private final int aggression;
    private final int goalkeeping;

    /**
     * Constructor of class AttributesPlayer with the specified attributes.
     * @param side the side of the player; left(1), center(2) or right(3)
     * @param shooting the shooting ability of the player
     * @param dribbling the dribbling ability of the player
     * @param passing the passing ability of the player
     * @param defending the defending ability of the player
     * @param aggression the aggression of the player
     * @param goalkeeping the goalkeeping ability of the player
     */
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
