public class Payment {
    private double amount;
    private String paymentMethod;

    public Payment(double amount, String paymentMethod) {
        this.amount = amount;
        this.paymentMethod = paymentMethod;
    }

    public void processPayment() {
        // Implement the payment processing logic here
        // For simplicity, let's just print a message indicating successful payment
        System.out.println("Payment of $" + amount + " processed successfully via " + paymentMethod);
    }
}
