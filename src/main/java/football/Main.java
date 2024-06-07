package football;

public class Main {
    public static void main(String[] args){
        String team1_name = args[0];
        String team2_name = args[1];
        int weather=0;
        try{
            if(Integer.parseInt(args[7])>=0 && Integer.parseInt(args[7])<=2){
                weather = Integer.parseInt(args[7]);
            }
        } catch (NumberFormatException e){
            weather = 0;
        }
        int[] parameters = new int[5];
        for(int i=2; i<7; i++){
            try{
                if(Integer.parseInt(args[i])>=-5 && Integer.parseInt(args[i])<=5){
                    parameters[i-2]=Integer.parseInt(args[i]);
                }
            } catch (NumberFormatException e){
                parameters[i-2] = 0;
            }
        }
        Match match = new Match(team1_name, team2_name, parameters[0], parameters[1],parameters[2],parameters[3],parameters[4],weather);
        match.simulate();
    }
}