package io.cantor.service.clients;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

import org.apache.commons.math3.util.Pair;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LocalIdGenerator {

    public static final long LOCAL_CATE = 2L;

    private static final int PER_CNT = 1;

    private static ConcurrentHashMap<Pair<Long, Long>, SnowFlake> idSpace = new ConcurrentHashMap<>();
    private static HashFunction MURMUR_3 = Hashing
            .murmur3_32(UUID.randomUUID().toString().hashCode());

    private long locatorId;

    public LocalIdGenerator() {
        this.locatorId = Integer.valueOf(MURMUR_3.hashString(UUID.randomUUID().toString()).asInt())
                                .longValue();
    }

    public Pair<Long, Long> getFromLocal(long serviceCode, long extra, long range) {
        Parser.Deserializer deserializer = next(serviceCode, extra, range);

        return new Pair<>(deserializer.timestamp(), deserializer.sequence());
    }

    private Parser.Deserializer next(long serviceCode, long extra, long range) {

        long[] parts = idSpace.computeIfAbsent(new Pair<>(serviceCode, extra),
                                               (k) -> new SnowFlake(locatorId, serviceCode, extra,
                                                                    LOCAL_CATE,
                                                                    Parser.DEFAULT_CLUSTER))
                              .next(range);
        return SnowFlake.deserializer(parts[0], parts[1]);
    }

}
