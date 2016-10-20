package com.drakos.spout;

import avro.shaded.com.google.common.collect.Lists;
import avro.shaded.com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import org.apache.storm.Config;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.trident.operation.TridentCollector;
import org.apache.storm.trident.spout.IBatchSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

/**
 *
 * @author Bruce Brown
 */
public class PiSpout implements IBatchSpout {

    private static final Fields OUT_FIELDS = new Fields("pi");
    private PiGen generator;
    private Map<Long, List<Values>> batches;
    private final int batchSize;

    public PiSpout() {
        this(255);
    }

    public PiSpout(final int batchSize) {
        this.batchSize = batchSize;
    }

    @Override
    public void open(Map conf, TopologyContext context) {
        generator = new PiGen();
        batches = Maps.newHashMap();
    }

    @Override
    public void emitBatch(final long batchId, final TridentCollector collector) {
        final List<Values> values = batches.getOrDefault(batchId, Lists.newArrayListWithCapacity(batchSize));
        if (values.isEmpty()) {
            for (int i = 0; i < batchSize; i++) {
                final Values v = new Values();
                final char c = generator.emit();
                v.add(c);
                values.add(v);
            }

            batches.put(batchId, values);
        }

        for (Values toEmit : values) {
            collector.emit(toEmit);
        }
    }

    @Override
    public void ack(final long batchId) {
        batches.remove(batchId);
    }

    @Override
    public void close() {
        batches.clear();
    }

    @Override
    public Map<String, Object> getComponentConfiguration() {
        return new Config();
    }

    @Override
    public Fields getOutputFields() {
        return OUT_FIELDS;
    }
}
