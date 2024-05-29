package football; /**
 * Class <code>football.Team</code> represents team, with its players, set pieces takers, lineup, bench, name.
 */

import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

public class Team {
    Random random = new Random();
    private String name;
    int mentality;
    int motivation;
    private List<Player> players;
    private List<Player> lineup;
    private List<Player> bench;
    private Pitch pitch;
    private int number;
    private StatsTeam stats;
    private Player corners_taker;
    private Player freekicks_taker;
    private Player penalties_taker;

    /**
     * Constructor of the class football.Team
     * @param pitch pitch of the game
     * @param number the index of the team
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
        load_players();
        set_lineup();
        this.corners_taker = setCornersTaker();
        this.freekicks_taker = setFreeKicksTaker();
        this.penalties_taker = setPenaltiesTaker();
    }

    /**
     * Choosing the team from the list of clubs
     * @return name of the team
     */
    private String choose_team(){
        System.out.println("insert team:");
        Scanner scanner1 = new Scanner(System.in);
        String team = scanner1.nextLine();
        try{
            File file = new File("teams.txt");
            Scanner scanner2 = new Scanner(file);
            while(scanner2.hasNextLine()){
                String current = scanner2.nextLine();
                if (current.equals(team)){
                    return current;
                }
            }
        } catch (FileNotFoundException e){
            System.out.println("NIE");
            return "";
        }
        return "";
    }

    /**
     * Loading players with their attributes etc. from the team text file
     */
    private void load_players(){
        try{
            File file = new File(name+".txt");
            Scanner scanner = new Scanner(file);
            while(scanner.hasNextLine()){
                String playername = scanner.nextLine();
                String[] names = playername.split(" ");
                for(int i=3; i< names.length; i++){
                }
                if(names[2].equals("G")){
                    Goalkeeper player = new Goalkeeper(names[0], names[1], Integer.parseInt(names[3]),
                            Integer.parseInt(names[4]), Integer.parseInt(names[5]), Integer.parseInt(names[6]),
                            Integer.parseInt(names[7]), Integer.parseInt(names[8]), Integer.parseInt(names[9]),
                            number, stats, mentality, motivation);
                    players.add(player);
                }
                else if(names[2].equals("D")){
                    Defender player = new Defender(names[0], names[1], Integer.parseInt(names[3]),
                            Integer.parseInt(names[4]), Integer.parseInt(names[5]), Integer.parseInt(names[6]),
                            Integer.parseInt(names[7]), Integer.parseInt(names[8]), Integer.parseInt(names[9]),
                            number, stats, mentality, motivation);
                    players.add(player);
                }
                else if(names[2].equals("M")){
                    Midfielder player = new Midfielder(names[0], names[1], Integer.parseInt(names[3]),
                            Integer.parseInt(names[4]), Integer.parseInt(names[5]), Integer.parseInt(names[6]),
                            Integer.parseInt(names[7]), Integer.parseInt(names[8]), Integer.parseInt(names[9]),
                            number, stats, mentality,motivation);
                    players.add(player);
                }
                else{
                    Forward player = new Forward(names[0], names[1], Integer.parseInt(names[3]),
                            Integer.parseInt(names[4]), Integer.parseInt(names[5]), Integer.parseInt(names[6]),
                            Integer.parseInt(names[7]), Integer.parseInt(names[8]), Integer.parseInt(names[9]),
                            number, stats, mentality, motivation);
                    players.add(player);
                }
            }
        } catch (FileNotFoundException e){
            System.out.println("NIE");
        }
    }

    /**
     * This method is setting the starting lineup and the bench of the team.
     */
    public void set_lineup(){
        for(int i=0; i<11; i++){
            lineup.add(players.get(i));
        }
        set_default_lineup();
        for(int i=11; i<22; i++){
            bench.add(players.get(i));
        }
    }

    /**
     * This method is setting default lineup of the team (used before kick-offs)
     */
    public void set_default_lineup(){
        for(int i=0; i<11; i++){
            if(number==1){
                if(lineup.get(i) instanceof Goalkeeper) lineup.get(i).setPlace(pitch, lineup.get(i).getAttributes().getSide(), 5);
                else if(lineup.get(i) instanceof Defender) lineup.get(i).setPlace(pitch, lineup.get(i).getAttributes().getSide(), 4);
                else if(lineup.get(i) instanceof Midfielder) lineup.get(i).setPlace(pitch, lineup.get(i).getAttributes().getSide(), 3);
                else if(lineup.get(i) instanceof Forward) lineup.get(i).setPlace(pitch, lineup.get(i).getAttributes().getSide(), 3);
            }
            else if(number==2){
                if(lineup.get(i) instanceof Goalkeeper) lineup.get(i).setPlace(pitch, 4-lineup.get(i).getAttributes().getSide(), 0);
                else if(lineup.get(i) instanceof Defender) lineup.get(i).setPlace(pitch, 4-lineup.get(i).getAttributes().getSide(), 1);
                else if(lineup.get(i) instanceof Midfielder) lineup.get(i).setPlace(pitch, 4-lineup.get(i).getAttributes().getSide(), 2);
                else if(lineup.get(i) instanceof Forward) lineup.get(i).setPlace(pitch, 4-lineup.get(i).getAttributes().getSide(), 2);
            }
        }
    }

    /**
     * This method is setting corners taker
     * @return the taker of corners
     */
    private Player setCornersTaker(){
        Player taker = lineup.get(1);
        for(int i=2; i<11; i++){
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
        for(int i=2; i<11; i++){
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
        for(int i=2; i<11; i++){
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
        for(int i=1; i<11; i++){
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
            for(int i=1; i<11; i++){
                if(lineup.get(i)!=corners_taker){
                    if(lineup.get(i) instanceof Defender) lineup.get(i).setPlace(pitch, Math.abs(modX-lineup.get(i).getAttributes().getSide()), Math.abs(modY-2));
                    else if(lineup.get(i) instanceof Midfielder) lineup.get(i).setPlace(pitch, Math.abs(modX-lineup.get(i).getAttributes().getSide()), Math.abs(modY-1));
                    else if(lineup.get(i) instanceof Forward) lineup.get(i).setPlace(pitch, Math.abs(modX-lineup.get(i).getAttributes().getSide()), Math.abs(modY-1));
                }
            }
        }
        else{
            for(int i=1; i<11; i++){
                if(lineup.get(i) instanceof Defender) lineup.get(i).setPlace(pitch, Math.abs(modX-lineup.get(i).getAttributes().getSide()), Math.abs(modY-4));
                else if(lineup.get(i) instanceof Midfielder) lineup.get(i).setPlace(pitch, Math.abs(modX-lineup.get(i).getAttributes().getSide()), Math.abs(modY-4));
                else if(lineup.get(i) instanceof Forward) lineup.get(i).setPlace(pitch, Math.abs(modX-lineup.get(i).getAttributes().getSide()), Math.abs(modY-3));
            }
        }
    }

    /**
     * this method is making a substituion
     * @param player the subbed-off player
     */
    public void substitution(Player player){
        Player holder = player;
        for(int i=0; i<11; i++){
            if(lineup.get(i)==player){
                System.out.println(lineup.get(i).surname+" is subbed off. "+bench.get(i).surname+" changes him.");
                lineup.set(i, bench.get(i));
                bench.set(i, holder);
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
}