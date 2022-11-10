package ru.job4j.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.domain.Person;
import ru.job4j.dto.PersonDTO;
import ru.job4j.service.PersonService;

import java.util.List;

@RestController
@RequestMapping("/persons")
@AllArgsConstructor
public class PersonController {
    public static final String PASSWORD_MATCH = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–{}:;',?/*~$^+=<>]).{8,20}$";
    private final PersonService persons;
    private BCryptPasswordEncoder encoder;

    @GetMapping()
    public List<Person> findAll() {
        return persons.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> findById(@PathVariable int id) {
        var person = this.persons.findById(id);
        if (person.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There is no user with this id");
        }
        return new ResponseEntity<>(
                person.get(),
                HttpStatus.OK
        );
    }

    @PutMapping()
    public ResponseEntity<Void> update(@RequestBody Person person) {
        var personUpdate = this.persons.update(person);
        if (personUpdate.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There is no user to be updated");
        }
        validatePersonFields(person);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        if (!persons.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There is no user with this id");
        }
        persons.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/sign-up")
    public ResponseEntity<Void> signUp(@RequestBody Person person) {
        validatePersonFields(person);
        person.setPassword(encoder.encode(person.getPassword()));
        persons.save(person);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> patch(@PathVariable int id, @RequestBody PersonDTO personDTO) {
        var person = persons.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "There is no user with this id"));
        person.setPassword(personDTO.getPassword());
        return ResponseEntity.ok().build();
    }

    private void validatePersonFields(Person person) {
        var login = person.getLogin();
        var password = person.getPassword();
        if (login == null || password == null) {
            throw new NullPointerException("Login and password mustn't be empty");
        }
        if (!password.matches(PASSWORD_MATCH)) {
            throw new IllegalArgumentException("""
                    Password must contain at least one digit [0-9].
                    Password must contain at least one lowercase Latin character [a-z].
                    Password must contain at least one uppercase Latin character [A-Z].
                    Password must contain at least one special character like ! @ # & ( ).
                    Password must contain a length of at least 8 characters and a maximum of 20 characters.
                    """);
        }
    }

}