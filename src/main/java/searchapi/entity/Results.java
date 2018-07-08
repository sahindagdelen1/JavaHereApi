package searchapi.entity;

import java.util.ArrayList;
import java.util.List;

public class Results {

    private String next;
    private List<Item> items = new ArrayList<Item>();

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

}