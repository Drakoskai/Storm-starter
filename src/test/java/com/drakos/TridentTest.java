package com.drakos;

import com.drakos.spout.PiSpout;
import java.util.concurrent.TimeUnit;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.LocalDRPC;
import org.apache.storm.trident.Stream;
import org.apache.storm.trident.TridentTopology;
import org.apache.storm.trident.operation.builtin.Debug;
import org.apache.storm.tuple.Fields;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Bruce Brown
 */
public class TridentTest {

    private static LocalCluster cluster;
    private static LocalDRPC drpc;
    private static Config config;

    @BeforeClass
    public static void setUpClass() {
        config = new Config();
        cluster = new LocalCluster();
        config.setNumWorkers(1);
        config.setMaxTaskParallelism(1);
        drpc = new LocalDRPC();
    }

    @AfterClass
    public static void tearDownClass() {
        if (cluster != null) {
            cluster.shutdown();
        }
        if (drpc != null) {
            drpc.shutdown();
        }
    }

    @Test
    public void TridentIntegrationTest() throws InterruptedException {
        TridentTopology topology = buildTopology(drpc);
        cluster.submitTopology("labweek-ml-test", config, topology.build());

        TimeUnit.MINUTES.sleep(3);
    }

    public static TridentTopology buildTopology(LocalDRPC drpc) {
        TridentTopology topology = new TridentTopology();

        Stream piStream = topology.newStream("spout", new PiSpout());

        piStream
                .each(new Fields("pi"), new Debug());

        return topology;
    }
}
