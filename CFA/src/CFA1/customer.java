package CFA1;

import java.util.ArrayList;

public class customer {

	//variables
		int custID;
		String firstName;
		String lastName;
		String phone;
		ArrayList<Order> orders = new ArrayList<>();
		
		public customer(int id, String first, String last, String phoneNum) {
			custID = id;
			firstName = first;
			lastName = last;
			phone = phoneNum;
		}
		
		//methods
		public void displayCustomer() {
			System.out.println("Customer Name: " + firstName + " " + lastName);
			System.out.println("Customer ID: " + custID);
			System.out.println("Customer Phone Number: " + phone);
		}
		
		public void placeOrder(Order order) {
			orders.add(order);
			System.out.println(firstName + " " + lastName + " placed an order.");
		}
		
		public void displayOrders() {
			System.out.println(firstName + " " + lastName + "'s Order: ");
			
			for (Order order : orders) {
				order.displayOrder();
			}
		}
		
		public void pay() {
			System.out.println(firstName + " " + lastName + " paid for their order.");
		}
}
