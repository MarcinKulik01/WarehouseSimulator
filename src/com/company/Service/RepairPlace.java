package com.company.Service;


import com.company.Person.Person;
import com.company.Vehicle.Car;

import java.time.LocalDate;

public class RepairPlace extends Repair {

    private int priceForMechanik = 100;

    RepairPlace(){
        super.setId();
    }

    public void startRepairPlace(Person osobaWynajmujaca, Car samochodNaprawiany, LocalDate localDate, int i) {
        super.startRepair(osobaWynajmujaca,samochodNaprawiany,localDate,i);
        osobaWynajmujaca.setPrice(priceForMechanik);
    }

    RepairPlace(Person osobaWynajmujaca, Car samochodNaprawiany,LocalDate localDate,int i,int id){
        super.startRepair(osobaWynajmujaca,samochodNaprawiany,localDate,i);
        super.setId(id);
        setPriceForMechanik(this.priceForMechanik*i);
    }


    public int getPriceForMechanik() {
        return priceForMechanik;
    }


    public void setPriceForMechanik(int priceForMechanik) {
        this.priceForMechanik = priceForMechanik;
    }

    @Override
    public String toString() {
        return  super.toString()+
                " priceForMechanik = " + priceForMechanik;
    }
}
