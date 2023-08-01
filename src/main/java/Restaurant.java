import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Restaurant implements RestaurantInterface {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/restaurent";
    private static final String USERNAME = "root";
    private static final String DB_PASSWORD = "Anusree@16";
    private List<MenuItem> menu;
    private List<Table> tables;
    private List<Order> orders;

    public Restaurant() {
        this.menu = getMenuFromDatabase(); // Load menu items from the database
        this.tables = getTablesFromDatabase();
        this.orders = new ArrayList<>();
    }

    public List<MenuItem> getMenu() {
        return menu;
    }

    public void addMenuItem(MenuItem item) {
        menu.add(item);
        addMenuItemToDatabase(item); // Add the new menu item to the database
    }

    public void addOrder(Order order) {
        orders.add(order);
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void addTable(Table table) {
        tables.add(table);
    }

    public void displayMenu() {
        System.out.println("Menu:");
        for (int i = 0; i < menu.size(); i++) {
            MenuItem item = menu.get(i);
            System.out.println((i + 1) + ". " + item.getName() + " - $" + item.getPrice());
        }
    }

    public List<Table> getTables() {
        return tables;
    }

    public boolean checkCredentials(String table, String email, String password) {
        try (Connection connection = DriverManager.getConnection(DB_URL, USERNAME, DB_PASSWORD)) {
            String query = "SELECT * FROM " + table + " WHERE email=? AND password=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private List<Table> getTablesFromDatabase() {
        List<Table> tablesList = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(DB_URL, USERNAME, DB_PASSWORD)) {
            String query = "SELECT table_number, status FROM tables";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int tableNumber = resultSet.getInt("table_number");
                String status = resultSet.getString("status");
                Table table = new Table(tableNumber);
                if (status.equalsIgnoreCase("booked")) {
                    table.reserveTable();
                }
                tablesList.add(table);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tablesList;
    }

    public void reserveTable(int tableNumber) {
        Table table = getTableByNumber(tableNumber);
        if (table != null) {
            if (!table.isReserved()) {
                table.reserveTable();
                System.out.println("Table " + table.getTableNumber() + " reserved.");
                updateTableStatusInDatabase(table.getTableNumber(), "Booked");
            } else {
                System.out.println("Table " + table.getTableNumber() + " is already reserved.");
            }
        } else {
            System.out.println("Invalid table number.");
        }
    }

    public Table getTableByNumber(int tableNumber) {
        for (Table table : tables) {
            if (table.getTableNumber() == tableNumber) {
                return table;
            }
        }
        return null;
    }

    private void updateTableStatusInDatabase(int tableNumber, String status) {
        try (Connection connection = DriverManager.getConnection(DB_URL, USERNAME, DB_PASSWORD)) {
            String query = "UPDATE tables SET status=? WHERE table_number=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, status);
            preparedStatement.setInt(2, tableNumber);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    

    public List<MenuItem> getMenuFromDatabase() {
        List<MenuItem> menuItems = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(DB_URL, USERNAME, DB_PASSWORD)) {
            String query = "SELECT item_name, item_price FROM menu";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String itemName = resultSet.getString("item_name");
                double itemPrice = resultSet.getDouble("item_price");
                MenuItem menuItem = new MenuItem(itemName, itemPrice);
                menuItems.add(menuItem);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return menuItems;
    }

    public void addMenuItemToDatabase(MenuItem item) {
        try (Connection connection = DriverManager.getConnection(DB_URL, USERNAME, DB_PASSWORD)) {
            String query = "INSERT INTO menu (item_name, item_price) VALUES (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, item.getName());
            preparedStatement.setDouble(2, item.getPrice());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void addMenuItemToDatabase(int id,MenuItem item) 
    {
    	try (Connection connection = DriverManager.getConnection(DB_URL, USERNAME, DB_PASSWORD)) {
            String query = "UPDATE menu SET item_name=?, item_price=? WHERE id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, item.getName());
            preparedStatement.setDouble(2, item.getPrice());
            preparedStatement.setInt(3, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

        public void getMenuItemsByPrice(double price) {
            List<MenuItem> matchingItems = new ArrayList<>();

            try (Connection connection = DriverManager.getConnection(DB_URL, USERNAME, DB_PASSWORD)) {
                String query = "SELECT item_name, item_price FROM menu WHERE item_price = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setDouble(1, price);

                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    String itemName = resultSet.getString("item_name");
                    double itemPrice = resultSet.getDouble("item_price");
                    System.out.println(itemName);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        public void getMenuItemsByPrice() {
        	for (int i = 0; i < menu.size(); i++) {
                MenuItem item = menu.get(i);
                System.out.println((i + 1) + ". " + item.getName() + " - $" + item.getPrice());
            }
            }
}


