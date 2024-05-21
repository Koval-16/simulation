import java.util.Random;

public class Match {

    private int time=0;
    private int event;
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
            if(time==0 || time==2700){
                kick_off();

            }
            else{
                moving();
                for(Team team: teams){
                    for(int j=1; j<11; j++){
                        if(team.lineup.get(j).ball_possessed){
                            Random random = new Random();
                            int player_choice = random.nextInt(10);
                            if(player_choice<7){
                                team.lineup.get(j).player_passing(team.lineup.get(j).recipient(team,j),ball);
                            }
                            else{
                                team.lineup.get(j).player_dribbling(football_pitch, ball);
                            }
                            break;
                        }
                    }
                }
            }
            time = time+3;
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
        ball.team = team1.lineup.get(9).team_number;
    }

    private void display_time(){
        int min = time/60;
        int sec = time%60;
        System.out.print(String.format("%02d:%02d",min,sec));
    }

    private void moving(){
        for(Team team: teams){
            for(int i=1; i<11; i++){
                if(!team.lineup.get(i).ball_possessed){
                    team.lineup.get(i).player_moving(football_pitch, ball.x, ball.y, ball.team);
                }
            }
        }
    }



}
