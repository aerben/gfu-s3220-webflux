package digital.erben.reactiveweb.ratings;

import java.time.LocalDate;
import java.util.List;
import net.datafaker.Faker;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.test.StepVerifier;

@SpringBootTest
public class CustomerRepositoryTest {

    Faker faker = new Faker();

    @Autowired
    private CustomerRepository customerRepository;

    @AfterEach
    public void clearDatabase() {
        this.customerRepository.deleteAll().block();
    }

    @Test
    public void shouldGetByLastName() {
        String firstCustomerLastName = faker.name().lastName();
        Customer firstCustomer = new Customer(faker.name().firstName(), firstCustomerLastName, LocalDate.now());
        Customer secondCustomer = new Customer(faker.name().firstName(), faker.name().lastName(), LocalDate.now());

        customerRepository.saveAll(List.of(firstCustomer, secondCustomer)).blockLast();
        StepVerifier.create(customerRepository.findByLastName(firstCustomerLastName))
            .expectNextMatches(customer -> customer.getLastName().equals(firstCustomerLastName))
            .verifyComplete();
    }

    @Test
    public void shouldOrderByBirthday() {
        Customer firstCustomer = new Customer(faker.name().firstName(), faker.name().lastName(), LocalDate.of(1999, 1, 1));
        Customer secondCustomer = new Customer(faker.name().firstName(), faker.name().lastName(), LocalDate.of(1980, 1 ,1));
        Customer thirdCustomer = new Customer(faker.name().firstName(), faker.name().lastName(), LocalDate.of(1985, 1 ,1));

        customerRepository.saveAll(List.of(firstCustomer, secondCustomer, thirdCustomer)).blockLast();
        StepVerifier.create(customerRepository.findAllCustomersOrderByBirthdayDesc())
            .expectNext(firstCustomer, thirdCustomer, secondCustomer)
            .verifyComplete();
    }

}
