package com.company.Warehouse;

import com.company.Exception.ToManyThingsException;
import com.company.Person.Person;

import java.time.LocalDate;
import java.util.LinkedList;

public class ConsumerWarehouse extends PlaceForRent {

    private boolean oplacone = true;
    private boolean free = true;
    private LinkedList<Person> personList = new LinkedList<>();

    ConsumerWarehouse(int vPomieszczenia, int price) {
        super(vPomieszczenia, price);
    }

    ConsumerWarehouse(int x, int y, int z, int price) {
        super(x, y, z, price);
    }


    public boolean checkPermision(Person person){
        for (Person checkPerson: personList){
            if(checkPerson==person)
                return true;
        }
        return false;
    }

    public LinkedList<Person> getPersonList() {
        return personList;
    }

    public void setPerson(Person person) {
            personList.add(person);
    }

    @Override
    public void setItem(Item item) throws ToManyThingsException {
        if(getZajeteMiejscePrzezPrzedmioty()+item.getSize()<super.getvPomieszczenia()) {
            itemlist.add(item);
            setZajeteMiejscePrzezPrzedmioty(getZajeteMiejscePrzezPrzedmioty()+item.getSize());
        }
        else {
            throw new ToManyThingsException("Remove some old items to insert a new item");
        }
    }

    public void setOplacone(boolean oplacone){
        this.oplacone = oplacone;
    }

    public boolean isFree() {
        return free;
    }

    public Person getOwner(){
        if(personList.isEmpty())
            return null;
        return personList.getFirst();
    }



    public void setOwner(Person person, LocalDate localDate){
        personList.clear();
        personList.add(person);
        itemlist.clear();
        setZajeteMiejscePrzezPrzedmioty(0);
        free = false;

        super.setStartRent(localDate);
        super.setEndRent(getStartRent().plusDays(30));
    }

    public boolean isOplacone() {
        return oplacone;
    }

    public void showList(){
        for(Item i: itemlist){
            System.out.println(i);
        }
    }

    public boolean getPermision(Person person){
        for(Person i : personList)
            if(i==person){
                personList.remove(person);
                return true;
            }
        return false;
    }

    public String personList() {
        int counter=0;
        String ans ="[ ";
        for(Person person: personList){
            if(counter==4){
                ans+="\n";
                counter=0;
            }
            counter++;
            ans+=" { ID = "+person.getId()+", imie = "+person.getName()+", nazwisko = "+person.getSurname()+" } ";
        }
        if(ans.equals("[ "))
            return " [ brak osob ]\n";
        return ans+"]\n";
    }

    @Override
    public void getClear(){
        super.getClear();
        oplacone = true;
        free = true;
        personList.getFirst().minusPrice(getPrice());
        personList.clear();
    }

    @Override
    public String toString() {
        return super.toString()+
                "oplacone = " + oplacone +
                " data poczatku najmu = " + super.getStartRent() +
                " data konca najmu = " + super.getEndRent() +
                "\nLista osob uprawionych = " + personList();
    }


}
