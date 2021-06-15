package com.company.Person;

import com.company.Exception.NeverRentException;
import com.company.Exception.NeverTireException;
import com.company.Vehicle.Car;
import com.company.Warehouse.Item;

import java.time.LocalDate;
import java.util.LinkedList;

public class Person {

    private static volatile int staticId = 0;
    private int id;
    private int price = 0;

    private String name;
    private String surname;
    private String pesel;
    private String address;
    private String birthDate;

    private LocalDate firstRent = null;

    private LinkedList<Car> cars = new LinkedList<>();
    private LinkedList<TenantAlert> tenantAlerts = new LinkedList<>();
    private LinkedList<Item> oponaID = new LinkedList<>();

    public Person(String name, String surname, String pesel, String address, String birthDate) {
        this.name = name;
        this.surname = surname;
        this.pesel = pesel;
        this.address = address;
        this.birthDate = birthDate;
        id=staticId++;

    }

    public boolean ifCar(){
        int pom =0;
        for(Car car: cars){
            if(car.isCarOnParking()==false)
                pom+=1;
        }
        if(pom==0)
            return true;
        if (cars.isEmpty())
            return true;
        return false;
    }

    public Car getCarByID(int id){
        for(Car car: cars){
            if(car.getId()==id)
                return car;
        }
        return null;
    }

    public void setTenantAlerts(TenantAlert tenantAlert) {
        tenantAlerts.add(tenantAlert);
    }

    public LinkedList<TenantAlert> getTenantAlerts() {
        return tenantAlerts;
    }

    public void setCars(Car car) {
        this.cars.add(car);
    }

    public void setPrice(int price) {
        this.price= this.price + price;
    }

    public void minusPrice(int price){
        this.price-=price;
    }

    public int getPrice() {
        return price;
    }

    public LocalDate getFirstRent() throws NeverRentException {
        if(firstRent!=null){
            return firstRent;
        }
        else
            throw new NeverRentException("Nigdy nic nie wynajales lub pierwszy raz wynajmujesz");
    }

    public void setFirstRent(LocalDate firstRent) {
        this.firstRent = firstRent;
    }

    public LinkedList<Car> getCars() {
        return this.cars;
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public void showCars() {
        cars.forEach((car) -> {
            if(car.isRepair()==false&&car.isCarOnParking()==false)
                System.out.println(car);
        });
    }

    public String carsTOString(){
        String cars="";
        for(Car car: this.cars){
            cars+=car.toString()+" \n";
        }
        return cars;
    }

    public Car checkCars(int index){

        for(Car car: cars){
            if (car.getId()==index)
                return car;
        }
        return null;
    }


    public String getTenantMessege() {
        String ans="";
        for(TenantAlert tenantAlerts : tenantAlerts){
            ans+=tenantAlerts.getPlaceForRent()+" - "+tenantAlerts.getLiabilities()+"\n";
        }
        return ans;
    }

    public void setItem(Item id) {
        oponaID.add(id);
    }

    public void showMyTire() throws NeverTireException {
        if(!oponaID.isEmpty()) {
            for (Item item : oponaID) {
                System.out.println(item);
            }
        }
        else
            throw new NeverTireException("Brak opon");
    }

    public boolean ifHasId(int id) {
        for (Item item : oponaID) {
            if(item.getId()==id) {
                oponaID.remove(item);
                return true;
            }
        }
        return false;
    }

    public TenantAlert checkTenant(int id) {
        for (TenantAlert tenantAlert: tenantAlerts){
            if(tenantAlert.getId()==id)
                return tenantAlert;
        }
        return null;
    }

    public boolean getTochangeFirstRent() {
        if(firstRent==null){
            return true;
        }
        return false;
    }

    public void showCarsToService() {
        cars.forEach((car) -> {
            if(car.isRepair()==false)
                System.out.println(car);
        });
    }

    public boolean checkCarsToService() {
        for (Car car: cars){
            if(car.isRepair()==false){
                return true;
            }
        }
        return false;
    }

    public void deleteTenant(TenantAlert checkTenant) {
        tenantAlerts.remove(checkTenant);
    }

    @Override
    public String toString() {

        return "id = " + id +
                ", imie = " + name +
                ", nazwisko = " + surname +
                ", pesel = " + pesel +
                ", adres = " + address +
                ", data urodzenia = "+ birthDate +
                ", oplaty = " + price +
                ", dataPierwszegoWynajmu = " + firstRent +
                "\nsamochody : \n" + carsTOString()+
                "ilosc niezaplaconcyh magazynow = " + tenantAlerts.size()+"\n";
    }


}
