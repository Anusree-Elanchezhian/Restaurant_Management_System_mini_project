import java.util.List;
import java.util.Scanner;
import service.AdminServiceImpl;

public class RestaurantManagementSystem {
    private static Scanner scanner = new Scanner(System.in);
    private static Restaurant restaurant = new Restaurant();

    public static void main(String[] args) {
        System.out.println("Restaurant Management System");
        System.out.println("Select your role:");
        System.out.println("1. Customer");
        System.out.println("2. Manager");
        System.out.print("Enter Your Choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character left by nextInt()

        switch (choice) {
            case 1:
                customerLogin();
                break;
            case 2:
                managerLogin();
                break;
            default:
                System.out.println("Invalid Choice");
        }

        scanner.close();
    }

    private static void customerLogin() {
        System.out.println("Customer Login:");
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        if (checkCustomerCredentials(email, password)) {
            System.out.println("Login Successful");

            // Customer actions
            boolean exit = false;
            while (!exit) {
                System.out.println("\nCustomer Menu:");
                System.out.println("1. Display Menu");
                System.out.println("2. Place Order");
                System.out.println("3. Book Table");
                System.out.println("4. Make Payment");
                System.out.println("5. Filter Menu by Price");
                System.out.println("6. Logout");
                System.out.print("Enter your choice: ");
                int customerChoice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character left by nextInt()

                switch (customerChoice) {
                    case 1:
                        restaurant.displayMenu();
                        break;
                    case 2:
                        placeOrder(restaurant);
                        break;
                    case 3:
                        bookTable(restaurant);
                        break;
                    case 4:
                        makePayment();
                        break;
                    case 5:
                    	System.out.println("Enter Price :");
                    	String temp=scanner.nextLine();
                    	if (temp=="")
                    	restaurant.getMenuItemsByPrice();
                    	else {
                    		Double price=Double.parseDouble(temp);
                    		restaurant.getMenuItemsByPrice(price);}
                    	break;
                    case 6:
                        exit = true;
                        System.out.println("Logged out successfully.");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        } else {
            System.out.println("Invalid credentials");
        }
    }

    private static void managerLogin() {
        System.out.println("Manager Login:");
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        
           AdminServiceImpl adminService = new AdminServiceImpl();
            
        if (adminService.isAdminUser(email, password)) {
            System.out.println("Login Successful");
            boolean exit = false;
            while (!exit) {
                System.out.println("\nManager Menu:");
                System.out.println("1. Add Menu Item");;
                System.out.println("2. Logout");
                System.out.print("Enter your choice: ");
                int managerChoice = scanner.nextInt();
//                int menu_id ;
//                if(managerChoice == 2)
//                {
//                	System.out.print("\n Enter menu_id : ");
//                	menu_id = scanner.nextInt();
//                }
                scanner.nextLine(); // Consume the newline character left by nextInt()

                switch (managerChoice) {
                    case 1:
                        addMenuItem();
                        break;
//                    case 2:
//                        addMenuItem(menu_id);
//                        break;
                    case 2:
                        exit = true;
                        System.out.println("Logged out successfully.");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        } else {
            System.out.println("Invalid credentials");
        }
    }

    private static boolean checkCustomerCredentials(String email, String password) {
    	return restaurant.checkCredentials("login", email, password);
    }

    private static boolean checkManagerCredentials(String email, String password) {
    	 return restaurant.checkCredentials("managerlogin", email, password);
    }

    private static void addMenuItem() {
        System.out.print("Enter the item name: ");
        String itemName = scanner.nextLine();
        System.out.print("Enter the item price: ");
        double itemPrice = scanner.nextDouble();
        scanner.nextLine(); // Consume the newline character left by nextDouble()

        MenuItem newItem = new MenuItem(itemName, itemPrice);
        restaurant.addMenuItemToDatabase(newItem); // Call the method to add the item to the database
        System.out.println("New menu item added: " + itemName + " - $" + itemPrice);
    }
    
    private static void placeOrder(Restaurant restaurant) {
        System.out.println("Menu:");
        restaurant.displayMenu();

        // Create a new Order for the customer
        Order order = new Order();

        // Prompt the customer to select items and quantities
        System.out.println("\nEnter the item numbers you want to order (comma-separated):");
        String input = scanner.nextLine();
        String[] itemNumbers = input.split(",");

        for (String itemNumber : itemNumbers) {
            try {
                int index = Integer.parseInt(itemNumber.trim()) - 1;
                if (index >= 0 && index < restaurant.getMenu().size()) {
                    MenuItem item = restaurant.getMenu().get(index);
                    System.out.print("Enter the quantity for " + item.getName() + ": ");
                    int quantity = scanner.nextInt();
                    scanner.nextLine(); // Consume the newline character left by nextInt()

                    if (quantity > 0) {
                        order.addItem(item, quantity);
                    } else {
                        System.out.println("Invalid quantity. Ignoring item.");
                    }
                } else {
                    System.out.println("Invalid item number. Ignoring item.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input format. Ignoring item.");
            }
        }

        // Add the customer's order to the list of orders in the restaurant
        restaurant.addOrder(order);

        System.out.println("Order placed successfully.");
    }

    private static void bookTable(Restaurant restaurant) {
        System.out.println("\nTables Status:");
        List<Table> tables = restaurant.getTables();
        for (Table table : tables) {
            System.out.println("Table " + table.getTableNumber() + ": " + (table.isReserved() ? "Reserved" : "Available"));
        }

        System.out.print("\nEnter table number to book (0 to cancel): ");
        int tableNumber = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character left by nextInt()

        if (tableNumber == 0) {
            return;
        }

        restaurant.reserveTable(tableNumber);
    }

    private static void makePayment() {
        // Get the total amount to be paid (for example, from the customer's order)
        double totalAmount = 100.00; // Replace this with the actual total amount

        // Prompt the customer to select a payment method
        System.out.println("\nPayment Options:");
        System.out.println("1. Credit Card");
        System.out.println("2. Cash");
        System.out.println("3. Mobile Payment");
        System.out.print("Enter your payment method choice: ");
        int paymentChoice = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character left by nextInt()

        String paymentMethod;
        switch (paymentChoice) {
            case 1:
                paymentMethod = "Credit Card";
                break;
            case 2:
                paymentMethod = "Cash";
                break;
            case 3:
                paymentMethod = "Mobile Payment";
                break;
            default:
                System.out.println("Invalid payment method choice. Payment cancelled.");
                return;
        }

        // Process the payment
        Payment payment = new Payment(totalAmount, paymentMethod);
        payment.processPayment();
    }

}
