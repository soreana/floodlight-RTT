package net.floodlightcontroller.learningswitch;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sinakashipazha on 2017/7/27 AD.
 */
public class UpPorts {
    protected static Logger log = LoggerFactory.getLogger(UpPorts.class);

    private static UpPorts myInstance = new UpPorts();

    private Map<Long, ArrayList<Integer>> portMap = new HashMap<>();

    private UpPorts(){
        JSONParser parser = new JSONParser();

        try {
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader("src/main/resources/topology/topo.json"));
            JSONObject switches = (JSONObject) jsonObject.get("switches");

            for (int i = 1; i <= switches.size(); i++) {
                ArrayList<Integer> ports = new ArrayList<>();
                JSONArray availablePorts = (JSONArray) switches.get("s" + i);

                for (Object availablePort : availablePorts)
                    ports.add(((Long) availablePort).intValue());

                portMap.put((long) i, ports);

            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Integer> getSwitchesUpPorts(Long switchId){
        // todo clone array and return that array
        ArrayList<Integer> temp = new ArrayList<>();
        for (Integer current: portMap.get(switchId))
            temp.add(current.intValue());
        return temp;
    }

    public static UpPorts getMyInstance(){
        return myInstance;
    }

    public void makeLinkUp(long switchId,int portNumber) throws PortIsAlreadyUpException {
        if (portMap.get(switchId).contains(portNumber))
            throw new PortIsAlreadyUpException();

        portMap.get(switchId).add(portNumber);
        log.info("make port " + portNumber + " of switch s" + switchId + " up");
    }

    public void makeLinkDown(long switchId,int portNumber) throws PortIsAlreadyDownException {
        if (! portMap.get(switchId).contains(portNumber))
            throw new PortIsAlreadyDownException();

        portMap.get(switchId).remove((Integer) portNumber);
        log.info("make port " + portNumber + " of switch s" + switchId + " down");
    }
}
