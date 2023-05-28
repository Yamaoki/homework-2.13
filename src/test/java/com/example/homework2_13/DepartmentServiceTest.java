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

    public static Stream<Arguments> getMaxWageTestParams() {
        return Stream.of(
                Arguments.of(1, new Employee("Михаил", "Викторович", 1, 6000)),
                Arguments.of(2, new Employee("Антонина", "Викторовна", 2, 3000)),
                Arguments.of(3, new Employee("Людмила", "Викторовна", 3, 1000))
        );
    }

    public static Stream<Arguments> getMinWageTestParams() {
        return Stream.of(
                Arguments.of(1, new Employee("Михаил", "Викторович", 1, 6000)),
                Arguments.of(2, new Employee("Дмитрий", "Викторович", 2, 2000)),
                Arguments.of(3, new Employee("Людмила", "Викторовна", 3, 1000))
        );
    }

    public static Stream<Arguments> getAllInDepartTestParams() {
        return Stream.of(
                Arguments.of(1,
                        List.of(
                                new Employee("Михаил", "Викторович", 1, 6000)
                        )
                ),
                Arguments.of(2,
                        List.of(
                                new Employee("Антонина", "Викторовна", 2, 3000),
                                new Employee("Дмитрий", "Викторович", 2, 2000)
                        )
                ),
                Arguments.of(3,
                        List.of(
                                new Employee("Людмила", "Викторовна", 3, 1000)
                        )
                ),
                Arguments.of(4,
                        Collections.emptyList()
                )

        );
    }


    @BeforeEach
    public void beforeEach() {
        List<Employee> employees = List.of(
                new Employee("Михаил",
                        "Викторович",
                        1,
                        6000),
                new Employee("Антонина",
                        "Викторовна",
                        2,
                        3000),
                new Employee("Дмитрий",
                        "Викторович",
                        2,
                        2000),
                new Employee("Людмила",
                        "Викторовна",
                        3,
                        1000)
        );
        Mockito.when(employeeService.list()).thenReturn(employees);
    }

    @ParameterizedTest
    @MethodSource("getMaxWageTestParams")
    public void getMaxWageTest(int departmentId, Employee expected) {
        Assertions.assertThat(departmentService.getMaxWage(departmentId))
                .isEqualTo(expected);
    }

    @Test
    public void getMaxWageTest2() {
        Assertions.assertThatExceptionOfType(EmployeeNotFoundException.class)
                .isThrownBy(() -> departmentService.getMaxWage(4));
    }

    @ParameterizedTest
    @MethodSource("getMinWageTestParams")
    public void getMinWageTest(int department, Employee expected) {
        Assertions.assertThat(DepartmentService.getMinWage(department))
                .isEqualTo(expected);
    }

    @Test
    public void getMinWageTest2() {
        Assertions.assertThatExceptionOfType(EmployeeNotFoundException.class)
                .isThrownBy(() -> DepartmentService.getMinWage(4));
    }

    @ParameterizedTest
    @MethodSource("getAllInDepartTestParams")
    public void getAllInDepartTest(int departmentId, List<Employee> expected) {
        Assertions.assertThat(DepartmentService.getAllInDepart(departmentId))
                .containsExactlyInAnyOrderElementsOf(expected);
    }

    @Test
    public void getAllByDepartTestParams() {
        Map<Integer, List<Employee>> expected = Map.of(
                1,
                List.of(
                        new Employee("Михаил", "Викторович", 1, 6000)
                ),
                2,
                List.of(
                        new Employee("Антонина", "Викторовна", 2, 3000),
                        new Employee("Дмитрий", "Викторович", 2, 2000)
                ),
                3,
                List.of(
                        new Employee("Людмила", "Викторовна", 3, 1000)
                )
        );

        Assertions.assertThat(DepartmentService.getAllByDepart())
                .containsExactlyInAnyOrderElementsOf((Iterable<? extends Employee>) expected);
    }
}
