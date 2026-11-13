package CFA1;

import java.util.HashMap;
import java.util.Map;

public class Transactions {

    private Menu menu;
    private Map<String, Integer> order;

    public Transactions() {
        menu = new Menu();
        order = new HashMap<>();
    }

    // Add an item to the transaction
    public void addItem(String item, int quantity) {
        if (quantity <= 0) {
            System.out.println("Quantity must be greater than 0.");
            return;
        }

        if (getPrice(item) == -1) {
            System.out.println("Item not found: " + item);
            return;
        }

        order.put(item, order.getOrDefault(item, 0) + quantity);
    }

    // Find the price of an item in the menu
    private double getPrice(String item) {

        if (menu.getEntrees().containsKey(item)) {
            return menu.getEntrees().get(item);
        }

        if (menu.getBreakfast().containsKey(item)) {
            return menu.getBreakfast().get(item);
        }

        if (menu.getSides().containsKey(item)) {
            return menu.getSides().get(item);
        }

        if (menu.getSalads().containsKey(item)) {
            return menu.getSalads().get(item);
        }

        if (menu.getFamily().containsKey(item)) {
            return menu.getFamily().get(item);
        }

        return -1;
    }

    // Calculate subtotal
    public double getSubtotal() {
        double subtotal = 0;

        for (String item : order.keySet()) {
            subtotal += getPrice(item) * order.get(item);
        }

        return subtotal;
    }

    // Calculate tax (example: 7.875%)
    public double getTax() {
        return getSubtotal() * 0.07875;
    }

    // Calculate final total
    public double getTotal() {
        return getSubtotal() + getTax();
    }

    // Display the transaction
    public void printReceipt() {
        System.out.println("------ RECEIPT ------");

        for (String item : order.keySet()) {
            int quantity = order.get(item);
            double price = getPrice(item);

            System.out.printf("%s x%d - $%.2f%n",
                    item, quantity, price * quantity);
        }

        System.out.println("---------------------");
        System.out.printf("Subtotal: $%.2f%n", getSubtotal());
        System.out.printf("Tax: $%.2f%n", getTax());
        System.out.printf("Total: $%.2f%n", getTotal());
    }
}
