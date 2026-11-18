package CFA1;


public class Transactions {

    Order order;

    public Transactions(Order order) {
        this.order = order;
    }

    //Calculate subtotal
    public double getSubtotal() {
        return order.getSubtotal();
    }

    //Calculate tax
    public double getTax() {
        return getSubtotal() * 0.07875;
    }

    //Calculate total
    public double getTotal() {
        return getSubtotal() + getTax();
    }
    
    //Calculate change
    public double calculateChange(double amountPaid) {
        if (amountPaid < getTotal()) {
            throw new IllegalArgumentException(
                "Insufficient value."
            );
        }

        return amountPaid - getTotal();
    }
    
    //Receipt
    public void printReceipt() {
    	
        System.out.println("------ RECEIPT ------");

        for (String item : order.getOrder()) {

            System.out.printf("%s - $%.2f%n",
                    item, order.getPrice(item));
        }

        System.out.println("---------------------");

        System.out.printf("Subtotal: $%.2f%n", getSubtotal());
        System.out.printf("Tax: $%.2f%n", getTax());
        System.out.printf("Total: $%.2f%n", getTotal());
    }
    
}



