package com.example.homework2_13;

import com.example.homework2_13.exception.*;
import com.example.homework2_13.service.EmployeeService;
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class EmployeeServiceTest {
    private final EmployeeService employeeService = new EmployeeService();

    public static Stream<Arguments> addIncorrectFirstNameTestParams() {
        return Stream.of(
                Arguments.of("Андрей1"),
                Arguments.of("Андрей="),
                Arguments.of("Андрей@")
        );
    }

    public static Stream<Arguments> addIncorrectLastNameTestParams() {
        return Stream.of(
                Arguments.of("Андреев1"),
                Arguments.of("Андреев="),
                Arguments.of("Андреев@")
        );
    }

    @Before
    public void beforeEach() {
        employeeService.add("Петр", "Николаев", 3, 22000);
        employeeService.add("Михаил", "Михайлов", 2, 21000);
        employeeService.add("Иван", "Иванов", 2, 18500);
    }
    @After
    public void afterEach() {
        employeeService.getAll()
                .forEach(employee -> employeeService.remove(employee.getFirstName(),
                        employee.getLastName(), employee.getDepartment(), employee.getWage()));
    }
    @Test
    public void addTest() {
        int beforeCount = employeeService.getAll().size();
        Employee expected = new Employee("Андрей", "Андреев", 2, 32000);
        Assertions.assertThat(employeeService.add("Андрей", "Андреев", 2, 32000))
                .isEqualTo(expected)
                .isIn(employeeService.getAll());
        Assertions.assertThat(employeeService.getAll()).hasSize(beforeCount + 1);
        Assertions.assertThat(employeeService.find("Андрей", "Андреев", 2, 32000))
                .isEqualTo(expected);
    }
    //@ParameterizedTest
    //@MethodSource("addIncorrectFirstNameTestParams")
    //public void addIncorrectFirstNameTest(String incorrectFirstName) {
    //    Assertions.assertThatExceptionOfType(IncorrectNameException.class)
    //            .isThrownBy(()-> employeeService.add(incorrectFirstName, "Андреев", 2, 32000));
    //}
    //@ParameterizedTest
    //@MethodSource("addIncorrectLastNameTestParams")
    //public void addIncorrectLastNameTest(String incorrectLastName) {
    //    Assertions.assertThatExceptionOfType(IncorrectLastNameException.class)
    //            .isThrownBy(()-> employeeService.add("Андрей", incorrectLastName, 2, 32000));
    //}
    @Test
    public void addWhenAlreadyExistsTest() {
        Assertions.assertThatExceptionOfType(EmployeeAlreadyAddedException.class)
                .isThrownBy(()-> employeeService.add("Иван", "Иванов", 2, 18500));
    }
    @Test
    public void addStorageIsFullTest() {
        Stream.iterate(1, i -> i +1)
                        .limit(9)
                        .map(number -> new Employee(
                                "Денис" + ((char)('а' + number)),
                                "Денисов" + ((char)('а' + number)),
                                number,
                                19000 + number))
                                .forEach(employee -> employeeService.add(employee.getFirstName(),
                                        employee.getLastName(),
                                        employee.getDepartment(),
                                        employee.getWage()));
        Assertions.assertThatExceptionOfType(EmployeeStorageIsFullException.class)
                .isThrownBy(()-> employeeService.add("Денис", "Денисов", 2, 19000));
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
                .isThrownBy(() -> employeeService.find("Андрей", "Андреев", 2, 32000));
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
                .isThrownBy(() -> employeeService.find("Андрей", "Андреев", 2, 32000));
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
