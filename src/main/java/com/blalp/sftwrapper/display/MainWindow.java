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

    public MainWindow(String version) {
        instance = this;
        frame.setIconImage(new ImageIcon("assets/logo.png").getImage());
        JLabel header = new JLabel("<html><h1 style='text-align=center;'>Super Fun Time Launcher - "+version+"</h1><h2>Features</h2><ul><li>Auto-RAM setting</li><li>Auto import of SFT's server version</li></ul><h1>Instructions</h1><ul><li>Click OK when prompted for Stoneblock 2 and MC Eternal</li><li>Add your minecraft account via the top right icon.<a href='https://github.com/MultiMC/MultiMC5/wiki/Getting-Started#adding-an-account'>instructions https://github.com/MultiMC/MultiMC5/wiki/Getting-Started#adding-an-account</a></li><li>To launch this again, just launch the jar again</li></ul></html>");
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
