
public class SimpleModel extends Agent{

    /**
     * Constructor
     * @param dem If tune then agent is a democrat, if false then republican
     * @param agg Aggression, between 0 and 100
     * @param res Resource, gained and lost by tweeting
     */
    public SimpleModel(boolean dem, int agg, int res, World w){
        super(dem, agg, res, w);
    }

    /**
     *  Simple implementation of decision-making
     *  @param speakerDem True if the democrats candidate is speaking, false if republican
     *  @return 1 if agent decided to tweet, 0 if not
     **/
    @Override
    public int makeTweet (boolean speakerDem){
        // if they have spend 5 turns making a tweet then it is sent
        if (super.getTwitting() >= 5) {
            super.changeTwitting(-5);
            return (1);
        }
        // if already twitting then continue to tweet
        if (super.getTwitting() > 0){
            super.changeTwitting(1);
            return(0);
        }
        // if already has a twitting partner and is done making a tweet, then check if they want to continue to tweet
        if (super.getPartner() != null){
            if (super.fight() & super.getPartner().fight()){
                super.changeTwitting(1);
                super.getPartner().changeTwitting(1);
            } else {
                super.getPartner().setPartner(null);
                super.setPartner(null);
            }
            return (0);
        }
        // for now if an agent is not making a tweet then there is a 20 procent chance they will start making one about its candidate
        if (20 >= super.getRamdom()){
            super.changeTwitting(1);
            super.setTweetP(super.getDem());
            super.setPartner(super.getW().getFighter(super.getDem())); // when first making a tweet check to see if they get a partner (abstraction of getting a someone to respond)
            if (super.getPartner() != null){
                super.getPartner().setPartner(this);
                super.getPartner().setTweetP(super.getDem()); // assumes the conversation is going to be about the candidate of the agent that started the conversation
            }
            return (0);
        }
        // retweet behavior (I know this can be done in a single if statement). Will take difference in number of tweets by each side. If we have more tweets then the other side it is more likely we will find a tweet to retweet
        if (super.getW().getSumDem()+super.getW().getSumRep() != 0 && super.getW().getSumDem()/(super.getW().getSumDem()+super.getW().getSumRep()) >= super.getRamdom() && super.getDem()){
            super.changeTwitting(1);
            super.setTweetP(super.getDem());
            return (0);
        }
        if (super.getW().getSumDem()+super.getW().getSumRep() != 0 && super.getW().getSumRep()/(super.getW().getSumDem()+super.getW().getSumRep()) >= super.getRamdom() && !super.getDem()){
            super.changeTwitting(1);
            super.setTweetP(super.getDem());
            return (0);
        }
        return (0);
    }
}
