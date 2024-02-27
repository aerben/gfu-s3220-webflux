package digital.erben.reactiveweb.bookmarks;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/bookmarks")
public class BookmarksController {
	private final BookmarkService service;

	public BookmarksController(BookmarkService service) {
		this.service = service;
	}

	@GetMapping("/{id}")
	public Mono<Bookmark> getBookmark(@PathVariable("id") long id) {
		return service.findById(id);
	}

	@GetMapping
	public Flux<Bookmark> getAllBookmarks() {
		return service.findAll();
	}

	@PostMapping
	public Mono<Bookmark> createBookmark(@RequestBody Bookmark bookmark) {
		throw new UnsupportedOperationException("Not implemented");
	}

	@PutMapping("/{id}")
	public Mono<ResponseEntity<Bookmark>> updateBookmark(@PathVariable("id") long id, @RequestBody Bookmark bookmark) {
		throw new UnsupportedOperationException("Not implemented");
	}

	@DeleteMapping("/{id}")
	public Mono<Void> deleteBookmark(@PathVariable("id") long id) {
		throw new UnsupportedOperationException("Not implemented");
	}
}
