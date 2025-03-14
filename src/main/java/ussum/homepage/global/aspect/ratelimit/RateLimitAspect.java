package ussum.homepage.global.aspect.ratelimit;

import static ussum.homepage.global.error.status.ErrorStatus._TOO_MANY_REQUESTS;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.BucketConfiguration;
import io.github.bucket4j.ConsumptionProbe;
import io.github.bucket4j.Refill;
import io.github.bucket4j.distributed.proxy.ProxyManager;
import jakarta.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.util.function.Supplier;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
import ussum.homepage.global.config.custom.RateLimit;
import ussum.homepage.global.error.exception.RateLimitException;

@Aspect
@Component
@EnableAspectJAutoProxy
public class RateLimitAspect {

    private final ProxyManager<String> proxyManager;
    private final HttpServletRequest request;

    @Autowired
    public RateLimitAspect(ProxyManager<String> proxyManager, HttpServletRequest request) {
        this.proxyManager = proxyManager;
        this.request = request;
    }

    @Around("@within(rateLimit)")
    public Object rateLimitCheck(ProceedingJoinPoint joinPoint, RateLimit rateLimit)
        throws Throwable {
        String apiPath = request.getRequestURI();
        String key = "IP:" + apiPath;

        // Bucket 설정
        Supplier<BucketConfiguration> bucketConfig = getConfigSupplier(rateLimit);
        Bucket bucket = proxyManager.builder().build(key, bucketConfig);

        ConsumptionProbe probe = bucket.tryConsumeAndReturnRemaining(1);  // 1개의 요청을 소모하려 시도

        if (probe.isConsumed()) {
            return joinPoint.proceed();  // 제한을 넘지 않으면 메소드 실행
        } else {
            throw new RateLimitException(_TOO_MANY_REQUESTS);
        }
    }

    private Supplier<BucketConfiguration> getConfigSupplier(RateLimit rateLimit) {
        Refill refill = Refill.greedy(rateLimit.refillTokens(),
            Duration.ofMinutes(rateLimit.refillDuration()));
        Bandwidth bandwidth = Bandwidth.classic(rateLimit.capacity(), refill);

        return () -> BucketConfiguration.builder()
            .addLimit(bandwidth)
            .build();
    }
}
