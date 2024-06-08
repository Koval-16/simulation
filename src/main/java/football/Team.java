package football;

import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

/**
 * Class represents team. Team contains 22 {@link Player}s.
 * <p>Attributes:</p>
 * <ul>
 *     <li>name: team's name</li>
 *     <li>mentality: team's tactical mentality</li>
 *     <li>motivation: team's motivation and attitude</li>
 *     <li>players: list of team's players</li>
 *     <li>lineup: list of team's players who are currently on the pitch</li>
 *     <li>bench: list of team's players who are sitting on the bench</li>
 *     <li>red_cards: list of team's players who received red card, and cant perform any action anymore</li>
 *     <li>pitch: pitch</li>
 *     <li>number: team's ID number</li>
 *     <li>stats: team's stats</li>
 *     <li>corners_taker: player who is corner taker</li>
 *     <li>freekicks_taker: player who is direct free kicks taker</li>
 *     <li>penalties_taker: player who shoots penalties</li>
 *     <li>team_exists: holds info if team exists (if it's false, match can't be performed)</li>
 * </ul>
 */
public class Team {
    Random random = new Random();
    private final String name;
    int mentality;
    int motivation;
    private final List<Player> players;
    private final List<Player> lineup;
    private final List<Player> bench;
    private List<Player> red_cards;
    private final Pitch pitch;
    private final int number;
    private final StatsTeam stats;
    private Player corners_taker;
    private Player freekicks_taker;
    private Player penalties_taker;
    boolean team_exists;

    /**
     * Constructor of class Team
     * @param pitch pitch of the game
     * @param number team's ID number
     * @param name team's name
     * @param mentality team's tactical mentality
     * @param motivation team's motivaiton
     */
    public Team(Pitch pitch, int number, String name, int mentality, int motivation){
        this.number = number;
        this.pitch = pitch;
        this.stats = new StatsTeam();
        this.name = name;
        this.mentality = mentality;
        this.motivation = motivation;
        this.players = new ArrayList<>();
        this.lineup = new ArrayList<>();
        this.bench = new ArrayList<>();
        this.team_exists = load_players();
        if(team_exists){
            if(set_lineup()){
                this.corners_taker = setCornersTaker();
                this.freekicks_taker = setFreeKicksTaker();
                this.penalties_taker = setPenaltiesTaker();
                this.red_cards = new ArrayList<>();
            }
            else team_exists = false;
        }
    }

    /**
     * This method is loading players from .txt file
     * @return information if loading players was successful
     */
    public boolean load_players(){
        try{
            File file = new File("Clubs/"+name+".txt");
            Scanner scanner = new Scanner(file);
            while(scanner.hasNextLine()){
                String playername = scanner.nextLine();
                String[] names = playername.split(" ");
                switch (names[2]){
                    case "G":
                        Goalkeeper playerG = new Goalkeeper(names[0], names[1], Integer.parseInt(names[3]),
                                Integer.parseInt(names[4]), Integer.parseInt(names[5]), Integer.parseInt(names[6]),
                                Integer.parseInt(names[7]), Integer.parseInt(names[8]), Integer.parseInt(names[9]),
                                number, stats, mentality, motivation);
                        players.add(playerG);
                        break;
                    case "D":
                        Defender playerD = new Defender(names[0], names[1], Integer.parseInt(names[3]),
                                Integer.parseInt(names[4]), Integer.parseInt(names[5]), Integer.parseInt(names[6]),
                                Integer.parseInt(names[7]), Integer.parseInt(names[8]), Integer.parseInt(names[9]),
                                number, stats, mentality, motivation);
                        players.add(playerD);
                        break;
                    case "M":
                        Midfielder playerM = new Midfielder(names[0], names[1], Integer.parseInt(names[3]),
                                Integer.parseInt(names[4]), Integer.parseInt(names[5]), Integer.parseInt(names[6]),
                                Integer.parseInt(names[7]), Integer.parseInt(names[8]), Integer.parseInt(names[9]),
                                number, stats, mentality,motivation);
                        players.add(playerM);
                        break;
                    case "F":
                        Forward player = new Forward(names[0], names[1], Integer.parseInt(names[3]),
                                Integer.parseInt(names[4]), Integer.parseInt(names[5]), Integer.parseInt(names[6]),
                                Integer.parseInt(names[7]), Integer.parseInt(names[8]), Integer.parseInt(names[9]),
                                number, stats, mentality, motivation);
                        players.add(player);
                        break;
                }
            }
            return true;
        } catch (FileNotFoundException e){
            System.out.println("Nie znaleziono pliku: Clubs/" + name + ".txt");
            return false;
        } catch (ArrayIndexOutOfBoundsException e){
            System.out.println("Plik Clubs/"+name+".txt zawiera niepelne dane");
            return false;
        } catch (NumberFormatException e){
            System.out.println("Plik Clubs/"+name+".txt zawiera nieprawidlowe dane");
            return false;
        }
    }

    /**
     * This method is setting starting lineup.
     * @return information if setting lineup was successful
     */
    public boolean set_lineup(){
        try {
            for(int i=0; i<11; i++){
                lineup.add(players.get(i));
            }
            set_default_lineup();
            for(int i=11; i<22; i++){
                bench.add(players.get(i));
            }
            return true;
        } catch (IndexOutOfBoundsException e){
            System.out.println("Plik Clubs/"+name+".txt zawiera zbyt małą liczbę graczy");
            return false;
        }
    }

    /**
     * This method is setting default lineup of the team (used before kick-offs). Players simply come back to their halfs.
     */
    public void set_default_lineup(){
        for (Player player : lineup) {
            int modX = 0;
            if (number == 2) modX = 4;
            if (player instanceof Goalkeeper) player.setPlace(pitch, Math.abs(modX - player.getAttributes().getSide()), 5);
            else if (player instanceof Defender) player.setPlace(pitch, Math.abs(modX - player.getAttributes().getSide()), 4);
            else if (player instanceof Midfielder) player.setPlace(pitch, Math.abs(modX - player.getAttributes().getSide()), 3);
            else if (player instanceof Forward) player.setPlace(pitch, Math.abs(modX - player.getAttributes().getSide()), 3);
        }
    }

    /**
     * This method is setting corners taker
     * @return the taker of corners
     */
    private Player setCornersTaker(){
        Player taker = lineup.get(1);
        for(int i=2; i<lineup.size(); i++){
            if(lineup.get(i).getAttributes().getPassing()>=taker.getAttributes().getPassing()){
                taker = lineup.get(i);
            }
        }
        return taker;
    }

    /**
     * This method is setting free kicks taker
     * @return the taker of free kicks
     */
    private Player setFreeKicksTaker(){
        Player taker = lineup.get(1);
        for(int i=2; i<lineup.size(); i++){
            if(lineup.get(i).getAttributes().getPassing()+lineup.get(i).getAttributes().getShooting()>=
            taker.getAttributes().getPassing()+taker.getAttributes().getShooting()){
                taker = lineup.get(i);
            }
        }
        return taker;
    }

    /**
     * This method is setting penalties taker
     * @return the taker of penalties
     */
    private Player setPenaltiesTaker(){
        Player taker = lineup.get(1);
        for(int i=2; i<lineup.size(); i++){
            if(lineup.get(i).getAttributes().getShooting()>=taker.getAttributes().getShooting()){
                taker = lineup.get(i);
            }
        }
        return taker;
    }

    /**
     * This method is setting lineup of the team for offensive corners and free kicks
     * @param event the event which occurred
     */
    public void set_corner_lineup(int event){
        int mod = 0;
        if(number==2) mod=5;
        int side = 4*random.nextInt(2);
        Player taker;
        if(event==6){
            taker = corners_taker;
            if(taker.ball_possessed){
                taker.setPlace(pitch, side, mod);
            }
        }
        else{
            taker = freekicks_taker;
        }
        List<Player> corner = new ArrayList<>();
        for(int i=1; i<lineup.size(); i++){
            if(taker.ball_possessed){
                if(lineup.get(i)!=taker) corner.add(lineup.get(i));
            }
            else corner.add(lineup.get(i));
        }
        for(int i=0; i<corner.size()-1; i++){
            for(int j=i+1; j<corner.size(); j++){
                Player temp;
                if(corner.get(i).getAttributes().getShooting()>corner.get(j).getAttributes().getShooting()){
                    temp = corner.get(i);
                    corner.set(i, corner.get(j));
                    corner.set(j, temp);
                }
            }
        }
        if(taker.ball_possessed){
            for(int i=0; i<corner.size(); i++){
                if(i<6) corner.get(i).setPlace(pitch, 2, mod);
                else if(i<7) corner.get(i).setPlace(pitch, 2, Math.abs(mod-1));
                else corner.get(i).setPlace(pitch, 2, Math.abs(mod-2));
            }
        }
        else{
            for(int i=0; i<corner.size(); i++){
                if(i<6) corner.get(i).setPlace(pitch, 2, 5-mod);
                else if(i<8) corner.get(i).setPlace(pitch, 2, 5-Math.abs(mod-1));
                else corner.get(i).setPlace(pitch, 2, 5-Math.abs(mod-2));
            }
        }

    }

    /**
     * This method is setting the team's lineup for penalties
     */
    public void set_penalty_lineup(){
        int modY=0;
        int modX=0;
        if(number==2){
            modX=4;
            modY=5;
        }
        if(penalties_taker.ball_possessed){
            for(int i=1; i<lineup.size(); i++){
                if(lineup.get(i)!=corners_taker){
                    if(lineup.get(i) instanceof Defender) lineup.get(i).setPlace(pitch, Math.abs(modX-lineup.get(i).getAttributes().getSide()), Math.abs(modY-2));
                    else if(lineup.get(i) instanceof Midfielder) lineup.get(i).setPlace(pitch, Math.abs(modX-lineup.get(i).getAttributes().getSide()), Math.abs(modY-1));
                    else if(lineup.get(i) instanceof Forward) lineup.get(i).setPlace(pitch, Math.abs(modX-lineup.get(i).getAttributes().getSide()), Math.abs(modY-1));
                }
            }
        }
        else{
            for(int i=1; i<lineup.size(); i++){
                if(lineup.get(i) instanceof Defender) lineup.get(i).setPlace(pitch, Math.abs(modX-lineup.get(i).getAttributes().getSide()), Math.abs(modY-4));
                else if(lineup.get(i) instanceof Midfielder) lineup.get(i).setPlace(pitch, Math.abs(modX-lineup.get(i).getAttributes().getSide()), Math.abs(modY-4));
                else if(lineup.get(i) instanceof Forward) lineup.get(i).setPlace(pitch, Math.abs(modX-lineup.get(i).getAttributes().getSide()), Math.abs(modY-3));
            }
        }
    }
    public void red_player(){
        for(int i=0; i<lineup.size(); i++){
            if(lineup.get(i).getStats().getRed_cards()!=0){
                red_cards.add(lineup.get(i));
                red_cards.add(bench.get(i));
                lineup.remove(i);
                bench.remove(i);
                corners_taker = setCornersTaker();
                freekicks_taker = setFreeKicksTaker();
                penalties_taker = setPenaltiesTaker();
            }
        }
    }

    /**
     * This method is making a substitution. Player from the pitch is replaced with player from the bench.
     * @param player the subbed-off player
     */
    public void substitution(Player player){
        for(int i=0; i<lineup.size(); i++){
            if(lineup.get(i)==player){
                System.out.println(lineup.get(i).surname+" is subbed off. "+bench.get(i).surname+" changes him.");
                System.out.println(lineup.get(i).getStamina());
                System.out.println(bench.get(i).getStamina());
                lineup.set(i, bench.get(i));
                bench.set(i, player);
                lineup.get(i).setPlace(pitch,bench.get(i).getPlace().getWidth(),bench.get(i).getPlace().getLength());
            }
        }
        corners_taker = setCornersTaker();
        freekicks_taker = setFreeKicksTaker();
        penalties_taker = setPenaltiesTaker();
    }

    public int getNumber(){
        return number;
    }

    public Player getCorners_taker(){
        return corners_taker;
    }

    public Player getFreekicks_taker(){
        return freekicks_taker;
    }

    public Player getPenalties_taker(){
        return penalties_taker;
    }

    public List<Player> getLineup(){
        return lineup;
    }
    public String getName(){
        return name;
    }
    public StatsTeam getStats(){
        return stats;
    }
    public List<Player> getBench(){
        return bench;
    }
    public List<Player> getRed_cards(){
        return red_cards;
    }
}