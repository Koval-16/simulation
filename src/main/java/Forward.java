public class Forward extends Player{
    public Forward(String name, String surname, int side, int shooting, int dribbling, int speed, int passing,
                   int defending, int heading, int aggression, int risk_taking, int intelligence, int team_number,
                   StatsTeam stats) {
        super(name, surname, side, shooting, dribbling, speed, passing,
                defending, heading, aggression, risk_taking, intelligence, team_number, stats);
    }

    public int decision_ball(Pitch pitch, Ball ball, Team team, int event){
        if(event==-1){}
        else if(event==0){}
        else if(event==1){
            int modX=0;
            int modY=0;
            if(team_number==2){
                modX=4;
                modY=5;
            }
            int action = random.nextInt(100);
            if(Math.abs(getPlace().getWidth()-modX)==2 && Math.abs(getPlace().getLength()-modY)==0){
                if(action<75) event=player_shooting(ball,event);
                else if(action<90) event=player_passing(recipient(team),ball,event);
                else player_dribbling(pitch,ball);
            }
            else if((Math.abs(getPlace().getWidth()-modX)==1 || Math.abs(getPlace().getWidth()-modX)==3) && Math.abs(getPlace().getLength()-modY)==0){
                if(action<65) event=player_shooting(ball,event);
                else if(action<85) event=player_passing(recipient(team),ball,event);
                else player_dribbling(pitch,ball);
            }
            else if((Math.abs(getPlace().getWidth()-modX)<=3 && Math.abs(getPlace().getWidth()-modX)>=1) && Math.abs(getPlace().getLength()-modY)==1){
                if(action<25) event=player_shooting(ball,event);
                else if(action<50) event=player_passing(recipient(team),ball,event);
                else if(action<75) player_dribbling(pitch,ball);
                else event=player_crossing(recipient(team),ball,event);
            }
            else if((Math.abs(getPlace().getWidth()-modX)==0 || Math.abs(getPlace().getWidth()-modX)==4) && (Math.abs(getPlace().getLength()-modY)>=0 && Math.abs(getPlace().getLength()-modY)<=1)){
                if(action<35) event=player_passing(recipient(team),ball,event);
                else if(action<75) player_dribbling(pitch,ball);
                else event=player_crossing(recipient(team),ball,event);
            }
            else if((Math.abs(getPlace().getWidth()-modX)<=3 && Math.abs(getPlace().getWidth()-modX)>=1) && (Math.abs(getPlace().getLength()-modY)>=2 && Math.abs(getPlace().getLength()-modY)<=3)){
                if(action<40) event=player_passing(recipient(team),ball,event);
                else if(action<80) player_dribbling(pitch,ball);
                else event=player_crossing(recipient(team),ball,event);
            }
            else if((Math.abs(getPlace().getWidth()-modX)==0 || Math.abs(getPlace().getWidth()-modX)==4) && (Math.abs(getPlace().getLength()-modY)>=2 && Math.abs(getPlace().getLength()-modY)<=3)){
                if(action<40) event=player_passing(recipient(team),ball,event);
                else if(action<80) player_dribbling(pitch,ball);
                else event=player_crossing(recipient(team),ball,event);
            }
            else if((Math.abs(getPlace().getWidth()-modX)<=3 && Math.abs(getPlace().getWidth()-modX)>=1) && Math.abs(getPlace().getLength()-modY)==4){
                if(action<45) event=player_passing(recipient(team),ball,event);
                else if(action<80) player_dribbling(pitch,ball);
                else event=player_crossing(recipient(team),ball,event);
            }
            else if((Math.abs(getPlace().getWidth()-modX)<=3 && Math.abs(getPlace().getWidth()-modX)>=1) && Math.abs(getPlace().getLength()-modY)==5){
                if(action<40) event=player_passing(recipient(team),ball,event);
                else if(action<80) player_dribbling(pitch,ball);
                else event=player_crossing(recipient(team),ball,event);
            }
            else if((Math.abs(getPlace().getWidth()-modX)==0 || Math.abs(getPlace().getWidth()-modX)==4) && (Math.abs(getPlace().getLength()-modY)>=4 && Math.abs(getPlace().getLength()-modY)<=5)){
                if(action<40) event=player_passing(recipient(team),ball,event);
                else if(action<80) player_dribbling(pitch,ball);
                else event=player_crossing(recipient(team),ball,event);
            }
        }
        else if(event==2){}
        else if(event==3){}
        else if(event==4){}
        else if(event==5){}
        else if(event==6){
            event=player_passing(recipient(team),ball,event);
        }
        else if(event==7){
            event=player_freekick(ball, event, team);
        }
        else if(event==8){
            event=player_penalty(ball, event);
        }
        return event;
    }
    public void player_moving(Pitch pitch, int ball_X, int ball_Y, int ball_team){
        super.player_moving(pitch, ball_X, ball_Y, ball_team);
        int modifier = 0;
        int m =1;
        if(team_number==2){
            modifier = 5;
            m = -1;
        }
        int newLength = place.getLength();
        if(ball_team==team_number){
            if(ball_Y==Math.abs(5-modifier) || ball_Y==Math.abs(4-modifier) || ball_Y==Math.abs(3-modifier)){
                if(place.getLength()>ball_Y-2)  newLength--;
                else if(place.getLength()<ball_Y-2) newLength++;
            }
            else if(ball_Y==Math.abs(2-modifier) || ball_Y==Math.abs(1-modifier)){
                if(place.getLength()>ball_Y-1)  newLength--;
                else if(place.getLength()<ball_Y-1) newLength++;
            }
            else if(ball_Y==Math.abs(-modifier)){
                if(place.getLength()>ball_Y)  newLength--;
                else if(place.getLength()<ball_Y) newLength++;
            }
        }
        else {
            if(ball_Y==Math.abs(-modifier) || ball_Y==Math.abs(1-modifier) || ball_Y==Math.abs(2-modifier)){
                if(place.getLength()>ball_Y)  newLength--;
                else if(place.getLength()<ball_Y) newLength++;
            }
            else if(ball_Y==Math.abs(3-modifier) || ball_Y==Math.abs(4-modifier)){
                if(place.getLength()>ball_Y-m*1)  newLength--;
                else if(place.getLength()<ball_Y-m*1) newLength++;
            }
            else if(ball_Y==Math.abs(5-modifier)){
                if(place.getLength()>ball_Y-m*2)  newLength--;
                else if(place.getLength()<ball_Y-m*2) newLength++;
            }
        }
        if(place.getWidth()>=0 && place.getWidth()<5 && newLength>=0 && newLength<6){
            place = pitch.getPitch()[place.getWidth()][newLength];
        }
    }
}