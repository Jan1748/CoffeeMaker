package CoffeeMachineLogic.Stock;

public class SugarCubes implements Recourse {
    private int stock = 100;
    @Override
    public int getFillLevel() {
        return stock;
    }

    @Override
    public void refill() {
        stock = 100;
    }

    @Override
    public void take(int quantity) {
        stock -= quantity;
    }
}
