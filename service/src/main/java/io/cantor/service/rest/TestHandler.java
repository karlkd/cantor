package io.cantor.service.rest;

import com.google.common.collect.Maps;

import java.util.concurrent.ConcurrentMap;

import io.cantor.http.AffinityScheduler;
import io.cantor.http.AppRequestResponseHandler;
import io.cantor.http.HandlerRequest;
import io.cantor.http.HandlerResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestHandler implements AppRequestResponseHandler<AffinityScheduler, HandlerRequest, HandlerResponse> {
    private ConcurrentMap<Integer,Object> concurrentMap = Maps.newConcurrentMap();
    @Override
    public void handle(AffinityScheduler scheduler, HandlerRequest req, HandlerResponse resp) {
        Integer random =(int) (Math.random() * 10000);
        //concurrentMap.put(random,new Object());
        log.info("random = {}",random);
        resp.ok();
    }
}
