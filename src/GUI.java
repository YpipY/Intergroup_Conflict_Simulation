import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Class for the GUI popup, input and file output
 */

public class GUI implements ActionListener {
    private boolean interror = false; // for caching errors in options selection for int
    private boolean doubleerror = false; // for caching errors in options selection for double
    private JFrame frame = new JFrame(); // frame for the GUI
    JTextField input5 = new JTextField("test"); // name of file that should contain the results

    // ArrayList for storing results
    private ArrayList<Integer> tweetsp1 = new ArrayList<>();
    private ArrayList<Integer> tweetsp2 = new ArrayList<>();
    private ArrayList<Integer> turnsfull = new ArrayList<>();
    private ArrayList<Integer> nDemsfull = new ArrayList<>();
    private ArrayList<Integer> nRepsfull = new ArrayList<>();
    private ArrayList<Integer> demAggfull = new ArrayList<>();
    private ArrayList<Integer> repAggfull = new ArrayList<>();
    private ArrayList<Integer> demDeffull = new ArrayList<>();
    private ArrayList<Integer> repDeffull = new ArrayList<>();
    private ArrayList<Integer> demtfull = new ArrayList<>();
    private ArrayList<Integer> reptfull = new ArrayList<>();
    private ArrayList<Double> demnetfull = new ArrayList<>();
    private ArrayList<Double> repnetfull = new ArrayList<>();
    private ArrayList<Integer> retweetsfull = new ArrayList<>();
    private ArrayList<Integer> normaltweetsfull = new ArrayList<>();

    /**
     * Constructor of the GUI class
     */
    public GUI() {
        World world; // the world which will run the simulations

        // setup of text box and its fields
        JPanel pane = new JPanel();
        // in quotation is default values
        JTextField input0 = new JTextField("10000"); //turns
        JTextField input1 = new JTextField("100"); // number of democrats
        JTextField input2 = new JTextField("20"); // aggression of democrats
        JTextField input3 = new JTextField("100"); // number of republicans
        JTextField input4 = new JTextField("20"); // aggression of republicans
        JTextField input6 = new JTextField("20"); // defence modifier on aggression of democrats
        JTextField input7 = new JTextField("20"); // defence modifier on aggression of republicans
        JTextField input8 = new JTextField("1"); // likelihood of democrats deciding to tweet
        JTextField input9 = new JTextField("1"); // likelihood of republicans deciding to tweet
        JTextField input10 = new JTextField("1.00"); // connectedness of democrats, increases likelihood of retweet
        JTextField input11 = new JTextField("1.00"); // connectedness of republicans, increases likelihood of retweet

        // text box layout
        pane.setLayout(new GridLayout(0, 2));

        // adding text of what the options do
        pane.add(new JLabel("Number of turns:"));
        pane.add(input0);

        pane.add(new JLabel("Number of Democrats:"));
        pane.add(input1);

        pane.add(new JLabel("Democrats aggression [0-100]:"));
        pane.add(input2);

        pane.add(new JLabel("Democrats defence modifier added to aggression in case of defence:"));
        pane.add(input6);

        pane.add(new JLabel("Likelihood of democrats deciding to tweet:"));
        pane.add(input8);

        pane.add(new JLabel("connectedness of democrats, increases likelihood of retweet. Multiplier:"));
        pane.add(input10);

        pane.add(new JLabel("Number of Republicans:"));
        pane.add(input3);

        pane.add(new JLabel("Republicans aggression [0-100]:"));
        pane.add(input4);

        pane.add(new JLabel("Republicans defence modifier added to aggression in case of defence:"));
        pane.add(input7);

        pane.add(new JLabel("Likelihood of republicans deciding to tweet:"));
        pane.add(input9);

        pane.add(new JLabel("connectedness of republicans, increases likelihood of retweet. Multiplier:"));
        pane.add(input11);

        // drop down menu for the automated simulations
        pane.add(new JLabel("<html> Automated simulations. Will vary in increments <br> of 1 this value while keep all others as specified:</html>"));
        String[] simoptions = {"None", "Democrats aggression", "Democrats defence modifier", "Democrats likelihood of tweeting", "Connectedness of democrat network"};
        JComboBox<String> combobox1 = new JComboBox<>(simoptions);
        pane.add(combobox1);

        // adding the file name
        pane.add(new JLabel("Save file name"));
        pane.add(input5);

        // drawing the options menu
        int options = JOptionPane.showConfirmDialog(frame, pane, "Select Options", JOptionPane.OK_CANCEL_OPTION);

        // converting inputs to int and double
        int turns = this.parseInt(input0.getText());
        int nDems = this.parseInt(input1.getText());
        int nReps = this.parseInt(input3.getText());
        int demAgg = this.parseInt(input2.getText());
        int repAgg = this.parseInt(input4.getText());
        int demdef = this.parseInt(input6.getText());
        int repdef = this.parseInt(input7.getText());
        int demt = this.parseInt(input8.getText());
        int rept = this.parseInt(input9.getText());
        double demnet = this.parseDouble(input10.getText());
        double repnet = this.parseDouble(input11.getText());

        // checking for an invalid input, if so the option selection is started from the beginning again
        if (interror) {
            JOptionPane.showMessageDialog(frame, "The values without .00 in the default value must be a whole number");
            new GUI();
        } else if (doubleerror) {
            JOptionPane.showMessageDialog(frame, "The values must be numeric");
            new GUI();
        } else if (100 < demAgg || 0 > demAgg || 100 < repAgg || 0 > repAgg) {
            JOptionPane.showMessageDialog(frame, "Aggression must be between 0 and 100 (inclusive)");
            new GUI();
            // only the input is valid and ok was selected will the simulation start
        } else if (options == 0) {

            // for the GUI while the simulations are running
            JFrame frame2 = new JFrame("Simulations are running");
            frame2.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            frame2.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                    if (JOptionPane.showConfirmDialog(frame,
                            "Are you sure you want to stop simulations?", "Stop simulations?",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
                        System.exit(1);
                    }
                }
            });
            Container con = frame2.getContentPane();
            con.setLayout(new FlowLayout());
            JLabel lable1 = new JLabel("Simulation progress:" + "0%");
            con.add(lable1);
            frame2.setSize(300, 200);
            frame2.setVisible(true);

            // Timer for seeing how long the simulations run
            long start = System.nanoTime();

            // will see what value if any should be varied
            String simSelected = Objects.requireNonNull(combobox1.getSelectedItem()).toString();
            switch (simSelected) {
                case "None":
                    world = new World(turns, nDems, nReps, demAgg, repAgg, demdef, repdef, demt, rept, demnet, repnet);
                    tweetsp1.add(world.getTweetCountDemTotal());
                    tweetsp2.add(world.getTweetCountRepTotal());
                    turnsfull.add(turns);
                    nDemsfull.add(nDems);
                    nRepsfull.add(nReps);
                    demAggfull.add(demAgg);
                    repAggfull.add(repAgg);
                    demDeffull.add(demdef);
                    repDeffull.add(repdef);
                    demtfull.add(demt);
                    reptfull.add(rept);
                    demnetfull.add(demnet);
                    repnetfull.add(repnet);
                    retweetsfull.add(world.getRetweets());
                    normaltweetsfull.add(world.getNormalTweets());
                    lable1.setText("Simulation progress: " + "100%");
                    con.repaint();
                    break;
                case "Democrats aggression":
                    for (int i = 0; i < 101; i++) {
                        world = new World(turns, nDems, nReps, i, repAgg, demdef, repdef, demt, rept, demnet, repnet);
                        tweetsp1.add(world.getTweetCountDemTotal());
                        tweetsp2.add(world.getTweetCountRepTotal());
                        turnsfull.add(turns);
                        nDemsfull.add(nDems);
                        nRepsfull.add(nReps);
                        demAggfull.add(i);
                        repAggfull.add(repAgg);
                        demDeffull.add(demdef);
                        repDeffull.add(repdef);
                        demtfull.add(demt);
                        reptfull.add(rept);
                        demnetfull.add(demnet);
                        repnetfull.add(repnet);
                        retweetsfull.add(world.getRetweets());
                        normaltweetsfull.add(world.getNormalTweets());
                        lable1.setText("Simulation progress: " + (i / 101 * 100) + "%");
                        con.repaint();
                    }
                    break;
                case "Democrats defence modifier":
                    for (int i = 0; i < 101; i++) {
                        world = new World(turns, nDems, nReps, demAgg, repAgg, i, repdef, demt, rept, demnet, repnet);
                        tweetsp1.add(world.getTweetCountDemTotal());
                        tweetsp2.add(world.getTweetCountRepTotal());
                        turnsfull.add(turns);
                        nDemsfull.add(nDems);
                        nRepsfull.add(nReps);
                        demAggfull.add(demAgg);
                        repAggfull.add(repAgg);
                        demDeffull.add(i);
                        repDeffull.add(repdef);
                        demtfull.add(demt);
                        reptfull.add(rept);
                        demnetfull.add(demnet);
                        repnetfull.add(repnet);
                        retweetsfull.add(world.getRetweets());
                        normaltweetsfull.add(world.getNormalTweets());
                        lable1.setText("Simulation progress: " + (i / 101 * 100) + "%");
                        con.repaint();
                    }
                    break;
                case "Democrats likelihood of tweeting":
                    for (int i = 0; i < 101; i++) {
                        world = new World(turns, nDems, nReps, demAgg, repAgg, demdef, repdef, i, rept, demnet, repnet);
                        tweetsp1.add(world.getTweetCountDemTotal());
                        tweetsp2.add(world.getTweetCountRepTotal());
                        turnsfull.add(turns);
                        nDemsfull.add(nDems);
                        nRepsfull.add(nReps);
                        demAggfull.add(demAgg);
                        repAggfull.add(repAgg);
                        demDeffull.add(demdef);
                        repDeffull.add(repdef);
                        demtfull.add(i);
                        reptfull.add(rept);
                        demnetfull.add(demnet);
                        repnetfull.add(repnet);
                        retweetsfull.add(world.getRetweets());
                        normaltweetsfull.add(world.getNormalTweets());
                        lable1.setText("Simulation progress: " + (i / 101 * 100) + "%");
                        con.repaint();
                    }
                    break;
                case "Connectedness of democrat network":
                    for (double i = 0.00; i < 5; i = i + 0.01) {
                        world = new World(turns, nDems, nReps, demAgg, repAgg, demdef, repdef, demt, rept, i, repnet);
                        tweetsp1.add(world.getTweetCountDemTotal());
                        tweetsp2.add(world.getTweetCountRepTotal());
                        turnsfull.add(turns);
                        nDemsfull.add(nDems);
                        nRepsfull.add(nReps);
                        demAggfull.add(demAgg);
                        repAggfull.add(repAgg);
                        demDeffull.add(demdef);
                        repDeffull.add(repdef);
                        demtfull.add(demt);
                        reptfull.add(rept);
                        demnetfull.add(i);
                        repnetfull.add(repnet);
                        retweetsfull.add(world.getRetweets());
                        normaltweetsfull.add(world.getNormalTweets());
                        lable1.setText("Simulation progress: " + (int) (i / 5 * 100) + "%");
                        con.repaint();
                    }
                    break;
            }
            long stop = System.nanoTime();
            output();
            JOptionPane.showMessageDialog(frame, "Simulations done. Time elapse: " + (((stop - start) / 1000000000) / 60) + ":" + (((stop - start) / 1000000000) % 60) + " minutes");
        }
        // if cancel or the x was selected the programs closes
        System.exit(1);
    }

    /**
     * Parses a string to int or catches the exception
     *
     * @param s String to be parsed
     */
    private int parseInt(String s) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            interror = true;
            return 0;
        }
    }

    /**
     * Parses a string to double or catches the exception
     *
     * @param s String to be parsed
     */
    private double parseDouble(String s) {
        try {
            return Double.parseDouble(s);
        } catch (NumberFormatException e) {
            doubleerror = true;
            return 0;
        }
    }

    /**
     * Output the data of the simulation in a .csv file
     */
    private void output() {
        String filename = System.getProperty("user.dir") + "\\data\\" + input5.getText() + ".csv2";
        try {
            FileWriter writer = new FileWriter(filename);
            writer.write("tweetCountPerson1");
            writer.write(';');
            writer.write("tweetCountPerson2");
            writer.write(';');
            writer.write("numberOfTurns");
            writer.write(';');
            writer.write("numberOfDemocrats");
            writer.write(';');
            writer.write("numberOfRepublicans");
            writer.write(';');
            writer.write("democratAggression");
            writer.write(';');
            writer.write("republicanAggression");
            writer.write(';');
            writer.write("democratDefenceModifier");
            writer.write(';');
            writer.write("republicanDefenceModifier");
            writer.write(';');
            writer.write("democratTweetChance");
            writer.write(';');
            writer.write("republicanTweetChance");
            writer.write(';');
            writer.write("democratNetworkModifier");
            writer.write(';');
            writer.write("republicanNetworkModifier");
            writer.write(';');
            writer.write("numberOfRetweets");
            writer.write(';');
            writer.write("numberOfNormalTweets");
            writer.write('\n');
            for (int i = 0; i < tweetsp1.size(); i++) {
                writer.write(tweetsp1.get(i).toString());
                writer.write(';');
                writer.write(tweetsp2.get(i).toString());
                writer.write(';');
                writer.write(turnsfull.get(i).toString());
                writer.write(';');
                writer.write(nDemsfull.get(i).toString());
                writer.write(';');
                writer.write(nRepsfull.get(i).toString());
                writer.write(';');
                writer.write(demAggfull.get(i).toString());
                writer.write(';');
                writer.write(repAggfull.get(i).toString());
                writer.write(';');
                writer.write(demDeffull.get(i).toString());
                writer.write(';');
                writer.write(repDeffull.get(i).toString());
                writer.write(';');
                writer.write(demtfull.get(i).toString());
                writer.write(';');
                writer.write(reptfull.get(i).toString());
                writer.write(';');
                writer.write(demnetfull.get(i).toString());
                writer.write(';');
                writer.write(repnetfull.get(i).toString());
                writer.write(';');
                writer.write(retweetsfull.get(i).toString());
                writer.write(';');
                writer.write(normaltweetsfull.get(i).toString());
                writer.write('\n');
            }
            writer.close();
        } catch (IOException e) {
            // Oh god please no!
            JOptionPane.showMessageDialog(frame, "An error occurred while trying to save the results");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.exit(1);
    }
}