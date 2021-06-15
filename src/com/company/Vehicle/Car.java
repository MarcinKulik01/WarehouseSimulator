package com.company.Vehicle;

public abstract class Car {

    public enum Type {offRoad,city,motorcycle,amphibious};
    public enum Color {red,blue,yellow,black};

    private static int index=0;
    private int id;
    private int engineCapacity;
    private String name;
    private int v;
    private boolean parkingAfterRepair = false;
    private boolean repair = false;
    private boolean carOnParking = false;



    private Type type;
    private Color color;

    public Car(int engineCapacity, int v, String name, Type type, Color color) {
        this.engineCapacity = engineCapacity;
        this.v = v;
        this.name = name;
        this.type = type;
        this.color = color;
        this.id = index++;
    }

    public boolean isCarOnParking() {
        return carOnParking;
    }

    public void setCarOnParking(boolean carOnParking) {
        this.carOnParking = carOnParking;
    }

    public Car(int engineCapacity, int x, int y, String name, Type type, Color color) {
        this.engineCapacity = engineCapacity;
        this.v = x*y;
        this.name = name;
        this.type = type;
        this.color = color;
        this.id = index++;
    }


    public boolean isRepair() {
        return repair;
    }

    public void setRepair(boolean repair) {
        this.repair = repair;
    }

    public int getV() {
        return v;
    }

    public int getId() {
        return id;
    }

    public boolean isParkingAfterRepair() {
        return parkingAfterRepair;
    }

    public void setParkingAfterRepair(boolean parkingAfterRepair) {
        this.parkingAfterRepair = parkingAfterRepair;
    }

    @Override
    public String toString() {
        return "id = " + id +
                ", engineCapacity=" + engineCapacity +
                ", name='" + name +
                ", powierzchnia = " + v +
                ", type = " + type +
                ", color = " + color +", ";
    }
}
