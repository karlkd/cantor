package io.cantor.sdk;

import java.util.Map;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RequestBuilder {

    private long serviceId;
    private long extra;
    private long range;
    private Long timeout;

    public static RequestBuilder builder() {
        return new RequestBuilder();
    }

    public RequestBuilder serviceId(long serviceId) {
        this.serviceId = serviceId;
        return this;
    }

    public RequestBuilder extra(long extra) {
        this.extra = extra;
        return this;
    }

    public RequestBuilder range(long range) {
        this.range = range;
        return this;
    }

    public RequestBuilder timeout(Long timeout) {
        this.timeout = timeout;
        return this;
    }

    public SequenceRequest build() {
        return new SequenceRequest(serviceId, extra, range, timeout);
    }


    @Getter
    public class SequenceRequest {
        private long serviceId;
        private long extra;
        private long range;
        private Long timeout;

        private SequenceRequest(long serviceId, long extra, long range, Long timeout) {
            this.serviceId = serviceId;
            this.extra = extra;
            this.range = range;
            this.timeout = timeout;
        }

        public Map<String, String> headers() {
            return null;
        }

        public Map<String, String> queries() {
            return null;
        }

        public byte[] content() {
            return String.format("{\"range\":%s,\"extra\":%s,\"mode\":1}", range, extra).getBytes();
        }

        public String path() {
            return "/id/" + serviceId;
        }
    }
}