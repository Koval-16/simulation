package football;

/**
 * Class representing the ball in a game.
 * This class stores the position of the ball, the current owner and the team which owns the ball.
 * <p>Attributes:</p>
 * <ul>
 *     <li>x: the x-coordinate of the ball</li>
 *     <li>y: the y-coordinate of the ball</li>
 *     <li>team: the number of the team in possession of the ball</li>
 *     <li>owner: the player who currently is in the possession of the ball</li>
 * </ul>
 */
public class Ball {
    private int x;
    private int y;
    private int team;
    private Player owner;

    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public int getTeam(){
        return team;
    }
    public Player getOwner(){
        return owner;
    }
    public void setX(int x){
        this.x = x;
    }
    public void setY(int y){
        this.y = y;
    }
    public void setTeam(int team){
        this.team = team;
    }
    public void setOwner(Player owner){
        this.owner = owner;
    }
}
