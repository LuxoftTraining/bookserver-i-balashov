package ru.ibalashov.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.ibalashov.entity.Book;
import ru.ibalashov.measure.Measure;
import ru.ibalashov.service.BookService;

import java.util.Arrays;
import java.util.List;

@RequestMapping(
        value = "/books"
)
@RestController
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping(path = "/count")
    public Long count() {
        return bookService.count();
    }

    @Measure(value = "baseline", warmup = 500)
    @GetMapping(path = "/search/like")
    public List<Book> searchLike(@RequestParam(name = "by") String searchString) {
        List<String> words = Arrays.asList(searchString.split(" "));
        return bookService.findBooksByNameLike(words);
    }

    @Measure(value = "join", warmup = 500)
    @GetMapping(path = "/search/join")
    public List<Book> searchJoin(@RequestParam(name = "by") String searchString) {
        List<String> words = Arrays.asList(searchString.split(" "));
        return bookService.findBooksByNameJoin(words);
    }
}
