package football;

public class Defender extends Player{
    public Defender(String name, String surname, int side, int shooting, int dribbling, int passing, int defending,
                    int aggression, int intelligence, int team_number, StatsTeam stats, int mentality, int motivation) {
        super(name, surname, side, shooting, dribbling, passing, defending, aggression, intelligence, team_number,
                stats, mentality, motivation);
    }
    public int decision_ball(Pitch pitch, Ball ball, Team team, int event, double con, int rain){
        if(event==1){
            int modX=0;
            int modY=0;
            if(team_number==2){
                modX=4;
                modY=5;
            }
            double action = random.nextInt(100)-((100-getStamina())/10)+mentality;
            if(Math.abs(getPlace().getLength()-modY)>=2){
                if(action<80) event=player_passing(recipient(team),ball,event,con,rain);
                else player_dribbling(pitch,ball,con);
            }
            else if(getPlace().getWidth()==0 || getPlace().getWidth()==4){
                if(action<70) event=player_passing(recipient(team),ball,event,con,rain);
                else player_dribbling(pitch,ball,con);
            }
            else if(Math.abs(getPlace().getLength()-modY)>=1){
                if(action<70) event=player_passing(recipient(team),ball,event,con,rain);
                else if(action<95) player_dribbling(pitch,ball,con);
                else event=player_shooting(ball,event,con,rain);
            }
            else {
                if(action<80) event=player_passing(recipient(team),ball,event,con,rain);
                else if(action<90) player_dribbling(pitch,ball,con);
                else event=player_shooting(ball,event,con,rain);
            }
        }
        else if(event==6){
            event=player_passing(recipient(team),ball,event,con,rain);
        }
        else if(event==7){
            event=player_freekick(ball, event, team, con,rain);
        }
        else if(event==8){
            event=player_penalty(ball, event, con,rain);
        }
        return event;
    }
    public void player_moving(Pitch pitch, int ball_X, int ball_Y, int ball_team, double con){
        super.player_moving(pitch, ball_X, ball_Y, ball_team, con);
        int modifier = 0;
        int m =1;
        if(team_number==2){
            modifier = 5;
            m = -1;
        }
        int newLength = place.getLength();
        int expY;
        if(ball_team==team_number){
            if(ball_Y==Math.abs(5-modifier) || ball_Y==Math.abs(4-modifier) || ball_Y==Math.abs(3-modifier)) expY=ball_Y;
            else if(ball_Y==Math.abs(2-modifier) || ball_Y==Math.abs(1-modifier)) expY=ball_Y+m;
            else expY=ball_Y+(2*m);
        }
        else{
            if(ball_Y==Math.abs(-modifier) || ball_Y==Math.abs(1-modifier) || ball_Y==Math.abs(2-modifier)) expY=ball_Y+(2*m);
            else if(ball_Y==Math.abs(3-modifier) || ball_Y==Math.abs(4-modifier)) expY=ball_Y+m;
            else expY=ball_Y;
        }
        if(place.getLength()>expY) newLength--;
        else if(place.getLength()<expY) newLength++;
        if(place.getWidth()>=0 && place.getWidth()<5 && newLength>=0 && newLength<6){
            place = pitch.getPitch()[place.getWidth()][newLength];
        }
    }
}