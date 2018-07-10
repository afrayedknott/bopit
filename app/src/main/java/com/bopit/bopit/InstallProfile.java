package com.bopit.bopit;

import java.io.Serializable;
import java.util.UUID;

//This used to be called User, but I decided I want to minimize complexity for myself and users by
// just tracking stats per install. It's not necessary to track across reinstalls or separate
// devices for my purpose or user experience.
public class InstallProfile implements Serializable {

    private String installID;
    private double installAverage;
    private double installBest;
    private double previousAverage;
    private double previousBest;

    public InstallProfile() { createAndSetInstallID(); }

    public String getInstallID() {
        return installID;
    }

    public void createAndSetInstallID() { UUID.randomUUID().toString(); };

    public void setInstallID(String installUUID) { this.installID = installUUID; };

    public double getInstallAverage() { return installAverage; }

    public void setInstallAverage(double installAverage) { this.installAverage = installAverage; }

    public double getInstallBest() { return installBest; }

    public void setInstallBest(double installBest) { this.installBest = installBest; }

    public double getPreviousAverage() { return previousAverage; }

    public void setPreviousAverage(double Average) { this.previousAverage = Average; }

    public double getPreviousBest() { return previousBest; }

    public void setPreviousBest(double Best) { this.previousBest = Best; }
}
