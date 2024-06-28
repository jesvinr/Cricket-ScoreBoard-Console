import java.util.LinkedHashMap;
import java.util.Map;

public class TeamDto {
    String teamName;
    String[] players;
    int teamTotalRun;
    String[] batsman;
    String[] bowler;
    Map<String, int[]> batsmanMap; // runs and balls and out
    Map<String,int[]> bowlerMap; // overs balls and runs givin and wickets taken
    int batsmanOut;
    String winOrLost;
    int over;
    int extras;
    int totalWicketsTeamTaken;
    int totalBalls = 0;

    TeamDto(String teamName,String[] players){
        extras = 0;
        over = 0;
        winOrLost = null;
        batsmanOut = 0;
        this.teamName = teamName;
        this.players = players;
        teamTotalRun = 0;
        batsman = new String[3];
        bowler = new String[2];
        batsmanMap = new LinkedHashMap<>();
        bowlerMap = new LinkedHashMap<>();
        totalWicketsTeamTaken = 0;

        for(int i=0;i<5;i++){
            if(i<3) {
                batsman[i] = players[i];
                batsmanMap.put(batsman[i], new int[3]);
            }
            else{
                bowler[i-3] = players[i];
                bowlerMap.put(bowler[i-3],new int[4]);
            }
        }
    }

    public void totalScoreBatting(){
        System.out.println(teamTotalRun+" / "+batsmanOut+" ("+winOrLost+", "+over+"."+totalBalls+") ");
    }

    public void battingScoreCard(){
        for(int i=0;i<3;i++){
            if(batsmanMap.get(batsman[i])[2]!=1)
                System.out.println(batsman[i]+"* - "+batsmanMap.get(batsman[i])[0]+"( "
                    +batsmanMap.get(batsman[i])[1]+" balls )");
            else{
                System.out.println(batsman[i]+" - "+batsmanMap.get(batsman[i])[0]+"( "
                        +batsmanMap.get(batsman[i])[1]+" balls )");
            }
        }
    }

    public void bowlingScoreCard(){
        for(int i=0;i<2;i++){
            if(bowlerMap.get(bowler[i])[1]==11)
                bowlerMap.get(bowler[i])[1] = 5;
            System.out.println(bowler[i]+" "+bowlerMap.get(bowler[i])[0]+"."+bowlerMap.get(bowler[i])[1]+
                    " "+bowlerMap.get(bowler[i])[2]+" / "+bowlerMap.get(bowler[i])[3]);
        }
    }

    public void playerStats(){
        for(int i=0;i<5;i++){
            if(i<3) {
                int runs = batsmanMap.get(batsman[i])[0];
                int balls = batsmanMap.get(batsman[i])[1];
                float sr = (int)100*((float)runs/balls);
                float c = (100*((float)runs/teamTotalRun));
                int srInt= (int)Math.ceil(sr);
                int cInt = (int)Math.ceil(c);
                System.out.println(batsman[i]+" - "+runs+
                        "("+balls+") "+srInt+"% "+ cInt +" "+runs+"/"+teamTotalRun);
            }
            else{
                int runs = bowlerMap.get(bowler[i-3])[2];
                int wicket = bowlerMap.get(bowler[i-3])[3];
                int c = bowlerMap.get(bowler[i-3])[3]/totalWicketsTeamTaken;
                String overs = bowlerMap.get(bowler[i-3])[0]+""+bowlerMap.get(bowler[i-3])[1];
                if(wicket==0)
                    System.out.println(bowler[i-3]+" "+runs+"/"+wicket+" ("+overs+") "+"W "+wicket+
                            " "+0);
                else
                    System.out.println(bowler[i-3]+" "+runs+"/"+wicket+" ("+overs+") "+"W "+wicket+
                        " "+(c/totalWicketsTeamTaken)*100);
            }
        }
    }

    public void inExtra(){
        System.out.println("Innings extra: "+ extras);
    }
}
