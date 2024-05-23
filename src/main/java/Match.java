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

    public Match(){
        this.football_pitch = new Pitch(5,6);
        this.team1 = new Team(football_pitch,1);
        this.team2 = new Team(football_pitch,2);
        this.ball = new Ball();
        this.teams = new Team[]{team1, team2};
    }

    public void simulate(){
        while(time<5400){
            display_time();
            if(event==-1){
                kick_off();
                time = time+3;
            }
            else if(event==0){
                System.out.println();
                time = time+60;
                event = -1;
            }
            else if (event==1){
                moving();
                action();
                players_react();
                time = time+3;
            }
            else if(event==7){
                for(Team team: teams){
                    if(team.getNumber()==ball.getTeam()){
                        ball.setOwner(team.getFreekicks_taker());
                        event = team.getFreekicks_taker().decision_ball(football_pitch,ball,team,event);
                        players_react();
                    }
                }
                time = time+3;
            }
            else if(event==8){
                for(Team team: teams){
                    if(team.getNumber()==ball.getTeam()){
                        ball.setOwner(team.getPenalties_taker());
                        ball.setX(2);
                        if(team.getNumber()==2) ball.setY(5);
                        else ball.setY(0);
                        team.getPenalties_taker().setPlace(football_pitch,ball.getX(), ball.getY());
                        event = team.getPenalties_taker().decision_ball(football_pitch,ball,team,event);
                        players_react();
                    }
                }
                time = time+3;
            }
        }
    }

    private void kick_off(){
        System.out.println("KICK OFF");
        team1.set_default_lineup();
        team2.set_default_lineup();
        for(int i=1; i<11; i++){
            for(Team team: teams){
                team.lineup.get(i).player_get_ball(false);
            }
        }
        team1.lineup.get(9).player_get_ball(true);
        ball.setOwner(team1.lineup.get(9));
        ball.setTeam(team1.lineup.get(9).team_number);
        event = 1;
    }

    private void display_time(){
        int min = time/60;
        int sec = time%60;
        System.out.print(String.format("%02d:%02d ",min,sec));
    }

    private void moving(){
        for(Team team: teams){
            for(int i=1; i<11; i++){
                if(!team.lineup.get(i).ball_possessed){
                    team.lineup.get(i).player_moving(football_pitch, ball.getX(), ball.getY(), ball.getTeam());
                }
            }
        }
    }

    private void action(){
        for(Team team: teams){
            if(team.lineup.get(0).ball_possessed){
                if(team.lineup.get(0) instanceof Goalkeeper){
                    event = team.lineup.get(0).decision_ball(football_pitch,ball,team,event);
                    break;
                }
            }
            for(int j=1; j<11; j++){
                if(team.lineup.get(j).ball_possessed){
                    event = team.lineup.get(j).decision_ball(football_pitch,ball,team,event);
                    break;
                }
            }
        }
    }

    private void players_react(){
        for(Team team: teams){
            if(team.getNumber()!=ball.getOwner().team_number){
                if((event==2 || event==9)&& team.lineup.get(0) instanceof Goalkeeper){
                    event = ((Goalkeeper)team.lineup.get(0)).decision_no_ball(event,ball.getOwner());
                    break;
                }
                List<Player> available = new ArrayList<>();
                int dist = 0;
                do {
                    for(int i=1; i<11; i++){
                        if(Math.abs(team.lineup.get(i).getPlace().getWidth()-ball.getX())<=dist &&
                                Math.abs(team.lineup.get(i).getPlace().getLength()-ball.getY())<=dist){
                            available.add(team.lineup.get(i));
                        }
                    }
                    dist++;
                }while(available.isEmpty());
                if(!available.isEmpty()){
                    int play = random.nextInt(available.size());
                    event = available.get(play).decision_no_ball(event,team,ball);
                }
            }
        }
    }
}




