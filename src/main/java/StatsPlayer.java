public class StatsPlayer {
    private Player player;
    int minutes_played;
    int shoots;
    int shoots_on_target;
    int passes_attempts;
    int passes_completed;
    int dribbling_attempts;
    int dribbling_completed;
    int duels_attempts;
    int duels_won;
    int offsides;
    int yellow_cards;
    int red_cards;
    int lost_possession;

    public StatsPlayer(Player player){
        this.player = player;
    }
}