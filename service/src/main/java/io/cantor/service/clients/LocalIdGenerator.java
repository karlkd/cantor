package io.cantor.service.clients;

import org.apache.commons.math3.util.Pair;

import java.util.concurrent.ConcurrentHashMap;

import io.cantor.service.Utils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LocalIdGenerator {

    public static final long LOCAL_CATE = 2L;

    private static ConcurrentHashMap<Long, SnowFlake> idSpace = new ConcurrentHashMap<>();

    private long locatorId;

    public LocalIdGenerator() {
        this.locatorId = (long) Utils.hostname().hashCode();
    }

    public Pair<Long, Long> getFromLocal(long category, long range) {
        Parser.Deserializer deserializer = next(category, range);

        return new Pair<>(deserializer.timestamp(), deserializer.sequence());
    }

    private Parser.Deserializer next(long category, long range) {

        long[] parts = idSpace.computeIfAbsent(category,
                (k) -> new SnowFlake(locatorId, category, LOCAL_CATE)).next(range);
        return SnowFlake.deserializer(parts[0]);
    }

}
