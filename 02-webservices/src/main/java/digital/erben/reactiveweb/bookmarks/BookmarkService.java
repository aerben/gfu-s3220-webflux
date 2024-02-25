package digital.erben.reactiveweb.bookmarks;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class BookmarkService {
	private final Map<Long, Bookmark> bookmarks = new ConcurrentHashMap<>();
	private final AtomicLong idGenerator = new AtomicLong(0);

	public Mono<Bookmark> save(Bookmark bookmark) {
		long id = idGenerator.incrementAndGet();
		bookmarks.put(id, bookmark);
		return Mono.just(bookmark);
	}

	public Mono<Bookmark> findById(long id) {
		return Mono.justOrEmpty(bookmarks.get(id));
	}

	public Flux<Bookmark> findAll() {
		return Flux.fromIterable(bookmarks.values());
	}

	public Mono<Void> deleteById(long id) {
		bookmarks.remove(id);
		return Mono.empty();
	}
}
