package ru.ibalashov.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.ibalashov.entity.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class GenerateService {
    private final BookService bookService;
    private Random random = new Random();
    private int batchSize = 1000;

    public void generateData(long size) {
        List<Book> books = new ArrayList<>();
        for (int i = 1; i <= size; ++i) {
            Book book = new Book();
            book.setName(generateName());
            books.add(book);
            if (i % batchSize == 0) {
                bookService.saveAll(books);
                books.clear();
            }
        }
        if (books.size() > 0) {
            bookService.saveAll(books);
        }
    }

    private String generateName() {
        return "Book" + Math.abs(random.nextInt()) +
                " by AuthorName" + Math.abs(random.nextInt()) +
                " AuthorSurname" + Math.abs(random.nextInt());
    }
}
