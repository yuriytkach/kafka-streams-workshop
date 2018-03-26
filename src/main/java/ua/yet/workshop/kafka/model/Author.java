package ua.yet.workshop.kafka.model;

public class Author {

  private final String firstName;

  private final String lastName;

  private final String email;

  public Author(final String firstName, final String lastName, final String email) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getEmail() {
    return email;
  }
}
