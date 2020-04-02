import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
/**
 * Main class of the simulation
 */

public class World {
    private ArrayList<Integer> tweets = new ArrayList<>();
    private ArrayList<Agent> agents = new ArrayList<>();
    private int turns;

    /**
     * Constructor
     * @param turns number of tunes of the simulation to run
     * @param nAgents number of agents
     */
    public World(int turns, int nAgents){
        this.turns = turns;
        // creates agents
        for(int i = 0; i < nAgents; i++){
            agents.add(new Agent());
        }
        // run the simulation
        for(int i = 0; i < turns; i++){
            doTurn();
        }
        output();
    }

    // Getter methods
    public ArrayList<Integer> getTweets(){return(tweets);}

    /**
     * Advance one turn and make the actors decide if they are going to tweet
     */
    private void doTurn(){
        int tweetcount = 0;
        for(Agent a : agents){
            tweetcount = tweetcount + a.makeTweet();
        }
        tweets.add(tweetcount);
    }

    /**
     * Output the data of the simulation in a .csv file
     */
    private void output() {
        try {
            FileWriter writer = new FileWriter("test.csv2");
            writer.write("tweetCount");
            writer.write('\n');
            for (Integer t : tweets) {
                //System.out.println(t);
                writer.write(t.toString());
                writer.write('\n');
            }
            writer.close();
        } catch (IOException e) {
            // Oh god please no!
        }
    }
}
