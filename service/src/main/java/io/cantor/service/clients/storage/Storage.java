package io.cantor.service.clients.storage;

import java.util.List;
import java.util.Optional;

public interface Storage {

    String METRIC_RATE = "Rate";
    String METRIC_SOURCE = "source";

    Optional<Long> incrementAndGet(long serviceCode, long extra, long ts, long range);

    void close();

    boolean available();

    int resetCount();

    long syncTime(long localTime);

    List<Long> timeMeta();

    void unregister();

    String type();

    long descriptor();
}
