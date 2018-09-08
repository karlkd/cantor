package io.cantor.service.clients.storage;

import java.util.List;
import java.util.Optional;

public interface Storage {

    Optional<Long> incrementAndGet(long category, long ts, long range);

    void close();

    boolean available();

    int resetCount();

    long syncTime(long localTime);

    List<Long> timeMeta();

    void unregister();

    String type();

    long descriptor();
}
