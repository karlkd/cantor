package io.cantor.service;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigValueFactory;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InitialService {

    private static final String ID_PATTERN = "/id/{service_code}";
    private static final String PARSE_PATTERN = "/info";

    // private TimeWatcher watcher;


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




       return;
    }


}
