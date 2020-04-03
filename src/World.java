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

    /**
     * Constructor
     * @param turns number of tunes of the simulation to run
     * @param nAgents number of agents
     */
    public World(int turns, int nAgents, int demAgg , int repAgg){
        // creates agents
        for(int i = 0; i < nAgents/2; i++){
            agents.add(new SimpleModel(true, demAgg, 0, this));
            agents.add(new SimpleModel(false, repAgg, 0, this));
        }

        // run the simulation
        int speakertime = 0;
        int speaker = 1;
        for(int i = 0; i < turns; i++){
            //System.out.println(i);
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

        int sumDem = 0;
        int sumRep = 0;
        for(Agent a : agents){
            if (a.getDem()){
                sumDem = sumDem + a.getRes();
            } else{
                sumRep = sumRep + a.getRes();
            }
        }
        System.out.println( "Sum Democrats: " + sumDem);
        System.out.println( "Sum Republicans: " + sumRep);
        System.out.println( demWin);
        System.out.println( repWin);
        //output();
    }

    /**
     * Returns an agent of opposite partisanship
     **/
    public Agent getFighter(boolean dem){
        Agent agent = null;
        if (dem) {
            while(agent == null || agent.getDem()) {
                agent = agents.get(random.nextInt(agents.size()));
            }
        } else {
            while(agent == null || !agent.getDem()) {
                agent = agents.get(random.nextInt(agents.size()));
            }
        }
        return (agent);
    }

    /**
     * Advance one turn and make the actors decide if they are going to tweet
     */
    private void doTurn(int speaker){
        int tweetcountdem = 0;
        int tweetcountrep = 0;
        for(Agent a : agents){
            if (a.getDem()) {
                tweetcountdem = tweetcountdem + a.makeTweet(true);
            } else{
                tweetcountrep = tweetcountrep + a.makeTweet(true);
            }
        }
        System.out.println("dem: " + tweetcountdem + " " + "rep: " + tweetcountrep);
        if (tweetcountdem > tweetcountrep){
            demWin++;
            for(Agent a : agents) {
                if (a.getDem()) {
                    a.changeRes(10);
                }
            }
        } else if(tweetcountdem < tweetcountrep){
            repWin++;
            for(Agent a : agents) {
                if (!a.getDem()) {
                    a.changeRes(10);
                }
            }
        }
        //tweetsp1.add(tweetcountp1);
        //tweetsp2.add(tweetcountp2);
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
