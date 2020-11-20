import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Class handling the data form the real debate
 */

public class Debate {
    private ArrayList<Integer> start = new ArrayList<>(); // list of speaking start time
    private ArrayList<Integer> end = new ArrayList<>(); // list of speaking end time
    private ArrayList<String> words = new ArrayList<>(); // list of words spoken
    private ArrayList<Integer> speaker = new ArrayList<>(); // list of who is speaking
    private ArrayList<Boolean> interrupt = new ArrayList<>(); // list of if this was started by an interrupt
    private int curindex; // current index

    public Debate(){
        curindex = 0;
        readCSV();
    }

    // Getter methods
    public ArrayList<String> getWord(){
        ArrayList<String> word = new ArrayList<>();
        int i = curindex;
        while (end.get(i).equals(end.get(curindex))){
            word.add(words.get(i));
            i++;
        }
        return (word);
    }
    public int getSpeaker(){return (speaker.get(curindex));}
    public boolean getInterrupt(){return (interrupt.get(curindex));}

    public void advanceTime(int turn) {
        while (end.get(curindex) <= turn){
            curindex++;
        }
    }

    private void readCSV (){
        String filename = System.getProperty("user.dir") + "\\data\\" + "Debate2012_1_tidy.csv";
        try {
            BufferedReader in = new BufferedReader(new FileReader(filename));
            String line;
            String[] lineval;
            in.readLine(); // getting ride of the header line
            while ((line = in.readLine()) != null){
                lineval = line.split(",");
                if (!lineval[1].equals("NA")) {
                    words.add(lineval[1].replace("\"", ""));
                    String temp = lineval[2].replace("S", "");
                    temp = temp.replace("\"", "");
                    start.add(parseInt(temp));
                    temp = lineval[3].replace("S", "");
                    temp = temp.replace("\"", "");
                    end.add(parseInt(temp));

                    temp = lineval[5].replace("\"", "");
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

                    temp = lineval[6].replace("\"", "");
                    if (temp.equals("Yes")) {
                        interrupt.add(true);
                    } else {
                        interrupt.add(false);
                    }
                }
            }
        } catch (IOException e) {
            JFrame frame = new JFrame();
            JOptionPane.showMessageDialog(frame, "Debate data file not found");
        }
    }

    private int parseInt(String s) {
        try {
            return (int) Double.parseDouble(s);
        } catch (NumberFormatException e) {
            JFrame frame = new JFrame();
            JOptionPane.showMessageDialog(frame, "Error in file read. Expecting int, value was: " + s);
            return 0;
        }
    }
}
