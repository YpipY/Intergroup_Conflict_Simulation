import static java.lang.Math.*;
import java.util.Random;
/**
* Class of the agents
*/
public class Agent {
    private Random random;
    private double k;
    /**
     * Constructor
     */
    public Agent(double k){
        this.k = k;
        random = new Random();
    }

    /**
     * Main decision function
     * @return 1 if the actor decided to tweet, 0 if not
     */
    public int makeTweet (double x){
        // basic function
        int p = (int) (1000000*(1/(1+exp(-k*x))));
        //int test = random.nextInt(100)+1;
        //System.out.println(100*(1/(1+exp(-k*x))));
        //System.out.println(p);
        if (p >= random.nextInt(1000000)+1 ){
            return(1);
        } else {
            return(0);
        }
    }
}
