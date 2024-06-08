package football;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Class representing football match.
 * <p>Attributes:</p>
 * <ul>
 *     <li>MATCH_DURATION: time of match duration</li>
 *     <li>time: the current time of the game</li>
 *     <li>no_play_time: helper variable, holds time without actual game</li>
 *     <li>event: current event: -1:kickoff ; 0:goal ; 1:play ; 2:shoot ; 3:highball ; 4:balllost ; 5:offside
 *     ; 6:corner ; 7:freekick ; 8:penalty ; 9:ballout</li>
 *     <li>football_pitch: pitch of the game</li>
 *     <li>team1: first team</li>
 *     <li>team2: second team</li>
 *     <li>ball: ball of the game</li>
 *     <li>weather: the weather type</li>
 *     <li>condition_modifier: modifier which affects players stamina</li>
 *     <li>rain_modifier: modifier which affects players actions success</li>
 *     <li>referee: referee's severity which affects fouls and cards frequency</li>
 *     <li>stats: match's stats summarising</li>
 * </ul>
 */
public class Match {
    private static final int MATCH_DURATION = 5400;
    Random random = new Random();
    private int time=0;
    private int no_play_time=0;
    private int event=-1; /* -1:kickoff ; 0:goal ; 1:play ; 2:shoot ; 3:highball ; 4:balllost ; 5:offside
    ; 6:corner ; 7:freekick ; 8:penalty ; 9:ballout */
    private Pitch football_pitch;
    Team team1;
    Team team2;
    private Ball ball;
    Team[] teams;
    private int weather;
    private double condition_modifier = 1;
    private int rain_modifier = 0;
    private int referee = 0;
    private MatchStats stats;

    /**
     * The constructor of the class football.Match. It creates a pitch for the game, two teams and the ball.
     */
    public Match(String t1, String t2, int mental1, int mental2, int motiv1, int motiv2, int referee, int weather){
        this.football_pitch = new Pitch(5,6);
        this.team1 = new Team(football_pitch,1, t1,mental1,motiv1);
        this.team2 = new Team(football_pitch,2, t2,mental2,motiv2);
        this.ball = new Ball();
        this.teams = new Team[]{team1, team2};
        this.weather = weather;
        influenceWeather();
        this.referee = referee;
        this.stats = new MatchStats(this);
    }

    /**
     * This method makes modification basing on weather.
     */
    public void influenceWeather(){
        switch (weather){
            case 0:
                break;
            case 1:
                condition_modifier=1.25;
                break;
            case 2:
                rain_modifier=1;
                break;
        }
    }

    /**
     * Method simulate simulates the match. The match won't be simulated if some team doesn't exist.
     * The match is divided into two halfs.
     * After each half there is saving match stats into the text file.
     * During second half there might be substitutions.
     */
    public void simulate(){
        if(!team1.team_exists || !team2.team_exists) return;
        while(time<(MATCH_DURATION/2)) situation();
        time=(MATCH_DURATION/2);
        event=-1;
        stats.stats_to_file(this);
        for(Team team: teams){
            for(int i=0; i<team.getLineup().size(); i++){
                if((team.getLineup().get(i).getStamina()+15)>100) team.getLineup().get(i).setStamina(100);
                else team.getLineup().get(i).setStamina(team.getLineup().get(i).getStamina()+10);
            }
        }
        while(time<MATCH_DURATION){
            for(Team team: teams){
                for(int i=0; i<team.getLineup().size(); i++){
                    if(team.getLineup().get(i).getStamina()<25){
                        if(team.getBench().get(i).getStamina()==100){
                            team.substitution(team.getLineup().get(i));
                        }
                    }
                    if(ball.getOwner()==team.getBench().get(i)){
                        ball.setOwner(team.getLineup().get(i));
                        team.getBench().get(i).player_get_ball(false);
                        team.getLineup().get(i).player_get_ball(true);
                    }
                }
            }
            situation();
        }
        System.out.println(team1.getName()+" "+team1.getStats().getGoals()+":"+team2.getStats().getGoals()+" "+team2.getName());
        stats.stats_to_file(this);
    }

    /**
     * Method situation is the single action during the game. There are 3 phases of situation.
     * First: players without ball are moving.
     * Second: player with ball is making his action (pass, shoot etc.)
     * Third: one player from opposite team might react (defender can try to tackle, goalkeeper to save)
     */
    private void situation(){
        int help_time = time;
        display_time();
        switch(event){
            case -1:
                kick_off();
                time = time+3;
                no_play_time +=3;
                break;
            case 0:
                System.out.println(team1.getName()+" "+team1.getStats().getGoals()+":"+team2.getStats().getGoals()+" "+team2.getName());
                time = time+60;
                no_play_time += 60;
                event = -1;
                break;
            case 1:
                time = time+3;
                teams[ball.getTeam()-1].getStats().setBall_time(teams[ball.getTeam()-1].getStats().getBall_time()+3);
                moving();
                action();
                players_react();
                if(event!=1){
                    time += 15;
                    no_play_time += 15;
                }
                break;
            case 6:
                time += 3;
                teams[ball.getTeam()-1].getStats().setBall_time(teams[ball.getTeam()-1].getStats().getBall_time()+3);
                for(Team team: teams) set_piece_prep(team, team.getCorners_taker());
                for(Team team: teams) set_piece(team, team.getCorners_taker());
                players_react();
                break;
            case 7:
                for(Team team: teams) team.red_player();
                time += 3;
                teams[ball.getTeam()-1].getStats().setBall_time(teams[ball.getTeam()-1].getStats().getBall_time()+3);
                for(Team team:teams){
                    int modY = 0;
                    if(team.getNumber()==2) modY= 5;
                    if(team.getNumber()==ball.getTeam()) if(Math.abs(ball.getOwner().getPlace().getLength()-modY)<=1) for(Team team1: teams) set_piece_prep(team1, team1.getFreekicks_taker());
                }
                for(Team team: teams) set_piece(team, ball.getOwner());
                players_react();
                break;
            case 8:
                for(Team team: teams) team.red_player();
                time += 3;
                teams[ball.getTeam()-1].getStats().setBall_time(teams[ball.getTeam()-1].getStats().getBall_time()+3);
                set_penalty();
                players_react();
                break;
        }
        teams[ball.getTeam()-1].getStats().setTotal_time(time-no_play_time);
        teams[ball.getTeam()-1].getStats().caculate_possession();
        for(Team team: teams){
            for(int i=0; i<team.getLineup().size(); i++){
                team.getLineup().get(i).getStats().addSeconds(time-help_time);
                team.getLineup().get(i).getStats().calculateMinutes();
            }
        }
    }

    /**
     * Method kick_off happens after goals and at the beginning of each half. Players are set up at their
     * starting fields and the ball is for team who has lost a goal.
     */
    private void kick_off(){
        System.out.println("KICK OFF");
        team1.set_default_lineup();
        team2.set_default_lineup();
        for(Team team: teams){
            for(int i=1; i<team.getLineup().size(); i++){
                team.getLineup().get(i).player_get_ball(false);
            }
        }
        if(time==0 || ball.getTeam()==2){
            team1.getLineup().get(team1.getLineup().size()-2).player_get_ball(true);
            ball.setOwner(team1.getLineup().get(team1.getLineup().size()-2));
            ball.setTeam(team1.getLineup().get(team1.getLineup().size()-2).team_number);
        }
        else if(time==(MATCH_DURATION/2) || ball.getTeam()==1){
            team2.getLineup().get(team2.getLineup().size()-2).player_get_ball(true);
            ball.setOwner(team2.getLineup().get(team2.getLineup().size()-2));
            ball.setTeam(team2.getLineup().get(team2.getLineup().size()-2).team_number);
        }
        event = 1;
    }

    /**
     * This method displays current time.
     */
    private void display_time(){
        int min = time/60;
        int sec = time%60;
        System.out.print(String.format("%02d:%02d ",min,sec));
    }

    /**
     * Method moving makes every player without ball moves.
     */
    private void moving(){
        for(Team team: teams){
            for(int i=1; i<team.getLineup().size(); i++){
                if(!team.getLineup().get(i).ball_possessed){
                    team.getLineup().get(i).player_moving(football_pitch, ball.getX(), ball.getY(), ball.getTeam(), condition_modifier);
                }
            }
        }
    }

    /**
     * Method action makes an action of player who has the ball.
     */
    private void action(){
        for(Team team: teams){
            if(team.getLineup().getFirst().ball_possessed){
                if(team.getLineup().getFirst() instanceof Goalkeeper){
                    event = team.getLineup().getFirst().decision_ball(football_pitch,ball,team,event,condition_modifier,rain_modifier);
                    break;
                }
            }
            for(int j=1; j<team.getLineup().size(); j++){
                if(team.getLineup().get(j).ball_possessed){
                    event = team.getLineup().get(j).decision_ball(football_pitch,ball,team,event,condition_modifier,rain_modifier);
                    break;
                }
            }
        }
    }

    /**
     * Method players_react makes one of the opponent team player's reaction. It might be tackle
     * or interception, or goalkeepers save if it's a shoot.
     */
    private void players_react(){
        for(Team team: teams){
            if(team.getNumber()!=ball.getOwner().team_number){
                if((event==2 || event==9)&& team.getLineup().getFirst() instanceof Goalkeeper){
                    event = ((Goalkeeper)team.getLineup().getFirst()).decision_no_ball(event,ball.getOwner());
                    return;
                }
                List<Player> available = new ArrayList<>();
                int dist = 0;
                do {
                    for(int i=1; i<team.getLineup().size(); i++){
                        if(Math.abs(team.getLineup().get(i).getPlace().getWidth()-ball.getX())<=dist && Math.abs(team.getLineup().get(i).getPlace().getLength()-ball.getY())<=dist){
                            available.add(team.getLineup().get(i));
                        }
                    }
                    dist++;
                }while(available.isEmpty());
                int play = random.nextInt(available.size());
                event = available.get(play).decision_no_ball(event,team,ball,condition_modifier,referee,rain_modifier);
                return;
            }
        }
    }

    /**
     * Method set_piece makes a set piece taker makes a set piece.
     * @param team team
     * @param player player
     */
    private void set_piece(Team team, Player player){
        if(team.getNumber()==ball.getTeam()){
            event = player.decision_ball(football_pitch,ball,team,event,condition_modifier,rain_modifier);
        }
    }

    /**
     * Method set_piece_prep makes both teams prepare for the corner or free kick if it's necessary.
     * @param team team
     * @param player player
     */
    private void set_piece_prep(Team team, Player player){
        if(team.getNumber()==ball.getTeam()){
            ball.getOwner().player_get_ball(false);
            ball.setOwner(player);
            player.player_get_ball(true);
        }
        team.set_corner_lineup(event);
    }

    /**
     * Method set_penalty makes a penalty in game.
     */
    private void set_penalty(){
        for(Team team: teams){
            team.set_penalty_lineup();
            if(team.getNumber()==ball.getTeam()){
                ball.setOwner(team.getPenalties_taker());
                team.getPenalties_taker().player_get_ball(true);
                ball.setX(2);
                if(team.getNumber()==2) ball.setY(5);
                else ball.setY(0);
                team.getPenalties_taker().setPlace(football_pitch,ball.getX(), ball.getY());
                event = team.getPenalties_taker().decision_ball(football_pitch,ball,team,event,condition_modifier,rain_modifier);
            }
        }
    }
    public int getTime(){
        return time;
    }
}




