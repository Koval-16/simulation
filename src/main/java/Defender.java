public class Defender extends Player{
    public Defender(String name, String surname, int side, int shooting, int dribbling, int speed, int passing, int defending, int heading, int aggression, int risk_taking, int intelligence, int team_number) {
        super(name, surname, side, shooting, dribbling, speed, passing, defending, heading, aggression, risk_taking, intelligence, team_number);
    }

    public void player_moving(Pitch pitch, int ball_X, int ball_Y, int ball_team){
        super.player_moving(pitch, ball_X, ball_Y, ball_team);
        int newLength = place.getLength();
        if(ball_team==team_number){
            if(ball_Y<=5 && ball_Y>=3){
                if(place.getLength()>ball_Y)  newLength--;
                else if(place.getLength()<ball_Y) newLength++;
            }
            else if(ball_Y==2 || ball_Y==1){
                if(place.getLength()>ball_Y+1)  newLength--;
                else if(place.getLength()<ball_Y+1) newLength++;
            }
            else if(ball_Y==0){
                if(place.getLength()>ball_Y+2)  newLength--;
                else if(place.getLength()<ball_Y+2) newLength++;
            }
        }
        else {
            if(ball_Y>=0 && ball_Y<=2){
                if(place.getLength()>ball_Y+2)  newLength--;
                else if(place.getLength()<ball_Y+2) newLength++;
            }
            else if(ball_Y==3 || ball_Y==4){
                if(place.getLength()>ball_Y+1)  newLength--;
                else if(place.getLength()<ball_Y+1) newLength++;
            }
            else{
                if(place.getLength()<ball_Y) newLength++;
            }
        }
        if(place.getWidth()>=0 && place.getWidth()<5 && newLength>=0 && newLength<6){
            place = pitch.getPitch()[place.getWidth()][newLength];
        }
    }

    public void player_shooting(int x){

    }
}