import java.util.List;

interface RestaurantInterface {
    List<MenuItem> getMenu();
    void addMenuItem(MenuItem item);
    void addOrder(Order order);
    List<Order> getOrders();
    void addTable(Table table);
    void displayMenu();
    List<Table> getTables();
    boolean checkCredentials(String table, String email, String password);
    void reserveTable(int tableNumber);
}
