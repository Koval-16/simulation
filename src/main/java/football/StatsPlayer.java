package football;

/**
 * Class StatsPlayer collects statistics of the player.
 */
public class StatsPlayer {
    private int sec_played;
    private int minutes_played;
    private int goals;
    private int shoots;
    private int shoots_on_target;
    private int passes_attempts;
    private int passes_completed;
    private int dribbling_completed;
    private int duels_attempts;
    private int duels_won;
    private int offsides;
    private int fouls;
    private int yellow_cards;
    private int red_cards;
    private int lost_possession;
    private int interceptions;
    private int saves;

    public StatsPlayer(){
    }
    public void addSeconds(int time){
        sec_played += time;
    }
    public void calculateMinutes(){
        minutes_played = sec_played/60;
    }
    public void addGoals(){
        goals++;
    }
    public void addShoots(){
        shoots++;
    }
    public void addShootOnTarget(){
        shoots_on_target++;
    }
    public void addPassesAttempts(){
        passes_attempts++;
    }
    public void addPassesCompleted(){
        passes_completed++;
    }
    public void addDribblingCompleted(){
        dribbling_completed++;
    }
    public void addDuel(){
        duels_attempts++;
    }
    public void addDuelWon(){
        duels_won++;
    }
    public void addYellow(){
        yellow_cards++;
    }

    public void addFoul(){
        fouls++;
    }
    public void addRed(){
        red_cards++;
    }
    public void addOffside(){
        offsides++;
    }
    public void addLost(){
        lost_possession++;
    }
    public int getMinutes_played(){
        return minutes_played;
    }
    public int getYellow_cards(){
        return yellow_cards;
    }
    public void addInterception(){
        interceptions++;
    }
    public void addSave(){
        saves++;
    }
    public int getGoals(){
        return goals;
    }
    public int getShoots(){
        return shoots;
    }
    public int getShoots_on_target(){
        return shoots_on_target;
    }
    public int getPasses_attempts(){
        return passes_attempts;
    }
    public int getPasses_completed(){
        return passes_completed;
    }
    public int getDribbling_completed(){
        return dribbling_completed;
    }
    public int getDuels_attempts(){
        return duels_attempts;
    }
    public int getDuels_won(){
        return duels_won;
    }
    public int getOffsides(){
        return offsides;
    }
    public int getRed_cards(){
        return red_cards;
    }
    public int getInterceptions(){
        return interceptions;
    }
    public int getLost_possession(){
        return lost_possession;
    }
    public int getSaves(){
        return saves;
    }
}