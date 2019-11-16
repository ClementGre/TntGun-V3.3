package fr.themsou.methodes;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PlayerInfo {

    private String playerName;


    private float lastPitch = 0;
    private float lastYaw = 0;
    private int lastViewVelChange = 0;

    private boolean isLoggin = false;
    private boolean isSecureLogin = false;

    private Player tpa = null;
    private Player tphere = null;

    private int claimToJoin = 0;

    private long lastMsgTime = 0;
    private String lastMsgText = "";

    public PlayerInfo(Player p){
        this.playerName = p.getName();
    }

    public float getLastPitch() {
        return lastPitch;
    }
    public float getLastYaw() {
        return lastYaw;
    }
    public void setLastPitch(float lastPitch) {
        if(lastPitch != this.lastPitch){
            if(lastViewVelChange >= 30)
                Bukkit.broadcastMessage("§5" + playerName + " n'est plus AFK");

            lastViewVelChange = 0;
            this.lastPitch = lastPitch;
        }
    }
    public void setLastYaw(float lastYaw) {
        if(lastYaw != this.lastYaw){
            if(lastViewVelChange >= 30)
                Bukkit.broadcastMessage("§5" + playerName + " n'est plus AFK");

            lastViewVelChange = 0;
            this.lastYaw = lastYaw;
        }
    }
    public int getLastViewVelChange() {
        return lastViewVelChange;
    }
    public void setLastViewVelChange(int lastViewVelChange) {
        if(lastViewVelChange == 0 && this.lastViewVelChange >= 30)
            Bukkit.broadcastMessage("§5" + playerName + " n'est plus AFK");

        this.lastViewVelChange = lastViewVelChange;
    }
    public void addOneToLastViewVelChange() {
        this.lastViewVelChange += 1;
    }

    public boolean isLoggin() {
        return isLoggin;
    }
    public void setLoggin(boolean loggin) {
        isLoggin = loggin;
    }
    public boolean isSecureLogin() {
        return isSecureLogin;
    }
    public void setSecureLogin(boolean secureLogin) {
        isSecureLogin = secureLogin;
    }

    public Player getTpa() {
        return tpa;
    }
    public void setTpa(Player tpa) {
        this.tpa = tpa;
    }
    public Player getTphere() {
        return tphere;
    }
    public void setTphere(Player tphere) {
        this.tphere = tphere;
    }

    public int getClaimToJoin() {
        return claimToJoin;
    }
    public void setClaimToJoin(int claimToJoin) {
        this.claimToJoin = claimToJoin;
    }

    public long getLastMsgTime() {
        return lastMsgTime;
    }
    public void setLastMsgTime(long lastMsgTime) {
        this.lastMsgTime = lastMsgTime;
    }

    public String getLastMsgText() {
        return lastMsgText;
    }
    public void setLastMsgText(String lastMsgText) {
        this.lastMsgText = lastMsgText;
    }
}
