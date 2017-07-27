package net.floodlightcontroller.learningswitch;

import java.io.FileReader;
import java.io.IOException;

import net.floodlightcontroller.staticentry.web.SFPEntryMap;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.restlet.data.Status;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by sinakashipazha on 2017/7/27 AD.
 */
public class StartChangeResource extends ServerResource {
    protected static Logger log = LoggerFactory.getLogger(StartChangeResource.class);

    @Post()
    public SFPEntryMap ListStaticFlowEntries() {

        long switchUpdateTime = Long.parseLong((String) getRequestAttributes().get("switchUpdateTime"));
        long betweenRoundTime = Long.parseLong((String) getRequestAttributes().get("betweenRoundTime"));

        log.info("switchUpdateTime set to " + switchUpdateTime);
        log.info("betweenRoundTime set to " + betweenRoundTime);

        JSONArray rounds = UpPorts.getMyInstance().getRounds();
        new RoundScheduler(rounds, betweenRoundTime, switchUpdateTime);

        setStatus(Status.SUCCESS_OK, "Start Changing topology.");
        return null;
    }
}
