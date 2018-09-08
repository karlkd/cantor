package io.cantor.service.clients;

import java.util.concurrent.TimeUnit;

class SnowFlake {


    private static final long BEGINNING = 1L;
    private static final long EPOCH = 0L;

    private long sequence = BEGINNING;
    private long lastEpochSeconds = -1L;
    private long instanceId;
    private long extra;
    private long serviceCode;
    private long descriptor;
    private long cluster;

    SnowFlake(Long instanceId, long serviceCode, long extra, long desc, long cluster) {
        this.instanceId = instanceId & Parser.CURRENT_SCHEMA.preserveMusk();
        this.serviceCode = serviceCode & Parser.CURRENT_SCHEMA.serviceCodeMusk();
        this.extra = extra & Parser.CURRENT_SCHEMA.extraMusk();
        this.descriptor = desc;
        this.cluster = cluster;
    }

    synchronized long[] next(long batch) {
        if (batch > Parser.CURRENT_SCHEMA.sequenceMusk() + 1) {
            throw new IllegalStateException("illegal batch");
        }
        long current = epochSeconds(System.currentTimeMillis());
        if (current < lastEpochSeconds) {
            throw new IllegalStateException("clock moved backwards");
        }

        if (current > lastEpochSeconds) {
            sequence = BEGINNING;
        }

        if ((sequence + batch) > Parser.CURRENT_SCHEMA.sequenceMusk()) {
            current = forceToNextMillis();
            if ((sequence + batch) > (Parser.CURRENT_SCHEMA.sequenceMusk() + 1)) {
                sequence = BEGINNING;
            }
        }

        lastEpochSeconds = current;

        long high = serviceCode << Parser.CURRENT_SCHEMA.serviceCodeLeft()
                | extra << Parser.CURRENT_SCHEMA.extraLeft()
                | cluster << Parser.CURRENT_SCHEMA.clusterLeft()
                | descriptor << Parser.CURRENT_SCHEMA.descriptorLeft()
                | instanceId;

        long low = current << Parser.CURRENT_SCHEMA.timestampLeft()
                | sequence;

        sequence = (sequence + batch) & Parser.CURRENT_SCHEMA.sequenceMusk();

        return new long[]{high, low, current};
    }

    private long forceToNextMillis() {
        long stamp = epochSeconds(System.currentTimeMillis());
        while (stamp <= lastEpochSeconds) {
            stamp = epochSeconds(System.currentTimeMillis());
        }
        return stamp;
    }

    private long epochSeconds(long millis) {
        long epochMillis = millis - EPOCH;
        return TimeUnit.MILLISECONDS.toSeconds(epochMillis);
    }

    static Parser.Deserializer deserializer(long high, long low) {
        return new Parser.Deserializer(high, low);
    }

}
