package net.floodlightcontroller.learningswitch;

import java.util.HashMap;
import java.util.Map;

import net.floodlightcontroller.core.web.ControllerSwitchesResource;
import net.floodlightcontroller.staticentry.IStaticEntryPusherService;

import net.floodlightcontroller.staticentry.web.SFPEntryMap;
import org.projectfloodlight.openflow.protocol.OFMessage;
import org.projectfloodlight.openflow.types.DatapathId;
import org.restlet.data.Status;
import org.restlet.resource.Get;
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

        String switchUpdateTime = (String) getRequestAttributes().get("switchUpdateTime");
        String betweenRoundTime = (String) getRequestAttributes().get("betweenRoundTime");

        log.info("switchUpdateTime set to " + switchUpdateTime);
        log.info("betweenRoundTime set to " + betweenRoundTime);

        setStatus(Status.SUCCESS_OK,"Start Changing topology.");
        return null;
    }
}
