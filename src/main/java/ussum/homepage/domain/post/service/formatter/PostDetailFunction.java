package ussum.homepage.domain.post.service.formatter;

import java.util.List;

@FunctionalInterface
public interface PostDetailFunction<T, U, V, W, Q, Y, R> {
    R apply(T t, U u, V v, W w, List<Q> q, List<Y> y);
}

