public class Match {

    private int time=0;
    private int event;
    Pitch football_pitch;
    Team team1;
    Team team2;

    public Match(){
        this.football_pitch = new Pitch(5,6);
        this.team1 = new Team(football_pitch,1);
        this.team2 = new Team(football_pitch,2);
    }

    public void simulate(){
        while(time<=5400){
            if(time==0 || time==2700){
                System.out.println("KICK OFF");
                team1.set_default_lineup();
                team2.set_default_lineup();
            }
            else{
                for(int i=1; i<11; i++){
                    team1.lineup.get(i).player_moving(football_pitch);
                    team2.lineup.get(i).player_moving(football_pitch);
                }
            }
            System.out.println(team1.lineup.get(8).surname+": "+team1.lineup.get(8).getPlace().getWidth()+"x"+team1.lineup.get(8).getPlace().getLength());
            System.out.println(team2.lineup.get(8).surname+": "+team2.lineup.get(8).getPlace().getWidth()+"x"+team2.lineup.get(8).getPlace().getLength());
            time = time+3;
        }
    }
}
