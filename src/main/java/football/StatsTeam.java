package football;

public class StatsTeam {
    private int goals;
    private double ball_possession;
    private int shoots;
    private int shoots_on_target;
    private int passes;
    private int free_kicks;
    private int corners;
    private int penalties;
    private int offsides;
    private int fouls;
    private int yellow_cards;
    private int red_cards;

    public StatsTeam(){
    }

    public void addGoal(){
        goals++;
    }
    public int getGoals(){
        return goals;
    }
    public void addShoot(){
        shoots++;
    }
    public int getShoots(){
        return shoots;
    }
    public void addShootOnTarget(){
        shoots_on_target++;
    }
    public int getShoots_on_target(){
        return shoots_on_target;
    }
    public void addPass(){
        passes++;
    }
    public int getPasses(){
        return passes;
    }
    public void addFreeKick(){
        free_kicks++;
    }
    public int getFree_kicks(){
        return free_kicks;
    }
    public void addCorner(){
        corners++;
    }
    public int getCorners(){
        return corners;
    }
    public void addPenalty(){
        penalties++;
    }
    public int getPenalties(){
        return penalties;
    }
    public void addOffside(){
        offsides++;
    }
    public int getOffsides(){
        return offsides;
    }
    public void addFoul(){
        fouls++;
    }
    public int getFouls(){
        return fouls;
    }
    public void addYellow(){
        yellow_cards++;
    }
    public int getYellow_cards(){
        return yellow_cards;
    }
    public void addRed(){
        red_cards++;
    }
    public int getRed_cards(){
        return red_cards;
    }
}