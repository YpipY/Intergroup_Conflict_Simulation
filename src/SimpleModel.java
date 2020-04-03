
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
    public int makeTweet (boolean speakerDem){
        if (20 >= super.getRamdom()){
            super.changeRes(-10);
            Agent opponent = super.getW().getFighter(super.getDem());
            if (opponent.fight()){
                super.changeRes(-50);
                opponent.changeRes(-50);
                return(0);
            } else {
                return (1);
            }
        } else {
            return(0);
        }
    }
}
