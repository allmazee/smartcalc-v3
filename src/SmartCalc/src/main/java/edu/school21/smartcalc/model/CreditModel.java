package edu.school21.smartcalc.model;

import java.util.Arrays;
import java.util.List;

public class CreditModel {

    // Перечисление типов кредита
    public enum CreditType {
        DIFF,
        ANNUITY
    }

    // Загрузка библиотеки JNI
    static {
        System.loadLibrary("credit");
    }

    // Нативные методы для расчета кредита и установки параметров
    private native void nativeSetCreditType(int creditType);
    private native void nativeSetCreditAmount(double creditAmount);
    private native void nativeSetCreditTerm(int creditTerm);
    private native void nativeSetCreditRate(int creditRate);
    private native void nativeCalculate();

    // Нативные методы для получения результатов расчета
    private native double nativeGetOverpayment();
    private native double nativeGetTotalPayment();
    private native Double[] nativeGetMonthlyPayments();

    // Java-методы для установки параметров кредита
    public void setCreditType(CreditType creditType) {
        nativeSetCreditType(creditType.ordinal());
    }

    public void setCreditAmount(double creditAmount) {
        nativeSetCreditAmount(creditAmount);
    }

    public void setCreditTerm(int creditTerm) {
        nativeSetCreditTerm(creditTerm);
    }

    public void setCreditRate(int creditRate) {
        nativeSetCreditRate(creditRate);
    }

    // Java-метод для выполнения расчета
    public void calculate() {
        nativeCalculate();
    }

    // Java-методы для получения результатов расчета
    public List<Double> getMonthlyPayments() {
        return Arrays.asList(nativeGetMonthlyPayments());
    }

    public double getOverpayment() {
        return nativeGetOverpayment();
    }

    public double getTotalPayment() {
        return nativeGetTotalPayment();
    }
}
