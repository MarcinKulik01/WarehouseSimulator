package com.company.Warehouse;


import com.company.Exception.ToManyThingsException;

public class ServiceWarehouse extends PlaceForRent {


    ServiceWarehouse(int vPomieszczenia, int price) {
        super(vPomieszczenia, price);
    }

    ServiceWarehouse(int x, int y, int z, int price) {
        super(x, y, z, price);
    }


    public void setItem(Item item) throws ToManyThingsException {

        int size = super.getvPomieszczenia()+item.getSize();

        if(size>super.getvPomieszczenia()) {

           if(item.getName().equals("Opona")) {
               itemlist.add(item);
               super.setZajeteMiejscePrzezPrzedmioty(size);
               System.out.println("Udalo sie");
           }
           else
               System.out.println("Tutaj mozna wlozyc tylko opony");
        }
        else {
            throw new ToManyThingsException("Remove some old items to insert a new item");
        }
    }

    public  boolean ifSpace(Item opona) throws ToManyThingsException {

        if(super.getvPomieszczenia()>(super.getZajeteMiejscePrzezPrzedmioty()+opona.getSize()))
            return true;
        else {
            throw new ToManyThingsException("Remove some old items to insert a new item");
        }
    }

    public void removeTire(int id){
        for (Item item: super.itemlist){
            if(item.getId()==id) {
                super.itemlist.remove(id);
                System.out.println("Udalo sie zabrac opone");
            }
        }
    }
}
