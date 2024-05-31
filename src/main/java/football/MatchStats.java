package football;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MatchStats {
    Match match;

    public MatchStats(Match match){
        this.match = match;
        stats_to_file(match);
    }

    public void stats_to_file(Match match){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter m_time = DateTimeFormatter.ofPattern("ddMMyyyy_HHmmssSSSS");
        String match_time = now.format(m_time);
        String file_name = match.team1.getName()+"_"+match.team2.getName()+"_"+match.team1.getStats().getGoals()+"_"+match.team2.getStats().getGoals()+"_"+match_time;
        System.out.println(file_name);
        try{
            File file = new File(file_name+".txt");
            file.createNewFile();

        } catch (IOException e){
            e.printStackTrace();
        }
        try{
            FileWriter file = new FileWriter(file_name+".txt");
            for(Team team: match.teams){
                file.write(team.getName()+"\t"+team.getStats().getGoals()+"\t"+team.getStats().getBall_possession()+
                        "\t"+team.getStats().getShoots()+"("+team.getStats().getShoots_on_target()+")\t"+
                        team.getStats().getPasses()+"\t"+team.getStats().getFree_kicks()+"\t"+team.getStats().getCorners()+
                        "\t"+team.getStats().getPenalties()+"\t"+team.getStats().getOffsides()+"\t"+team.getStats().getFouls()+
                        "\t"+team.getStats().getYellow_cards()+"\t"+team.getStats().getRed_cards()+"\n");
            }
            file.write("\n");
            for(Team team: match.teams){
                for(int i=0; i<11; i++){
                    file.write(team.getLineup().get(i).surname+"\t"+team.getLineup().get(i).getStats().getMinutes_played()+"\t"+
                            team.getLineup().get(i).getStats().getShoots()+"("+team.getLineup().get(i).getStats().getShoots_on_target()+
                            ")\t"+team.getLineup().get(i).getStats().getPasses_attempts()+"("+team.getLineup().get(i).getStats().getPasses_completed()+
                            ")\t"+team.getLineup().get(i).getStats().getDribbling_completed()+"\t"+team.getLineup().get(i).getStats().getDuels_attempts()+
                            "("+team.getLineup().get(i).getStats().getDuels_won()+")\t"+team.getLineup().get(i).getStats().getOffsides()+"\t"+
                            team.getLineup().get(i).getStats().getYellow_cards()+"\t"+team.getLineup().get(i).getStats().getRed_cards()+"\t"+
                            team.getLineup().get(i).getStats().getLost_possession()+"\t"+team.getLineup().get(i).getStats().getInterceptions()+"\t"+
                            team.getLineup().get(i).getStats().getSaves()+"\n");
                }
                for(int i=0; i<11; i++){
                    if(team.getBench().get(i).getStats().getMinutes_played()>0){
                        file.write(team.getBench().get(i).surname+"\t"+team.getBench().get(i).getStats().getMinutes_played()+"\t"+
                                team.getBench().get(i).getStats().getShoots()+"("+team.getBench().get(i).getStats().getShoots_on_target()+
                                ")\t"+team.getBench().get(i).getStats().getPasses_attempts()+"("+team.getBench().get(i).getStats().getPasses_completed()+
                                ")\t"+team.getBench().get(i).getStats().getDribbling_completed()+"\t"+team.getBench().get(i).getStats().getDuels_attempts()+
                                "("+team.getBench().get(i).getStats().getDuels_won()+")\t"+team.getBench().get(i).getStats().getOffsides()+"\t"+
                                team.getBench().get(i).getStats().getYellow_cards()+"\t"+team.getBench().get(i).getStats().getRed_cards()+"\t"+
                                team.getBench().get(i).getStats().getLost_possession()+"\t"+team.getBench().get(i).getStats().getInterceptions()+"\t"+
                                team.getBench().get(i).getStats().getSaves()+"\n");
                    }
                }
            }
            file.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
