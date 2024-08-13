package ussum.homepage.domain.post.service.formatter;

import java.util.List;

@FunctionalInterface
public interface PostDetailFunction<T, K, U, V, W, O, Q, Y, R> {
    R apply(T t, K k, U u, V v, W w, O o, List<Q> q, List<Y> y);
}

