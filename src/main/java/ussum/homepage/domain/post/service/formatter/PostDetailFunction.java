package ussum.homepage.domain.post.service.formatter;

import java.util.List;

@FunctionalInterface
public interface PostDetailFunction<T, K, H , U, S, V, W, Q, I, P, R> {
    R apply(T t, K k, H h, U u, S s, V v, W w, List<Q> q, List<I> i, List<P> p);
}

