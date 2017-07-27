package net.floodlightcontroller.learningswitch;

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


    @Override
    public void run() {
        count--;

        // todo change one switch
        Date now = new Date();
        System.out.println("Time is :" + now); // Display current time
        System.out.println(iterator.next());


        if(count == 0){
            nextRoundExecutor.start();
            System.out.println("End timer");
            time.cancel();
            time.purge();
        }
    }

}
