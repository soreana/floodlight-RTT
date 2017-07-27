package net.floodlightcontroller.learningswitch;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
        // todo stop this scheduler
        time.cancel();
        time.purge();

        System.out.println(count);
        // todo if rounds ends stop this scheduler
        if (count == rounds.size())
            return;

        // todo start PortUpDownScheduler to change switches
        new PortUpDownScheduler((JSONObject) rounds.get(count), switchUpdateTime, new RoundScheduler(this));

        count++;
    }

    public void start() {
        time.schedule(this, betweenRoundTime, betweenRoundTime);
    }

    public static void main(String[] args) throws InterruptedException, IOException, ParseException {
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(new FileReader("src/main/resources/topology/topo.json"));
        JSONArray rounds = (JSONArray) jsonObject.get("rounds");
        new RoundScheduler(rounds, 3000,1000 );
        Thread.sleep(1000);
    }
}
