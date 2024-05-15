public class Goalkeeper extends Player{
    public Goalkeeper(String name, String surname) {
        super(name, surname);
    }
    public void player_saving(){
        System.out.println(surname+" SAVES");
    }
}