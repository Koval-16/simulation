import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Player {
    Random random = new Random();
    private StatsPlayer stats;
    private AttributesPlayer attributes;
    protected final String name;
    protected final String surname;
    int stamina;
    protected Field place;
    protected boolean ball_possessed;
    protected int team_number;

    public Player(String name, String surname, int side, int shooting, int dribbling, int speed, int passing,
                  int defending, int heading, int aggression, int risk_taking, int intelligence, int team_number){
        this.name = name;
        this.surname = surname;
        this.ball_possessed = false;
        this.stats = new StatsPlayer();
        this.attributes = new AttributesPlayer(side, shooting, dribbling, speed, passing, defending, heading,
                aggression, risk_taking, intelligence);
        this.team_number = team_number;
    }

    public int decision_ball(Pitch pitch, Ball ball, Team team, int event){
        if(event==-1){}
        else if(event==0){}
        else if(event==1){
            int modx = 0;
            if(team_number==2) modx=5;
            if(Math.abs(getPlace().getLength()-modx)==0 && getPlace().getWidth()>0 && getPlace().getWidth()<4){
                int action = random.nextInt(10);
                if(action<2) player_dribbling(pitch, ball);
                else if(action<6) event=player_shooting(ball, event);
                else event=player_passing(recipient(team), ball, event);
            }
            else if(Math.abs(getPlace().getLength()-modx)==1 && getPlace().getWidth()>0 && getPlace().getWidth()<4){
                int action = random.nextInt(10);
                if(action<4) player_dribbling(pitch, ball);
                else if(action<6) event=player_shooting(ball, event);
                else event=player_passing(recipient(team), ball, event);
            }
            else{
                int action = random.nextInt(10);
                if(action<3) player_dribbling(pitch, ball);
                else event=player_passing(recipient(team), ball, event);
            }
        }
        else if(event==2){}
        else if(event==3){}
        else if(event==4){}
        else if(event==5){}
        else if(event==6){}
        else if(event==7){
            event=player_freekick(ball, event, team);
        }
        else if(event==8){
            event=player_penalty(ball, event);
        }

        return event;
    }

    public int decision_no_ball(int event, Team team, Ball ball){
        if(event==-1){}
        else if(event==0){}
        else if(event==1){
            if(getPlace().getWidth()==ball.getOwner().getPlace().getWidth() &&
                    getPlace().getLength()==ball.getOwner().getPlace().getLength()){
                int action = random.nextInt(2);
                if(action==0) event = player_tackling(ball.getOwner(),ball,event);
            }
        }
        else if(event==2){}
        else if(event==3){}
        else if(event==4){
            event = player_intercepting(ball, event);
        }
        else if(event==5){}
        else if(event==6){}
        else if(event==7){}
        else if(event==8){}
        return event;
    }
    public Field getPlace(){
        return place;
    }

    public void setPlace(Pitch pitch, int width, int length){
        place = pitch.getPitch()[width][length];
    }

    public void player_moving(Pitch pitch, int ball_X, int ball_Y, int ball_team){
        int newWidth = place.getWidth();
        Random random = new Random();
        int modifierx = 0;
        if(team_number==2) modifierx=4;
        if(place.getWidth()==Math.abs(attributes.getSide()-modifierx)){
            int choice = random.nextInt(3);
            if(choice==0) newWidth++;
            else if(choice==1) newWidth--;
        }
        else if(place.getWidth()>Math.abs(attributes.getSide()-modifierx)){
            int choice = random.nextInt(2);
            if(choice==0) newWidth--;
        }
        else{
            int choice = random.nextInt(2);
            if(choice==0) newWidth++;
        }
        if(newWidth>=0 && newWidth<5 && place.getLength()>=0 && place.getLength()<6){
            place = pitch.getPitch()[newWidth][place.getLength()];
        }
    }

    public void player_get_ball(boolean ball_possesed){
        this.ball_possessed = ball_possesed;
    }

    public int player_shooting(Ball ball, int event){
        int success = (random.nextInt(100))+1;
        float ability = ((float)attributes.getShooting()/20)*100;
        if(success<ability){
            ball.setX(2);
            if(team_number==2) ball.setY(5);
            else ball.setY(0);
            System.out.println(surname+" shoots...");
            event = 2;
            return event;
        }
        else{
            System.out.println(surname+" shoots, but he misses");
            event = 9;
            return event;
        }
    }

    public Player recipient(Team team){
        List<List<Player>> distances = new ArrayList<>();
        for(int i=0;i<3;i++){
            distances.add(new ArrayList<>());
        }
        for (int i=1; i<11; i++){
            if(team.getLineup().get(i)!=this){
                if(team.getLineup().get(i).getPlace().getWidth()==place.getWidth()
                        && team.getLineup().get(i).getPlace().getLength()==place.getLength()){
                    distances.get(0).add(team.getLineup().get(i));
                }
                else if(Math.abs(team.getLineup().get(i).getPlace().getLength()-place.getLength())<=1
                && Math.abs(team.getLineup().get(i).getPlace().getWidth()-place.getWidth())<=1){
                    distances.get(1).add(team.getLineup().get(i));
                }
                else distances.get(2).add(team.getLineup().get(i));
            }
        }
        Random random = new Random();
        int distance;
        do {
            int prob = random.nextInt(10);
            if(prob<6) distance=0;
            else if(prob<9) distance=1;
            else distance=2;
        }while(distances.get(distance).isEmpty());
        int index = random.nextInt(distances.get(distance).size());
        return distances.get(distance).get(index);
    }

    public int player_passing(Player recipient, Ball ball, int event){
        int success = (random.nextInt(100))+1;
        float ability = ((float)attributes.getPassing()/20)*100;
        if(success<ability){
            player_get_ball(false);
            recipient.player_get_ball(true);
            ball.setOwner(recipient);
            ball.setX(recipient.getPlace().getWidth());
            ball.setY(recipient.getPlace().getLength());
            System.out.println(surname+" passes to "+recipient.surname);
            event = 1;
        }
        else {
            player_get_ball(false);
            ball.setX(recipient.getPlace().getWidth());
            ball.setY(recipient.getPlace().getLength());
            System.out.println(surname+" passes, but he misses!");
            event = 4;
        }
        return event;
    }

    public void player_dribbling(Pitch pitch, Ball ball){
        Random random = new Random();
        int choice = random.nextInt(3);
        int newWidth = place.getWidth();
        int newLength = place.getLength();
        if(choice==0) newWidth++;
        else if(choice==1) newWidth--;
        choice = random.nextInt(3);
        if(choice==0) newLength++;
        else if(choice==1) newLength--;
        if(newWidth>=0 && newWidth<5 && newLength>=0 && newLength<6) {
            place = pitch.getPitch()[newWidth][newLength];
            ball.setX(getPlace().getWidth());
            ball.setY(getPlace().getLength());
        }
        System.out.println(surname+" dribbles");
    }

    public int player_tackling(Player opponent, Ball ball, int event){
        Random random = new Random();
        int chance = random.nextInt(100);
        if(chance<25){
            System.out.println(surname+" makes a tackle! "+opponent.surname+" loses possession.");
            opponent.player_get_ball(false);
            player_get_ball(true);
            ball.setOwner(this);
            ball.setTeam(team_number);
            event = 1;
        }
        else if(chance<50){
            int mod = 0;
            if(team_number==2) mod=5;
            if(getPlace().getWidth()<=3 && getPlace().getWidth()>=1 && getPlace().getLength()==mod){
                System.out.println(surname+" fouls in the box! Penalty!");
                opponent.player_get_ball(false);
                event = 8;
            }
            else{
                System.out.println(surname+" fouls! Free kick!");
                opponent.player_get_ball(false);
                event = 7;
            }
        }
        else{
            System.out.println(surname+" tries to make a tackle but he isn't successful!");
            event = 1;
        }
        return event;
    }

    public void player_crossing(){
    }

    public void player_heading(){
    }

    public int player_intercepting(Ball ball, int event){
        ball.setOwner(this);
        ball.setTeam(team_number);
        player_get_ball(true);
        event = 1;
        System.out.println(surname+" takes over the ball!");
        return event;
    }

    public int player_penalty(Ball ball, int event){
        int success = (random.nextInt(100))+1;
        float ability = ((float)attributes.getShooting()/20)*100;
        if(success<ability){
            ball.setX(2);
            if(team_number==2) ball.setY(5);
            else ball.setY(0);
            System.out.println(surname+" shoots...");
            event = 2;
            return event;
        }
        else{
            System.out.println(surname+" shoots, but he misses");
            event = 9;
            return event;
        }
    }

    public int player_freekick(Ball ball, int event, Team team){
        int mod = 0;
        if(team_number==2) mod=5;
        if(Math.abs(getPlace().getLength()-mod)<=1){
            int choice = random.nextInt(2);
            if(choice==0) event = player_shooting(ball, event);
            else event = player_passing(recipient(team), ball, event);
        }
        else{
            event = player_passing(recipient(team), ball, event);
        }
        return event;
    }

    public AttributesPlayer getAttributes(){
        return attributes;
    }



}