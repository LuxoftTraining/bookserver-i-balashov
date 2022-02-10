package ru.ibalashov.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.ibalashov.entity.Book;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BookRepository {
    private final BaseBookRepository baseBookRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public Book save(Book book) {
        return baseBookRepository.save(book);
    }

    public void saveAll(Iterable<Book> books) {
        baseBookRepository.saveAll(books);
    }

    public long count() {
        return baseBookRepository.count();
    }

    public List<Book> findBooksByNameLike(List<String> words) {
        Query query = entityManager.createQuery(createLikeQuery(words));
        for (int i = 0; i < words.size(); ++i) {
            query.setParameter(i, "%" + words.get(i) + "%");
        }
        return query.getResultList();
    }

    private String createLikeQuery(List<String> words) {
        StringBuilder sb = new StringBuilder("SELECT b FROM Book b");
        if (words.size() > 0) {
            sb.append(" WHERE ");
            for (int i = 0; i < words.size(); ++i) {
                if (i != 0) {
                    sb.append(" AND ");
                }
                sb.append(" b.name LIKE ?").append(i);
            }
        }
        return sb.toString();
    }
}
