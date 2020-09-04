package com.blalp.sftwrapper.display;

import java.awt.Component;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MainWindow {
    public static MainWindow instance;
    private JFrame frame = new JFrame("SFTLauncher");
    private JPanel panel = new JPanel();

    public MainWindow() {
        instance = this;
        frame.setIconImage(new ImageIcon("assets/logo.png").getImage());
        JLabel header = new JLabel("<html><h1 style='text-align=center;'>Super Fun Time Launcher</h1></html>");
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(header);
        frame.add(panel);
    }

    public JFrame getFrame() {
        return frame;
    }

    public JPanel getPanel() {
        return panel;
    }

    public void show(){
        frame.pack();
        frame.setVisible(true);
    }
}
