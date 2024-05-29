package football; /**
 * Klasa <code>football.Player</code> reprezentująca piłkarza.
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class Player {
    Random random = new Random();
    private StatsPlayer stats;
    private StatsTeam team_stats;
    private AttributesPlayer attributes;
    protected final String name;
    protected final String surname;
    private double stamina = 100;
    protected Field place;
    protected boolean ball_possessed;
    protected int team_number;

    /**
     * Constructor of class <code>football.Player</code> with his name, surname, and attributes
     * @param name player's name
     * @param surname player's surname
     * @param side player's side, 1=left, 2=center, 3=right
     * @param shooting player's shooting ability
     * @param dribbling player's dribbling ability
     * @param passing player's passing ability
     * @param defending player's defending ability
     * @param aggression player's aggression
     * @param intelligence player's intelligence
     * @param team_number the index of player's team
     */
    public Player(String name, String surname, int side, int shooting, int dribbling,int passing,
                  int defending, int aggression, int intelligence, int team_number,
                  StatsTeam team_stats){
        this.name = name;
        this.surname = surname;
        this.ball_possessed = false;
        this.stats = new StatsPlayer();
        this.attributes = new AttributesPlayer(side, shooting, dribbling, passing, defending, aggression, intelligence);
        this.team_number = team_number;
        this.team_stats = team_stats;
    }

    /**
     * Method <code>decision_ball</code> makes a player's with ball decision
     * @param pitch the pitch of the game
     * @param ball the ball of the game
     * @param team the team of the player
     * @param event the event which occurred
     * @return new event which will happen
     */
    public abstract int decision_ball(Pitch pitch, Ball ball, Team team, int event);

    /**
     * Method <code>decison_no_ball</code> makes a player's with no ball decision
     * @param event the event which occurred
     * @param team the team of the player
     * @param ball the ball of the game
     * @return new event which will happen
     */
    public int decision_no_ball(int event, Team team, Ball ball){
        if(event==-1){}
        else if(event==0){}
        else if(event==1){
            if(getPlace().getWidth()==ball.getOwner().getPlace().getWidth() &&
                    getPlace().getLength()==ball.getOwner().getPlace().getLength()){
                int modY=0;
                if(team_number==2) modY=5;
                int action = random.nextInt(10);
                if(Math.abs(getPlace().getLength()-modY)>=4){
                    if(action<7) event=player_tackling(ball.getOwner(),ball,event);
                }
                else if(Math.abs(getPlace().getLength()-modY)>=2){
                    if(action<5) event=player_tackling(ball.getOwner(),ball,event);
                }
                else{
                    if(action<3) event=player_tackling(ball.getOwner(),ball,event);
                }
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

    /**
     * Method <code>player_moving</code> moves a player without ball. football.Player's movement depends on his position,
     * current ball state (where the ball is, which team owns the ball).
     * @param pitch the pitch of the game
     * @param ball_X X coordinate of the ball
     * @param ball_Y Y coordinate of the ball
     * @param ball_team team which owns the ball
     */
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
        stamina -= 0.02;
    }

    /**
     * Method <code>player_get_ball</code> sets if the player has the ball.
     * @param ball_possesed true=has ball, false=doesn't have ball
     */
    public void player_get_ball(boolean ball_possesed){
        this.ball_possessed = ball_possesed;
    }

    /**
     * Method <code>player_shooting</code> makes player shoots. Shot can be either on-target or missed. It depends on
     * player's shooting ability and distance from the goal.
     * @param ball the ball
     * @param event the event
     * @return the event which will happen, it might be shot on-target or ball out of game
     */
    public int player_shooting(Ball ball, int event){
        int modX=0; int modY=0;
        if(team_number==2){
            modX=4; modY=5;
        }
        int success = (random.nextInt(100))+1;
        float ability;
        if(Math.abs(getPlace().getLength()-modY)==0 && Math.abs(getPlace().getWidth()-modX)==2){
            ability = 65+(((float)attributes.getShooting()-15)*3);
        }
        else if(Math.abs(getPlace().getLength()-modY)==0 && Math.abs(getPlace().getWidth()-modX)!=2){
            ability = 55+(((float)attributes.getShooting()-15)*3);
        }
        else {
            ability = 45+(((float)attributes.getShooting()-15)*3);
        }

        stamina -= 0.5;
        if(success<ability){
            ball.setX(2);
            if(team_number==2) ball.setY(5);
            else ball.setY(0);
            System.out.println(surname+" shoots...");
            event = 2;
            stats.addShoots();
            stats.addShootOnTarget();
            team_stats.addShoot();
            team_stats.addShootOnTarget();
            return event;
        }
        else{
            System.out.println(surname+" shoots, but he misses");
            event = 9;
            stats.addShoots();
            team_stats.addShoot();
            return event;
        }
    }

    /**
     * Method <code>recipient</code> chooses the recipient of the pass. It divides players from the same team into
     * few pots depending on their distance from the <code>football.Player</code>. It's more likely to choose a recipient
     * who is closer to the player.
     * @param team the player's team
     * @return returns the recipient of the pass
     */
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

    /**
     * Method <code>player_passing</code> makes football.Player pass. He passes to the chosen recipient. Pass can be either
     * successful, missed, or off-side. It depends on player's pass ability, and the distance from the recipient.
     * @param recipient the chosen recipient of the pass
     * @param ball the ball
     * @param event the event
     * @return returns event that will happen, there might be normal game, missed ball or free kick(after off-side)
     */
    public int player_passing(Player recipient, Ball ball, int event){
        int modY=0;
        if(team_number==2) modY=5;
        int success = (random.nextInt(100))+1;
        float ability;
        if(Math.abs(getPlace().getLength()-modY)<=5 && Math.abs(getPlace().getLength()-modY)>=4){
            if(getPlace()==recipient.getPlace()){
                ability = 90+(((float)attributes.getPassing()-15));
            }
            else if(Math.abs(getPlace().getWidth()-recipient.getPlace().getWidth())<=1 && Math.abs(getPlace().getLength()-recipient.getPlace().getLength())<=1){
                ability = 85+(((float)attributes.getPassing()-15));
            }
            else{
                ability = 50+(((float)attributes.getPassing()-15)*6);
            }
        }
        else if(Math.abs(getPlace().getLength()-modY)<=3 && Math.abs(getPlace().getLength()-modY)>=2){
            if(getPlace()==recipient.getPlace()){
                ability = 80+(((float)attributes.getPassing()-15)*2);
            }
            else if(Math.abs(getPlace().getWidth()-recipient.getPlace().getWidth())<=1 && Math.abs(getPlace().getLength()-recipient.getPlace().getLength())<=1){
                ability = 75+(((float)attributes.getPassing()-15)*2);
            }
            else{
                ability = 50+(((float)attributes.getPassing()-15)*6);
            }
        }
        else if(Math.abs(getPlace().getLength()-modY)==1){
            if(getPlace()==recipient.getPlace()){
                ability = 65+(((float)attributes.getPassing()-15)*3);
            }
            else if(Math.abs(getPlace().getWidth()-recipient.getPlace().getWidth())<=1 && Math.abs(getPlace().getLength()-recipient.getPlace().getLength())<=1){
                ability = 60+(((float)attributes.getPassing()-15)*3);
            }
            else{
                ability = 40+(((float)attributes.getPassing()-15)*4);
            }
        }
        else {
            if(getPlace()==recipient.getPlace()){
                ability = 40+(((float)attributes.getPassing()-15)*4);
            }
            else if(Math.abs(getPlace().getWidth()-recipient.getPlace().getWidth())<=1 && Math.abs(getPlace().getLength()-recipient.getPlace().getLength())<=1){
                ability = 35+(((float)attributes.getPassing()-15)*4);
            }
            else{
                ability = 40+(((float)attributes.getPassing()-15)*4);
            }
        }
        if(success<ability){
            player_get_ball(false);
            recipient.player_get_ball(true);
            ball.setOwner(recipient);
            ball.setX(recipient.getPlace().getWidth());
            ball.setY(recipient.getPlace().getLength());
            System.out.println(surname+" passes to "+recipient.surname);
            stats.addPassesAttempts();
            stats.addPassesCompleted();
            team_stats.addPass();
            event = 1;
        }
        else {

            int choi=0;
            if((Math.abs(getPlace().getLength()-modY)<=1)){
                choi = random.nextInt(2);
            }
            if(choi==0){
                System.out.println(surname+" passes, but he misses!");
                stats.addPassesAttempts();
                stats.addLost();
                event =4;
            }
            else{
                System.out.println(surname+" passes, but "+recipient.surname+" is off-side!");
                stats.addPassesAttempts();
                stats.addOffside();
                team_stats.addOffside();
                event=7;
            }
            player_get_ball(false);
            if(team_number==1) ball.setTeam(2);
            else ball.setTeam(1);
            ball.setX(recipient.getPlace().getWidth());
            ball.setY(recipient.getPlace().getLength());

        }
        stamina -= 0.15;
        return event;
    }

    /**
     * Method <code>player_dribbling</code> makes football.Player with ball dribbles.
     * @param pitch the pitch
     * @param ball the ball
     */
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
        stats.addDribblingCompleted();
        stamina -=1;
    }

    /**
     * Method <code>player_tackling</code> makes football.Player tackle. Depending on player's defending ability, and opponent's
     * dribbling ability it might be successful or unsuccessful. It might result in foul as well. There's a chance, that
     * player who fouls will receive a yellow or red card. The higher player's aggression is the more chance for foul
     * and card player has. Foul ends in free kick (if foul happened outside the box) or penalty (if inside the box).
     * @param opponent the opponent with the ball, football.Player tries to take the ball from him.
     * @param ball the ball
     * @param event the event
     * @return the event which will happen then, there might be: normal game, free kick, penalty
     */
    public int player_tackling(Player opponent, Ball ball, int event){
        int modY=0;
        if(team_number==2) modY=5;
        int chance = random.nextInt(100);
        float ability;
        if(Math.abs(getPlace().getLength()-modY)>=2){
            ability = 60+(((float)attributes.getDefending()-15)*2)+(((float)opponent.getAttributes().getDribbling()-15)*2);
        }
        else{
            ability = 20+(((float)attributes.getDefending()-15)*2)+(((float)opponent.getAttributes().getDribbling()-15)*2);
        }
        if(chance<ability){
            System.out.println(surname+" makes a tackle! "+opponent.surname+" loses possession.");
            opponent.player_get_ball(false);
            player_get_ball(true);
            ball.setOwner(this);
            ball.setTeam(team_number);
            event = 1;
            stats.addDuel();
            stats.addDuelWon();
            opponent.getStats().addDuel();
            opponent.getStats().addLost();
        }
        else{
            float foulrate = 30+(((float)attributes.getAggression()-15)*2);
            int foulrandom = random.nextInt(100);
            if(foulrandom<foulrate){
                if(getPlace().getWidth()<=3 && getPlace().getWidth()>=1 && getPlace().getLength()==modY){
                    System.out.println(surname+" fouls in the box! Penalty!");
                    opponent.player_get_ball(false);
                    event = 8;
                }
                else{
                    System.out.println(surname+" fouls! Free kick!");
                    opponent.player_get_ball(false);
                    event = 7;
                }
                int card = random.nextInt(100);
                if(card<3){
                    stats.addRed();
                    System.out.println(surname+" gets red card! He is sent off!");
                    team_stats.addRed();
                }
                else if(card<15){
                    stats.addYellow();
                    System.out.println(surname+" gets yellow card!");
                    team_stats.addYellow();
                    if(stats.getYellow_cards()==2){
                        stats.addRed();
                        team_stats.addRed();
                        System.out.println("It's his second yellow card! "+surname+" receives red card and he's sent off!");
                    }
                }
                opponent.setStamina(getStamina()-1);
                stats.addFoul();
                team_stats.addFoul();
            }
            else {
                System.out.println(surname+" tries to make a tackle but he isn't successful!");
                event = 1;
                stats.addDuel();
                opponent.getStats().addDuel();
                opponent.getStats().addDuelWon();
            }
        }
        stamina -= 0.25;
        return event;
    }

    /**
     * Method <code>player_crossing</code> makes football.Player cross. He crosses to the chosen recipient. Cross can be either
     * successful, missed, or off-side. It depends on player's pass ability, and the distance from the recipient.
     * @param recipient the chosen recipient of the cross
     * @param ball the ball
     * @param event the event
     * @return returns event that will happen, there might be high ball, missed ball or free kick(after off-side)
     */
    public int player_crossing(Player recipient, Ball ball, int event){
        int success = (random.nextInt(100))+1;
        float ability = ((float)attributes.getPassing()/20)*100;
        if(success<ability){
            player_get_ball(false);
            recipient.player_get_ball(true);
            ball.setOwner(recipient);
            ball.setX(recipient.getPlace().getWidth());
            ball.setY(recipient.getPlace().getLength());
            System.out.println(surname+" crosses to "+recipient.surname);
            event = 1;
        }
        else {
            player_get_ball(false);
            ball.setX(recipient.getPlace().getWidth());
            ball.setY(recipient.getPlace().getLength());
            System.out.println(surname+" crosses, but he misses!");
            event = 4;
        }
        stamina -= 0.2;
        return event;
    }

    public void player_heading(){
    }

    /**
     * Method <code>player_intercepting</code> makes football.Player take over the ball, when nobody owns the ball.
     * @param ball the ball
     * @param event the event
     * @return the event that will happen, here it's always the normal game
     */
    public int player_intercepting(Ball ball, int event){
        ball.setOwner(this);
        ball.setTeam(team_number);
        player_get_ball(true);
        event = 1;
        System.out.println(surname+" takes over the ball!");
        stamina -= 0.1;
        stats.addInterception();
        return event;
    }

    /**
     * Method <code>player_penalty</code> makes football.Player take a penalty. It's simple shoot, but it's more possible to score.
     * @param ball the ball
     * @param event the event
     * @return it returns the event that will happen, here shoot on-target or missed shoot
     */
    public int player_penalty(Ball ball, int event){
        team_stats.addPenalty();
        return player_shooting(ball, event);
    }

    /**
     * Method <code>player_freekick</code> makes football.Player take a free kick. It can be either pass or shoot.
     * @param ball the ball
     * @param event the event
     * @param team the team
     * @return the event that will happen
     */
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
        team_stats.addFreeKick();
        return event;
    }

    public AttributesPlayer getAttributes(){
        return attributes;
    }

    public StatsPlayer getStats(){
        return stats;
    }

    public double getStamina(){
        return stamina;
    }
    public void setStamina(double stamina){
        this.stamina = stamina;
    }
    public StatsTeam getTeam_stats(){
        return team_stats;
    }
}