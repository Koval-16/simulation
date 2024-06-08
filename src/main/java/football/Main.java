package football;

public class Main {
    /**
     *
     * @param args simulation arguments. Expected to be in the following order:
     *             <ul>
     *                 <li>args[0]: Team 1 name (String)</li>
     *                 <li>args[1]: Team 2 name (String)</li>
     *                 <li>args[2]: Team 1 mentality (int, [-5;5], -5:defensive, 5:offensive)</li>
     *                 <li>args[3]: Team 2 mentality (int, [-5;5], -5:defensive, 5:offensive)</li>
     *                 <li>args[4]: Team 1 motivation (int, [-5;5], -5:bad, 5:good)</li>
     *                 <li>args[5]: Team 2 motivation (int, [-5;5], -5:bad, 5:good)</li>
     *                 <li>args[6]: Referee severity (int, [-5;5], -5:gentle, 5:strict)</li>
     *                 <li>args[7]: Weather (int, [0;2], 0:normal, 1:hot, 2:rain)</li>
     *             </ul>
     *             If any of int values are invalid, they will be set to default value = 0.
     *             If the team with given name doesn't exist, the match won't happen.
     */
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