package ru.ibalashov.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ibalashov.entity.Book;

@Repository
public interface BaseBookRepository extends JpaRepository<Book, Long> {

}
