package CFA1;

import java.util.ArrayList;

public class Order {

	//variables
	ArrayList<String> order = new ArrayList<>();
	int custID;
	int orderNum;
	String menuItem;
	int itemAmount;
	double total;
		
	public Order(int custID, int orderNum) {
		this.custID = custID;
		this.orderNum = orderNum;
	}
		
	//methods 
	public void addItem(String menuItem, int itemAmount) {
			
		for (int i = 0; i < itemAmount; i++) {
			order.add(menuItem);
		}
			
		System.out.println(itemAmount + " " + menuItem + "(s) has been added to the order.");
		//display order
		System.out.println(order);
	}
		
	public void removeItem(String menuItem, int itemAmount) {
		int remove = 0;
			
		for (int i = 0; i < itemAmount; i++) {
			if (order.remove(menuItem)) {
				remove++;
			}
			else {
				break;
			}
		}
		System.out.println(remove + " " + menuItem + "(s) have been removed from the order.");
		//display order
		System.out.println(order);
	}
		
	public void editItem(String oldItem, String newItem) {
		int index = order.indexOf(oldItem);
		
		if (index != -1) {
			order.set(index, newItem);
			System.out.println("Item updated.");
		}
		else {
			System.out.println("Item not found.");
		}
		//display order
		System.out.println(order);
	}
		
	public void total() {
		System.out.println("The total is: $" + total);
	}
	
	public void displayOrder() {
		System.out.println("Order Number: " + orderNum);
		System.out.println("Customer ID: " + custID);
		System.out.println("Items: " + order);
	}
}
