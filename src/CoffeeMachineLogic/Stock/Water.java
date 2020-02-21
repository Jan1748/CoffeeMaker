package CoffeeMachineLogic.Stock;

public class Water implements Recourse {
    private int stock = 1000;
    @Override
    public int getFillLevel() {
        return 1000;
    }

    @Override
    public void refill() {
        stock = 1000;
    }

    @Override
    public void take(int quantity) {
        stock -= quantity;
        refill();
    }
}
