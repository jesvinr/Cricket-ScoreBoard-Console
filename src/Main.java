import java.util.*;

/*
Punbjab Chennai
Smith Dhawan Agarwal Patel Singh
Dhoni Conway Rayudu Bravo Jadeja
0 1 0Wd 1 6 0 1
W 0 0 2 1 1
1 2 2 4 0 1
4 4 2 0Nb 6 4 W
0 0 1 4 2 0
0 1 6 W 0 0
4 4 6 0Wd 1 1 1
0Nb 4 2 1 0 6

Punbjab Chennai
Smith Dhawan Agarwal Patel Singh
Dhoni Conway Rayudu Bravo Jadeja
0 1 0Wd 1 6 0 1
W 0 0 2 1 1
1 2 2 4 0 1
4 4 2 0Nb 6 4 W
0 0 1 4 2 0
0 1 6 W 0 0
4 4 6 0Wd 1 1 1
0Nb 4 2 1 0 W
 */
public class Main {
    /*
    Cricket calculator
     */
    public static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {

        // getting input
        System.out.println("Enter inputs: ");
        String[] teamName = new String[2];
        teamName[0] = sc.next();
        teamName[1] = sc.next(); sc.nextLine();

        // players input
        String[] team1Players = new String[5];
        String[] team2Players = new String[5];
        // for team1 players
        for(int i=0;i<5;i++){
            team1Players[i]= sc.next();
        }
        sc.nextLine();
        // for team2 palyers
        for(int i=0;i<5;i++){
            team2Players[i] = sc.next();
        }
        sc.nextLine();

        // creating teamDTO
        TeamDto team1 = new TeamDto(teamName[0],team1Players);
        TeamDto team2 = new TeamDto(teamName[1],team2Players);
        // getting score for each team

        // team1 batting
        totalScoreOfEachTeam(team1,team2);
        //sc.nextLine();
        //System.out.println("Ending");
        //team2 batting
        totalScoreOfEachTeam(team2,team1);
        if(team1.teamTotalRun<team2.teamTotalRun){
            team1.winOrLost = "Lost";
            team2.winOrLost = "Win";
        }
        else{
            team2.winOrLost = "Lost";
            team1.winOrLost = "Win";
        }

        // First innings
        System.out.println("First innings");
        team1.totalScoreBatting();
        System.out.println();
        team1.battingScoreCard();
        team1.inExtra();
        System.out.println();
        team1.bowlingScoreCard();
        System.out.println();
        team1.playerStats();
        System.out.println();

        // second innings
        System.out.println("Second innings");
        team2.totalScoreBatting();
        System.out.println();
        team2.battingScoreCard();
        team2.inExtra();
        System.out.println();
        team2.bowlingScoreCard();
        System.out.println();
        team2.playerStats();
    }
    // t1 = batting team, t2 = bowling team
    public static void totalScoreOfEachTeam(TeamDto t1, TeamDto t2){
        //Scanner sc= new Scanner(System.in);
        //System.out.println("satrting");
        int overCount = 0;
        //int currBatsman = 0;

        // queue to find who bats currently and bowls currently
        Deque<Integer> batsmanQ = new ArrayDeque<>();
        batsmanQ.add(0);
        batsmanQ.add(1);
        Queue<Integer> bowlerQ = new LinkedList<>();
        bowlerQ.add(0);
        bowlerQ.add(1);

        boolean PlayerOut2 = false;
        //Loop
        while(overCount<5 && !PlayerOut2){
            int balls = 0;
            int totalBallPerOver = 6;
            int currBowler = bowlerQ.peek();
            //System.out.println(batsmanQ);
            while(balls<6){
                System.out.print(balls);
                balls++;
                int currBatsman = batsmanQ.peek();
                String runOnBallStr = sc.next();
                int runOnBall = 0;
                //System.out.print(team1.batsman[currBatsman]+" ");
                if(runOnBallStr.length()==3){
                    t1.teamTotalRun = t1.teamTotalRun+1;
                    t1.extras++;
                    totalBallPerOver++;
                    t2.bowlerMap.get(t2.bowler[currBowler])[2]++;
                    runOnBall = runOnBallStr.charAt(0)-'0';
                    balls--;
                }
                if(Character.isDigit(runOnBallStr.charAt(0))){
                    t2.bowlerMap.get(t2.bowler[currBowler])[1]++;
                    t1.batsmanMap.get(t1.batsman[currBatsman])[1]++;
                    runOnBall = Integer.parseInt(runOnBallStr.charAt(0)+"");

                    // batting team
                    t1.teamTotalRun = t1.teamTotalRun+runOnBall;

                    t1.batsmanMap.get(t1.batsman[currBatsman])[0] +=runOnBall;
                    //team1.batsmanMap.get(team1.batsman[currBatsman])[1]++;

                    // bowling team

                    t2.bowlerMap.get(t2.bowler[currBowler])[1]++;  // over balls
                    t2.bowlerMap.get(t2.bowler[currBowler])[2]+=runOnBall; // run per ball

                }
                else if(runOnBallStr.equals("W")){
                    t2.totalWicketsTeamTaken++;
                    t1.batsmanMap.get(t1.batsman[currBatsman])[1]++;
                    runOnBall = 0;
                    t1.batsmanMap.get(t1.batsman[currBatsman])[2] = 1;
                    t1.batsmanOut++;
                    t2.bowlerMap.get(t2.bowler[currBowler])[3]++;
                    t2.bowlerMap.get(t2.bowler[currBowler])[1]++;
                    if(t1.batsmanOut==2) {
                        PlayerOut2 = true;
                        if(balls>=6)
                        {
                            balls = 0;
                            t1.over++;
                            t2.bowlerMap.get(t2.bowler[currBowler])[0]++;
                            t2.bowlerMap.get(t2.bowler[currBowler])[1]=0;
                        }
                        t1.totalBalls = balls;
                        break;
                    }
                    batsmanQ.poll();
                    batsmanQ.addFirst(2);
                }

                //System.out.println(runOnBall);
                if(runOnBall%2==1){
                    batsmanQ.add(batsmanQ.poll());
                }
                //System.out.println(team1.batsman[currBatsman]+" ");
                if(t1.teamTotalRun>t2.teamTotalRun && t2.teamTotalRun!=0){
                    break;
                }
            }
            if(t1.batsmanOut==2)
                break;
            if(t2.bowlerMap.get(t2.bowler[currBowler])[1]==6){
                t2.bowlerMap.get(t2.bowler[currBowler])[1]=0;
            }
            if(t1.teamTotalRun>t2.teamTotalRun && t2.teamTotalRun!=0){
                PlayerOut2 = true;
                //t2.bowlerMap.get(t2.bowler[currBowler])[0];
                t2.bowlerMap.get(t2.bowler[currBowler])[1]=balls;
                continue;
            }

            //System.out.println(team1.batsmanMap.get(team1.batsman[currBatsman])[0]);
            sc.nextLine(); // for next over

            // batting team
            batsmanQ.add(batsmanQ.poll());

            // bowling team
            t2.bowlerMap.get(t2.bowler[currBowler])[0]++;
            t2.bowlerMap.get(t2.bowler[currBowler])[1]=0;
            bowlerQ.add(bowlerQ.poll());

            overCount++;
            t1.over = overCount;
        }
//        t1.totalScoreBatting();
//        t1.battingScoreCard();
//        t2.bowlingScoreCard();
    }
}