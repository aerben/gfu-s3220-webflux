package digital.erben.reactiveweb.ratings;

import org.springframework.data.annotation.Id;

import java.time.LocalDate;
import java.util.Objects;

public class Customer {

    @Id
    private Long id;

    private final String firstName;

    private final String lastName;

    private final LocalDate birthday;

    public Customer(String firstName, String lastName, LocalDate birthday) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(id, customer.id) && Objects.equals(firstName,
            customer.firstName) && Objects.equals(lastName, customer.lastName) && Objects.equals(
            birthday, customer.birthday);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, birthday);
    }

    @Override
    public String toString() {
        return String.format(
            "Customer[id=%d, firstName='%s', lastName='%s']",
            id, firstName, lastName);
    }
}
