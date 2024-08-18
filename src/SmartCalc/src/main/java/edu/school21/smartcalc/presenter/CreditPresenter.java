package edu.school21.smartcalc.presenter;

import edu.school21.smartcalc.model.CreditModel;
import edu.school21.smartcalc.model.CreditModel.CreditType;

import java.util.List;

public class CreditPresenter {

    private final CreditModel creditModel;
    private double creditAmount;
    private int percent;
    private int term;
    private String type;
    private boolean isValid;

    public CreditPresenter() {
        creditModel = new CreditModel();
    }

    public String getMonthlyPayments() {
        String result = "";
        if (isValid) {
            List<Double> payments = creditModel.getMonthlyPayments();
            if (type.equals("Annuity")) {
                result = String.format("%.2f", payments.getFirst());
            } else if (type.equals("Differentiated")) {
                result = String.format("%.2f", payments.getFirst()) + "₽ ... " +
                        String.format("%.2f", payments.getLast()) + "₽";
            }
        }
        return result;
    }

    public String getOverpayment() {
        return isValid ? String.format("%.2f", creditModel.getOverpayment()) : "";
    }

    public String getTotalPayment() {
        return isValid ? String.format("%.2f", creditModel.getTotalPayment()) : "";
    }

    public void calculate() {
        if (isValid) {
            creditModel.setCreditAmount(creditAmount);
            creditModel.setCreditRate(percent);
            creditModel.setCreditTerm(term);

            if (type.equals("Annuity")) {
                creditModel.setCreditType(CreditType.ANNUITY);
            } else if (type.equals("Differentiated")) {
                creditModel.setCreditType(CreditType.DIFF);
            }

            creditModel.calculate();
        }
    }

    public String validate(String amount, String term,
                           String loanRate, String type) {
        String result = "";
        if (isValidLoanRate(loanRate)
                && isValidAmount(amount)
                && isValidTerm(term)) {
            isValid = true;
            this.creditAmount = Double.parseDouble(amount);
            this.percent = Integer.parseInt(loanRate);
            this.term = Integer.parseInt(term);
            this.type = type;
        } else {
            isValid = false;
            result = "Invalid input";
        }
        return result;
    }

    private boolean isValidTerm(String text) {
        if (!text.isEmpty()
                && text.length() <= 4
                && isValidInt(text)) {
            int term = Integer.parseInt(text);
            return term > 0 && term <= 1200;
        }
        return false;
    }

    private boolean isValidLoanRate(String text) {
        if (!text.isEmpty()
                && text.length() <= 100
                && isValidDouble(text)) {
            double percent = Double.parseDouble(text);
            return percent >= 0 && percent <= 100;
        }
        return false;
    }

    private boolean isValidAmount(String text) {
        if (!text.isEmpty()
                && text.length() <= 15
                && isValidDouble(text)) {
            double amount = Double.parseDouble(text);
            return amount > 0 && amount <= 100000000;
        }
        return false;
    }

    private boolean isValidInt(String text) {
        return text.matches("-?\\d+");
    }

    private boolean isValidDouble(String text) {
        return text.matches("-?\\d+(\\.\\d+)?");
    }
}
