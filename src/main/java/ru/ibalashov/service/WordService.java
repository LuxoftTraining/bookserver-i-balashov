package ru.ibalashov.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ibalashov.entity.Word;
import ru.ibalashov.repository.WordRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WordService {
    private final WordRepository wordRepository;

    public Word merge(String word) {
        Optional<Word> wordOptional = wordRepository.findByWord(word);
        if (wordOptional.isPresent()) {
            return wordOptional.get();
        } else {
            Word entity = new Word();
            entity.setWord(word);
            return wordRepository.save(entity);
        }
    }
}
