package com.company.Service;

import com.company.Person.Person;
import com.company.Vehicle.Car;

import java.time.LocalDate;


public class IndependentRepairPlace extends Repair {

    private int price = 10;

    IndependentRepairPlace(){
        super.setId();
    }

    public void startIndependentRepairPlace(Person osobaWynajmujaca, Car samochodNaprawiany, LocalDate localDate, int i) {
        super.startRepair(osobaWynajmujaca,samochodNaprawiany,localDate,i);
        osobaWynajmujaca.setPrice(this.price);
    }

    IndependentRepairPlace(Person osobaWynajmujaca, Car samochodNaprawiany,LocalDate localDate,int i,int id){
        super.startRepair(osobaWynajmujaca,samochodNaprawiany,localDate,i);
        super.setId(id);
        setPrice(this.price*i);
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return super.toString()+
                "price = " + price;
    }
}
