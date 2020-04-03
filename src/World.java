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
    private double bias;
    private double k;

    /**
     * Constructor
     * @param turns number of tunes of the simulation to run
     * @param nAgents number of agents
     */
    public World(int turns, int nAgents, double bias, double k){
        this.bias = bias;
        this.k = k;
        // creates agents
        for(int i = 0; i < nAgents; i++){
            agents.add(new Agent(k));
        }
        // run the simulation
        int speakertime = 0;
        int speaker = 1;
        for(int i = 0; i < turns; i++){
            System.out.println(i);
            doTurn(speaker);
            speakertime++;
            if (speakertime > 100){
                if (speaker == 1){
                    speaker = 2;
                } else {
                    speaker = 1;
                }
                speakertime = 0;
            }
        }
        output();
    }

    /**
     * Advance one turn and make the actors decide if they are going to tweet
     */
    private void doTurn(int speaker){
        int tweetcountp1 = 0;
        int tweetcountp2 = 0;
        double baserate = -9;
        for(Agent a : agents){
            if (speaker == 1) {
                tweetcountp1 = tweetcountp1 + a.makeTweet(baserate+bias);
                tweetcountp2 = tweetcountp2 + a.makeTweet(baserate);
            } else {
                tweetcountp1 = tweetcountp1 + a.makeTweet(baserate);
                tweetcountp2 = tweetcountp2 + a.makeTweet(baserate+bias);
            }
        }
        tweetsp1.add(tweetcountp1);
        tweetsp2.add(tweetcountp2);
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
