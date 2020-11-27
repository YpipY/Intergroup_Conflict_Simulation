// Agent Based Model simulator for simulating twitter mentions
// Author: Simon Moeller Nielsen
// Date: 27/11 2020


// TODO add gradual decrease in when speaker is not speaking, instead of pure cutoff
// TODO make the long term attention function work on the overall tweet chance instead of a seperate value
// TODO go though and comment code for posterity
// TODO refactor some field and local variables that do not follow consistent style (e.g. demT -> demt)
/**
 * Main class. Only for initial setup
 */

public class Main {
    public static void main(String[] args){
        new GUI();
    }
}
