package football;

import java.util.Random;

/**
 * Class representing the goalkeeper in a game.
 * This class extends Player class.
 * Goalkeeper has an extra method which allows him to save shoots.
 */
public class Goalkeeper extends Player{

    Random random = new Random();

    /**
     * Constructor of class Goalkeeper with the specified attributes.
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
    public Goalkeeper(String name, String surname, int side, int shooting, int dribbling, int passing, int defending, int aggression, int goalkeeping, int team_number, StatsTeam stats, int mentality, int motivation) {
        super(name, surname, side, shooting, dribbling, passing, defending, aggression, goalkeeping, team_number, stats, mentality,motivation);
    }

    /**
     * Method decision_ball of goalkeeper.
     * When goalkeeper has the ball, he can only pass the ball.
     * @param pitch the pitch of the game
     * @param ball the ball
     * @param team the team of the player who makes a decision
     * @param event the event
     * @param con the condition modifier
     * @param rain the rain modifier
     * @return new event which will happen
     */
    @Override
    public int decision_ball(Pitch pitch, Ball ball, Team team, int event, double con, int rain){
        ball.setOwner(this);
        event = player_passing(recipient(team), ball, event,con,rain);
        return event;
    }

    public int decision_no_ball(int event, Player shooter){
        if(event==2){
            event = player_saving(shooter, event);
        }
        else if(event==9){
            shooter.player_get_ball(false);
            player_get_ball(true);
            event = 1;
        }
        return event;
    }

    public int player_saving(Player shooter, int event){
        int modX=0; int modY=0;
        if(shooter.team_number==2){
            modX=4; modY=5;
        }
        int success = random.nextInt(100);
        double ability = (((double) getAttributes().getGoalkeeping()/20)*100);
        if(Math.abs(shooter.getPlace().getLength()-modY)==0 && Math.abs(shooter.getPlace().getWidth()-modX)==2){
            ability = 80+(((double)getAttributes().getGoalkeeping()-15)*2)+(((double)shooter.getAttributes().getShooting()-15)*2);
        }
        else if(Math.abs(shooter.getPlace().getLength()-modY)==0 && Math.abs(shooter.getPlace().getWidth()-modX)!=2){
            ability = 85+(((double)getAttributes().getGoalkeeping()-15)*1.5)+(((double)shooter.getAttributes().getShooting()-15)*1.5);
        }
        else if(Math.abs(shooter.getPlace().getLength()-modY)==1){
            ability = 90+(((double)getAttributes().getGoalkeeping()-15))+(((double)shooter.getAttributes().getShooting()-15));
        }
        if(success<(ability/2)){
            System.out.println(surname+" catches the shot.");
            shooter.player_get_ball(false);
            player_get_ball(true);
            getStats().addSave();
            event = 1;
        }
        else if(success<ability){
            System.out.println(surname+" saves the shot. Corner!");
            shooter.player_get_ball(false);
            player_get_ball(false);
            getStats().addSave();
            shooter.getTeam_stats().addCorner();
            event = 6;
        }
        else{
            System.out.println(shooter.surname+" scores a goal!");
            shooter.player_get_ball(false);
            shooter.getStats().addGoals();
            shooter.getTeam_stats().addGoal();
            event = 0;
        }
        return event;
    }
}