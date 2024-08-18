package edu.school21.smartcalc.test;

import edu.school21.smartcalc.model.CreditModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

public class CreditModelTest {

    private CreditModel creditModel;
    private double delta;

    @BeforeEach
    public void initialize() {
        creditModel = new CreditModel();
        delta = 0.09;
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/annuityPayments.csv")
    public void testAnnuityLoanPayment(double amount,
                                       int term,
                                       int interestRate,
                                       double expectedPayment,
                                       double expectedTotalInterest,
                                       double expectedTotalPayment) {
        creditModel.setCreditAmount(amount);
        creditModel.setCreditTerm(term);
        creditModel.setCreditRate(interestRate);
        creditModel.setCreditType(CreditModel.CreditType.ANNUITY);
        creditModel.calculate();

        Assertions.assertEquals(expectedPayment,
                creditModel.getMonthlyPayments()
                        .getFirst(), delta);
        Assertions.assertEquals(expectedTotalInterest,
                creditModel.getOverpayment(), delta);
        Assertions.assertEquals(expectedTotalPayment,
                creditModel.getTotalPayment(), delta);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/differentialPayments.csv")
    public void checkCreditDiffPayment(double amount,
                                       int term,
                                       int interestRate,
                                       double expectedFirstPayment,
                                       double expectedLastPayment,
                                       double expectedTotalInterest,
                                       double expectedTotalPayment) {
        creditModel.setCreditAmount(amount);
        creditModel.setCreditTerm(term);
        creditModel.setCreditRate(interestRate);
        creditModel.setCreditType(CreditModel.CreditType.DIFF);
        creditModel.calculate();

        Assertions.assertEquals(expectedFirstPayment,
                creditModel.getMonthlyPayments()
                        .getFirst(), delta);
        Assertions.assertEquals(expectedLastPayment,
                creditModel.getMonthlyPayments()
                        .getLast(), delta);
        Assertions.assertEquals(expectedTotalInterest,
                creditModel.getOverpayment(), delta);
        Assertions.assertEquals(expectedTotalPayment,
                creditModel.getTotalPayment(), delta);
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
}
