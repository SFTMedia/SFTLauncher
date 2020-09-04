package com.blalp.sftwrapper.display;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.blalp.sftwrapper.instances.MCEternal;
import com.blalp.sftwrapper.instances.Stoneblock2;

public class Configure {
    public Configure() {
        createWindow();
    }
    private void createWindow() {
        JPanel panel = new JPanel();
        JLabel servers = new JLabel("Servers");
        JCheckBox survival = new JCheckBox("Survival", true);
        JCheckBox mcEternal = new JCheckBox("MC Eternal", MCEternal.instance.canRun());
        JCheckBox stoneblock2 = new JCheckBox("StoneBlock 2", Stoneblock2.instance.canRun());
    }
}
