package CoffeeMachineLogic.Stock;

public class MilkPowder implements Recourse {
    private int stock = 1000;
    @Override
    public int getFillLevel() {
        return stock;
    }

    @Override
    public void refill() {
        stock = 1000;
    }

    @Override
    public void take(int quantity) {
        stock -= quantity;
    }
}
