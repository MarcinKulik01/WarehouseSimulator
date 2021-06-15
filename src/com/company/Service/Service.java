package com.company.Service;

import com.company.Exception.ToBigCarException;
import com.company.Person.Person;
import com.company.Person.TenantAlert;
import com.company.Vehicle.Car;
import com.company.Warehouse.ParkingSpace;
import com.company.Warehouse.Warehouse;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;

public class Service {

    Random random = new Random();

    private static int index = 0;
    private String name;
    private int id;

    private LinkedList<Warehouse> warehouses = new LinkedList<>();
    public LinkedList<RepairPlace> repairPlaces = new LinkedList<>();
    public Map<Car, Person> waitingListForIndependentRepair = new HashMap<>();
    public Map<Car, Person> waitingMapForMechanikRepair = new HashMap<>();
    private LinkedList<ParkingSpace> parkingSpaces = new LinkedList<>();

    public LinkedList<RepairPlace> mechanikRepairPlacesHistory = new LinkedList<>();
    public LinkedList<IndependentRepairPlace> independentRepairHistory = new LinkedList<>();


    public IndependentRepairPlace independentRepairPlace;


    public Service(String name) {
        this.name = name;
        this.id = index++;
        repairPlaces.add(new RepairPlace());
        repairPlaces.add(new RepairPlace());
        repairPlaces.add(new RepairPlace());
        repairPlaces.add(new RepairPlace());
        independentRepairPlace = new IndependentRepairPlace();

        for (int i = 0; i < 20; i++) {
            parkingSpaces.add(new ParkingSpace(20,random.nextInt(10)+10));
        }

    }

    public LinkedList<ParkingSpace> getParkingSpaces() {
        return parkingSpaces;
    }

    public LinkedList<Warehouse> getWarehouses() {
        return warehouses;
    }

    public int getId() {
        return id;
    }

    public void startService(Person logPerson, Car car, LocalDate localDate,int ifParking) {

        if(ifParking==1)
            car.setParkingAfterRepair(true);

        if (independentRepairPlace.isFree()) {
            independentRepairPlace.startIndependentRepairPlace(logPerson, car, localDate, random.nextInt(5) + 1);

        } else
            addToListIndependentRepairPlace(logPerson, car);

        car.setRepair(true);
    }

    public void addToListIndependentRepairPlace(Person logPerson, Car car) {
        waitingListForIndependentRepair.put(car,logPerson);
    }

    public void startMechanikService(Person logPerson, Car car, LocalDate localDate,String string,int ifParking) {

        if (ifParking == 1)
            car.setParkingAfterRepair(true);
            car.setRepair(true);


                int pom = 0;
                for (RepairPlace repairPlace : repairPlaces) {
                    if (repairPlace.isFree()) {
                        if (pom == 0) {
                            repairPlace.startRepairPlace(logPerson, car, localDate, random.nextInt(5) + 1);
                            pom++;
                        }
                    }
                }
                if(pom==0)
                addToListMehanikRepair(logPerson, car);

    }

    public void addToListMehanikRepair(Person logPerson,Car car) {
        waitingMapForMechanikRepair.put(car,logPerson);
    }

    public void checkServices(LocalDate localDate) {

        for(RepairPlace repairPlace: repairPlaces){

            if(repairPlace.isFree()==false&&repairPlace.getEndDate().isBefore(localDate)){

                repairPlace.getOsobaWynajmujaca().minusPrice(repairPlace.getPriceForMechanik());


                mechanikRepairPlacesHistory.add(new RepairPlace(repairPlace.getOsobaWynajmujaca(),repairPlace.getCar(),
                        repairPlace.getStartDate(),(int)(ChronoUnit.DAYS.between(repairPlace.getStartDate(), repairPlace.getEndDate())),repairPlace.getId()));

                repairPlace.getCar().setRepair(false);

                if(repairPlace.getCar().isParkingAfterRepair()){
                    setCarOnParking(repairPlace.getOsobaWynajmujaca(),repairPlace.getCar(),localDate);
                    repairPlace.getCar().setParkingAfterRepair(false);
                }
                repairPlace.clear();

                if(!waitingMapForMechanikRepair.isEmpty()){

                    Person person = waitingMapForMechanikRepair.entrySet().iterator().next().getValue();
                    Car car =  waitingMapForMechanikRepair.entrySet().iterator().next().getKey();

                    repairPlace.startRepairPlace(person,car,localDate,random.nextInt(5)+1);

                    waitingMapForMechanikRepair.remove(car,person);
                }
            }
        }
        if(independentRepairPlace.isFree()==false&&independentRepairPlace.getEndDate().isBefore(localDate)){

            independentRepairPlace.getOsobaWynajmujaca().minusPrice(independentRepairPlace.getPrice());

            independentRepairHistory.add(new IndependentRepairPlace(independentRepairPlace.getOsobaWynajmujaca(),independentRepairPlace.getCar(),
                    independentRepairPlace.getStartDate(),(int)(ChronoUnit.DAYS.between(independentRepairPlace.getStartDate(),
                    independentRepairPlace.getEndDate())),independentRepairPlace.getId()));

            independentRepairPlace.getCar().setRepair(false);

            if(independentRepairPlace.getCar().isParkingAfterRepair()){
                setCarOnParking(independentRepairPlace.getOsobaWynajmujaca(),independentRepairPlace.getCar(),localDate);
                independentRepairPlace.getCar().setParkingAfterRepair(false);
            }
            independentRepairPlace.clear();
            if(!waitingListForIndependentRepair.isEmpty()){

                Person person = waitingListForIndependentRepair.entrySet().iterator().next().getValue();
                Car car = waitingListForIndependentRepair.entrySet().iterator().next().getKey();

                independentRepairPlace.startIndependentRepairPlace(person,car,localDate,random.nextInt(5)+1);

                waitingListForIndependentRepair.remove(car,person);
            }
        }
    }

    public void getCar(Person person, Car car) {

        parkingSpaces.forEach((parkingSpace -> {
            if(parkingSpace.getCar()==car&&parkingSpace.getPerson()==person) {
                person.minusPrice(parkingSpace.getPrice());
                parkingSpace.endParking();
            }}));
    }

    public void setCarOnParking(Person person, Car car, LocalDate localDate){
        int i=0;
        for(ParkingSpace parkingSpace : parkingSpaces){
            if(i==0&&parkingSpace.isFree()){
                try {
                    parkingSpace.startParking(person, car, localDate);
                    person.setPrice(parkingSpace.getPrice());
                    i++;
                }catch (ToBigCarException toBigCarException){
                    toBigCarException.getMessage();
                }
            }
        }
    }

    public void showCarsOnParking(LinkedList<Car> cars) {
        for(ParkingSpace parkingSpace: parkingSpaces){
            for(Car car: cars){
                if(parkingSpace.getCar()==car)
                    System.out.println(car);
            }
        }
    }

    public String getName() {
        return name;
    }

    public void addWarehouses(Warehouse warehouse) {
        warehouses.add(warehouse);
    }

    public void checkParkingPlaces(LocalDate localDate) {
        for(ParkingSpace parkingSpace : parkingSpaces){
            if(parkingSpace.isFree()==false&&parkingSpace.getEndRent().isBefore(localDate)){
                if(parkingSpace.isOplacone()==true){
                    parkingSpace.getPerson().setTenantAlerts(new TenantAlert("Nieoplacony parking",parkingSpace,parkingSpace.getPrice()));
                    parkingSpace.setOplacone(false);
                    parkingSpace.getPerson().minusPrice(parkingSpace.getPrice());
                }
                if(ChronoUnit.DAYS.between(parkingSpace.getEndRent(), localDate)>14&&parkingSpace.isOplacone()==false){
                    parkingSpace.getCar().setCarOnParking(false);
                    parkingSpace.endParking();
                }
            }
        }
    }

    @Override
    public String toString() {
        return ", name='" + name +
                ", id=" + id +
                "\n, warehouse=" + warehouses.toString() +
                "\n, repairPlaces=" + repairPlaces.toString() +
                "\n, waitingListForIndependentRepair=" + waitingListForIndependentRepair.toString() +
                "\n, waitingMapForMechanikRepair=" + waitingMapForMechanikRepair.toString() +
                "\n, parkingSpaces=" + parkingSpaces.toString() +
                "\n, mechanikRepairPlacesHistory=" + mechanikRepairPlacesHistory.toString() +
                "\n, independentRepairHistory=" + independentRepairHistory.toString() +
                "\n, independentRepairPlace=" + independentRepairPlace.toString() +
                '}';
    }


}
