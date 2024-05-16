import java.util.Random;

public class Player {
    StatsPlayer stats;
    String name;
    String surname;
    int side;
    int shooting;
    int dribbling;
    int speed;
    int passing;
    int defending;
    int heading;
    int aggression;
    int risk_taking;
    int intelligence;
    int stamina;
    private Field place;
    boolean ball_possessed;

    public Player(String name, String surname, int side, int shooting, int dribbling, int speed, int passing, int defending, int heading, int aggression, int risk_taking, int intelligence){
        this.side = side;
        this.name = name;
        this.surname = surname;
        this.ball_possessed = false;
        this.stats = new StatsPlayer();
        this.shooting = shooting;
        this.dribbling = dribbling;
        this.speed = speed;
        this.passing = passing;
        this.defending = defending;
        this.heading = heading;
        this.aggression = aggression;
        this.risk_taking = risk_taking;
        this.intelligence = intelligence;
    }

    public Field getPlace(){
        return place;
    }

    public void setPlace(Pitch pitch, int width, int length){
        place = pitch.getPitch()[width][length];
    }

    public void player_moving(Pitch pitch){
        Random random = new Random();
        int choice = random.nextInt(3);
        int newWidth = place.getWidth();
        int newLength = place.getLength();
        if(choice==0) newWidth++;
        else if(choice==1) newWidth--;
        choice = random.nextInt(3);
        if(choice==0) newLength++;
        else if(choice==1) newLength--;
        if(newWidth>=0 && newWidth<5 && newLength>=0 && newLength<6){
            place = pitch.getPitch()[newWidth][newLength];
        }
    }

    public void player_get_ball(boolean ball_possesed){
        this.ball_possessed = ball_possesed;
    }

    public void player_shooting(){
    }

    public void player_passing(){
    }

    public void player_dribbling(){
    }

    public void player_tackling(){
    }

    public void player_crossing(){
    }

    public void player_heading(){
    }

    public void player_intercepting(){
    }

    public void player_penalty(){
    }

    public void player_freekick(){
    }



}