package net.floodlightcontroller.learningswitch;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.util.Date;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by sinakashipazha on 2017/7/27 AD.
 */
public class PortUpDownScheduler extends TimerTask {
    private JSONObject round ;
    private static int count = -1;
    private Timer time = new Timer();
    private long period;
    private RoundScheduler nextRoundExecutor;
    private Iterator iterator;

    public PortUpDownScheduler(JSONObject round, long period, RoundScheduler nextRoundExecutor){
        this.round = round;

        if(count <= 0 )
            count = round.size();

        this.period = period;
        schedule();

        this.nextRoundExecutor = nextRoundExecutor;

        iterator = round.keySet().iterator();
    }

    private void schedule(){
        time.schedule(this,0,period);
    }

    private long extractSwitchId(String switchName){
        return Long.parseLong(switchName.substring(switchName.length()-1));
    }

    @Override
    public void run() {
        count--;

        String switchName = (String) iterator.next();
        long switchId = extractSwitchId(switchName);
        JSONObject sw = (JSONObject) round.get(switchName);

        for(Object current : (JSONArray) sw.get("up"))
            UpPorts.getMyInstance().makeLinkUp(switchId,Integer.parseInt(String.valueOf(current)));

        for(Object current : (JSONArray) sw.get("down"))
            UpPorts.getMyInstance().makeLinkDown(switchId,Integer.parseInt(String.valueOf(current)));

        if(count == 0){
            nextRoundExecutor.start();
            System.out.println("End round.");
            time.cancel();
            time.purge();
        }
    }

}
