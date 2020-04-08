import javax.swing.*;
import java.awt.*;

/**
 * Main class
 */
public class Main {

    public static void main(String[] args){
        JFrame frame = new JFrame();
        JPanel pane = new JPanel();
        JTextField input0 = new JTextField(5);
        JTextField input1 = new JTextField(5);
        JTextField input2 = new JTextField(5);
        JTextField input3 = new JTextField(5);
        JTextField input4 = new JTextField(5);
        JTextField input5 = new JTextField(5);
        JTextField input6 = new JTextField(5);
        JTextField input7 = new JTextField(5);

        pane.setLayout(new GridLayout(0, 2));


        pane.add(new JLabel("Number of turns:"));
        pane.add(input0);

        pane.add(new JLabel("Number of Democrats:"));
        pane.add(input1);

        pane.add(new JLabel("Democrats aggression [1-100]:"));
        pane.add(input3);

        pane.add(new JLabel("Number of Republicans:"));
        pane.add(input2);

        pane.add(new JLabel("Republicans aggression [1-100]:"));
        pane.add(input4);

        pane.add(new JLabel("Cost of tweet/corporation:"));
        pane.add(input5);

        pane.add(new JLabel("Cost of argument/fight:"));
        pane.add(input6);

        pane.add(new JLabel("Payoff for winning a round (Given to each agent in the winning group):"));
        pane.add(input7);

        int options =  JOptionPane.showConfirmDialog(frame, pane,"Select Options",JOptionPane.OK_CANCEL_OPTION);
        int turns = Integer.parseInt(input0.getText());
        int nDems = Integer.parseInt(input1.getText());
        int nReps = Integer.parseInt(input2.getText());
        int demAgg = Integer.parseInt(input3.getText());
        int repAgg = Integer.parseInt(input4.getText());
        int cCost = Integer.parseInt(input5.getText());
        int dCost = Integer.parseInt(input6.getText());
        int payoff = Integer.parseInt(input7.getText());


        World world = new World(turns, nDems, nReps, demAgg, repAgg, cCost, dCost, payoff);
        //World world = new World(1, 1, 0.01, 1.0);
    }
}
