import static java.lang.Math.*;
import java.util.Random;
/**
* Class of the agents
*/
public class Agent{
    private Random random = new Random();
    private boolean dem;
    private int agg;
    private int res;
    private World w;
    private int twitting;
    private Agent partner;
    private boolean tweetP;

    /**
     * Constructor
     * @param dem If tune then agent is a democrat, if false then republican
     */
    public Agent(boolean dem, int agg, int res, World w){
        this.dem = dem;
        this.agg = agg;
        this.res = res;
        this.w = w;
        this.twitting = 0;
        this.tweetP = true;
    }

    // Getter methods
    public boolean getDem(){return (dem);}
    public int getAgg(){return (agg);}
    public int getRes(){return (res);}
    public World getW(){return (w);}
    public int getRamdom(){return (random.nextInt(100)+1);}
    public int getTwitting(){return (twitting);}
    public Agent getPartner(){return (partner);}
    public boolean getTweetP(){return (tweetP);}

    /**
     * Change agents resource by res
     * @param res Amount to increase by
     **/
    public void changeRes(int res){
        this.res = this.res + res;
    }

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
     * Decide if wants agent fights
     * @return True if agents wants fights
     **/
    public boolean fight(){
        if (agg >= getRamdom()){
            return(true);
        } else{
            return(false);
        }
    }

    /**
     * Main decision function
     * Dummy version, implementation in sub classes
     * @return 1 if the actor decided to tweet, 0 if not
     */
    public int makeTweet (boolean x){
        // basic function
        //int p = (int) (1000000*(1/(1+exp(-k*x))));
        //int test = random.nextInt(100)+1;
        //System.out.println(100*(1/(1+exp(-k*x))));
        //System.out.println(p);

        //if (30 >= random.nextInt(100)+1 ){
        //    return(1);
        //} else {
        //    return(0);
        //}
        return (0);
    }
}

