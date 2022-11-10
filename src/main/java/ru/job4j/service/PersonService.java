package ru.job4j.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.domain.Person;
import ru.job4j.repository.PersonRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PersonService {
    private final PersonRepository personRepository;

    public Person save(Person person) {
        return personRepository.save(person);
    }

    public Optional<Person> findById(int id) {
        return personRepository.findById(id);
    }

    public List<Person> findAll() {
        return personRepository.findAll();
    }

    public boolean existsById(int id) {
       return personRepository.existsById(id);
    }

    public boolean existsByLogin(String login) {
        return personRepository.existsByLogin(login);
    }

    public void deleteById(int id) {
        personRepository.deleteById(id);
    }

    public Optional<Person> update(Person person) {
        var personById = findById(person.getId());
        if (personById.isPresent()) {
            personById = Optional.of(save(person));
        }
        return personById;
    }

    public Person findByLogin(String username) {
        return personRepository.findByLogin(username);
    }
}