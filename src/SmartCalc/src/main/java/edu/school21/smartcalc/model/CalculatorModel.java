package edu.school21.smartcalc.model;

public class CalculatorModel {
    private double result;
    private boolean isValid;
    private final CalculatorJNI calculatorJNI;

    public CalculatorModel() {
        calculatorJNI = new CalculatorJNI();
    }

    public double getResult() throws CalculationException {
        if (!isValid) {
            throw new CalculationException("Invalid input");
        }
        return result;
    }

    public boolean isValid() {
        return isValid;
    }

    public void calculate(String infix, double x) {
        calculatorJNI.calculate(infix, x);
        if (calculatorJNI.getStatus() == CalculatorJNI.Status.OK.ordinal()) {
            result = calculatorJNI.getResult();
            isValid = true;
        } else {
            isValid = false;
        }
    }

    public boolean isValidX(String xText) {
        try {
            Double.parseDouble(xText);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;

    }
}
