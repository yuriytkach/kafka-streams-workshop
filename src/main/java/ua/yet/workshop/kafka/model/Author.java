package ua.yet.workshop.kafka.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Author {

    private final String firstName;

    private final String lastName;

    private final String email;
}
