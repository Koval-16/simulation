public class StatsPlayer {
    private int minutes_played;
    private int goals;
    private int shoots;
    private int shoots_on_target;
    private int passes_attempts;
    private int passes_completed;
    private int dribbling_attempts;
    private int dribbling_completed;
    private int duels_attempts;
    private int duels_won;
    private int offsides;
    private int fouls;
    private int yellow_cards;
    private int red_cards;
    private int lost_possession;

    public StatsPlayer(){
    }
    public void addMinutes(int time){
        minutes_played = time/60;
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
    public void addDribblingAttempts(){
        dribbling_attempts++;
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
}