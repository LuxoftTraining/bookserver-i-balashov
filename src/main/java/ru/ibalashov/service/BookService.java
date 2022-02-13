package ru.ibalashov.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ibalashov.entity.Book;
import ru.ibalashov.repository.BookRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository repository;

    @Transactional
    public void saveBook(Book book) {
        repository.save(book);
    }

    @Transactional
    public void saveAll(Iterable<Book> books) {
        repository.saveAll(books);
    }

    public long count() {
        return repository.count();
    }

    @Transactional(readOnly = true)
    public List<Book> findBooksByNameLike(List<String> words) {
        return repository.findBooksByNameLike(words);
    }

    @Transactional(readOnly = true)
    public List<Book> findBooksByNameJoin(List<String> words) {
        return repository.findBooksByNameJoin(words);
    }
}
