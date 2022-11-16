package ru.job4j.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.domain.Person;
import ru.job4j.dto.PersonDTO;
import ru.job4j.dto.PersonPasswordDTO;
import ru.job4j.exception.PersonNotFoundException;
import ru.job4j.service.PersonService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/persons")
@AllArgsConstructor
public class PersonController {
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
            throw new PersonNotFoundException("There is no user with this id");
        }
        return new ResponseEntity<>(
                person.get(),
                HttpStatus.OK
        );
    }

    @PutMapping()
    public ResponseEntity<Void> update(@RequestBody PersonDTO personDTO) {
        var person = new Person();
        person.setLogin(personDTO.getLogin());
        person.setPassword(personDTO.getPassword());
        var personUpdate = this.persons.update(person);
        if (personUpdate.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There is no user to be updated");
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        if (!persons.existsById(id)) {
            throw new PersonNotFoundException("There is no user with this id");
        }
        persons.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/sign-up")
    public ResponseEntity<Void> signUp(@Valid @RequestBody PersonDTO personDTO) {
        var person = new Person();
        person.setLogin(personDTO.getLogin());
        person.setPassword(encoder.encode(personDTO.getPassword()));
        persons.save(person);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> patch(@PathVariable int id,
                                     @Valid @RequestBody PersonPasswordDTO personPasswordDTO) {
        var person = persons.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "There is no user with this id"));
        person.setPassword(personPasswordDTO.getPassword());
        return ResponseEntity.ok().build();
    }
}