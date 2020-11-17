import java.util.*;

/**
 * Main class of the simulation
 */

public class World {
    private ArrayList<Integer> tweetsp1 = new ArrayList<>(); // for storing running tally of tweets
    private ArrayList<Integer> tweetsp2 = new ArrayList<>(); // for storing running tally of tweets
    private ArrayList<Agent> agents = new ArrayList<>(); // for storing the agents
    private Random random = new Random(); // Random object for random number generation

    /**
     * Constructor
     * @param turns number of tunes of the simulation to run
     * @param nDems number of democrat agents
     * @param nReps number of republican agents
     * @param demAgg democrat aggression [0-100]
     * @param repAgg republican aggression [0-100]
     */
    public World(int turns, int nDems, int nReps, int demAgg , int repAgg, int demDef, int repDef, int demT, int repT, double demnet, double repnet){
        // creates agents, tries to interweave them
        for(int i = 0; i < nDems; i++){
            agents.add(new SimpleModel(true, demAgg, demDef, demT, demnet, this));
            if (nReps > 0){
                agents.add(new SimpleModel(false, repAgg, repDef, repT, repnet, this));
                nReps--;
            }
        }
        for(int i = 0; i < nReps; i++){
            agents.add(new SimpleModel(false, repAgg, repDef, repT, repnet, this));
        }

        // run the simulation
        int speakertime = 0;
        boolean speaker = true;
        for(int i = 0; i < turns; i++){
            doTurn(speaker);
            speakertime++;
            if (speakertime > 10){
                speaker = !speaker;
                speakertime = 0;
            }
        }

        // output simulation by simulation totals, mostly for debugging purposes
        System.out.println( "Sum Democrats: " + getTotalp1());
        System.out.println( "Sum Republicans: " + getTotalp2());
    }

    // Getter methods
    public int getRamdom(){return (random.nextInt(100)+1);}
    public int getRamdomLarge(){return (random.nextInt(10000)+1);}
    public int getTotalp1(){
        int tweetn = 0;
        for(Integer tweets: tweetsp1){
            tweetn = tweets + tweetn;
        }
        return (tweetn);
    }
    public int getTotalp2(){
        int tweetn = 0;
        for(Integer tweets: tweetsp2){
            tweetn = tweets + tweetn;
        }
        return (tweetn);
    }

    /**
     * Returns an agent of opposite partisanship, that is not currently twitting and does not have a partner already. Null if one cannot be found
     * @param dem If tune then agent is a democrat, if false then republican
     * @return Agent of opposite partisanship
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
     * @param speaker true if democrat candidate is speaking, false if republican
     */
    private void doTurn(boolean speaker){
        int tweetcountdem = 0;
        int tweetcountrep = 0;
        for(Agent a : agents){
            if (a.getTweetP()) {
                tweetcountdem = tweetcountdem + a.makeTweet(speaker);
            } else{
                tweetcountrep = tweetcountrep + a.makeTweet(speaker);
            }
        }

        // output turn by turn totals, mostly for debugging purposes
        //System.out.println("dem: " + tweetcountdem + " " + "rep: " + tweetcountrep);

        // adding the new total of tweets
        tweetsp1.add(tweetcountdem);
        tweetsp2.add(tweetcountrep);
    }
}