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
    protected Field place;
    boolean ball_possessed;
    int team_number;

    public Player(String name, String surname, int side, int shooting, int dribbling, int speed, int passing, int defending, int heading, int aggression, int risk_taking, int intelligence, int team_number){
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
        this.team_number = team_number;
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
        if(place.getWidth()==Math.abs(side-modifierx)){
            int choice = random.nextInt(3);
            if(choice==0) newWidth++;
            else if(choice==1) newWidth--;
        }
        else if(place.getWidth()>Math.abs(side-modifierx)){
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

    public void player_shooting(Ball ball){
        player_get_ball(false);
        Random random = new Random();
        int target = random.nextInt(2);
        if(target==0){
            ball.x = 2;
            if(team_number==2) ball.y=5;
            else ball.y=0;
            System.out.println(surname+" shoots...");
        }
        else{
            System.out.println(surname+" shoots, but he misses");
        }
    }

    public Player recipient(Team team, int j){
        Random random = new Random();
        int index;
        do {
            index = (random.nextInt(10))+1;
        }while(index==j);
        return team.lineup.get(index);
    }

    public void player_passing(Player recipient, Ball ball){
        player_get_ball(false);
        recipient.player_get_ball(true);
        ball.x = recipient.getPlace().getWidth();
        ball.y = recipient.getPlace().getLength();
        System.out.println(surname+" passes to "+recipient.surname);
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
            ball.x = getPlace().getWidth();
            ball.y = getPlace().getLength();
        }
        System.out.println(surname+" dribbles");
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