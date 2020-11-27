/**
* Class of the agents, implementation of the decision-making model (makeTweet()) is done in its subclasses
*/

public class Agent{
    private boolean dem; // is agent a democrat, true = democrat, false = republican
    private int agg; // aggression of agent [1-100]
    private int def; // defence modifier on aggression
    private int t; // likelihood of agent deciding to tweet
    private double net; // connectedness of democrats, increases likelihood of retweet. Multiplier
    private World w; // the world the agents are operating in
    private int twitting; // how long has the agent spend twitting, 0 if is not currently twitting
    private Agent partner; // another agent that this agent is having a conversion with, null of not having a conversation
    private boolean tweetP; // what candidate is the agent twitting about, true = democrat, false = republican
    private boolean whattweet; // What kind of tweet. True = normal tweet, false = retweet

    /**
     * Constructor
     * @param dem If tune then agent is a democrat, if false then republican
     * @param agg Aggression [0-100]
     * @param def defence modifier on aggression
     * @param t likelihood of deciding to tweet
     * @param net connectedness of, increases likelihood of retweet. Multiplier
     * @param w The world the agent is operating in
     */
    public Agent(boolean dem, int agg, int def, int t, double net, World w){
        this.dem = dem;
        this.agg = agg;
        this.def = def;
        this.t = t;
        this.net = net;
        this.w = w;
        this.twitting = 0;
        this.tweetP = dem;
        this.tweetP = true;
    }

    // Getter methods
    public boolean getDem(){return (dem);}
    public int getAgg(){return (agg);}
    public int getDef() {return def;}
    public int getBaseT() {return t;}
    public int getT() {return t;}
    public int getRT() {return t;}
    public double getNet() {return net;}
    public World getW(){return (w);}
    public int getTwitting(){return (twitting);}
    public Agent getPartner(){return (partner);}
    public boolean getTweetP(){return (tweetP);}
    public boolean getWhatTweet(){return (whattweet);}

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
     * Change tweetP
     * @param whattweet True if agent is currently tweeting a normal tweet, false if retweet
     **/
    public void setWhatTweet(boolean whattweet){
        this.whattweet = whattweet;
    }

    /**
     * Decide if agent wants to fights (have a conversation)
     * @param x the amount to increase the likelihood of fighting with
     * @return True if agents wants fights
     **/
    public boolean fight(int x){
        return (agg + x >= w.getRamdomFight());
    }

    /**
     * Main decision function, uses tweet behavior and external factors depending on which model was chosen
     * Dummy version, implementation in sub classes
     * @param x which candidate is currently speaking
     * @return 1 if the actor decided to tweet, 0 if not
     */
    public int makeTweet (boolean x){
        return (0);
    }

    /**
     *  Implementation of normal tweet behavior
     *  @return true if agent decided to tweet, false if not
     **/
    public boolean normalTweet (){
        // for now if an agent is not making a tweet then there is a percent chance (given by the t value) they will start making one about its candidate
        if (getT() >= getW().getRamdomLarge()) {
            changeTwitting(1);
            setTweetP(getDem());
            setWhatTweet(true);
            return (true);
        }
        return (false);
    }

    /**
     *  Implementation of aggressive tweet behavior
     *  @return true if agent decided to tweet, false if not
     **/
    public boolean aggTweet (){
        // decide if agent is aggressively trying to find a partner (Mentioning someone in the tweet or hashtag hijacking)
        if (getT() >= getW().getRamdomLarge()) {
            if (fight(0)) {
                changeTwitting(1);
                setTweetP(getDem());
                setPartner(getW().getFighter(getDem()));
                if (getPartner() != null) {
                    // check of the partner responds and starts a conversation
                    if (getPartner().fight(getPartner().getDef())) {
                        getPartner().setWhatTweet(true);
                        getPartner().setPartner(this);
                        getPartner().setTweetP(getDem()); // assumes the conversation is going to be about the candidate of the agent that started the conversation
                        return (true);
                    } else { // if partner declines to respond
                        setPartner(null);
                    }
                }
            }
        }
        return (false);
    }

    /**
     *  Implementation of retweet behavior
     *  @return true if agent decided to tweet, false if not
     **/
    public boolean retweet (){
        // retweet behavior. Will take difference in number of tweets by each side. If we have more tweets then the other side it is more likely we will find a tweet to retweet modified by net
        // First decide if it wants to retweet, if number of total tweets are > 0.
        if (getW().getTweetCountDemTotal() + getW().getTweetCountRepTotal() != 0 && getRT() >= getW().getRamdomLarge()) {
            // If democrat find out of they find a tweet from same partisanship
            if (getDem() && 100*((getNet() * getW().getTweetCountDemTotal()) / (getNet() * getW().getTweetCountDemTotal() + getW().getTweetCountRepTotal())) >= getW().getRamdom()) {
                // Find out if the tweet is going to be about a democrat or republican
                if (100*((double) getW().getTweetsFromDemADem() / (getW().getTweetsFromDemADem() + getW().getTweetsFromDemARep())) >= getW().getRamdom()){
                    setTweetP(getDem());
                } else {
                    setTweetP(!getDem());
                }
                changeTwitting(1);
                setWhatTweet(false);
                return (true);
            }
            // If republican find out of they find a tweet from same partisanship
            if (!getDem() && 100*((getNet() * getW().getTweetCountRepTotal()) / (getW().getTweetCountDemTotal() + getNet() * getW().getTweetCountRepTotal())) >= getW().getRamdom()) {
                // Find out if the tweet is going to be about a democrat or republican
                if (100*( (double) getW().getTweetsFromRepARep() / (getW().getTweetsFromRepARep() + getW().getTweetsFromRepADem())) >= getW().getRamdom()){
                    setTweetP(getDem());
                } else {
                    setTweetP(!getDem());
                }
                changeTwitting(1);
                setWhatTweet(false);
                return (true);
            }
        }
        return (false);
    }

    /**
     *  Implementation of decision to continue conversation
     *  @return true if agent decided to continue conversation tweet, false if not
     **/
    public boolean continueCon (){
        // if already has a twitting partner and is done making a tweet, then check if they want to continue to tweet
        // if the agent is aggressor
        if (getPartner() != null){
            // if the agent is aggressor
            if (getTweetP() == getDem()){
                if (fight(0) && getPartner().fight(getPartner().getDef())){
                    changeTwitting(1);
                    getPartner().changeTwitting(1);
                    return(true);
                } else {
                    getPartner().setPartner(null);
                    setPartner(null);
                }
                // if the agent is defender
            } else {
                if (fight(getDef()) && getPartner().fight(0)){
                    changeTwitting(1);
                    getPartner().changeTwitting(1);
                    return(true);
                } else {
                    getPartner().setPartner(null);
                    setPartner(null);
                }
            }
        }
        return (false);
    }
}