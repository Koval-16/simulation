/**
 * Class <code>Match</code> it's the match.
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Match {

    Random random = new Random();
    private int time=0;
    private int event=-1; /* -1:kickoff ; 0:goal ; 1:play ; 2:shoot ; 3:highball ; 4:balllost ; 5:offside
    ; 6:corner ; 7:freekick ; 8:penalty ; 9:ballout */
    Pitch football_pitch;
    Team team1;
    Team team2;
    Ball ball;
    Team[] teams;

    /**
     * The constructor of the class Match. It creates a pitch for the game, two teams and the ball.
     */
    public Match(){
        this.football_pitch = new Pitch(5,6);
        this.team1 = new Team(football_pitch,1);
        this.team2 = new Team(football_pitch,2);
        this.ball = new Ball();
        this.teams = new Team[]{team1, team2};
    }

    /**
     * Method <code>simulate</code> simulates the match. The game is divided into two halfs. Time represents the
     * game time in seconds. Each half lasts 45 min = 2700 sec.
     */
    public void simulate(){
        while(time<2700){
            half();
        }
        time=2700;
        event=-1;
        for(Team team: teams){
            for(int i=0; i<11; i++){
                if((team.getLineup().get(i).getStamina()+15)>100){
                    team.getLineup().get(i).setStamina(100);
                }
                else{
                    team.getLineup().get(i).setStamina(team.getLineup().get(i).getStamina()+10);
                }
            }
        }
        while(time<5400){
            for(Team team: teams){
                for(int i=0; i<11; i++){
                    if(team.getLineup().get(i).getStamina()<25){
                        team.substitution(team.getLineup().get(i));
                    }
                    if(ball.getOwner()==team.getBench().get(i)){
                        ball.setOwner(team.getLineup().get(i));
                        team.getBench().get(i).player_get_ball(false);
                        team.getLineup().get(i).player_get_ball(true);
                    }
                }
            }
            half();
        }
        System.out.println(team1.getName()+" "+team1.getStats().getGoals()+":"+team2.getStats().getGoals()+" "+team2.getName());
    }

    /**
     * Method <code>half</code> checks which event currently happens, and depending on it certain actions take place.
     */
    private void half(){
        display_time();
        if(event==-1){
            kick_off();
            time = time+3;
        }
        else if(event==0){
            System.out.println(team1.getName()+" "+team1.getStats().getGoals()+":"+team2.getStats().getGoals()+" "+team2.getName());
            time = time+60;
            event = -1;
        }
        else if (event==1){
            moving();
            action();
            players_react();
            time = time+3;
        }
        else if(event==6){
            for(Team team: teams){
                set_piece_prep(team, team.getCorners_taker());
            }
            for(Team team: teams){
                set_piece(team, team.getCorners_taker());
            }
            players_react();
            time = time+3;
        }
        else if(event==7){
            for(Team team: teams){
                set_piece_prep(team, team.getFreekicks_taker());
            }
            for(Team team: teams){
                set_piece(team, team.getFreekicks_taker());
            }
            players_react();
            time = time+3;
        }
        else if(event==8){
            set_penalty();
            players_react();
            time = time+3;
        }
        for(Team team: teams){
            for(int i=0; i<11; i++){
                team.getLineup().get(i).getStats().addMinutes(time);
            }
        }
    }

    /**
     * Method <code>kick_off</code> happens after goals and at the beginning of each half. Players are set up at their
     * starting fields and the ball is for team who has lost a goal.
     */
    private void kick_off(){
        System.out.println("KICK OFF");
        team1.set_default_lineup();
        team2.set_default_lineup();
        for(int i=1; i<11; i++){
            for(Team team: teams){
                team.getLineup().get(i).player_get_ball(false);
            }
        }
        if(time==0){
            team1.getLineup().get(9).player_get_ball(true);
            ball.setOwner(team1.getLineup().get(9));
            ball.setTeam(team1.getLineup().get(9).team_number);
        }
        else if(time==2700){
            team2.getLineup().get(9).player_get_ball(true);
            ball.setOwner(team2.getLineup().get(9));
            ball.setTeam(team2.getLineup().get(9).team_number);
        }
        else if(ball.getTeam()==2){
            team1.getLineup().get(9).player_get_ball(true);
            ball.setOwner(team1.getLineup().get(9));
            ball.setTeam(team1.getLineup().get(9).team_number);
        }
        else if(ball.getTeam()==1){
            team2.getLineup().get(9).player_get_ball(true);
            ball.setOwner(team2.getLineup().get(9));
            ball.setTeam(team2.getLineup().get(9).team_number);
        }

        event = 1;
    }

    private void display_time(){
        int min = time/60;
        int sec = time%60;
        System.out.print(String.format("%02d:%02d ",min,sec));
    }

    /**
     * Method <code>moving</code> moves every player who doesn't have a ball.
     */
    private void moving(){
        for(Team team: teams){
            for(int i=1; i<11; i++){
                if(!team.getLineup().get(i).ball_possessed){
                    team.getLineup().get(i).player_moving(football_pitch, ball.getX(), ball.getY(), ball.getTeam());
                }
            }
        }
    }

    /**
     * Method <code>action</code> makes an action of player who has the ball.
     */
    private void action(){
        for(Team team: teams){
            if(team.getLineup().get(0).ball_possessed){
                if(team.getLineup().get(0) instanceof Goalkeeper){
                    event = team.getLineup().get(0).decision_ball(football_pitch,ball,team,event);
                    break;
                }
            }
            for(int j=1; j<11; j++){
                if(team.getLineup().get(j).ball_possessed){
                    event = team.getLineup().get(j).decision_ball(football_pitch,ball,team,event);
                    break;
                }
            }
        }
    }

    /**
     * Method <code>players_react</code> makes one of the opponent team player's reaction. It might be tackle
     * or interception, or goalkeepers save if it's a shoot.
     */
    private void players_react(){
        for(Team team: teams){
            if(team.getNumber()!=ball.getOwner().team_number){
                if((event==2 || event==9)&& team.getLineup().get(0) instanceof Goalkeeper){
                    event = ((Goalkeeper)team.getLineup().get(0)).decision_no_ball(event,ball.getOwner());
                    return;
                }
                List<Player> available = new ArrayList<>();
                int dist = 0;
                do {
                    for(int i=1; i<11; i++){
                        if(Math.abs(team.getLineup().get(i).getPlace().getWidth()-ball.getX())<=dist &&
                                Math.abs(team.getLineup().get(i).getPlace().getLength()-ball.getY())<=dist){
                            available.add(team.getLineup().get(i));
                        }
                    }
                    dist++;
                }while(available.isEmpty());
                if(!available.isEmpty()){
                    int play = random.nextInt(available.size());
                    event = available.get(play).decision_no_ball(event,team,ball);
                }
                return;
            }
        }
    }

    private void set_piece(Team team, Player player){
        if(team.getNumber()==ball.getTeam()){
            event = player.decision_ball(football_pitch,ball,team,event);
        }
    }
    private void set_piece_prep(Team team, Player player){
        if(team.getNumber()==ball.getTeam()){
            ball.setOwner(player);
            player.player_get_ball(true);
        }
        team.set_corner_lineup(event);
    }
    
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
                event = team.getPenalties_taker().decision_ball(football_pitch,ball,team,event);
            }
        }
    }
}




