import java.util.HashMap;
import java.util.Map;

public class Order {
    private Map<MenuItem, Integer> itemsOrdered;

    public Order() {
        this.itemsOrdered = new HashMap<>();
    }

    public void addItem(MenuItem item, int quantity) {
        itemsOrdered.put(item, quantity);
    }

    public Map<MenuItem, Integer> getItemsOrdered() {
        return itemsOrdered;
    }
}
