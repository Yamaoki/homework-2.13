package com.example.homework2_13;

import com.example.homework2_13.exception.*;
import com.example.homework2_13.service.EmployeeService;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class EmployeeServiceTest {
    private final EmployeeService employeeService = new EmployeeService();

    public static Stream<Arguments> addIncorrectFirstNameTestParams() {
        return Stream.of(
                Arguments.of("Иван1"),
                Arguments.of("Иван="),
                Arguments.of("Иван@")
        );
    }

    public static Stream<Arguments> addIncorrectLastNameTestParams() {
        return Stream.of(
                Arguments.of("Иванов1"),
                Arguments.of("Иванов="),
                Arguments.of("Иванов@")
        );
    }

    @BeforeEach
    public void beforeEach() {
        employeeService.add("Петр", "Николаев", 3, 22000);
        employeeService.add("Михаил", "Михайлов", 2, 21000);
        employeeService.add("Иван", "Иванов", 2, 18500);
    }
    @AfterEach
    public void afterEach() {
        employeeService.getAll()
                .forEach(employee -> employeeService.remove(employee.getFirstName(),
                        employee.getLastName(), employee.getDepartment(), employee.getWage()));
    }
    @Test
    public void addTest() {
        int beforeCount = employeeService.getAll().size();
        Employee expected = new Employee("Иван", "Иванов", 2, 18500);
        Assertions.assertThat(employeeService.add("Иван", "Иванов", 2, 18500))
                .isEqualTo(expected)
                .isIn(employeeService.getAll());
        Assertions.assertThat(employeeService.getAll()).hasSize(beforeCount + 1);
        Assertions.assertThat(employeeService.find("Иван", "Иванов", 2, 18500))
                .isEqualTo(expected);
    }
    @ParameterizedTest
    @MethodSource("addIncorrectFirstNameTestParams")
    public void addIncorrectFirstNameTest(String incorrectFirstName) {
        Assertions.assertThatExceptionOfType(IncorrectNameException.class)
                .isThrownBy(()-> employeeService.add(incorrectFirstName, "Иванов", 2, 18500));
    }
    @ParameterizedTest
    @MethodSource("addIncorrectLastNameTestParams")
    public void addIncorrectLastNameTest(String incorrectLastName) {
        Assertions.assertThatExceptionOfType(IncorrectLastNameException.class)
                .isThrownBy(()-> employeeService.add("Иван", incorrectLastName, 2, 18500));
    }
    @Test
    public void addWhenAlreadyExistsTest() {
        Assertions.assertThatExceptionOfType(EmployeeAlreadyAddedException.class)
                .isThrownBy(()-> employeeService.add("Иван", "Иванов", 2, 18500));
    }
    @Test
    public void addStorageIsFullTest() {
        Stream.iterate(1, i -> i +1)
                        .limit(7)
                        .map(number -> new Employee(
                                "Иван" + ((char)('а' + number)),
                                "Иванов" + ((char)('а' + number)),
                                number,
                                18500 + number))
                                .forEach(employee -> employeeService.add(employee.getFirstName(),
                                        employee.getLastName(),
                                        employee.getDepartment(),
                                        employee.getWage()));
        Assertions.assertThatExceptionOfType(EmployeeStorageIsFullException.class)
                .isThrownBy(()-> employeeService.add("Иван", "Иванов", 2, 18500));
    }
    @Test
    public void removeTest() {
        int beforeCount = employeeService.getAll().size();
        Employee expected = new Employee("Иван", "Иванов", 2, 18500);
        Assertions.assertThat(employeeService.remove("Иван", "Иванов", 2, 18500))
                .isEqualTo(expected)
                .isNotIn(employeeService.getAll());
        Assertions.assertThat(employeeService.getAll()).hasSize(beforeCount - 1);
        Assertions.assertThatExceptionOfType(EmployeeNotFoundException.class)
                .isThrownBy(() -> employeeService.find("Иван", "Иванов", 2, 18500));
    }
    @Test
    public void removeWhenNotFoundTest() {
        Assertions.assertThatExceptionOfType(EmployeeNotFoundException.class)
                .isThrownBy(() -> employeeService.find("Иван", "Иванов", 2, 18500));
    }
    @Test
    public void findTest() {
        int beforeCount = employeeService.getAll().size();
        Employee expected = new Employee("Иван", "Иванов", 2, 18500);
        Assertions.assertThat(employeeService.find("Иван", "Иванов", 2, 18500))
                .isEqualTo(expected)
                .isIn(employeeService.getAll());
        Assertions.assertThat(employeeService.getAll()).hasSize(beforeCount);
    }
    @Test
    public void findWhenNotFoundTest() {
        Assertions.assertThatExceptionOfType(EmployeeNotFoundException.class)
                .isThrownBy(() -> employeeService.find("Иван", "Иванов", 2, 18500));
    }
    @Test
    public void getAllTest() {
        Assertions.assertThat(employeeService.getAll())
                .hasSize(3)
                .containsExactlyInAnyOrder(
                        new Employee("Петр", "Николаев", 3, 22000),
                        new Employee("Михаил", "Михайлов", 2, 21000),
                        new Employee("Иван", "Иванов", 2, 18500)
                );
    }
}
