package edu.school21.smartcalc.test;

import edu.school21.smartcalc.model.CalculationException;
import edu.school21.smartcalc.model.CalculatorModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

public class CalculatorModelTest {

    private CalculatorModel calculatorModel;

    @BeforeEach
    public void initialize() {
        calculatorModel = new CalculatorModel();
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/validInputCalculator.csv")
    public void testValidInputCalculation(String input, double x,
                                          double expectedResult) {
        double calculatedResult = 0;
        double delta = 0.00001;
        try {
            calculatorModel.calculate(input, x);
            String strX = String.valueOf(x);
            if (calculatorModel.isValid() && calculatorModel.isValidX(strX)) {
                calculatedResult = calculatorModel.getResult();
            }
        } catch (CalculationException e) {
            Assertions.fail();
        }
        Assertions.assertEquals(expectedResult, calculatedResult, delta);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/invalidInputCalculator.csv")
    public void testInvalidInputCalculation(String input, double x) {
        try {
            calculatorModel.calculate(input, x);
            calculatorModel.getResult();
            System.out.println("FAILURE: " + input);
            Assertions.fail();
        } catch (CalculationException e) {
            Assertions.assertTrue(true);
        }
    }
}