package ru.job4j.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PersonDTO {
    @NotBlank(message = "Login must be not empty")
    private String login;

    @Pattern.List({
            @Pattern(regexp = "(?=.*[0-9]).+", message = "Password must contain one digit."),
            @Pattern(regexp = "(?=.*[a-z]).+", message = "Password must contain one lowercase letter."),
            @Pattern(regexp = "(?=.*[A-Z]).+", message = "Password must contain one upper letter."),
            @Pattern(regexp = "(?=.*[!@#$%^&*+=?-_()/\".,<>~`;:]).+", message = "Password must contain one special character."),
            @Pattern(regexp = "(?=\\S+$).+", message = "Password must contain no whitespace.")
    })
    private String password;
}
