package entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Inventory<T extends Objek> implements Serializable {

    // Deklarasi Atribut
    private Map<T, Integer> container;

    // Konstruktor
    public Inventory() {
        container = new HashMap<T, Integer>();
    }

    // Method
    public Map<T, Integer> getContainer() {
        return container;
    }

    public int getContainerCapacity() {
        return container.size();
    }

    public void addItem(T item) {
        if (checkItem(item)) {
            container.put(item, container.get(item) + 1);
        } else {
            container.put(item, 1);
        }
    }

    public void addItem(T item, int jumlah) {
        if (checkItem(item)) {
            container.put(item, container.get(item) + jumlah);
        } else {
            container.put(item, jumlah);
        }
    }
    
    public void removeItem(T item) {
        if (checkItem(item)) {
            if (container.get(item) > 1) {
                container.put(item, container.get(item) - 1);
            } else {
                container.remove(item);
            }
        } else {
            System.out.println("Tidak ada item yang dimaksud");
        }
    }

    public boolean checkItem(T item) {
        return container.containsKey(item);
    }

    public String[] showItem() {
        String[] inventoryPrinter = new String[getContainerCapacity()];
        int i = 0;
        for (Map.Entry<T, Integer> pair : container.entrySet()) {
            inventoryPrinter[i] = pair.getKey().getNama() + " sejumlah " + pair.getValue() + " buah";
            i++;
        }
        return inventoryPrinter;
    }
}
