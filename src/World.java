import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * Main class of the simulation
 */

public class World {
    private ArrayList<Integer> tweetsp1 = new ArrayList<>();
    private ArrayList<Integer> tweetsp2 = new ArrayList<>();
    private ArrayList<Agent> agents = new ArrayList<>();
    private Random random = new Random();
    private int demWin = 0;
    private int repWin = 0;
    private int cCost = 0;
    private int dCost = 0;
    private int payoff = 0;
    private int sumDem = 0;
    private int sumRep = 0;

    /**
     * Constructor
     * @param turns number of tunes of the simulation to run
     */
    public World(int turns, int nDems, int nReps, int demAgg , int repAgg, int cCost, int dCost, int payoff){
        // creates agents
        for(int i = 0; i < nDems; i++){
            agents.add(new SimpleModel(true, demAgg, 0, this));
            if (nReps > 0){
                agents.add(new SimpleModel(false, repAgg, 0, this));
                nReps--;
            }
        }

        for(int i = 0; i < nReps; i++){
            agents.add(new SimpleModel(false, repAgg, 0, this));
        }

        // run the simulation
        int speakertime = 0;
        int speaker = 1;
        for(int i = 0; i < turns; i++){
            doTurn(speaker);
            speakertime++;
            if (speakertime > 10){
                if (speaker == 1){
                    speaker = 2;
                } else {
                    speaker = 1;
                }
                speakertime = 0;
            }
        }
        /*
        for(Agent a : agents){
            if (a.getDem()){
                sumDem = sumDem + a.getRes();
            } else{
                sumRep = sumRep + a.getRes();
            }
        }
         */

        System.out.println( "Sum Democrats: " + sumDem);
        System.out.println( "Sum Republicans: " + sumRep);
        //System.out.println( demWin);
        //System.out.println( repWin);
        //output();
        System.exit(1);
    }

    // Getter methods
    public int getcCost(){return(cCost);};
    public int getdCost(){return(dCost);};
    public int getSumDem(){return(sumDem);};
    public int getSumRep(){return(sumRep);};

    /**
     * Returns an agent of opposite partisanship, that is not currently twitting and does not have a parter already. Null if one cannot be found
     * @param dem If tune then agent is a democrat, if false then republican
     **/
    public Agent getFighter(boolean dem){
        Agent agent = null;
        int i = 0;
        if (dem) {
            while(agent == null || agent.getDem() || agent.getPartner() != null || agent.getTwitting() != 0) {
                agent = agents.get(random.nextInt(agents.size()));
                i++;
                if (i > agents.size()*10){
                    return null;
                }
            }
        } else {
            while(agent == null || !agent.getDem() || agent.getPartner() != null || agent.getTwitting() != 0) {
                agent = agents.get(random.nextInt(agents.size()));
                i++;
                if (i > agents.size()*10){
                    return null;
                }
            }
        }
        return (agent);
    }

    /**
     * Advance one turn and make the actors decide if they are going to tweet
     * @param speaker 1 of democrat candidate is speaking, 2 if republican
     */
    private void doTurn(int speaker){
        int tweetcountdem = 0;
        int tweetcountrep = 0;
        for(Agent a : agents){
            if (a.getTweetP()) {
                tweetcountdem = tweetcountdem + a.makeTweet(true);
            } else{
                tweetcountrep = tweetcountrep + a.makeTweet(true);
            }
        }
        sumDem = tweetcountdem + sumDem;
        sumRep = tweetcountrep + sumRep;

        System.out.println("dem: " + tweetcountdem + " " + "rep: " + tweetcountrep);
        /*
        if (tweetcountdem > tweetcountrep){
            demWin++;
            for(Agent a : agents) {
                if (a.getDem()) {
                    a.changeRes(payoff);
                }
            }
        } else if(tweetcountdem < tweetcountrep){
            repWin++;
            for(Agent a : agents) {
                if (!a.getDem()) {
                    a.changeRes(payoff);
                }
            }
        }
        */
        tweetsp1.add(tweetcountdem);
        tweetsp2.add(tweetcountrep);
    }

    /**
     * Output the data of the simulation in a .csv file
     */
    private void output() {
        try {
            FileWriter writer = new FileWriter("test.csv2");
            writer.write("tweetCountPerson1");
            writer.write(';');
            writer.write("tweetCountPerson2");
            writer.write('\n');
            for (int i = 0; i < tweetsp1.size(); i++) {
                writer.write(tweetsp1.get(i).toString());
                writer.write(';');
                writer.write(tweetsp2.get(i).toString());
                writer.write('\n');
            }
            //for (Integer t : tweetsp1) {
            //    writer.write(t.toString());
            //    writer.write('\n');
            //}
            writer.close();
        } catch (IOException e) {
            // Oh god please no!
        }
    }
}
