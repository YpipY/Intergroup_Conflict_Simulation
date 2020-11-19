/**
 * Simple implementation of agent decision-making (no external factors)
 */

public class SimpleModel extends Agent{

    /**
     * Constructor
     * @param dem If tune then agent is a democrat, if false then republican
     * @param agg Aggression [0-100]
     * @param def defence modifier on aggression
     * @param t likelihood of deciding to tweet
     * @param net connectedness of, increases likelihood of retweet. Multiplier
     * @param w The world the agent is operating in
     */
    public SimpleModel(boolean dem, int agg, int def, int t, double net, World w) { super(dem, agg, def, t, net, w); }

    /**
     *  Simple implementation of decision-making
     *  @param speakerDem True if the democrats candidate is speaking, false if republican
     *  @return 1 if agent decided to tweet, 0 if not
     **/
    @Override
    public int makeTweet (boolean speakerDem){
        // if they have spend 10 turns (seconds) making a tweet then it is sent
        if (super.getTwitting() >= 10) {
            super.changeTwitting(-10);
            if (super.getWhatTweet()){
                super.getW().addNormalTweets(1);
            } else{
                super.getW().addReteets(1);
            }
            return (1);
        }

        // if already twitting then continue to tweet or if partner is already twitting (then it as already decided to start twitting this turn)
        if (super.getTwitting() > 0 || (super.getPartner() != null && super.getPartner().getTwitting() > 0)){
            super.changeTwitting(1);
            return(0);
        }

        // if already has a twitting partner and is done making a tweet, then check if they want to continue to tweet
        // if the agent is aggressor
        if (super.getPartner() != null){
            // if the agent is aggressor
            if (super.getTweetP() == super.getDem()){
                if (super.fight(0) && super.getPartner().fight(20)){
                    super.changeTwitting(1);
                    super.getPartner().changeTwitting(1);
                } else {
                    super.getPartner().setPartner(null);
                    super.setPartner(null);
                }
            // if the agent is defender
            } else {
                if (super.fight(20) && super.getPartner().fight(0)){
                    super.changeTwitting(1);
                    super.getPartner().changeTwitting(1);
                } else {
                    super.getPartner().setPartner(null);
                    super.setPartner(null);
                }
            }
            return (0);
        }

        // for now if an agent is not making a tweet then there is a percent chance (given by the t value) they will start making one about its candidate
        if (super.getT() >= super.getW().getRamdomLarge()){
            super.changeTwitting(1);
            super.setTweetP(super.getDem());
            super.setWhatTweet(true);
            // decide if agent is aggressively trying to find a partner (Mentioning someone in the tweet or hashtag hijacking)
            if (super.fight(0)){
                super.setPartner(super.getW().getFighter(super.getDem()));
                if (super.getPartner() != null){
                    // check of the partner responds and starts a conversation
                    if (super.getPartner().fight(super.getPartner().getDef())){
                        super.getPartner().setWhatTweet(true);
                        super.getPartner().setPartner(this);
                        super.getPartner().setTweetP(super.getDem()); // assumes the conversation is going to be about the candidate of the agent that started the conversation
                    } else { // if partner declines to respond
                        super.setPartner(null);
                    }
                }
            }
            return (0);
        }

        // retweet behavior. Will take difference in number of tweets by each side. If we have more tweets then the other side it is more likely we will find a tweet to retweet modified by net
        // First decide if it wants to retweet, if number of total tweets are > 0.
        if (super.getW().getTweetCountDemTotal() + super.getW().getTweetCountRepTotal() != 0 && super.getT() >= super.getW().getRamdomLarge()) {
            // If democrat find out of they find a tweet from same partisanship
            if (super.getDem() && 100*((super.getNet() * super.getW().getTweetCountDemTotal()) / (super.getNet() * super.getW().getTweetCountDemTotal() + super.getW().getTweetCountRepTotal())) >= super.getW().getRamdom()) {
                // Find out if the tweet is going to be about a democrat or republican
                if (100*(super.getW().getTweetsFromDemADem() / (super.getW().getTweetsFromDemADem() + super.getW().getTweetsFromDemARep())) >= super.getW().getRamdom()){
                    super.setTweetP(super.getDem());
                } else {
                    super.setTweetP(!super.getDem());
                }
                super.changeTwitting(1);
                super.setWhatTweet(false);
                return (0);
            }
            // If republican find out of they find a tweet from same partisanship
            if (!super.getDem() && 100*((super.getNet() * super.getW().getTweetCountRepTotal()) / (super.getW().getTweetCountDemTotal() + super.getNet() * super.getW().getTweetCountRepTotal())) >= super.getW().getRamdom()) {
                // Find out if the tweet is going to be about a democrat or republican
                if (100*(super.getW().getTweetsFromRepARep() / (super.getW().getTweetsFromRepARep() + super.getW().getTweetsFromRepADem())) >= super.getW().getRamdom()){
                    super.setTweetP(super.getDem());
                } else {
                    super.setTweetP(!super.getDem());
                }
                super.changeTwitting(1);
                super.setWhatTweet(false);
                return (0);
            }
        }
        return (0);
    }
}