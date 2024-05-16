import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class Team {
    String name;
    List<Player> players;
    List<Player> lineup;
    List<Player> bench;
    private Pitch pitch;
    private int number;
    StatsTeam stats;

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
                            Integer.parseInt(names[10]), Integer.parseInt(names[11]), Integer.parseInt(names[12]));
                    players.add(player);
                }
                else if(names[2].equals("D")){
                    Defender player = new Defender(names[0], names[1], Integer.parseInt(names[3]),
                            Integer.parseInt(names[4]), Integer.parseInt(names[5]), Integer.parseInt(names[6]),
                            Integer.parseInt(names[7]), Integer.parseInt(names[8]), Integer.parseInt(names[9]),
                            Integer.parseInt(names[10]), Integer.parseInt(names[11]), Integer.parseInt(names[12]));
                    players.add(player);
                }
                else if(names[2].equals("M")){
                    Midfielder player = new Midfielder(names[0], names[1], Integer.parseInt(names[3]),
                            Integer.parseInt(names[4]), Integer.parseInt(names[5]), Integer.parseInt(names[6]),
                            Integer.parseInt(names[7]), Integer.parseInt(names[8]), Integer.parseInt(names[9]),
                            Integer.parseInt(names[10]), Integer.parseInt(names[11]), Integer.parseInt(names[12]));
                    players.add(player);
                }
                else{
                    Forward player = new Forward(names[0], names[1], Integer.parseInt(names[3]),
                            Integer.parseInt(names[4]), Integer.parseInt(names[5]), Integer.parseInt(names[6]),
                            Integer.parseInt(names[7]), Integer.parseInt(names[8]), Integer.parseInt(names[9]),
                            Integer.parseInt(names[10]), Integer.parseInt(names[11]), Integer.parseInt(names[12]));
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
                if(lineup.get(i) instanceof Goalkeeper) lineup.get(i).setPlace(pitch, lineup.get(i).side, 5);
                else if(lineup.get(i) instanceof Defender) lineup.get(i).setPlace(pitch, lineup.get(i).side, 4);
                else if(lineup.get(i) instanceof Midfielder) lineup.get(i).setPlace(pitch, lineup.get(i).side, 3);
                else if(lineup.get(i) instanceof Forward) lineup.get(i).setPlace(pitch, lineup.get(i).side, 3);
            }
            else if(number==2){
                if(lineup.get(i) instanceof Goalkeeper) lineup.get(i).setPlace(pitch, 4-lineup.get(i).side, 0);
                else if(lineup.get(i) instanceof Defender) lineup.get(i).setPlace(pitch, 4-lineup.get(i).side, 1);
                else if(lineup.get(i) instanceof Midfielder) lineup.get(i).setPlace(pitch, 4-lineup.get(i).side, 2);
                else if(lineup.get(i) instanceof Forward) lineup.get(i).setPlace(pitch, 4-lineup.get(i).side, 2);
            }
        }
    }

}