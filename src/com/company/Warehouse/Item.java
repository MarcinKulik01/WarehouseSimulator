package com.company.Warehouse;

public class Item implements Comparable<Item> {
    private static volatile int staticId = 0;
    private int id;
    private int size;
    private String name;


    public Item(int size, String name) {
        this.size = size;
        this.name = name;
        id=staticId++;
    }

    public Item(int dlugosc, int szerkokosc, int wysokosci, String name) {
        this.size = dlugosc*szerkokosc*wysokosci;
        this.name = name;
        id=staticId++;
    }

    public String getName() {
        return name;
    }

    public int getSize() {
        return size;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "id = " + id +
                ", size = " + size +
                ", name = " + name;
    }

    @Override
    public int compareTo(Item o) {
        if(this.getSize()==o.getSize()) {
            if(name.equals(o.name))
                return 0;
            if(name.length()<o.name.length()){
                int number = name.length();
                int index=0;
                while (number-- > 0){
                    if(name.charAt(index)==o.name.charAt(index)){
                        index+=1;
                        number-=1;
                    }
                    else if(name.charAt(index)<o.name.charAt(index)){
                        return 1;
                    }
                    else if(name.charAt(index)>o.name.charAt(index)){
                        return -1;
                    }
                }
                return  1;
            }
            if(name.length()>o.name.length()){
                int number = name.length();
                int index=0;
                while (number-- > 0){
                    if(name.charAt(index)==o.name.charAt(index)){
                        index+=1;
                        number-=1;
                    }
                    else if(name.charAt(index)<o.name.charAt(index)){
                        return -1;
                    }
                    else if(name.charAt(index)>o.name.charAt(index)){
                        return 1;
                    }
                }
                return  -1;
            }

        }
        return this.getSize() - o.getSize();
    }
}
