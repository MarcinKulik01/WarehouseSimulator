package com.company.Warehouse;

import com.company.Exception.ToBigCarException;
import com.company.Exception.ToManyThingsException;
import com.company.Person.Person;
import com.company.Vehicle.Car;

import java.time.LocalDate;

public class ParkingSpace extends PlaceForRent {

    private Person person;
    private int space;
    private Car car;
    private int price = 50;
    private boolean Free = true;
    private boolean oplacone = true;

    public void setOplacone(boolean oplacone) {
        this.oplacone = oplacone;
    }

    public boolean isOplacone() {
        return oplacone;
    }

    public boolean isFree() {
        return Free;
    }

     public ParkingSpace(int vRoom, int price){
        super(vRoom,price);
    }

     ParkingSpace(int x,int y, int z,int price) {
        super(x,y,z,price);
    }

    public void startParking(Person person, Car car, LocalDate localDate) throws ToBigCarException {
        if(car.getV()<space) {
            this.person = person;
            this.car = car;
            setStartRent(localDate);
            setEndRent(localDate.plusDays(14));
            this.Free = false;
            person.setPrice(this.price);
        }
        throw new ToBigCarException("Car is too big");
    }

    public void endParking() {
        this.person = null;
        this.car = null;
        setStartRent(null);
        setEndRent(null);
        this.Free = true;
    }

    public Person getPerson() {
        return person;
    }

    public Car getCar() {
        return car;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public void setItem(Item item) throws ToManyThingsException {

    }

    @Override
    public String toString() {
        return super.toString()+
                ", wynajmujacy = " + person +
                ", powierzchnia = " + space +
                ", pojazd =" + car;
    }
}
