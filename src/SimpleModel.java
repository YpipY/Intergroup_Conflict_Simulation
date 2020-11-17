
public class SimpleModel extends Agent{

    /**
     * Constructor
     * @param dem If tune then agent is a democrat, if false then republican
     * @param agg Aggression [0-100]
     * @param w The world the agent is operating in
     */
    public SimpleModel(boolean dem, int agg, World w){
        super(dem, agg, w);
    }

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

        // for now if an agent is not making a tweet then there is a 20 percent chance they will start making one about its candidate
        if (20 >= super.getW().getRamdom()){
            super.changeTwitting(1);
            super.setTweetP(super.getDem());
            // decide if agent is aggressively trying to find a partner (Mentioning someone in the tweet or hashtag hijacking)
            if (super.fight(0)){
                super.setPartner(super.getW().getFighter(super.getDem()));
                if (super.getPartner() != null){
                    // check of the partner responds and starts a conversation
                    if (super.getPartner().fight(20)){
                        super.getPartner().setPartner(this);
                        super.getPartner().setTweetP(super.getDem()); // assumes the conversation is going to be about the candidate of the agent that started the conversation
                    } else { // if partner declines to respond
                        super.setPartner(null);
                    }
                    super.changeTwitting(1);
                }
            }
            return (0);
        }

        // retweet behavior (I know this can be done in a single if statement). Will take difference in number of tweets by each side. If we have more tweets then the other side it is more likely we will find a tweet to retweet
        if (super.getW().getTotalp1()+super.getW().getTotalp2() != 0 && super.getW().getTotalp1()/(super.getW().getTotalp1()+super.getW().getTotalp2()) >= super.getW().getRamdom() && super.getDem()){
            super.changeTwitting(1);
            super.setTweetP(super.getDem());
            return (0);
        }
        if (super.getW().getTotalp1()+super.getW().getTotalp2() != 0 && super.getW().getTotalp2()/(super.getW().getTotalp1()+super.getW().getTotalp2()) >= super.getW().getRamdom() && !super.getDem()){
            super.changeTwitting(1);
            super.setTweetP(super.getDem());
            return (0);
        }
        return (0);
    }
}
