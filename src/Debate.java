import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.lang.Math;

/**
 * Class handling the data form the real debate. But advancing the index using the advanceTime method the getter methods will return the data form that second of the debate
 */

public class Debate {
    private ArrayList<Integer> start = new ArrayList<>(); // list of speaking start time
    private ArrayList<Integer> end = new ArrayList<>(); // list of speaking end time
    private ArrayList<String> words = new ArrayList<>(); // list of words spoken
    private ArrayList<Integer> speaker = new ArrayList<>(); // list of who is speaking
    private ArrayList<Boolean> interrupt = new ArrayList<>(); // list of if this was started by an interrupt
    private int curindex; // current index
    private int lastmeme; // time since last meme
    private int turn; // for holding current turn
    private int demspeakingturns; // number of turns the democrat candidate has spoken for
    private int repspeakingturns; // number of turns the republican candidate has spoken for
    private ArrayList<String> memewords = new ArrayList<>(); // list of the words that should be recognised as memes

    /**
    * Constructor for the debate class
    */
    public Debate(String debatename){
        // initialising variables
        curindex = 0;
        lastmeme = -100000;

        // adding the meme words
        // big bird is looked at separately since it is two words that have to come right after each other
        memewords.add("binders");
        memewords.add("bayonets");

        // reading the debate data
        readCSV(debatename);
    }

    // Getter methods
    public ArrayList<String> getWord(){
        ArrayList<String> word = new ArrayList<>();
        int i = curindex;
        while (i < end.size() && end.get(i).equals(end.get(curindex))){
            word.add(words.get(i));
            i++;
        }
        return (word);
    }
    public int getSpeaker(){return (speaker.get(curindex));}
    public ArrayList<Integer> getEnd(){return (end);}
    public boolean getInterrupt(){return (interrupt.get(curindex));}
    public ArrayList<String> getMemewords(){return (memewords);}
    public int getLastmeme(){return (lastmeme);}
    public int getTurn(){return (turn);}

    /**
     * Returns how long a speaker been speaking for
     * @param dem True of you want to know about the democrat speaker, false for republican
     */
    public int speakeingTurns (boolean dem){
        if (dem) {
            return (demspeakingturns);
        }
        return (repspeakingturns);
    }

    /**
     * Makes the debate object advance its indexing to the turn give (cannot go backwards)
     * @param turn The turn to advance to
     */
    public void advanceTime(int turn) {
        this.turn = turn;

        // check to see if the speaker has change. Done be looking at the time of the end of speech
        if (end.get(curindex) <= turn) {
            // find the first index of the new speaking turn
            while (end.get(curindex) <= turn) {
                curindex++;
            }
            // look to if the words spoken in the new speaking turn
            for (String word : getWord()){
                for (String meme : getMemewords()){
                    if (word.equals(meme)){
                        lastmeme = turn + (int) ((1+getWord().indexOf("big")) * ((double) (end.get(curindex) - start.get(curindex))/getWord().size()));
                    }
                }
                // for big bird since it is two words
                if (word.equals("big") && getWord().get(getWord().indexOf(word) + 1).equals("bird")){
                    lastmeme = turn + (int) ((1+getWord().indexOf("big")) * ((double) (end.get(curindex) - start.get(curindex))/getWord().size()));
                }
            }
        }

        // count up the number of turns a candidate has spoken for
        // for democrat
        int decrease = 1;
        switch (speaker.get(curindex)){
            // for none
            case 0:
                // If the candidate has more then 5 turns spoken then count it down by 5 (to give a gradual decrease in aggression and not a strait cut of, to overcome small interrupts)
                if (repspeakingturns >= decrease){
                    repspeakingturns = repspeakingturns - decrease;
                } else {
                    repspeakingturns = 0;
                }
                if (demspeakingturns >= decrease){
                    demspeakingturns = demspeakingturns - decrease;
                } else {
                    demspeakingturns = 0;
                }
                break;
            // for democrat
            case 1:
                demspeakingturns++;
                if (repspeakingturns >= decrease){
                    repspeakingturns = repspeakingturns - decrease;
                } else {
                    repspeakingturns = 0;
                }
                break;
            // for republican
            default:
                repspeakingturns++;
                if (demspeakingturns >= decrease){
                    demspeakingturns = demspeakingturns - decrease;
                } else {
                    demspeakingturns = 0;
                }
        }
    }

    /**
     * Returns the current increase in tweet chance due to a meme event
     * @return The percentage to increase tweet chance with
     */
    public double memeTweetFunc (){
        // Meme function tweet: (1/(1+e^(-0.75(t-25)) * (1000*e^(-0.015t))) . t is times since meme onset
        // 724 max value
        return ((1/(1+(Math.exp(-0.75*((getTurn()-getLastmeme())-15)))) * (1000*(Math.exp((-0.015*(getTurn()-getLastmeme()))))))/724);
    }

    /**
     * Returns the current increase in retweet chance due to a meme event
     * @return The percentage to increase retweet chance with
     */
    public double memeRetweetFunc (){
        // Meme function retweet: (1/(1+e^(-0.75(t-15)) * (1000*e^(-0.015t))) . t is times since meme onset
        // 724 max value
        return ((1/(1+(Math.exp(-0.75*((getTurn()-getLastmeme())-25)))) * (1000*(Math.exp((-0.015*(getTurn()-getLastmeme()))))))/724);
    }

    /**
     * Returns the current tweet chance according to the long term interest function
     * @return The current tweet chance modifier
     */
    public double interestFunc (){
        // Interest decay function: (-0.00001t^2) + 0.08t + 10 .  t is times since the start of the debate
        // 170 max value
        return ((-0.00001*(Math.pow(turn,2)) + (0.06*turn) + 200)/300);
    }

    /**
    * Reads the debate data off the .csv file provided
    */
    private void readCSV (String debatename){
        String filename = System.getProperty("user.dir") + "\\" + debatename;
        try {
            BufferedReader in = new BufferedReader(new FileReader(filename));
            String line;
            String[] lineval;
            in.readLine(); // getting ride of the header line
            while ((line = in.readLine()) != null){
                lineval = line.split(",");
                words.add(lineval[1].replace("\"", ""));
                String temp = lineval[2].replace("S", "");
                temp = temp.replace("\"", "");
                start.add(parseInt(temp));
                temp = lineval[3].replace("S", "");
                temp = temp.replace("\"", "");
                end.add(parseInt(temp));
                temp = lineval[5].replace("\"", "");

                switch (debatename) {
                    case "Debate2012_1_tidy.csv":
                        switch (temp) {
                            case "Lehrer:":
                                speaker.add(0);
                                break;
                            case "Obama:":
                                speaker.add(1);
                                break;
                            default:
                                speaker.add(2);
                                break;
                        }
                        break;
                    case "Debate2012_2_tidy.csv":
                        switch (temp) {
                            case "Crowley:":
                                speaker.add(0);
                                break;
                            case "Obama:":
                                speaker.add(1);
                                break;
                            default:
                                speaker.add(2);
                                break;
                        }
                        break;
                    case "Debate2012_3_tidy.csv":
                        switch (temp) {
                            case "Schieffer:":
                                speaker.add(0);
                                break;
                            case "Obama:":
                                speaker.add(1);
                                break;
                            default:
                                speaker.add(2);
                                break;
                        }
                        break;
                    case "Debate2016_1_tidy.csv":
                        switch (temp) {
                            case "HOLT:":
                                speaker.add(0);
                                break;
                            case "TRUMP:":
                                speaker.add(1);
                                break;
                            default:
                                speaker.add(2);
                                break;
                        }
                        break;
                    case "Debate2016_2_tidy.csv":
                        switch (temp) {
                            case "RADDATZ:":
                                speaker.add(0);
                                break;
                            case "TRUMP:":
                                speaker.add(1);
                                break;
                            default:
                                speaker.add(2);
                                break;
                        }
                        break;
                    default:
                        switch (temp) {
                            case "Wallace:":
                                speaker.add(0);
                                break;
                            case "Trump:":
                                speaker.add(1);
                                break;
                            default:
                                speaker.add(2);
                                break;
                        }
                        break;
                }
                temp = lineval[6].replace("\"", "");
                if (temp.equals("Yes")) {
                    interrupt.add(true);
                } else {
                    interrupt.add(false);
                }
            }
        } catch (IOException e) {
            JFrame frame = new JFrame();
            JOptionPane.showMessageDialog(frame, "Debate data file not found");
        }
    }

    /**
     * Parses a string to int or catches the exception
     * @param s String to be parsed
     * @return The int value found in the string
     */
    private int parseInt(String s) {
        try {
            if (s.equals("NA")){
                return (0);
            }
            return (int) Double.parseDouble(s);
        } catch (NumberFormatException e) {
            JFrame frame = new JFrame();
            JOptionPane.showMessageDialog(frame, "Error in file read. Expecting numeric, value was: " + s);
            return 0;
        }
    }
}
