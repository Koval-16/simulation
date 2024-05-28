package football;

public class Main {
    public static void main(String[] args){
        String team1_name = args[0];
        String team2_name = args[1];
        int team1_mentality = Integer.parseInt(args[2]);
        int team2_mentality = Integer.parseInt(args[3]);
        int referee_strict = Integer.parseInt(args[4]);
        int weather = Integer.parseInt(args[5]);
        Match match = new Match(team1_name, team2_name, team1_mentality, team2_mentality,referee_strict,weather);
        match.simulate();
    }
}