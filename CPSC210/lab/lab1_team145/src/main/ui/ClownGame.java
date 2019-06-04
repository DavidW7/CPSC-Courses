package ui;

import javax.swing.*;

/**
 * Created by norm on 2015-09-17.
 */
public class ClownGame {

    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("Clown Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(new ClownPanel(false, false, false, false, false));

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}

