package com.mapasgoogle.javi.calculator;

import java.text.DecimalFormat;

/**
 * Created by javi on 22/11/17.
 */
public class Operation {

    private String numberOne;
    private String numberTwo;
    private String operation1;
    private int base;

    public Operation(String numberOne, String numberTwo, String operation) {
        this.numberOne = numberOne;
        this.numberTwo = numberTwo;
        this.operation1 = operation;
    }

    public Operation() {
    }

    public void setBase(int base){
        this.base = base;
    }

    public int getBase(){
        return base;
    }

    public String getOperation1() {
        return operation1;
    }

    public void setOperation1(String operation1) {
        this.operation1 = operation1;
    }

    public String getNumberOne() {
        return numberOne;
    }

    public void setNumberOne(String numberOne) {
        this.numberOne = numberOne;
    }

    public String getNumberTwo() {
        return numberTwo;
    }

    public void setNumberTwo(String numberTwo) {
        this.numberTwo = numberTwo;
    }

    double operationProduct(double n1, double n2){
        return n1*n2;
    }

    String operationSum(String n1, String n2){

        n1 = String.format("#", n1);
        n2 = String.format("#", n2);

        int i1 = Integer.parseUnsignedInt(n1, base);
        int i2 = Integer.parseUnsignedInt(n2, base);

        /*int i1 = Integer.parseUnsignedInt(String.valueOf((int)Double.parseDouble(n1)), base);
        int i2 = Integer.parseUnsignedInt(String.valueOf((int)Double.parseDouble(n2)), base);*/

        return Integer.toString(i1+i2, base).toUpperCase();
    }

    double operationDeduct(double n1, double n2){
        return n1-n2;
    }

    double operationDivision(double n1, double n2){
        return n1/n2;
    }

    double operationPorcentage(double n1, double n2){
        return (n1/100*n2);
    }

}
