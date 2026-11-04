package CFA1;

public class customer {

	//variables
		int custID;
		String firstName;
		String lastName;
		int phone;
		
		public customer(int id, String first, String last, int phoneNum) {
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
		
		public void placeOrder() {
			System.out.println(firstName + " " + lastName + " placed an order.");
		}
		
		public void pay() {
			System.out.println(firstName + " " + lastName + " paid for their order.");
		}
}
