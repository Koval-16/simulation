public class Match {

    private int time=0;
    private int event;
    Pitch football_pitch;
    Team team1;
    Team team2;
    int ball_x, ball_y, ball_team;

    public Match(){
        this.football_pitch = new Pitch(5,6);
        this.team1 = new Team(football_pitch,1);
        this.team2 = new Team(football_pitch,2);
    }

    public void simulate(){
        while(time<5400){
            if(time%60==0){
                for(int i=1; i<11; i++){
                    team1.lineup.get(i).stats.minutes_played++;
                    team2.lineup.get(i).stats.minutes_played++;
                }
            }
            if(time==0 || time==2700){
                kick_off();
                team1.lineup.get(9).player_get_ball(true);
                ball_team = team1.lineup.get(9).team_number;
            }
            else{
                for(int i=1; i<11; i++){
                    if(!team1.lineup.get(i).ball_possessed){
                        team1.lineup.get(i).player_moving(football_pitch, ball_x, ball_y, ball_team);
                    }
                    else{
                        team1.lineup.get(i).player_dribbling(football_pitch);
                        ball_x = team1.lineup.get(i).getPlace().getWidth();
                        ball_y = team1.lineup.get(i).getPlace().getLength();
                    }
                    if(!team2.lineup.get(i).ball_possessed){
                        team2.lineup.get(i).player_moving(football_pitch, ball_x, ball_y, ball_team);
                    }
                    else{
                        team2.lineup.get(i).player_dribbling(football_pitch);
                        ball_x = team2.lineup.get(i).getPlace().getWidth();
                        ball_y = team2.lineup.get(i).getPlace().getLength();
                    }
                }
            }
            System.out.println(team1.lineup.get(8).surname+": "+team1.lineup.get(8).getPlace().getWidth()+"x"+team1.lineup.get(8).getPlace().getLength());
            System.out.println(team2.lineup.get(8).surname+": "+team2.lineup.get(8).getPlace().getWidth()+"x"+team2.lineup.get(8).getPlace().getLength());
            time = time+3;

        }
        System.out.println(team2.lineup.get(8).stats.minutes_played);
    }

    private void kick_off(){
        System.out.println("KICK OFF");
        team1.set_default_lineup();
        team2.set_default_lineup();
    }


}
