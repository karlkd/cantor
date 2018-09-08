package io.cantor.service;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigValueFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import io.cantor.service.clients.TimeWatcher;
import io.cantor.service.clients.storage.Storage;
import io.cantor.service.clients.storage.StorageFactory;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InitialService {

    private static final String ID_PATTERN = "/id";
    private static final String PARSE_PATTERN = "/info";

    private TimeWatcher watcher;
    private List<Storage> storages;

    public static void main(String[] args) {
        InitialService initialService = new InitialService();
    }

    public InitialService() {
        Config sysConfig = ConfigFactory.load("application");
        sysConfig = sysConfig.withFallback(ConfigFactory.empty()
                                                        .withValue("instance.id",
                                                                   ConfigValueFactory.fromAnyRef(System.getProperty("host.name"))));
        String instanceId = sysConfig.getString("instance.id");
        if (log.isInfoEnabled()) {
            log.info("starting service {}", instanceId);
        }
        Config appConfig = sysConfig.getConfig("application");

        String[] storageList = appConfig.getString("storages.sequence").split(",");
        if (storageList.length <= 0)
            throw new IllegalStateException("Number of storage should be at least 1.");

        storages = new ArrayList<>();
        for (String name : storageList) {
            name = name.trim();
            if (!StorageFactory.supportedStorage().contains(name)) {
                if (log.isWarnEnabled())
                    log.warn("Storage {} is not supported!");

                continue;
            }

            log.info("init storage {}", name);
            Optional<Storage> opt = StorageFactory.getInstance(name, appConfig, instanceId);
            if (!opt.isPresent()) {
                if (log.isErrorEnabled())
                    log.error("create {} Storage failed", name);
                throw new IllegalStateException(String.format("init %s failed", name));
            }
            storages.add(opt.get());
        }
        if (storages.isEmpty())
            throw new IllegalStateException("Number of storage should be at least 1.");

        //start time watcher
        watcher = new TimeWatcher(appConfig, storages, instanceId);
        watcher.start();

       return;
    }


}
