package com.drakos;

import com.drakos.spout.PiSpout;
import org.apache.storm.Config;
import org.apache.storm.LocalDRPC;
import org.apache.storm.StormSubmitter;
import org.apache.storm.trident.Stream;
import org.apache.storm.trident.TridentTopology;

/**
 *
 * @author Bruce Brown
 */
public class StarterTopology {

    public static void main(String[] args) throws Exception {
        Config config = new Config();
        config.put("cassandra.keyspace", "vpsq");
        config.put("cassandra.port", 9042);
        config.put("cassandra.nodes", "96.118.49.19");
        config.put("cassandra.username", "cassandra");
        StormSubmitter.submitTopologyWithProgressBar(args[0], config, buildTopology(null).build());
        buildTopology(null);
    }

    public static TridentTopology buildTopology(LocalDRPC drpc) {
        TridentTopology topology = new TridentTopology();
        Stream piStream = topology.newStream("spout", new PiSpout());
            
        return topology;
    }
}
