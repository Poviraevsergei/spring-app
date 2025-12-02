package by.tms.relation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/page", produces = MediaType.APPLICATION_JSON_VALUE)
public class PageController {

    private final PageRepository pageRepository;

    @Autowired
    public PageController(PageRepository pageRepository) {
        this.pageRepository = pageRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Page> getPageById(@PathVariable int id) {
        Page page = pageRepository.findById(id);
        return new ResponseEntity<>(page,HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Page>> getAllPages() {
        return new ResponseEntity<>(pageRepository.findAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<HttpStatus> createPage(@RequestBody Page page) {
        pageRepository.save(page);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<HttpStatus> updateComponentById(@RequestBody Page page) {
        pageRepository.update(page);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity<HttpStatus> delete(@RequestBody Page page) {
        pageRepository.delete(page);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
