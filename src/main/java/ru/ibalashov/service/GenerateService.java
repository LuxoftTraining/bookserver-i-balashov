package ru.ibalashov.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.ibalashov.entity.Book;
import ru.ibalashov.entity.Word;

import java.util.*;

@Component
@RequiredArgsConstructor
public class GenerateService {
    private final BookService bookService;
    private final WordService wordService;
    private Random random = new Random();
    private int batchSize = 1000;

    public void generateData(long size) {
        List<Book> books = new ArrayList<>();
        for (int i = 1; i <= size; ++i) {
            Book book = new Book();
            book.setName(generateName());
            book.setWords(getBookWords(book));
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

    private Set<Word> getBookWords(Book book) {
        Set<Word> set = new HashSet<>();
        String[] split = book.getName().split(" ");
        for (String s : split) {
            if (s.length() > 3) {
                Word word = wordService.merge(s);
                set.add(word);
            }
        }
        return set;
    }
}
