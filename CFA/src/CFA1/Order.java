package CFA1;

import java.util.ArrayList;

public class Order {

	//variables
	ArrayList<String> order = new ArrayList<>();
	int custID;
	int orderNum;
	double total;
	Menu menu;
		
	public Order(int custID, int orderNum, Menu menu) {
		this.custID = custID;
		this.orderNum = orderNum;
		this.menu = menu;
	}
		
	//methods 
	public void addItem(String menuItem, int itemAmount) {
			
		for (int i = 0; i < itemAmount; i++) {
			order.add(menuItem);
		}
			
		System.out.println(itemAmount + " " + menuItem + "(s) has been added to the order.");
		//display order
		displayOrder();
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
		displayOrder();
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
		displayOrder();
	}
	
	public double getPrice(String menuItem) {
		if (menu.getEntrees().containsKey(menuItem)) {
			return menu.getEntrees().get(menuItem);
		}
		
		if (menu.getBreakfast().containsKey(menuItem)) {
			return menu.getBreakfast().get(menuItem);
		}
		
		if (menu.getSides().containsKey(menuItem)) {
	        return menu.getSides().get(menuItem);
	    }

	    if (menu.getSalads().containsKey(menuItem)) {
	        return menu.getSalads().get(menuItem);
	    }

	    if (menu.getFamily().containsKey(menuItem)) {
	        return menu.getFamily().get(menuItem);
	    }

	    return 0;
	}
	
    public double getSubtotal() {

        double total = 0;

        for (String item : order) {
            total += getPrice(item);
        }

        return total;
    }
		
	public void total() {
		
		double total = getSubtotal();
		System.out.println("The total is: $" + total);
	}
	
	public void displayOrder() {
		System.out.println("Order Number: " + orderNum);
		System.out.println("Customer ID: " + custID);
		System.out.println("Items: " + order);
	}
	
	 public ArrayList<String> getOrder() {
	        return order;
	    }

}
