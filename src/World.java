import javax.swing.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * Main class of the simulation
 */

public class World {
    private ArrayList<Integer> tweetsp1 = new ArrayList<>(); // for storing running tally of tweets
    private ArrayList<Integer> tweetsp2 = new ArrayList<>(); // for storing running tally of tweets
    private ArrayList<Integer> retweetsfull = new ArrayList<>(); // for storing running tally of tweets
    private ArrayList<Integer> normaltweetsfull = new ArrayList<>(); // for storing running tally of tweets
    private ArrayList<Integer> tweetsfromdemademfull = new ArrayList<>(); // for storing running tally of tweets
    private ArrayList<Integer> tweetsfromrepademfull = new ArrayList<>(); // for storing running tally of tweets
    private ArrayList<Integer> tweetsfromdemarepfull = new ArrayList<>(); // for storing running tally of tweets
    private ArrayList<Integer> tweetsfromreparepfull = new ArrayList<>(); // for storing running tally of tweets
    private ArrayList<Agent> agents = new ArrayList<>(); // for storing the agents
    private Random random = new Random(); // Random object for random number generation
    private int nDems; //number of democrat agents
    private int nReps; //number of republican agents
    private int demAgg; // democrat aggression [0-1000000]
    private int repAgg; // republican aggression [0-1000000]
    private int demDef; // defence modifier on aggression of democrats
    private int repDef; // defence modifier on aggression of republicans
    private int demT; // likelihood of democrats deciding to tweet
    private int repT; // likelihood of republicans deciding to tweet
    private double demnet; // connectedness of democrats, increases likelihood of retweet
    private double repnet; // connectedness of republicans, increases likelihood of retweet
    private boolean nortb; // switch for the normal tweet behavior, true = on
    private boolean aggtb; // switch for the aggressive tweet behavior, true = on
    private boolean retb; // switch for the retweet behavior, true = on
    private double speakera; // a value for the impact of speaker
    private double speakerb; // b value for the impact of speaker
    private int interruptsv; // value for the impact of interrupts
    private int memev; // value for the impact of memes
    private int interestdecayv; // value used in the interest decay function
    private int retweets; // counter for the number of retweets
    private int normaltweets; // count for the number of normal tweets (non retweets)
    private int tweetcountdemtotal; // total number of tweets about democrats
    private int tweetcountreptotal; // total number of tweets about republicans
    private int tweetsfromdemadem; // total number of tweets from democrat agents about democrat
    private int tweetsfromrepadem; // total number of tweets from republican agents about democrat
    private int tweetsfromdemarep; // total number of tweets from democrat agents about republican
    private int tweetsfromreparep; // total number of tweets from republican agents about republican
    private int tweetsfromdemademlast; // total number of tweets from democrat agents about democrat last turn
    private int tweetsfromrepademlast; // total number of tweets from republican agents about democrat last turn
    private int tweetsfromdemareplast; // total number of tweets from democrat agents about republican last turn
    private int tweetsfromrepareplast; // total number of tweets from republican agents about republican last turn
    private Debate debate; // the debate data object
    private int turn = 0; // turn counter for debugging

    /**
     * Constructor
     * @param turns number of tunes of the simulation to run
     * @param nDems number of democrat agents
     * @param nReps number of republican agents
     * @param demAgg democrat aggression [0-1000000]
     * @param repAgg republican aggression [0-1000000]
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
     * @param savefliename
     */
    public World(int turns, int nDems, int nReps, int demAgg , int repAgg, int demDef, int repDef, int demT, int repT, double demnet, double repnet, boolean nortb, boolean aggtb, boolean retb, boolean useex, double speakera, double speakerb, int interruptsv, int memev, int interestdecayv, String debatename, String savefliename){
        // initialising the tweet counters
        retweets = 0;
        normaltweets = 0;
        tweetcountdemtotal = 0;
        tweetcountreptotal = 0;
        tweetsfromdemadem = 0;
        tweetsfromrepadem = 0;
        tweetsfromdemarep = 0;
        tweetsfromreparep = 0;
        tweetsfromdemademlast= 0;
        tweetsfromdemademlast = 0;
        tweetsfromdemademlast = 0;
        tweetsfromdemademlast = 0;

        // initialising tweet behavior options
        this.nDems = nDems;
        this.nReps = nReps;
        this.demAgg = demAgg;
        this.repAgg = repAgg;
        this.demDef = demDef;
        this.repDef = repDef;
        this.demT = demT;
        this.repT = repT;
        this.demnet = demnet;
        this.repnet = repnet;
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
            output(savefliename); // save the data
            // without external factors
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
        System.out.println( "Total: " + (tweetcountdemtotal + tweetcountreptotal));
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
    public int getRamdomFight(){return (random.nextInt(1000)+1);}
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
     * resents the tally of retweets and normal tweets
     **/
    public void resetReandtweets(){
        retweets = 0;
        normaltweets = 0;
    }

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
        //System.out.println("turn:" + turn + " "  + "dem:" + tweetcountdem + " " + "rep:" + tweetcountrep);
        //turn++;

        // adding new totals needed in the retweet calculations
        tweetcountdemtotal = tweetcountdemtotal + tweetcountdem;
        tweetcountreptotal = tweetcountreptotal + tweetcountrep;

        // adding data to the arrays used in the output
        tweetsp1.add(tweetcountdem);
        tweetsp2.add(tweetcountrep);
        retweetsfull.add(retweets);
        normaltweetsfull.add(normaltweets);
        tweetsfromdemademfull.add(tweetsfromdemadem-tweetsfromdemademlast);
        tweetsfromrepademfull.add(tweetsfromrepadem-tweetsfromrepademlast);
        tweetsfromdemarepfull.add(tweetsfromdemarep-tweetsfromdemareplast);
        tweetsfromreparepfull.add(tweetsfromreparep-tweetsfromrepareplast);

        // updating the last total for next round
        tweetsfromdemademlast = tweetsfromdemadem;
        tweetsfromrepademlast = tweetsfromrepadem;
        tweetsfromdemareplast = tweetsfromdemarep;
        tweetsfromrepareplast = tweetsfromreparep;

        // resetting the retweet and normal tweet tallies
        resetReandtweets();

    }
    /**
     * Output the data of the simulation in a .csv file
     */
    private void output(String name) {
        String filename = System.getProperty("user.dir") + "\\data\\" + name + ".csv2";
        try {
            FileWriter writer = new FileWriter(filename);
            writer.write("turn");
            writer.write(';');
            writer.write("tweetCountDem");
            writer.write(';');
            writer.write("tweetCountRep");
            writer.write(';');
            writer.write("numberOfRetweets");
            writer.write(';');
            writer.write("numberOfNormalTweets");
            writer.write(';');
            writer.write("numberOfTweetsFromDemAboutDem");
            writer.write(';');
            writer.write("numberOfTweetsFromRepAboutDem");
            writer.write(';');
            writer.write("numberOfTweetsFromDemAboutRep");
            writer.write(';');
            writer.write("numberOfTweetsFromRepAboutRep");
            writer.write(';');
            writer.write("numberOfDemocrats");
            writer.write(';');
            writer.write("numberOfRepublicans");
            writer.write(';');
            writer.write("democratBaseAggression");
            writer.write(';');
            writer.write("republicanBaseAggression");
            writer.write(';');
            writer.write("democratDefenceModifier");
            writer.write(';');
            writer.write("republicanDefenceModifier");
            writer.write(';');
            writer.write("democratTweetChance");
            writer.write(';');
            writer.write("republicanTweetChance");
            writer.write(';');
            writer.write("democratNetworkModifier");
            writer.write(';');
            writer.write("republicanNetworkModifier");
            writer.write(';');
            writer.write("normalTweetBehaviorOn");
            writer.write(';');
            writer.write("aggressiveTweetBehaviorOn");
            writer.write(';');
            writer.write("retweetBehaviorOn");
            writer.write(';');
            writer.write("speakerAlphaValue");
            writer.write(';');
            writer.write("speakerBetaValue");
            writer.write(';');
            writer.write("interruptValue");
            writer.write(';');
            writer.write("memeValue");
            writer.write(';');
            writer.write("longTermAttentionValue");
            writer.write('\n');
            for (int i = 0; i < tweetsp1.size(); i++) {
                writer.write(String.valueOf(i));
                writer.write(';');
                writer.write(tweetsp1.get(i).toString());
                writer.write(';');
                writer.write(tweetsp2.get(i).toString());
                writer.write(';');
                writer.write(retweetsfull.get(i).toString());
                writer.write(';');
                writer.write(normaltweetsfull.get(i).toString());
                writer.write(';');
                writer.write(tweetsfromdemademfull.get(i).toString());
                writer.write(';');
                writer.write(tweetsfromrepademfull.get(i).toString());
                writer.write(';');
                writer.write(tweetsfromdemarepfull.get(i).toString());
                writer.write(';');
                writer.write(tweetsfromreparepfull.get(i).toString());
                writer.write(';');
                writer.write(String.valueOf(nDems));
                writer.write(';');
                writer.write(String.valueOf(nReps));
                writer.write(';');
                writer.write(String.valueOf(demAgg));
                writer.write(';');
                writer.write(String.valueOf(repAgg));
                writer.write(';');
                writer.write(String.valueOf(demDef));
                writer.write(';');
                writer.write(String.valueOf(repDef));
                writer.write(';');
                writer.write(String.valueOf(demT));
                writer.write(';');
                writer.write(String.valueOf(repT));
                writer.write(';');
                writer.write(String.valueOf(demnet));
                writer.write(';');
                writer.write(String.valueOf(repnet));
                writer.write(';');
                writer.write(String.valueOf(nortb));
                writer.write(';');
                writer.write(String.valueOf(aggtb));
                writer.write(';');
                writer.write(String.valueOf(retb));
                writer.write(';');
                writer.write(String.valueOf(speakera));
                writer.write(';');
                writer.write(String.valueOf(speakerb));
                writer.write(';');
                writer.write(String.valueOf(interruptsv));
                writer.write(';');
                writer.write(String.valueOf(memev));
                writer.write(';');
                writer.write(String.valueOf(interestdecayv));
                writer.write('\n');
            }
            writer.close();
        } catch (IOException e) {
            // Oh god please no!
            JFrame frame = new JFrame();
            JOptionPane.showMessageDialog(frame, "An error occurred while trying to save the results");
        }
    }
}