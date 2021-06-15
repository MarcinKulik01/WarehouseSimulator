package com.company.Vehicle;

public class Motorcycle extends Car {

    private int iloscKoni;
    private String producent;
    private boolean czyWyscigowy;

    public Motorcycle(int engineCapacity,int v, String name, Type type, Color color, int iloscKoni, String producent, boolean czyWyscigowy) {
        super(engineCapacity,v, name, type, color);
        this.iloscKoni = iloscKoni;
        this.producent = producent;
        this.czyWyscigowy = czyWyscigowy;
    }

    @Override
    public String toString() {
        return super.toString()+ " iloscKoni = " + iloscKoni +
                ", producent = '" + producent +
                ", czyWyscigowy = " + czyWyscigowy;
    }
}
