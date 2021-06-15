package com.company.Vehicle;

public class OffRoadCar extends Car {

    private boolean naped4x4;
    private boolean oponyZimowe;
    private String krajPochodzenia;

    public OffRoadCar(int engineCapacity,int powierzchnia, String name, Type type, Color color, boolean naped4x4, boolean oponyZimowe, String krajPochodzenia) {
        super(engineCapacity,powierzchnia, name, type, color);
        this.naped4x4 = naped4x4;
        this.oponyZimowe = oponyZimowe;
        this.krajPochodzenia = krajPochodzenia;
    }


    @Override
    public String toString() {
        return super.toString()+
                " naped4x4 = " + naped4x4 +
                ", oponyZimowe = " + oponyZimowe +
                ", krajPochodzenia = '" + krajPochodzenia;
    }
}
