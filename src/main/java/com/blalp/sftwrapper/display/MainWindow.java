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
        JLabel header = new JLabel("<html><h1 style='text-align=center;'>Super Fun Time Launcher - "+version+"</h1><h2>Notices</h2><p>Sorry this is a BETA build. While it should work, many sensible features are missing.</p><h2>Features</h2><ul><li>Auto-RAM setting</li><li>Auto import of SFT's server version</li></ul><h2>Instructions</h2><ul><li>Launch SFTLauncher</li><li>MultiMC will prompt you for setup, choose your langauge and what-not.</li><li>Import MC Eternal (MultiMC may not respond for a bit while it's thinking)</li><li>Close MultiMC</li><li>Launch SFTLauncher</li><li>Import Stoneblock 2 (MultiMC may not respond for a bit while it's thinking)</li><li>Close MultiMC</li><li>Start SFTLauncher</li><li>add your minecraft account via the top right icon.</li><li>Click on either one to play!</li></ul></html>");
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
