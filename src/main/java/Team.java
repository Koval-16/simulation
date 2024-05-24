import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

public class Team {
    Random random = new Random();
    private String name;
    private List<Player> players;
    private List<Player> lineup;
    private List<Player> bench;
    private Pitch pitch;
    private int number;
    private StatsTeam stats;
    private Player corners_taker;
    private Player freekicks_taker;
    private Player penalties_taker;

    public Team(Pitch pitch, int number){
        this.number = number;
        this.pitch = pitch;
        this.name = choose_team();
        this.players = new ArrayList<>();
        this.lineup = new ArrayList<>();
        this.bench = new ArrayList<>();
        load_players();
        set_lineup();
        this.stats = new StatsTeam();
        this.corners_taker = setCornersTaker();
        this.freekicks_taker = setFreeKicksTaker();
        this.penalties_taker = setPenaltiesTaker();
    }

    private String choose_team(){
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
                            Integer.parseInt(names[10]), Integer.parseInt(names[11]), Integer.parseInt(names[12]),
                            number);
                    players.add(player);
                }
                else if(names[2].equals("D")){
                    Defender player = new Defender(names[0], names[1], Integer.parseInt(names[3]),
                            Integer.parseInt(names[4]), Integer.parseInt(names[5]), Integer.parseInt(names[6]),
                            Integer.parseInt(names[7]), Integer.parseInt(names[8]), Integer.parseInt(names[9]),
                            Integer.parseInt(names[10]), Integer.parseInt(names[11]), Integer.parseInt(names[12]),
                            number);
                    players.add(player);
                }
                else if(names[2].equals("M")){
                    Midfielder player = new Midfielder(names[0], names[1], Integer.parseInt(names[3]),
                            Integer.parseInt(names[4]), Integer.parseInt(names[5]), Integer.parseInt(names[6]),
                            Integer.parseInt(names[7]), Integer.parseInt(names[8]), Integer.parseInt(names[9]),
                            Integer.parseInt(names[10]), Integer.parseInt(names[11]), Integer.parseInt(names[12]),
                            number);
                    players.add(player);
                }
                else{
                    Forward player = new Forward(names[0], names[1], Integer.parseInt(names[3]),
                            Integer.parseInt(names[4]), Integer.parseInt(names[5]), Integer.parseInt(names[6]),
                            Integer.parseInt(names[7]), Integer.parseInt(names[8]), Integer.parseInt(names[9]),
                            Integer.parseInt(names[10]), Integer.parseInt(names[11]), Integer.parseInt(names[12]),
                            number);
                    players.add(player);
                }
            }
        } catch (FileNotFoundException e){
            System.out.println("NIE");
        }
    }

    public void set_lineup(){
        for(int i=0; i<11; i++){
            lineup.add(players.get(i));
        }
        set_default_lineup();
        for(int i=11; i<22; i++){
            bench.add(players.get(i));
        }
    }

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

    private Player setCornersTaker(){
        Player taker = lineup.get(1);
        for(int i=2; i<11; i++){
            if(lineup.get(i).getAttributes().getPassing()>=taker.getAttributes().getPassing()){
                taker = lineup.get(i);
            }
        }
        return taker;
    }

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

    private Player setPenaltiesTaker(){
        Player taker = lineup.get(1);
        for(int i=2; i<11; i++){
            if(lineup.get(i).getAttributes().getShooting()>=taker.getAttributes().getShooting()){
                taker = lineup.get(i);
            }
        }
        return taker;
    }

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
                if(corner.get(i).getAttributes().getHeading()>corner.get(j).getAttributes().getHeading()){
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

    public void substitution(Player player){
        Player holder = player;
        for(int i=0; i<11; i++){
            if(lineup.get(i)==player){
                lineup.set(i, bench.get(i));
                bench.set(i, holder);
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

}