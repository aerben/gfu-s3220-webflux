package digital.erben;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/book")
public class BookController {

    private final BookRepository repository;

    public BookController(BookRepository repository) {
        this.repository = repository;
    }

    @Operation(summary = "Get a book by its id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Found the book",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Book.class))}),
        @ApiResponse(responseCode = "400", description = "Invalid id supplied", content = @Content),
        @ApiResponse(responseCode = "404", description = "Book not found", content = @Content)}) // @formatter:on
    @GetMapping("/{id}")
    public Book findById(@Parameter(description = "id of book to be searched") @PathVariable long id) {
        return repository.findById(id)
            .orElseThrow(BookNotFoundException::new);
    }

    @GetMapping("/")
    public Iterable<Book> findBooks() {
        return repository.findAll();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Book updateBook(@PathVariable("id") final Long id, @RequestBody final Book book) {
        return repository.save(book);
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public Book postBook(@NotNull @Valid @RequestBody final Book book) {
        return repository.save(book);
    }

    @RequestMapping(method = RequestMethod.HEAD, value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void headBook(@PathVariable("id") final Long id) {
        boolean found = repository.existsById(id);
        if (!found) throw new BookNotFoundException();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteBook(@PathVariable final long id) {
        this.repository.deleteById(id);
    }
}
