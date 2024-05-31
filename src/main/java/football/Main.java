package football;

public class Main {
    public static void main(String[] args){
        String team1_name = args[0];
        String team2_name = args[1];
        int t1_mentality = Integer.parseInt(args[2]);
        int t2_mentality = Integer.parseInt(args[3]);
        int t1_motivation = Integer.parseInt(args[4]);
        int t2_motivation = Integer.parseInt(args[5]);
        int referee_strict = Integer.parseInt(args[6]);
        int weather = Integer.parseInt(args[7]);
        Match match = new Match(team1_name, team2_name, t1_mentality, t2_mentality,t1_motivation,t2_motivation,referee_strict,weather);
        match.simulate();
        MatchStats stats = new MatchStats(match);
    }
}