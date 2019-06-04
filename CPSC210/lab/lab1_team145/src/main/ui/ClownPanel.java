package ui;

import model.ListOfClowns;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by norm on 2015-09-17.
 */
public class ClownPanel extends JPanel {
    final ListOfClowns cl = new ListOfClowns();
    final List<JButton> buttons = new ArrayList<>();
    ClownImages clownImages = new ClownImages();

    public ClownPanel(boolean ...args) {
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        for (int i = 0; i < args.length; ++i) {
            JButton b = new JButton(new ClownIcon(args[i]));
            cl.addClown(args[i]);
            final int myindex = i;
            //Add action listener to button
            b.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    //Execute when button is pressed
                    System.out.println("You clicked button " + myindex);
                    flip(myindex);
                }
            });
            add(b);
            buttons.add(b);
        }
    }

    void createAndShowWinMessage() {
        JOptionPane.showMessageDialog(this, "You have won the game!", "You Win!", JOptionPane.INFORMATION_MESSAGE,
                clownImages.getStandingImage());
        System.exit(0);
    }

    void flip(int index) {
        cl.flip(index);
        for (int i = 0; i < buttons.size(); ++i) {
            if (cl.isClownStanding(i)) {
                buttons.get(i).setIcon(clownImages.getStandingImage());
            } else {
                buttons.get(i).setIcon(clownImages.getSittingImage());
            }
        }
        if (cl.allClownsStanding()) {
            javax.swing.SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    createAndShowWinMessage();
                }
            });
        }
    }
}
