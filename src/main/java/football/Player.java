package football;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Class representing the player in a game.
 * This class contains player's stats, his attributes, name, the value of his stamina, his current place, ball possession.
 * There are 4 classes which extends Player, they are: {@link Goalkeeper}, {@link Defender}, {@link Midfielder}, {@link Forward}
 * <p>Attributes:</p>
 * <ul>
 *     <li>stats: individual stats of the player</li>
 *     <li>team_stats: team stats of the team player belongs to</li>
 *     <li>attributes: player's attributes</li>
 *     <li>name: player's name</li>
 *     <li>surname: player's surname</li>
 *     <li>stamina: value of player's stamina (100 is full stamina)</li>
 *     <li>place: the area where player is</li>
 *     <li>ball_possessed: whether the player possesses the ball or no</li>
 *     <li>team_number: the number of the team player belongs to</li>
 *     <li>mentality: mentality of the player(common for players from the same team)</li>
 *     <li>motivation: motivation of the player(common for players from the same team)</li>
 * </ul>
 */
public abstract class Player {
    Random random = new Random();
    private final StatsPlayer stats;
    private final StatsTeam team_stats;
    private final AttributesPlayer attributes;
    protected final String name;
    protected final String surname;
    private double stamina = 100;
    protected Field place;
    protected boolean ball_possessed;
    protected int team_number;
    protected int mentality;
    protected int motivation;

    /**
     * Constructor of class Player with the specified attributes.
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
     * @param team_stats the stats of the team player belongs to
     * @param mentality the mentality of the player
     * @param motivation the motivation of the player
     */
    public Player(String name, String surname, int side, int shooting, int dribbling,int passing,
                  int defending, int aggression, int goalkeeping, int team_number,
                  StatsTeam team_stats, int mentality, int motivation){
        this.name = name;
        this.surname = surname;
        this.ball_possessed = false;
        this.stats = new StatsPlayer();
        this.attributes = new AttributesPlayer(side, shooting, dribbling, passing, defending, aggression, goalkeeping);
        this.team_number = team_number;
        this.team_stats = team_stats;
        this.mentality = mentality;
        this.motivation = motivation;
        adjustAttributes();
    }

    /**
     * Method adjustAttributes adjusts player's attributes based on mentality and motivation.
     * High value of motivation increases the ability of: shooting, dribbling, passing, defending.
     * Low value of motivation decreases the ability of: shooting, dribbling, passing, defending.
     * Positive value of mentality (offensive) increases ability of: shooting and dribbling.
     * Negative value of mentality (defensive) increases defending but decreases passing.
     */
    private void adjustAttributes() {
        if (mentality > 0) {
            attributes.setShooting(attributes.getShooting() + 1);
            attributes.setDribbling(attributes.getDribbling() + 1);
        } else {
            attributes.setDefending(attributes.getDefending() + 1);
            attributes.setPassing(attributes.getPassing() - 1);
        }
        if (motivation > 3) {
            attributes.setDefending(attributes.getDefending() + 1);
            attributes.setDribbling(attributes.getDribbling() + 1);
            attributes.setShooting(attributes.getShooting() + 1);
            attributes.setPassing(attributes.getPassing() + 1);
        } else if (motivation < 0) {
            attributes.setDefending(attributes.getDefending() - 1);
            attributes.setDribbling(attributes.getDribbling() - 1);
            attributes.setShooting(attributes.getShooting() - 1);
            attributes.setPassing(attributes.getPassing() - 1);
        }
    }

    /**
     * The abstract method decision_ball. Makes decision when player owns the ball.
     * The decision-making depend on player's position (other for goalkeepers, defenders etc.)
     * @param pitch the pitch of the game
     * @param ball the ball
     * @param team the team of the player who makes a decision
     * @param event the event
     * @param con the condition modifier
     * @param rain the rain modifier
     * @return new event which will happen
     */
    public abstract int decision_ball(Pitch pitch, Ball ball, Team team, int event, double con, int rain);

    /**
     * The method decision_no_ball. Makes decision when player doesn't own the ball.
     * Player might try to tackle a ball from an opponent or take over the ball, when the other team lost it.
     * Player's decision depends on the current place on the pitch of the player.
     * @param event the event
     * @param team the team of the player who makes a decision
     * @param ball the ball
     * @param con the condition modifier
     * @param referee the severity of the referee
     * @param rain the rain modifier
     * @return new event which will happen
     */
    public int decision_no_ball(int event, Team team, Ball ball, double con, int referee, int rain){
        if(event==1){
            if(getPlace().getWidth()==ball.getOwner().getPlace().getWidth() &&
                    getPlace().getLength()==ball.getOwner().getPlace().getLength()){
                int modY=0;
                if(team_number==2) modY=5;
                int action = random.nextInt(100);
                if(Math.abs(getPlace().getLength()-modY)>=4){
                    if(action<70) event=player_tackling(ball.getOwner(),ball,event,con,referee,rain);
                }
                else if(Math.abs(getPlace().getLength()-modY)>=2){
                    if(action<50) event=player_tackling(ball.getOwner(),ball,event,con,referee,rain);
                }
                else{
                    if(action<30) event=player_tackling(ball.getOwner(),ball,event,con,referee,rain);
                }
            }
        }
        else if(event==4) event = player_intercepting(ball, event,con);
        return event;
    }
    public Field getPlace(){
        return place;
    }

    public void setPlace(Pitch pitch, int width, int length){
        place = pitch.getPitch()[width][length];
    }

    /**
     * The method player_moving moves the player who doesn't have the ball.
     * The movement depends on:
     * - player's position
     * - player's current place
     * - ball's current place
     * - team which possesses the ball
     * Movement in X-coordinates is common for all players.
     * Movement in Y-coordinates depends on player's position.
     * Movement decreases player's stamina. The loss of stamina is higher when player's team don't have the ball.
     * @param pitch the pitch of the game
     * @param ball_X ball X-coordinate
     * @param ball_Y ball Y-coordinate
     * @param ball_team team which owns the ball
     * @param con condition modifier
     */
    public void player_moving(Pitch pitch, int ball_X, int ball_Y, int ball_team, double con){
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
        if(team_number==ball_team) stamina -= 0.02*con;
        else stamina -= 0.03*con;
    }

    /**
     * Method <code>player_get_ball</code> sets if the player has the ball.
     * @param ball_possessed true=has ball, false=doesn't have ball
     */
    public void player_get_ball(boolean ball_possessed){
        this.ball_possessed = ball_possessed;
    }

    /**
     * The method player_shooting makes player shoots.
     * Shot might be either on-target or missed. The success of making a shot-on-target depends on:
     * - player's shooting ability
     * - place where he shoots from
     * - player's stamina
     * - player's motivation
     * - weather (rain)
     * If players misses, the game will be resumed by enemy goalkeeper. If the shoot is on target, enemy goalkeeper needs to save it.
     * @param ball the ball
     * @param event the event
     * @param con condition modifier
     * @param rain rain modifier
     * @return new event which will happen
     */
    public int player_shooting(Ball ball, int event, double con, int rain){
        int modX=0; int modY=0;
        if(team_number==2){
            modX=4; modY=5;
        }
        double success = ((random.nextInt(100))+1)+((100-getStamina())/10)-motivation+(rain*5);
        float ability;
        if(Math.abs(getPlace().getLength()-modY)==0 && Math.abs(getPlace().getWidth()-modX)==2){
            ability = 90+(((float)attributes.getShooting()-20)*3);
        }
        else if(Math.abs(getPlace().getLength()-modY)==0 && Math.abs(getPlace().getWidth()-modX)!=2){
            ability = 90+(((float)attributes.getShooting()-20)*4);
        }
        else {
            ability = 70+(((float)attributes.getShooting()-20)*4);
        }
        stamina -= 0.5*con;
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
        }
        else{
            System.out.println(surname+" shoots, but he misses");
            event = 9;
            stats.addShoots();
            team_stats.addShoot();
            ball.setX(2);
            if(team_number==2) ball.setY(5);
            else ball.setY(0);
        }
        return event;
    }

    /**
     * Method recipient chooses the recipient of the pass. It divides players from the same team into
     * few pots depending on their distance from the football.Player. It's more likely to choose a recipient
     * who is closer to the player.
     * @param team team of the player
     * @return the recipient of the pass
     */
    public Player recipient(Team team){
        List<List<Player>> distances = new ArrayList<>();
        for(int i=0;i<3;i++){
            distances.add(new ArrayList<>());
        }
        for (int i=1; i<team.getLineup().size(); i++){
            if(team.getLineup().get(i)!=this){
                if(team.getLineup().get(i).getPlace().getWidth()==place.getWidth()
                        && team.getLineup().get(i).getPlace().getLength()==place.getLength()){
                    distances.getFirst().add(team.getLineup().get(i));
                }
                else if(Math.abs(team.getLineup().get(i).getPlace().getLength()-place.getLength())<=1
                && Math.abs(team.getLineup().get(i).getPlace().getWidth()-place.getWidth())<=1){
                    distances.get(1).add(team.getLineup().get(i));
                }
                else distances.get(2).add(team.getLineup().get(i));
            }
        }
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
     * The method player_passing makes player pass.
     * The pass may be successful, missed or off-side.
     * The success of making an accurate pass depends on:
     * - player's passing ability
     * - place where he passes from
     * - player's stamina
     * - player's motivation
     * - weather (rain)
     * When the pass is successful, the recipient gets the ball. When it's missed, an opponent takes over the ball.
     * @param recipient the player who is recipient of the pass
     * @param ball the ball
     * @param event the event
     * @param con condition modifier
     * @param rain rain modifier
     * @return new event which will happen
     */
    public int player_passing(Player recipient, Ball ball, int event, double con,int rain){
        int modY=0;
        if(team_number==2) modY=5;
        double success = ((random.nextInt(100))+1)+((100-getStamina())/10)-motivation+rain;
        double ability;
        if(Math.abs(getPlace().getLength()-modY)<=5 && Math.abs(getPlace().getLength()-modY)>=4){
            ability = ability_passing(recipient,90,1,1,4);
        }
        else if(Math.abs(getPlace().getLength()-modY)<=3 && Math.abs(getPlace().getLength()-modY)>=2){
            ability = ability_passing(recipient,90,1.5,2,4);
        }
        else if(Math.abs(getPlace().getLength()-modY)==1){
            ability = ability_passing(recipient,80,3,3,4);
        }
        else{
            ability = ability_passing(recipient,60,3,3,3);
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
            }
            else{
                System.out.println(surname+" passes, but "+recipient.surname+" is off-side!");
                stats.addPassesAttempts();
                stats.addOffside();
                team_stats.addOffside();
            }
            event=4;
            player_get_ball(false);
            if(team_number==1) ball.setTeam(2);
            else ball.setTeam(1);
            ball.setX(recipient.getPlace().getWidth());
            ball.setY(recipient.getPlace().getLength());
        }
        stamina -= 0.15*con;
        return event;
    }

    /**
     * Method ability_passing calculates the actual ability of the specific pass.
     * @param recipient recipient of the pass
     * @param value basic value
     * @param m1 modifier1
     * @param m2 modifier2
     * @param m3 modifier3
     * @return the ability of the specific pass
     */
    private double ability_passing(Player recipient, int value, double m1, double m2, double m3){
        if(getPlace()==recipient.getPlace()){
            return value+(((float)attributes.getPassing()-20)*m1);
        }
        else if(Math.abs(getPlace().getWidth()-recipient.getPlace().getWidth())<=1 && Math.abs(getPlace().getLength()-recipient.getPlace().getLength())<=1){
            return (value-5)+(((float)attributes.getPassing()-20)*m2);
        }
        else{
            return (value-10)+(((float)attributes.getPassing()-20)*m3);
        }
    }

    /**
     * Method player_dribbling makes player with the ball dribbles.
     * Player can dribble without changing his field, or he can dribble into neighboring field.
     * Dribbling causes big loss of stamina.
     * @param pitch pitch of the game
     * @param ball ball
     * @param con condition modifier
     */
    public void player_dribbling(Pitch pitch, Ball ball, double con){
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
        stamina -=1*con;
    }

    /**
     * Method player_tackling makes player tackle.
     * Tackle might be successful or unsuccessful. Unsuccessful tackle might result in foul.
     * The success of tackle depends on:
     * - player's defending ability
     * - opponent's dribbling ability
     * - player's and opponent's stamina
     * - player's and opponent's motivation
     * - place where the player tries to tackle
     * - weather (rain)
     * If the tackle was not successful, it might result in foul, and even in yellow or red card. It depends on:
     * - player's aggression
     * - referee's severity
     * - weather (rain)
     * Tackling decreases player's stamina. If foul occurred, also opponent's stamina is decreased.
     * @param opponent the player who owns the ball
     * @param ball the ball
     * @param event the event
     * @param con condition modifier
     * @param referee the severity of the referee
     * @param rain rain modifier
     * @return new event which will happen
     */
    public int player_tackling(Player opponent, Ball ball, int event, double con, int referee, int rain){
        int modY=0;
        if(team_number==2) modY=5;
        double chance = random.nextInt(100)+((100-getStamina())/20)-motivation-((100-opponent.getStamina())/10)+opponent.motivation-(rain*5);
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
            int foulrandom = random.nextInt(100)-referee-(rain*5);
            if(foulrandom<foulrate){
                if(getPlace().getWidth()<=3 && getPlace().getWidth()>=1 && getPlace().getLength()==5-modY){
                    System.out.println(surname+" fouls in the box! Penalty!");
                    opponent.player_get_ball(false);
                    event = 8;
                }
                else{
                    System.out.println(surname+" fouls! Free kick!");
                    event = 7;
                }
                double card = random.nextDouble(100);
                if(card<(0.4+(double)(referee/25))){
                    stats.addRed();
                    System.out.println(surname+" gets red card! He is sent off!");
                    team_stats.addRed();
                }
                else if(card<(10+(referee/0.83))){
                    stats.addYellow();
                    System.out.println(surname+" gets yellow card!");
                    team_stats.addYellow();
                    if(stats.getYellow_cards()==2){
                        stats.addRed();
                        team_stats.addRed();
                        System.out.println("It's his second yellow card! "+surname+" receives red card and he's sent off!");
                    }
                }
                opponent.setStamina(getStamina()-(1*con));
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
        stamina -= 0.25*con;
        return event;
    }


    /**
     * Method player_intercepting makes player take over the ball, when nobody owns it.
     * @param ball the ball
     * @param event the event
     * @param con condition modifier
     * @return the event that will happen, here it's always the normal game
     */
    public int player_intercepting(Ball ball, int event, double con){
        ball.setOwner(this);
        ball.setTeam(team_number);
        player_get_ball(true);
        event = 1;
        System.out.println(surname+" takes over the ball!");
        stamina -= 0.1*con;
        stats.addInterception();
        return event;
    }

    /**
     * Method player_penalty makes player take a penalty. It's simple shoot, but it's more possible to score.
     * @param ball the ball
     * @param event the event
     * @param rain rain modifier
     * @return it returns the event that will happen, here shoot on-target or missed shoot
     */
    public int player_penalty(Ball ball, int event,double con, int rain){
        team_stats.addPenalty();
        return player_shooting(ball, event,con,rain);
    }

    /**
     * Method player_freekick makes player take a free kick. It can be either pass or shoot.
     * @param ball the ball
     * @param event the event
     * @param team the team
     * @param con condition modifier
     * @param rain rain modifier
     * @return the event that will happen
     */
    public int player_freekick(Ball ball, int event, Team team, double con, int rain){
        int mod = 0;
        if(team_number==2) mod=5;
        if(Math.abs(getPlace().getLength()-mod)<=1){
            int choice = random.nextInt(2);
            if(choice==0) event = player_shooting(ball, event, con, rain);
            else event = player_passing(recipient(team), ball, event, con, rain);
        }
        else{
            event = player_passing(recipient(team), ball, event, con, rain);
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