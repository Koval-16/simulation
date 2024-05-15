public class StatsTeam {
    private Team team;
    double ball_possession;
    int shoots;
    int shoots_on_target;
    int passes;
    int free_kicks;
    int corners;
    int penalties;
    int offsides;
    int fouls;
    int yellow_cards;
    int red_cards;

    public StatsTeam(Team team){
        this.team = team;
    }
}