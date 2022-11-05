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

    public void delete(Person person) {
        personRepository.delete(person);
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

    public void deleteById(int id) {
        personRepository.deleteById(id);
    }

}