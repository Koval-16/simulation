import java.util.Random;

public class Goalkeeper extends Player{

    Random random = new Random();
    public Goalkeeper(String name, String surname, int side, int shooting, int dribbling, int speed, int passing,
                      int defending, int heading, int aggression, int risk_taking, int intelligence, int team_number) {
        super(name, surname, side, shooting, dribbling, speed, passing, defending,
                heading, aggression, risk_taking, intelligence, team_number);
    }

    @Override
    public int decision_ball(Pitch pitch, Ball ball, Team team, int j, int event){
        player_passing(recipient(team, j), ball, event);
        return event;
    }

    public int decision_no_ball(int event, Player shooter){
        if(event==1){
            event = player_saving(shooter, event);
        }
        else if(event==2){
            shooter.player_get_ball(false);
            player_get_ball(true);
            event = 0;
        }
        return event;
    }

    public int player_saving(Player shooter, int event){
        int success = random.nextInt(10);
        if(success<4){
            System.out.println(surname+" catches the shot.");
            shooter.player_get_ball(false);
            player_get_ball(true);
            event = 0;
        }
        else if(success<8){
            System.out.println(surname+" saves the shot.");
            shooter.player_get_ball(false);
            player_get_ball(true);
            event = 0;
        }
        else{
            System.out.println(shooter.surname+" scores a goal!");
            shooter.player_get_ball(false);
            event = -1;
        }
        return event;
    }
}