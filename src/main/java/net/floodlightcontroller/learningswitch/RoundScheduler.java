package net.floodlightcontroller.learningswitch;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by sinakashipazha on 2017/7/27 AD.
 */
public class RoundScheduler extends TimerTask {
    private long switchUpdateTime;
    private long betweenRoundTime;
    private JSONArray rounds;
    private Timer time = new Timer();
    private static int count;

    public RoundScheduler(JSONArray rounds, long betweenRoundTime, long switchUpdateTime) {
        this.betweenRoundTime = betweenRoundTime;
        this.switchUpdateTime = switchUpdateTime;
        this.rounds = rounds;
        count = 0;

        time.schedule(this, 0, betweenRoundTime);
    }

    private RoundScheduler(RoundScheduler roundScheduler) {
        this.switchUpdateTime = roundScheduler.switchUpdateTime;
        this.betweenRoundTime = roundScheduler.betweenRoundTime;
        this.rounds = roundScheduler.rounds;
        this.time = new Timer();
    }

    @Override
    public void run() {
        time.cancel();
        time.purge();

        if (count == rounds.size())
            return;

        new PortUpDownScheduler((JSONObject) rounds.get(count), switchUpdateTime, new RoundScheduler(this));

        count++;
    }

    public void start() {
        time.schedule(this, betweenRoundTime, betweenRoundTime);
    }
}
