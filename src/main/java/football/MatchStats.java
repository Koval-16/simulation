package football;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Class MatchStats saves stats of the match into files.
 */
public class MatchStats {
    Match match;
    String match_time;
    String file_name;

    /**
     * The constructor of the class.
     * @param match match
     */
    public MatchStats(Match match){
        this.match = match;
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter m_time = DateTimeFormatter.ofPattern("ddMMyyyy_HHmmssSSSS");
        this.match_time = now.format(m_time);
        this.file_name = match.team1.getName()+"_"+match.team2.getName()+"_"+match_time;
    }

    /**
     * Method stats_to_file saves all stats into the file.
     * @param match match
     */
    public void stats_to_file(Match match){
        String directoryPath = "Matches/"+file_name;
        File directory = new File(directoryPath);
        if(!directory.exists()){
            directory.mkdirs();
        }
        try{
            File file = new File(directoryPath+"/"+(match.getTime()/60)+"_"+file_name+".txt");
            file.createNewFile();

        } catch (IOException e){
            e.printStackTrace();
        }
        try{
            FileWriter file = new FileWriter(directoryPath+"/"+(match.getTime()/60)+"_"+file_name+".txt");
            file.write(String.format("%-20s %5s %5s %6s %3s %2s %3s %3s %3s %3s %2s %2s\n","Team Name","Goals","Ball%","Sh(oT)","Pas","FK","Cor","Pen","Off","Fou","YC","RD"));
            for(Team team: match.teams){
                file.write(String.format("%-20s %5d %5.2f %2d(%2d) %3d %2d %3d %3d %3d %2d %2d %2d\n",team.getName(),
                        team.getStats().getGoals(),team.getStats().getBall_possession()*100,team.getStats().getShoots(),
                        team.getStats().getShoots_on_target(), team.getStats().getPasses(), team.getStats().getFree_kicks(),
                        team.getStats().getCorners(), team.getStats().getPenalties(), team.getStats().getOffsides(),
                        team.getStats().getFouls(),team.getStats().getYellow_cards(),team.getStats().getRed_cards()));
            }
            file.write("\n");
            for(Team team: match.teams){
                file.write(team.getName()+"\n");
                file.write(String.format("%-20s %2s %2s %6s %6s %2s %6s %2s %2s %2s %2s %2s %2s\n","Name","MP","GS","Sh(oT)",
                        "Pa(Sc)","Dr","Dl(Wn)","Of","YC","RC","LP","IN","SV"));
                for(int i=0; i<team.getLineup().size(); i++){
                    file.write(String.format("%-20s %2d %2d %2d(%2d) %2d(%2d) %2d %2d(%2d) %2d %2d %2d %2d %2d %2d\n",
                            team.getLineup().get(i).surname,team.getLineup().get(i).getStats().getMinutes_played(), team.getLineup().get(i).getStats().getGoals(),
                            team.getLineup().get(i).getStats().getShoots(),team.getLineup().get(i).getStats().getShoots_on_target(),
                            team.getLineup().get(i).getStats().getPasses_attempts(),team.getLineup().get(i).getStats().getPasses_completed(),
                            team.getLineup().get(i).getStats().getDribbling_completed(),team.getLineup().get(i).getStats().getDuels_attempts(),
                            team.getLineup().get(i).getStats().getDuels_won(),team.getLineup().get(i).getStats().getOffsides(),
                            team.getLineup().get(i).getStats().getYellow_cards(),team.getLineup().get(i).getStats().getRed_cards(),
                            team.getLineup().get(i).getStats().getLost_possession(),team.getLineup().get(i).getStats().getInterceptions(),
                            team.getLineup().get(i).getStats().getSaves()));
                }
                for(int i=0; i<team.getBench().size(); i++){
                    if(team.getBench().get(i).getStats().getMinutes_played()>0){
                        file.write(String.format("%-20s %2d %2d %2d(%2d) %2d(%2d) %2d %2d(%2d) %2d %2d %2d %2d %2d %2d\n",
                                team.getBench().get(i).surname,team.getBench().get(i).getStats().getMinutes_played(),team.getBench().get(i).getStats().getGoals(),
                                team.getBench().get(i).getStats().getShoots(),team.getBench().get(i).getStats().getShoots_on_target(),
                                team.getBench().get(i).getStats().getPasses_attempts(),team.getBench().get(i).getStats().getPasses_completed(),
                                team.getBench().get(i).getStats().getDribbling_completed(),team.getBench().get(i).getStats().getDuels_attempts(),
                                team.getBench().get(i).getStats().getDuels_won(),team.getBench().get(i).getStats().getOffsides(),
                                team.getBench().get(i).getStats().getYellow_cards(),team.getBench().get(i).getStats().getRed_cards(),
                                team.getBench().get(i).getStats().getLost_possession(),team.getBench().get(i).getStats().getInterceptions(),
                                team.getBench().get(i).getStats().getSaves()));
                    }
                }
                for(int i=0; i<team.getRed_cards().size(); i++){
                    if(team.getRed_cards().get(i).getStats().getMinutes_played()>0){
                        file.write(String.format("%-20s %2d %2d %2d(%2d) %2d(%2d) %2d %2d(%2d) %2d %2d %2d %2d %2d %2d\n",
                                team.getRed_cards().get(i).surname,team.getRed_cards().get(i).getStats().getMinutes_played(),team.getRed_cards().get(i).getStats().getGoals(),
                                team.getRed_cards().get(i).getStats().getShoots(),team.getRed_cards().get(i).getStats().getShoots_on_target(),
                                team.getRed_cards().get(i).getStats().getPasses_attempts(),team.getRed_cards().get(i).getStats().getPasses_completed(),
                                team.getRed_cards().get(i).getStats().getDribbling_completed(),team.getRed_cards().get(i).getStats().getDuels_attempts(),
                                team.getRed_cards().get(i).getStats().getDuels_won(),team.getRed_cards().get(i).getStats().getOffsides(),
                                team.getRed_cards().get(i).getStats().getYellow_cards(),team.getRed_cards().get(i).getStats().getRed_cards(),
                                team.getRed_cards().get(i).getStats().getLost_possession(),team.getRed_cards().get(i).getStats().getInterceptions(),
                                team.getRed_cards().get(i).getStats().getSaves()));
                    }
                }
                file.write("\n");
            }
            file.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
