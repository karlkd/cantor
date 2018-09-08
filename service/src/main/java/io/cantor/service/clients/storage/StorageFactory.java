package io.cantor.service.clients.storage;

import com.typesafe.config.Config;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StorageFactory {

    private static final String HBASE = "hbase";
    private static final String REDIS = "redis";
    private static final Set<String> SUPPORTED = new HashSet<>(Arrays.asList(HBASE, REDIS));

    private static Storage hbaseStorage;
    private static Storage redisStorage;

    public static Set<String> supportedStorage() {
        return SUPPORTED;
    }

    public static Optional<Storage> getInstance(String name, Config config, String localId) {

        switch (name) {
            case HBASE:
                return hbase(config, localId);
            case REDIS:
                return redis(config, localId);
            default:
                if (log.isErrorEnabled())
                    log.error("No such storage {}, supported storage are [hbase, redis]", name);
                return Optional.empty();
        }
    }

    private synchronized static Optional<Storage> hbase(Config config, String localId) {
        // todo: implement hbase storage
        return Optional.empty();
    }

    private synchronized static Optional<Storage> redis(Config config, String localId) {
        // todo: implement redis storage
        return Optional.empty();
    }
}
