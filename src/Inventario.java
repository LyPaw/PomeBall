import java.util.ArrayList;

public class Inventario {
     private ArrayList<Item> items = new ArrayList<>();
     
     public void agregarItem(Item item) { items.add(item); }
     public ArrayList<Item> getItems() { return items; }
}
