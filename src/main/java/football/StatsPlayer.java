package football;

/**
 * Class StatsPlayer collects statistics of the {@link Player}
 * <p>Attributes:</p>
 * <ul>
 *     <li>sec_played: seconds played</li>
 *     <li>minutes_played: minutes played by player</li>
 *     <li>goals: goals scored</li>
 *     <li>shoots: total shoots made by player</li>
 *     <li>shoots_on_target: shoots on target made by player</li>
 *     <li>dribbling_completed: successful dribbles</li>
 *     <li>duels_attempts: attempts of duels</li>
 *     <li>duels_won: duels won by player</li>
 *     <li>offsides: number of times when player was caught offside</li>
 *     <li>fouls: number of fouls made by player</li>
 *     <li>yellow_cards: number of player's yellow cards</li>
 *     <li>red_cards: number of player's red cards</li>
 *     <li>lost_possession: number of times when player lost the ball</li>
 *     <li>interceptions: interceptions made by player</li>
 *     <li>saves: shoots saved by player</li>
 * </ul>
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

    /**
     * Constructor of class StatsPlayer
     */
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