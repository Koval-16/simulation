import java.util.Random;

public class Goalkeeper extends Player{

    Random random = new Random();
    public Goalkeeper(String name, String surname, int side, int shooting, int dribbling, int speed, int passing,
                      int defending, int heading, int aggression, int risk_taking, int intelligence, int team_number, StatsTeam stats) {
        super(name, surname, side, shooting, dribbling, speed, passing, defending,
                heading, aggression, risk_taking, intelligence, team_number, stats);
    }

    @Override
    public int decision_ball(Pitch pitch, Ball ball, Team team, int event){
        event = player_passing(recipient(team), ball, event);
        return event;
    }

    public int decision_no_ball(int event, Player shooter){
        if(event==-1){}
        else if(event==0){}
        else if(event==1){}
        else if(event==2){
            event = player_saving(shooter, event);
        }
        else if(event==3){}
        else if(event==4){}
        else if(event==5){}
        else if(event==6){}
        else if(event==7){}
        else if(event==8){}
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
        float ability = (((float)getAttributes().getDefending()/20)*100);
        if(Math.abs(shooter.getPlace().getLength()-modY)==0 && Math.abs(shooter.getPlace().getWidth()-modX)==2){
            ability = 80+(((float)getAttributes().getDefending()-15)*2)+(((float)shooter.getAttributes().getShooting()-15)*2);
        }
        else if(Math.abs(shooter.getPlace().getLength()-modY)==0 && Math.abs(shooter.getPlace().getWidth()-modX)!=2){
            ability = 85+(((float)getAttributes().getDefending()-15)*1.5f)+(((float)shooter.getAttributes().getShooting()-15)*1.5f);
        }
        else if(Math.abs(shooter.getPlace().getLength()-modY)==1){
            ability = 90+(((float)getAttributes().getDefending()-15))+(((float)shooter.getAttributes().getShooting()-15));
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