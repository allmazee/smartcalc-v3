package edu.school21.smartcalc.presenter;

import edu.school21.smartcalc.model.CalculationException;
import edu.school21.smartcalc.model.CalculatorModel;
import edu.school21.smartcalc.model.HistoryManager;

public class CalculatorPresenter {
    private final CalculatorModel calculatorModel;
    private final HistoryManager historyManager;
    private StringBuilder operations;
    private String resultStr;
    private double x;

    public CalculatorPresenter() {
        calculatorModel = new CalculatorModel();
        historyManager = new HistoryManager();
        operations = new StringBuilder();
        operations.append(historyManager.loadHistory());
        x = 0;
    }

    public String getResultStr() {
        return resultStr;
    }

    public double getX() {
        return x;
    }

    public String setX(String xText) {
        String result;
        if (!xText.isEmpty() && xText.length() <= 256) {
            if (calculatorModel.isValidX(xText)) {
                result = xText;
                x = Double.parseDouble(xText);
            } else {
                result = "Invalid x";
            }
        } else {
            result = "Invalid x";
        }
        return result;
    }

    public String getOperations() {
        return operations.toString();
    }

    public void calculate(String text) {
        resultStr = "";
        if (!text.isEmpty() && text.length() <= 256) {
            double result = 0;
            try {
                calculatorModel.calculate(text, x);
                result = calculatorModel.getResult();
            } catch (CalculationException e) {
                resultStr = "Invalid input";
                return;
            }
            resultStr = String.format("%.7f", result);
            String history = text + " = " + result + "\n";
            addOperation(history);
        }
        if (text.isEmpty()) {
            resultStr = "Empty input";
        }
        if (text.length() > 256) {
            resultStr = "Too large input";
        }
    }

    public void addOperation(String text) {
        operations.append(text);
        historyManager.saveHistory(operations.toString());
    }

    public void clearHistory() {
        operations = new StringBuilder();
        historyManager.saveHistory(operations.toString());
    }
}
