package edu.ufp.inf.sd.rmi.advanceWars.server;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Timer;

public class TokenRing {

    private int ringPlace;
    private int ringMax;
    private Timer timer;

    public TokenRing(int ringMax) {
        this.ringMax = ringMax;
        this.ringPlace = 0;

    }
    public void nextHolder() {
        this.ringPlace++;
        if ( this.ringPlace >= this.ringMax) {
            this.ringPlace = 0;
        }
    }

    public int currentHolder() {
        return this.ringPlace;
    }


}
