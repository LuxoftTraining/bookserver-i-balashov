package ru.ibalashov.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.ibalashov.entity.Book;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.ArrayList;
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

    public List<Book> findBooksByNameJoin(List<String> words) {
        List<Book> books = new ArrayList<>();
        Query query = entityManager.createNativeQuery(createFindByWordQuery(words));
        for (int i = 0; i < words.size(); ++i) {
            query.setParameter(i, words.get(i));
        }
        query.setParameter(words.size(), words.size());
        List resultList = query.getResultList();
        for (Object obj : resultList) {
            if (obj instanceof Object[]) {
                Object[] objArray = (Object[]) obj;
                books.add(new Book(((BigInteger)objArray[0]).longValue(), (String) objArray[1]));
            }
        }
        return books;
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

    private String createFindByWordQuery(List<String> words) {
        StringBuilder sb = new StringBuilder("select b.* from book b\n")
                .append("join (\n")
                .append("    select count(1), bw.book_id\n")
                .append("    from public.book_word bw\n")
                .append("             join word w on bw.word_id = w.id\n")
                .append("    where ");
        for (int i = 0; i < words.size(); ++i) {
            if (i != 0) {
                sb.append(" OR ");
            }
            sb.append("w.word = ?").append(i);
        }
        sb.append("    group by bw.book_id\n")
                .append("    having count(1) = ?").append(words.size()).append("\n")
                .append(") sub on sub.book_id = b.id");
        return sb.toString();
    }
}
