package cz.martin.lekce09ukol.model;

import cz.martin.lekce09ukol.Settings;

public class Contribution {

    private String contribution;
    private int contributionId;

    private boolean isVisible;

    private String user;

    public Contribution(String contribution, int contributionId, boolean isVisible, String user) {
        this.contribution = contribution;
        this.contributionId = contributionId;
        this.isVisible = isVisible;
        this.user = user;
    }

    public String getContribution() {
        return contribution;
    }

    public void setContribution(String contribution) {
        this.contribution = contribution;
    }

    public int getContributionId() {
        return contributionId;
    }

    public void setContributionId(int contributionId) {
        this.contributionId = contributionId;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getFileLine(){
        return this.getContribution() + Settings.SEPARATOR() +
                this.getContributionId() + Settings.SEPARATOR() +
                this.isVisible() + Settings.SEPARATOR() +
                this.getUser();
    }
}
