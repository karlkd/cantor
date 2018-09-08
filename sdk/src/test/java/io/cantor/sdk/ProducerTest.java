package io.cantor.sdk;

import org.junit.Assert;
import org.junit.Test;

import java.util.Comparator;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.CountDownLatch;

import okhttp3.OkHttpClient;

public class ProducerTest {

    @Test
    public void test() throws Exception {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new MockHttpInterceptor())
                .build();
        ServiceCaller caller = new ServiceCaller(client);
        SequenceProducer sequenceProducer = new SequenceProducer(caller,1000);
        Sequence sequence = sequenceProducer.produce(5);
        CopyOnWriteArrayList<Long> list = new CopyOnWriteArrayList<>();
        Assert.assertEquals(5L, sequence.category());
        Assert.assertEquals(1L, sequence.sequence());
        CountDownLatch latch = new CountDownLatch(5);
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                for (int j = 0; j < 200; j++) {
                    Sequence seq = sequenceProducer.produce(5);
                    list.add(seq.sequence());
                }
                latch.countDown();
            }).start();
        }
        latch.await();
        list.sort(Comparator.naturalOrder());
        Assert.assertEquals(1001L, list.get(list.size() - 1).longValue());
    }
}
