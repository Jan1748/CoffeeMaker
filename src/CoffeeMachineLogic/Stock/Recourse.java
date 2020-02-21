package CoffeeMachineLogic.Stock;

public interface Recourse {
    int getFillLevel();
    void refill();
    void take(int quantity);
}
