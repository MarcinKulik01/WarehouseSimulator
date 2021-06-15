package com.company.Vehicle;

public class CityCar extends Car {

    public boolean elektryczny;
    public int iloscDrzwi;
    public int rozmiarOpon;

    public CityCar(int engineCapacity,int powierzchnia, String name, Type type, Color color, boolean elektryczny, int iloscDrzwi, int rozmiarOpon) {
        super(engineCapacity,powierzchnia, name, type, color);
        this.elektryczny = elektryczny;
        this.iloscDrzwi = iloscDrzwi;
        this.rozmiarOpon = rozmiarOpon;
    }

    @Override
    public String toString() {
        return super.toString()+
                "elektryczny=" + elektryczny +
                ", iloscDrzwi=" + iloscDrzwi +
                ", rozmiarOpon=" + rozmiarOpon;
    }
}
