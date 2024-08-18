package edu.school21.smartcalc.model;

public class CalculatorJNI {

    static {
        System.loadLibrary("calculator");
    }

    public native void calculate(String infix, double x);
    public native double getResult();
    public native int getStatus();

    public enum Status {
        OK,
        ERROR
    }
}
