package Drinks;

public class CoffeeCorretto implements Drink {
    @Override
    public String getName() {
        return "Coffee Corretto (18+)";
    }

    @Override
    public float getPrice() {
        return 4.2f;
    }

    @Override
    public int getNeededCoffeePowder() {
        return 20;
    }

    @Override
    public int getNeededMilkPowder() {
        return 0;
    }

    @Override
    public int getNeededWater() {
        return 200;
    }

    @Override
    public int getNeededSugar() {
        return 1;
    }
}
