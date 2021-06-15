package com.company.Service;

import com.company.Person.Person;
import com.company.Vehicle.Car;

import java.time.LocalDate;

public abstract class Repair {

    private static volatile int staticId = 0;
    private int id;
    private LocalDate startDate = null;
    private LocalDate endDate = null;
    private Person osobaWynajmujaca = null;
    private Car car = null;
    private boolean isFree = true;

    public void setId(){
        this.id=staticId++;
    }

    public void startRepair(Person osobaWynajmujaca, Car samochodNaprawiany,LocalDate localDate,int i) {
        this.osobaWynajmujaca = osobaWynajmujaca;
        this.car = samochodNaprawiany;
        this.startDate= localDate;
        this.endDate = localDate.plusDays(i);
        isFree=false;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void clear(){
        osobaWynajmujaca=null;
        startDate=null;
        endDate = null;
        car=null;
        isFree=true;
    }

    public Car getCar() {
        return car;
    }

    public boolean isFree() {
        return isFree;
    }

    public Person getOsobaWynajmujaca() {
        return osobaWynajmujaca;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setOsobaWynajmujaca(Person osobaWynajmujaca) {
        this.osobaWynajmujaca = osobaWynajmujaca;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public void setFree(boolean free) {
        isFree = free;
    }

    @Override
    public String toString() {
        if(osobaWynajmujaca==null){
            return "\n zgloszenie wykonal = brak" +
                    "\n samochodNaprawiany = brak" +
                    "\n data rozpoczecia = "  + startDate+
                    "\n data zakonczenia = "  + endDate+"\n ";
        }
        return "\n zgloszenie wykonal = " + osobaWynajmujaca.getName()+" "+osobaWynajmujaca.getSurname()+
                "\n samochodNaprawiany = { " + car.toString()+ " }"+
                "\n data rozpoczecia = "  + startDate+
                 "\n data zakonczenia = "  + endDate+"\n ";
    }

}
