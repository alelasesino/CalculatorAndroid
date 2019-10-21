package com.mapasgoogle.javi.calculator;

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

    String operationProduct(String n1, String n2){

        int i1 = getNumberBase(n1);
        int i2 = getNumberBase(n2);

        return getStringBase(i1*i2);
    }

    String operationSum(String n1, String n2){

        int i1 = getNumberBase(n1);
        int i2 = getNumberBase(n2);

        return getStringBase(i1+i2);
    }

    String operationDeduct(String n1, String n2){

        int i1 = getNumberBase(n1);
        int i2 = getNumberBase(n2);

        return getStringBase(i1-i2);
    }

    String operationDivision(String n1, String n2){

        int i1 = getNumberBase(n1);
        int i2 = getNumberBase(n2);

        return getStringBase(i1/i2);
    }

    String operationPorcentage(String n1, String n2){

        int i1 = getNumberBase(n1);
        int i2 = getNumberBase(n2);

        return getStringBase(i1/100*i2);
    }

    private int getNumberBase(String n1){
        return Integer.parseUnsignedInt(n1, base);
    }

    private String getStringBase(int n1){
        return Integer.toString(n1, base).toUpperCase();
    }

}
