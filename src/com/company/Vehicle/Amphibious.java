package com.company.Vehicle;

public class Amphibious extends Car {

    public String rokProdukcji;
    public int okien;
    public boolean czyDisel;

    public Amphibious(int engineCapacity,int powierzchnia, String name, Type type, Color color, String rokProdukcji, int okien, boolean czyDisel) {
        super(engineCapacity,powierzchnia, name, type, color);
        this.rokProdukcji = rokProdukcji;
        this.okien = okien;
        this.czyDisel = czyDisel;
    }

    @Override
    public String toString() {
        return super.toString() +
                "rokProdukcji = " + rokProdukcji + '\'' +
                ", okien =" + okien +
                ", czyDisel =" + czyDisel;
    }
}
