package by.tms.relation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/author", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthorController {

    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorController(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Author> getAuthorById(@PathVariable int id) {
        Author author = authorRepository.findById(id);
        return new ResponseEntity<>(author,HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Author>> getAllAuthors() {
        return new ResponseEntity<>(authorRepository.findAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<HttpStatus> createAuthor(@RequestBody Author author) {
        authorRepository.save(author);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<HttpStatus> updateComponentById(@RequestBody Author author) {
        authorRepository.update(author);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity<HttpStatus> delete(@RequestBody Author author) {
        authorRepository.delete(author);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
