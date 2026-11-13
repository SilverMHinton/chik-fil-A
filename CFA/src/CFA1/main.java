package CFA1;

public class main {

	public static void main(String[] args) {
		
		//this is just a test to see that things are properly connected
		//we can change this later to be more adaptable to user
		
		// Create restaurant
        Restaurant restaurant = new Restaurant();

        // Add employees
        restaurant.addEmployee();
        restaurant.addEmployee();
        restaurant.addEmployee();

        // Set restaurant information
        restaurant.editSchedule("Monday - Saturday: 6:30 AM - 10:00 PM");

        // Add inventory
        restaurant.editInventory("Chick-fil-A Chicken Sandwich", 20);
        restaurant.editInventory("Waffle Fries (Medium)", 30);
        restaurant.editInventory("Chick-fil-A Nuggets", 25);

        // Display restaurant
        restaurant.displayRestaurant();

        System.out.println();

        // Create customer
        customer customer1 = new customer(
            101,
            "John",
            "Smith",
            "555-1234"
        );

        // Create order using restaurant's menu
        Order order1 = new Order(
            101,
            1,
            restaurant.getMenu()
        );

        // Add items
        order1.addItem("Chick-fil-A Chicken Sandwich", 2);
        order1.addItem("Waffle Fries (Medium)", 1);

        // Give order to customer
        customer1.placeOrder(order1);

        System.out.println();

        // Display customer
        customer1.displayCustomer();

        System.out.println();

        // Display customer's orders
        customer1.displayOrders();

        System.out.println();

        // Create transaction
        Transactions transaction = new Transactions(order1);

        // Display receipt and calculate total
        transaction.printReceipt();
        
		/*System.out.println("What month is it?");

		// print the menu

		System.out.println("What would you like to order?");

		System.out.println("Are you a rewards member?");

		System.out.println("Cash or Card?");

		System.out.println("Enter payment:");

		// call orders */

	} 

}
