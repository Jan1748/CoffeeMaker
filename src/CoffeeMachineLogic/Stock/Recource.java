package CoffeeMachineLogic.Stock;

public interface Recource {
    int getFillLevel();
    void refill();
    void take(int quantity);
}
