import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Class for the GUI popup, input and file output
 */

public class GUI {
    private boolean error = false; // for caching errors in options selection

    // ArrayList for storing results
    private ArrayList<Integer> tweetsp1 = new ArrayList<>();
    private ArrayList<Integer> tweetsp2 = new ArrayList<>();
    private ArrayList<Integer> turnsfull = new ArrayList<>();
    private ArrayList<Integer> nDemsfull = new ArrayList<>();
    private ArrayList<Integer> nRepsfull = new ArrayList<>();
    private ArrayList<Integer> demAggfull = new ArrayList<>();
    private ArrayList<Integer> repAggfull = new ArrayList<>();

    /**
     * Constructor of the GUI class
     */
    public GUI() {
        World world; // the world which will run the simulations

        // setup of text box and its fields
        JFrame frame = new JFrame();
        JPanel pane = new JPanel();
        // in quotation is default values
        JTextField input0 = new JTextField("10000"); //turns
        JTextField input1 = new JTextField("100"); // number of democrats
        JTextField input2 = new JTextField("20"); // aggression of democrats
        JTextField input3 = new JTextField("100"); // number of republicans
        JTextField input4 = new JTextField("20"); // aggression of republicans

        // text box layout
        pane.setLayout(new GridLayout(0, 2));

        // adding text of what the options do
        pane.add(new JLabel("Number of turns:"));
        pane.add(input0);

        pane.add(new JLabel("Number of Democrats:"));
        pane.add(input1);

        pane.add(new JLabel("Democrats aggression [0-100]:"));
        pane.add(input2);

        pane.add(new JLabel("Number of Republicans:"));
        pane.add(input3);

        pane.add(new JLabel("Republicans aggression [0-100]:"));
        pane.add(input4);

        // drop down menu for the automated simulations
        pane.add(new JLabel("<html> Automated simulations. Will vary in increments <br> of 1 this value while keep all others as specified:</html>"));
        String[] simoptions = {"None", "Democrats aggression", "Republicans aggression"};
        JComboBox<String> combobox1 = new JComboBox<>(simoptions);
        pane.add(combobox1);

        // drawing the options menu
        int options = JOptionPane.showConfirmDialog(frame, pane, "Select Options", JOptionPane.OK_CANCEL_OPTION);

        // converting inputs to int
        int turns = this.parseInt(input0.getText());
        int nDems = this.parseInt(input1.getText());
        int nReps = this.parseInt(input3.getText());
        int demAgg = this.parseInt(input2.getText());
        int repAgg = this.parseInt(input4.getText());

        // checking for an invalid input, if so the option selection is started from the beginning again
        if (error) {
            JOptionPane.showMessageDialog(frame, "The values must be numeric");
            new GUI();
        } else if (100 < demAgg || 0 > demAgg || 100 < repAgg || 0 > repAgg) {
            JOptionPane.showMessageDialog(frame, "Aggression must be between 0 and 100 (inclusive)");
            new GUI();
            // only the input is valid and ok was selected will the simulation start
        } else if (options == 0) {
            String simSelected = Objects.requireNonNull(combobox1.getSelectedItem()).toString();
            // will see what value if any should be varied
            switch (simSelected) {
                case "None":
                    world = new World(turns, nDems, nReps, demAgg, repAgg);
                    tweetsp1.add(world.getTotalp1());
                    tweetsp2.add(world.getTotalp2());
                    turnsfull.add(turns);
                    nDemsfull.add(nDems);
                    nRepsfull.add(nReps);
                    demAggfull.add(demAgg);
                    repAggfull.add(repAgg);

                    break;
                case "Democrats aggression":
                    for (int i = 0; i < 101; i++) {
                        world = new World(turns, nDems, nReps, i, repAgg);
                        tweetsp1.add(world.getTotalp1());
                        tweetsp2.add(world.getTotalp2());
                        turnsfull.add(turns);
                        nDemsfull.add(nDems);
                        nRepsfull.add(nReps);
                        demAggfull.add(i);
                        repAggfull.add(repAgg);
                    }
                    break;
                case "Republicans aggression":
                    for (int i = 0; i < 101; i++) {
                        world = new World(turns, nDems, nReps, demAgg, i);
                        tweetsp1.add(world.getTotalp1());
                        tweetsp2.add(world.getTotalp2());
                        turnsfull.add(turns);
                        nDemsfull.add(nDems);
                        nRepsfull.add(nReps);
                        demAggfull.add(demAgg);
                        repAggfull.add(i);
                    }
                    break;
            }
            output();
        }
        // if cancel or the x was selected the programs closes
        System.exit(1);
    }

    /**
     * Parses a string to int or catches the exception
     * @param s String to be parsed
     */
    private int parseInt(String s) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            error = true;
            return 0;
        }
    }

    /**
     * Output the data of the simulation in a .csv file
     */
    private void output() {
        try {
            FileWriter writer = new FileWriter("test.csv2");
            writer.write("tweetCountPerson1");
            writer.write(';');
            writer.write("tweetCountPerson2");
            writer.write(';');
            writer.write("numberOfTruns");
            writer.write(';');
            writer.write("numberOfDemocrats");
            writer.write(';');
            writer.write("numberOfRepublicans");
            writer.write(';');
            writer.write("democratAggression");
            writer.write(';');
            writer.write("repulicanAggression");
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
                writer.write('\n');
            }
            writer.close();
        } catch (IOException e) {
            // Oh god please no!
        }
    }
}