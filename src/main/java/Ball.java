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
