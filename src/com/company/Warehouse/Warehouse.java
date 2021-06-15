package com.company.Warehouse;
import com.company.Exception.ToManyThingsException;
import com.company.Exception.ToManyWarehousesException;
import com.company.Person.Person;
import com.company.Person.TenantAlert;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.LinkedList;
import java.util.Random;

public class Warehouse {
    private static int index = 0;
    private String name;
    private int id;
    private LinkedList<ConsumerWarehouse> consumerWarehouses = new LinkedList<>();
    private LinkedList<ServiceWarehouse> serviceWarehouses = new LinkedList<>();

    public Warehouse(String name, int numberOfWarehouses,int numberOfPercent) {
        this.name = name;
        this.id = index++;

        Random random = new Random();

        for(int i=0; i<(numberOfWarehouses*numberOfPercent/100); i++){
            serviceWarehouses.add(new ServiceWarehouse(random.nextInt(1000)+200,10));
        }
        for(int i=0; i<(numberOfWarehouses-(numberOfWarehouses*numberOfPercent/100)); i++){
            consumerWarehouses.add(new ConsumerWarehouse(random.nextInt(1000)+200,random.nextInt(100)+200));
        }
    }

    public void showFreeWarehouses(){
        for(ConsumerWarehouse i : consumerWarehouses){
            if(i.isFree()==true)
                System.out.println(i);
        }
    }

    public void rent(int chose, Person person, LocalDate localDate) throws ToManyWarehousesException {

        try {
            for (ConsumerWarehouse i : consumerWarehouses) {
                if (i.isFree() == true && i.getId() == chose) {
                    if (person.getPrice()+i.getPrice() < 1250) {
                        i.setOwner(person, localDate);
                        person.setPrice(i.getPrice());//pomyslec nad wyjatkiem
                    } else
                        throw new ToManyWarehousesException("Przekraczasz 1250zl najmu");
                }
            }
        }catch (ToManyWarehousesException toManyWarehousesException){
            toManyWarehousesException.getMessage();
        }
    }

    public void showOwnerWarehouse(Person person){

        int pom = 0;
        for(ConsumerWarehouse i : consumerWarehouses){
            if(i.isFree()==false&&i.getOwner()==person) {
               System.out.println("ID = " + i.getId() + ", cena = " + i.getPrice() + ", V = " + i.getvPomieszczenia() + ", koniec najmu = " + i.getEndRent());
               pom++;
            }
        }
        if(pom==0){
            System.out.println("brak wynajetych magazynow");
        }
    }

    public void addPermision(int c,Person person, Person person1){

        for(ConsumerWarehouse i : consumerWarehouses){
            if(i.isFree()==false&&i.getId()==c&&i.getOwner()==person) {
                i.setPerson(person1);
            }
        }

    }

    public void getPermision(int e,Person person, Person person1){
        boolean czyIstnieje = true;
        for(ConsumerWarehouse i : consumerWarehouses){
            if(i.getId()==e&&i.isFree()==true&&i.getOwner()==person) {
                czyIstnieje = i.getPermision(person1);
                System.out.println("zabrano prawa");
            }
        }
        if(czyIstnieje==false)
            System.out.println(person1+" nie ma praw w tym pomieszczeniu");
    }

    public void addItem(Item item, int id) throws ToManyThingsException {
        for(ConsumerWarehouse i: consumerWarehouses) {
            if(i.getId()==id){
                    i.setItem(item);
            }

        }
    }

    public boolean showMyItems(int id){
        for(ConsumerWarehouse i: consumerWarehouses){
                if(i.getId()==id) {
                    if(i.itemlist.isEmpty()) {
                        System.out.println("brak przedmiotow");
                        return false;
                    }
                    i.showList();
                }
            }
        return true;
    }

    public void showMyWarehousePermision(Person person){
        for(ConsumerWarehouse consumerWarehouse: consumerWarehouses){
            if(consumerWarehouse.isFree()==false&&consumerWarehouse.checkPermision(person))
                        System.out.println("ID = "+consumerWarehouse.getId()+", cenna najmu = "+consumerWarehouse.getPrice()+", wlasciciel = "+
                                consumerWarehouse.getOwner().getName()+" "+consumerWarehouse.getOwner().getSurname());
        }
    }


    public boolean checkWarehousePermision(Person person,int id){
        for(ConsumerWarehouse i: consumerWarehouses){
            if(i.isFree()==false&&i.getId()==id){
                for(Person p : i.getPersonList()){
                    if(p==person) {
                       return true;
                    }
                }
            }
        }
        return false;
    }


    public boolean checkItem(int idWarehouse, int idItem) {
        for(ConsumerWarehouse i: consumerWarehouses){
            if(i.isFree()==false&&i.getId()==idWarehouse){
                for(Item item: i.itemlist){
                    if(item.getId()==idItem) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void removeItem(int idWarehouse, int idItem) {
        for(ConsumerWarehouse i: consumerWarehouses){
            if(i.isFree()==false&&i.getId()==idWarehouse){
                for(Item item: i.itemlist){
                    if(item.getId()==idItem) {
                      i.itemlist.remove(item);
                    }
                }
            }
        }
    }

    public LinkedList<ConsumerWarehouse> getConsumerWarehouses() {
        return consumerWarehouses;
    }

    public ConsumerWarehouse getConsumerWarehouse(int index){
        return consumerWarehouses.get(index);
    }

    public LinkedList<ServiceWarehouse> getServiceWarehouses() {
        return serviceWarehouses;
    }

    public void checkAllWarehouses(LocalDate localDate) {


        for(ConsumerWarehouse consumerWarehouse: consumerWarehouses){

            if((consumerWarehouse.isFree()==false)&&(consumerWarehouse.getEndRent().isBefore(localDate))){


                if(consumerWarehouse.isOplacone()==true){
                    consumerWarehouse.getOwner().setTenantAlerts(new TenantAlert("brak oplaty za magazyn",consumerWarehouse,consumerWarehouse.getPrice()));
                    consumerWarehouse.setOplacone(false);
                    consumerWarehouse.getOwner().minusPrice(consumerWarehouse.getPrice());
                }
                if(ChronoUnit.DAYS.between(consumerWarehouse.getEndRent(), localDate)>30) {
                    consumerWarehouse.getClear();
                }
            }
        }
    }

    public boolean checkIfPersonHasWarehouse(Person person) {
        for(ConsumerWarehouse consumerWarehouse : consumerWarehouses){
            if(consumerWarehouse.isFree()==false&&consumerWarehouse.checkPermision(person))
                return true;
        }
        return false;
    }

    public boolean ifEgzist(int chose) {
        for(ConsumerWarehouse consumerWarehouse : consumerWarehouses){
            if(consumerWarehouse.isFree()!=false&&consumerWarehouse.getId()==chose)
                return true;
        }
        return false;
    }

    public boolean checkIfOwner(Person person, int warehouseId) {

        for(ConsumerWarehouse consumerWarehouse : consumerWarehouses){
            if(consumerWarehouse.isFree()==false&&consumerWarehouse.getId()==warehouseId)
                if(consumerWarehouse.getOwner()==person)
                return true;
        }
        return false;
    }

    public void addTireToServiceWarehouse(Item opona, Person logPerson) {

        int number=0;
        for(ServiceWarehouse serviceWarehouse : serviceWarehouses){
            if(number==0){
                try {
                if(serviceWarehouse.ifSpace(opona)){
                    serviceWarehouse.setItem(opona);
                    logPerson.setItem(opona);
                    number = -1;
                 }
                }
                catch (ToManyThingsException toManyThingsException){
                    toManyThingsException.getMessage();
                }
            }
        }
    }

    public void removeTire(int id) {
        for(ServiceWarehouse serviceWarehouse : serviceWarehouses) {
            serviceWarehouse.removeTire(id);
        }
    }

    public void extension(PlaceForRent placeForRent, LocalDate localDate) {
        for(ConsumerWarehouse consumerWarehouse: consumerWarehouses){
            if(consumerWarehouse==placeForRent) {
                consumerWarehouse.setOplacone(true);
                consumerWarehouse.setEndRent(localDate.plusDays(30));
                consumerWarehouse.getOwner().setPrice(consumerWarehouse.getPrice());
            }
        }
    }

    public void showWarehouse(int index) {
        for(ConsumerWarehouse consumerWarehouse: consumerWarehouses){
            if(consumerWarehouse.getId()==index)
                System.out.println(consumerWarehouse);
        }
    }

    public void showWarehouseID(Person person) {
        for(ConsumerWarehouse consumerWarehouse: consumerWarehouses){
            if(consumerWarehouse.getOwner()==person)
                System.out.println("Pomieszczenie od iD: "+consumerWarehouse.getId());
        }
    }

    @Override
    public String toString() {
        return "name='" + name + '\'' +
                ", id=" + id +
                "\n, consumerWarehouses=" + consumerWarehouses.toString() +
                "\n, serviceWarehouses=" + serviceWarehouses.toString() +
                '}';
    }


}
