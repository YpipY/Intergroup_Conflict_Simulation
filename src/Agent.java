/**
* Class of the agents, implementation of the decision-making model (makeTweet()) is done in its subclasses
*/
public class Agent{
    private boolean dem; // is agent a democrat, true = democrat, false = republican
    private int agg; // aggression of agent [1-100]
    private World w; // the world the agents are operating in
    private int twitting; // how long has the agent spend twitting, 0 if is not currently twitting
    private Agent partner; // another agent that this agent is having a conversion with, null of not having a conversation
    private boolean tweetP; // what candidate is the agent twitting about, true = democrat, false = republican

    /**
     * Constructor
     * @param dem If tune then agent is a democrat, if false then republican
     * @param agg Aggression [0-100]
     * @param w The world the agent is operating in
     */
    public Agent(boolean dem, int agg, World w){
        this.dem = dem;
        this.agg = agg;
        this.w = w;
        this.twitting = 0;
        this.tweetP = dem;
    }

    // Getter methods
    public boolean getDem(){return (dem);}
    public World getW(){return (w);}
    public int getTwitting(){return (twitting);}
    public Agent getPartner(){return (partner);}
    public boolean getTweetP(){return (tweetP);}

    /**
     * Change the twitting value (seconds spend twitting)
     * @param twitting Amount to increase by
     **/
    public void changeTwitting(int twitting){
        this.twitting = this.twitting + twitting;
    }

    /**
     * Add a twitting partner
     * @param partner Partner to be added, will override previous partner
     **/
    public void setPartner(Agent partner){
        this.partner = partner;
    }

    /**
     * Change tweetP
     * @param tweetP True if agent is currently tweeting about democrats, false if republicans
     **/
    public void setTweetP(boolean tweetP){
        this.tweetP = tweetP;
    }

    /**
     * Decide if agent wants to fights (have a conversation)
     * @param x the amount to increase the likelihood of fighting with
     * @return True if agents wants fights
     **/
    public boolean fight(int x){
        return agg + x >= w.getRamdom();
    }

    /**
     * Main decision function
     * Dummy version, implementation in sub classes
     * @param x which candidate is currently speaking
     * @return 1 if the actor decided to tweet, 0 if not
     */
    public int makeTweet (boolean x){
        return (0);
    }
}