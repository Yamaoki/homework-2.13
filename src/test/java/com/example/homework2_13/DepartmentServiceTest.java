package com.example.homework2_13;

import com.example.homework2_13.exception.EmployeeNotFoundException;
import com.example.homework2_13.service.DepartmentService;
import com.example.homework2_13.service.EmployeeService;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@ExtendWith(MockitoExtension.class)
public class DepartmentServiceTest {
    @Mock
    private EmployeeService employeeService;
    @InjectMocks
    private DepartmentService departmentService;
    private List<Employee> employees;

    public static Stream<Arguments> employeeWithMaxWageTestParams() {
        return Stream.of(
                Arguments.of(1, new Employee("Михаил", "Викторович", 1, 6000)),
                Arguments.of(2, new Employee("Антонина", "Викторовна", 2, 3000))
        );
    }

    public static Stream<Arguments> employeeWithMinWageTestParams() {
        return Stream.of(
                Arguments.of(1, new Employee("Михаил", "Викторович", 1, 6000)),
                Arguments.of(2, new Employee("Дмитрий", "Викторович", 2, 2000))
        );
    }

    public static Stream<Arguments> employeeAllInDepartmentTestParams() {
        return Stream.of(
                Arguments.of(
                        1,
                        List.of(
                                new Employee("Михаил", "Викторович", 1, 6000)
                        )
                ),
                Arguments.of(
                        2,
                        List.of(
                                new Employee("Дмитрий", "Викторович", 2, 2000),
                                new Employee("Антонина", "Викторовна", 2, 3000)
                        )
                ),
                Arguments.of(
                        4,
                        Collections.emptyList()
                )
        );
    }

    @BeforeEach
    public void beforeEach() {
        employees = List.of(
                new Employee("Михаил", "Викторович", 1, 6000),
                new Employee("Антонина", "Викторовна", 2, 3000),
                new Employee("Дмитрий", "Викторович", 2, 2000)
        );
        Mockito.when(employeeService.getAll()).thenReturn(employees);
    }
    @ParameterizedTest
    @MethodSource("employeeWithMaxWageTestParams")
    public void employeeWithMaxWageTest(int departmentId, Employee expected) {
        Assertions.assertThat(departmentService.getMaxWage(departmentId))
                .isEqualTo(expected);
    }
    @Test
    public void employeeWithMaxWageNotFoundTest() {
        Assertions.assertThatExceptionOfType(EmployeeNotFoundException.class)
                .isThrownBy(() -> departmentService.getMaxWage(4));
    }
    @ParameterizedTest
    @MethodSource("employeeWithMinWageTestParams")
    public void employeeWithMinWageTest(int departmentId, Employee expected) {
        Assertions.assertThat(departmentService.getMinWage(departmentId))
                .isEqualTo(expected);
    }
    @Test
    public void employeeWithMinWageNotFoundTest() {
        Assertions.assertThatExceptionOfType(EmployeeNotFoundException.class)
                .isThrownBy(() -> departmentService.getMinWage(4));
    }
    @ParameterizedTest
    @MethodSource("employeeAllInDepartmentTestParams")
    public void employeeAllInDepartTest(int departmentId, List<Employee> expected) {
        Assertions.assertThat(departmentService.getAllInDepart())
                .containsExactlyInAnyOrderElementsOf(expected);
    }
    @Test
    public void employeeAllByDepartTest() {
        Map<Integer, List<Employee>> expected = Map.of(
                        1,
                        List.of(
                                new Employee("Михаил", "Викторович", 1, 6000)
                        ),

                        2,
                        List.of(
                                new Employee("Дмитрий", "Викторович", 2, 2000),
                                new Employee("Антонина", "Викторовна", 2, 3000)
                        )
        );
        Assertions.assertThat(departmentService.getAllByDepart())
                .containsExactlyInAnyOrder((Employee) expected);
    }
}
