package com.blalp.sftwrapper.display;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.blalp.sftwrapper.instances.GenericInstance;
import com.blalp.sftwrapper.instances.MCEternal;
import com.blalp.sftwrapper.instances.Stoneblock2;
import com.blalp.sftwrapper.util.Config;
import com.blalp.sftwrapper.util.MultiMC;

public class Configure {
    public HashMap<GenericInstance,JCheckBox> instances = new HashMap<>();
    public Configure() {
        createItems();
        if(!MultiMC.isInstalled()){
            MultiMC.install();
        }
    }
    private void createItems() {
        JPanel panel = new JPanel();
        JLabel servers = new JLabel("Servers");
        //instances.put(Survival.instance, new JCheckBox("Survival", true));
        instances.put(MCEternal.instance, new JCheckBox("MC Eternal", MCEternal.instance.canRun()));
        instances.put(Stoneblock2.instance, new JCheckBox("StoneBlock 2", Stoneblock2.instance.canRun()));
        panel.add(servers);
        for (JCheckBox checkBox : instances.values()) {
            panel.add(checkBox);
        }
        MainWindow.instance.getPanel().add(panel);
    }
    public Set<GenericInstance> getChecked() {
        return instances.keySet();
    }
}
