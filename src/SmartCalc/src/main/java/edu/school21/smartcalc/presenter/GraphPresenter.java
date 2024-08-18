package edu.school21.smartcalc.presenter;

import edu.school21.smartcalc.model.CalculationException;
import edu.school21.smartcalc.model.CalculatorModel;
import javafx.scene.chart.XYChart;

public class GraphPresenter {

    private final CalculatorModel calculatorModel;

    private int minX;
    private int maxX;
    private int minY;
    private int maxY;
    private double step;

    private static final int DEFAULT_MIN = -10;
    private static final int DEFAULT_MAX = 10;
    private static final int MAX_RANGE = 1_000_000;
    private static final int INPUT_LENGTH_LIMIT = 256;

    public GraphPresenter() {
        calculatorModel = new CalculatorModel();
        minX = DEFAULT_MIN;
        maxX = DEFAULT_MAX;
        minY = DEFAULT_MIN;
        maxY = DEFAULT_MAX;
    }

    public String validateAndPrepare(String function, String minX, String maxX,
                                     String minY, String maxY) {
        String functionValidationMessage = validateFunction(function);
        if (!functionValidationMessage.isEmpty()) {
            return functionValidationMessage;
        }
        return validateAndSetAxes(minX, maxX, minY, maxY);
    }

    public XYChart.Series<Number, Number> calculateGraph(String function) {
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName(function);

        configureStepSize();

        for (double x = minX; x <= maxX; x += step) {
            try {
                calculatorModel.calculate(function, x);
                double y = calculatorModel.getResult();
                if (y >= minY && y <= maxY) {
                    series.getData().add(new XYChart.Data<>(x, y));
                }
            } catch (CalculationException e) {
                System.err.println("Error calculating the function at x = "
                        + x + ": " + e.getMessage());
            }
        }

        return series;
    }

    private String validateFunction(String function) {
        if (function.isEmpty()) {
            return "Empty input";
        }
        if (function.length() > INPUT_LENGTH_LIMIT) {
            return "Input too large";
        }

        String sanitizedFunction = function.replaceAll(" ", "");
        calculatorModel.calculate(sanitizedFunction, 1);
        if (!calculatorModel.isValid()) {
            return "Invalid input";
        }

        return "";
    }

    private String validateAndSetAxes(String xMinText, String xMaxText,
                                      String yMinText, String yMaxText) {
        String validationResult = validateAxes(xMinText,
                xMaxText, yMinText, yMaxText);
        if (!validationResult.isEmpty()) {
            return validationResult;
        }
        setAxes(xMinText, xMaxText, yMinText, yMaxText);
        return "";
    }

    private String validateAxes(String xMinText, String xMaxText,
                                String yMinText, String yMaxText) {
        if (!isValidNumberString(xMinText)
                || !isValidNumberString(xMaxText)
                || !isValidNumberString(yMinText)
                || !isValidNumberString(yMaxText)) {
            return "Invalid coordinates";
        }

        int xMin = Integer.parseInt(xMinText);
        int xMax = Integer.parseInt(xMaxText);
        int yMin = Integer.parseInt(yMinText);
        int yMax = Integer.parseInt(yMaxText);

        if (!isValidCoordinateRange(xMin, xMax)
                || !isValidCoordinateRange(yMin, yMax)) {
            return "Invalid coordinate range";
        }

        return "";
    }

    private void setAxes(String xMinText, String xMaxText,
                         String yMinText, String yMaxText) {
        this.minX = Integer.parseInt(xMinText);
        this.maxX = Integer.parseInt(xMaxText);
        this.minY = Integer.parseInt(yMinText);
        this.maxY = Integer.parseInt(yMaxText);
    }

    private boolean isValidNumberString(String text) {
        return text.matches("-?\\d+");
    }

    private boolean isValidCoordinateRange(int min, int max) {
        return min <= max && min >= -MAX_RANGE && max <= MAX_RANGE;
    }

    private void configureStepSize() {
        int range = maxX - minX;
        if (range < 20) {
            step = 0.01;
        } else if (range < 200) {
            step = 0.1;
        } else if (range < 10_000) {
            step = 1;
        } else if (range < 100_000) {
            step = 2;
        } else if (range < 200_000) {
            step = 50;
        } else if (range < 400_000) {
            step = 100;
        } else if (range < 800_000) {
            step = 150;
        } else {
            step = 200;
        }
    }
}
