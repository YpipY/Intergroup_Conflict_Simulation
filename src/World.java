import java.util.*;

/**
 * Main class of the simulation
 */

public class World {
    private ArrayList<Integer> tweetsp1 = new ArrayList<>(); // for storing running tally of tweets
    private ArrayList<Integer> tweetsp2 = new ArrayList<>(); // for storing running tally of tweets
    private ArrayList<Agent> agents = new ArrayList<>(); // for storing the agents
    private Random random = new Random(); // Random object for random number generation
    private boolean nortb; // switch for the normal tweet behavior, true = on
    private boolean aggtb; // switch for the aggressive tweet behavior, true = on
    private boolean retb; // switch for the retweet behavior, true = on
    private double speakera; // a value for the impact of speaker
    private double speakerb; // b value for the impact of speaker
    private int interruptsv; // value for the impact of interrupts
    private int memev; // value for the impact of memes
    private int interestdecayv; // value used in the interest decay function
    private int retweets; // counter for the number of retweets. For testing
    private int normaltweets; // count for the number of normal tweets (non retweets). For testing
    private int tweetcountdemtotal; // total number of tweets about democrats
    private int tweetcountreptotal; // total number of tweets about republicans
    private int tweetsfromdemadem; // total number of tweets from democrat agents about democrat
    private int tweetsfromrepadem; // total number of tweets from republican agents about democrat
    private int tweetsfromdemarep; // total number of tweets from democrat agents about republican
    private int tweetsfromreparep; // total number of tweets from republican agents about republican
    private Debate debate; // The debate data object
    private int turn = 0; // turn counter for debugging

    /**
     * Constructor
     * @param turns number of tunes of the simulation to run
     * @param nDems number of democrat agents
     * @param nReps number of republican agents
     * @param demAgg democrat aggression [0-100]
     * @param repAgg republican aggression [0-100]
     * @param demDef defence modifier on aggression of democrats
     * @param repDef defence modifier on aggression of republicans
     * @param demT likelihood of democrats deciding to tweet
     * @param repT likelihood of republicans deciding to tweet
     * @param demnet connectedness of democrats, increases likelihood of retweet
     * @param repnet connectedness of republicans, increases likelihood of retweet
     * @param aggtb
     * @param retb
     * @param nortb
     * @param useex
     * @param speakera
     * @param speakerb
     * @param interruptsv
     * @param memev
     * @param interestdecayv
     * @param debatename
     */
    public World(int turns, int nDems, int nReps, int demAgg , int repAgg, int demDef, int repDef, int demT, int repT, double demnet, double repnet, boolean nortb, boolean aggtb, boolean retb, boolean useex, double speakera, double speakerb, int interruptsv, int memev, int interestdecayv, String debatename){
        // initialising the tweet counters
        retweets = 0;
        normaltweets = 0;
        tweetcountdemtotal = 0;
        tweetcountreptotal = 0;
        tweetsfromdemadem = 0;
        tweetsfromrepadem = 0;
        tweetsfromdemarep = 0;
        tweetsfromreparep = 0;

        // initialising tweet behavior options
        this.nortb = nortb;
        this.aggtb = aggtb;
        this.retb = retb;
        this.speakera = speakera;
        this.speakerb = speakerb;
        this.interruptsv = interruptsv;
        this.memev = memev;
        this.interestdecayv = interestdecayv;

        // creates agents, with or without external factors, tries to interweave them
        if (useex) {
            debate = new Debate(debatename);
            turns = debate.getEnd().get(debate.getEnd().size()-1);
            for (int i = 0; i < nDems; i++) {
                agents.add(new FullModelExternal(true, demAgg, demDef, demT, demnet, this));
                if (nReps > 0) {
                    agents.add(new FullModelExternal(false, repAgg, repDef, repT, repnet, this));
                    nReps--;
                }
            }
            for (int i = 0; i < nReps; i++) {
                agents.add(new FullModelExternal(false, repAgg, repDef, repT, repnet, this));
            }
            // run the simulation
            int speakertime = 0;
            boolean speaker = true;
            for(int i = 0; i < turns; i++){
                debate.advanceTime(i);
                doTurn(speaker);
                speakertime++;
                if (speakertime > 10){
                    speaker = !speaker;
                    speakertime = 0;
                }
            }
        } else {
            for (int i = 0; i < nDems; i++) {
                agents.add(new FullModelNoExternal(true, demAgg, demDef, demT, demnet, this));
                if (nReps > 0) {
                    agents.add(new FullModelNoExternal(false, repAgg, repDef, repT, repnet, this));
                    nReps--;
                }
            }
            for (int i = 0; i < nReps; i++) {
                agents.add(new FullModelNoExternal(false, repAgg, repDef, repT, repnet, this));
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
        }

        // output simulation by simulation totals, mostly for debugging purposes
        System.out.println( "Sum Democrats: " + tweetcountdemtotal);
        System.out.println( "Sum Republicans: " + tweetcountreptotal);
        /*
        System.out.println(retweets);
        System.out.println(normaltweets);
        System.out.println(tweetcountdemtotal);
        System.out.println(tweetcountreptotal);
        System.out.println(tweetsfromdemadem);
        System.out.println(tweetsfromrepadem);
        System.out.println(tweetsfromdemarep);
        System.out.println(tweetsfromreparep);
        */
    }

    // Getter methods
    public int getRamdom(){return (random.nextInt(100)+1);}
    public int getRamdomLarge(){return (random.nextInt(1000000)+1);}
    public int getRetweets(){return (retweets);}
    public int getNormalTweets(){return (normaltweets);}
    public boolean getNortb(){return (nortb);}
    public boolean getAggtb(){return (aggtb);}
    public boolean getRetb(){return (retb);}
    public int getTweetCountDemTotal(){ return (tweetcountdemtotal);}
    public int getTweetCountRepTotal(){ return (tweetcountreptotal);}
    public int getTweetsFromDemADem(){ return (tweetsfromdemadem);}
    public int getTweetsFromRepADem(){ return (tweetsfromrepadem);}
    public int getTweetsFromDemARep(){ return (tweetsfromdemarep);}
    public int getTweetsFromRepARep(){ return (tweetsfromreparep);}
    public Debate getDebate(){return (debate);}
    public double getSpeakera(){ return (speakera);}
    public double getSpeakerb(){ return (speakerb);}
    public int getInterruptsv(){ return (interruptsv);}
    public int getMemev(){ return (memev);}
    public int getInterestdecayv(){ return (interestdecayv);}

    /**
     * Add to the number of retweets
     * @param retweets Amount of retweets it should be added
     **/
    public void addReteets(int retweets){
        this.retweets = this.retweets + retweets;
    }

    /**
     * Add to the number of normaltweets
     * @param normaltweets Amount of retweets it should be added
     **/
    public void addNormalTweets(int normaltweets){
        this.normaltweets = this.normaltweets + normaltweets;
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
            int x = a.makeTweet(speaker);
            if (a.getTweetP()) {
                tweetcountdem = tweetcountdem + x;
                if (a.getDem()) {
                    tweetsfromdemadem = tweetsfromdemadem + x;
                } else{
                    tweetsfromrepadem = tweetsfromrepadem + x;
                }
            } else{
                tweetcountrep = tweetcountrep + x;
                if (a.getDem()) {
                    tweetsfromdemarep = tweetsfromdemarep + x;
                } else{
                    tweetsfromreparep = tweetsfromreparep + x;
                }
            }
        }

        //output turn by turn totals, mostly for debugging purposes
        System.out.println("turn:" + turn + " "  + "dem:" + tweetcountdem + " " + "rep:" + tweetcountrep);
        turn++;

        // adding the new total of tweets
        tweetsp1.add(tweetcountdem);
        tweetsp2.add(tweetcountrep);
        tweetcountdemtotal = tweetcountdemtotal + tweetcountdem;
        tweetcountreptotal = tweetcountreptotal + tweetcountrep;
    }
}