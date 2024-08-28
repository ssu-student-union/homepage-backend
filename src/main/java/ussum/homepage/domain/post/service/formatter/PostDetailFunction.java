package ussum.homepage.domain.post.service.formatter;

import java.util.List;

@FunctionalInterface
public interface PostDetailFunction<T, K, H , U, V, W, Q, Y, I, R> {
    R apply(T t, K k, H h, U u, V v, W w, List<Q> q, List<Y> y, List<I> i);
}

