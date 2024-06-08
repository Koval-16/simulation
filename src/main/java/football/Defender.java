package football;

/**
 * Class representing Defender in a game.
 * This class extends {@link Player} class.
 */
public class Defender extends Player{
    /**
     * Constructor of class Defender with the specified attributes.
     * @param name name of the player
     * @param surname surname of the player
     * @param side the side of the player; left(1), center(2) or right(3)
     * @param shooting the shooting ability of the player
     * @param dribbling the dribbling ability of the player
     * @param passing the passing ability of the player
     * @param defending the defending ability of the player
     * @param aggression the aggression of the player
     * @param goalkeeping the goalkeeping ability of the player
     * @param team_number the number of the team player belongs to
     * @param stats the stats of the team player belongs to
     * @param mentality the mentality of the player
     * @param motivation the motivation of the player
     */
    public Defender(String name, String surname, int side, int shooting, int dribbling, int passing, int defending, int aggression, int goalkeeping, int team_number, StatsTeam stats, int mentality, int motivation) {
        super(name, surname, side, shooting, dribbling, passing, defending, aggression, goalkeeping, team_number, stats, mentality, motivation);
    }

    /**
     * Method decision_ball of defender.
     * Defender can perform actions:
     * - passing
     * - dribbling
     * - shooting
     * - free kick
     * - penalty
     * His choice depends on:
     * - event which occurred
     * - place
     * - stamina
     * - mentality
     * @param pitch the pitch of the game
     * @param ball the ball
     * @param team the team of the player who makes a decision
     * @param event the event
     * @param con the condition modifier
     * @param rain the rain modifier
     * @return new event which will happen
     */
    public int decision_ball(Pitch pitch, Ball ball, Team team, int event, double con, int rain){
        switch (event){
            case 1:
                int modY=0;
                if(team_number==2){
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
                break;
            case 6:
                event=player_passing(recipient(team),ball,event,con,rain);
                break;
            case 7:
                event=player_freekick(ball, event, team, con,rain);
                break;
            case 8:
                event=player_penalty(ball, event, con,rain);
                break;
        }
        return event;
    }

    /**
     * Method player_moving for defender.
     * Moving in X-coordinates is common for defenders, midfielders, forwards.
     * Moving in Y-coordinates is specific for other positions.
     * It depends on player's current place, ball place, and what team possesses the ball.
     * @param pitch the pitch of the game
     * @param ball_X ball X-coordinate
     * @param ball_Y ball Y-coordinate
     * @param ball_team team which owns the ball
     * @param con condition modifier
     */
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