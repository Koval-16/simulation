public class Goalkeeper extends Player{

    public Goalkeeper(String name, String surname, int side, int shooting, int dribbling, int speed, int passing,
                      int defending, int heading, int aggression, int risk_taking, int intelligence, int team_number) {
        super(name, surname, side, shooting, dribbling, speed, passing, defending,
                heading, aggression, risk_taking, intelligence, team_number);
    }
    public void player_saving(){
        System.out.println(surname+" SAVES");
    }
}