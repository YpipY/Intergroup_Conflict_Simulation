/**
 * Agent with all decision-making enabled, with external factors
 */

public class FullModelExternal extends Agent{

    /**
     * Constructor
     * @param dem If tune then agent is a democrat, if false then republican
     * @param agg Aggression [0-100]
     * @param def defence modifier on aggression
     * @param t likelihood of deciding to tweet
     * @param net connectedness of, increases likelihood of retweet. Multiplier
     * @param w The world the agent is operating in
     */
    public FullModelExternal(boolean dem, int agg, int def, int t, double net, World w) { super(dem, agg, def, t, net, w); }

    /**
     *  Simple implementation of decision-making
     *  @param speakerDem True if the democrats candidate is speaking, false if republican
     *  @return 1 if agent decided to tweet, 0 if not
     **/
    @Override
    public int makeTweet (boolean speakerDem){
        // if they have spend 5 turns (seconds) making a tweet then it is sent
        if (super.getTwitting() >= 5) {
            super.changeTwitting(-5);
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

        // continue current conversation
        if (super.continueCon()){
            return (0);
        }

        // normal tweet behavior
        if (super.getW().getNortb()) {
            if (super.normalTweet()) {
                return (0);
            }
        }

        // aggressive tweet behavior
        if (super.getW().getAggtb()) {
            if (super.aggTweet()){
                return (0);
            }
        }

        // retweet behavior
        if (super.getW().getRetb()) {
            if (super.retweet()) {
                return (0);
            }
        }
        return (0);
    }
    /**
     * Modified return of normal tweet chance that takes the external data into account
     * @return tweet chance
     **/
    @Override
    public int getT() {return 0;}

    /**
     * Modified return of retweet chance that takes the external data into account
     * @return retweet chance
     **/
    @Override
    public int getRT() {return 0;}

    /**
     * Decide if agent wants to fights (have a conversation) accounts for external factors
     * @param x the amount to increase the likelihood of fighting with
     * @return True if agents wants fights
     **/
    @Override
    public boolean fight(int x){
        double bonus = 0;
        int speak = 2;
        if (super.getDem()){
            speak = 1;
        }

        // check if I my candidate is speaker and I should get and increase to aggression
        if (speak == super.getW().getDebate().getSpeaker()){
            bonus = bonus + super.getW().getSpeakera() + super.getW().getDebate().getSpeakingturns() * super.getW().getSpeakerb();
            // check if it was an interrupt and I should increase to aggression even further
            if (super.getW().getDebate().getInterrupt()){
                bonus = bonus + super.getW().getInterruptsv();
            }
        }

        return (super.getAgg() + bonus + x  >= super.getW().getRamdom());
    }
}