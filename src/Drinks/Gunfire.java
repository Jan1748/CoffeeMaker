package Drinks;

public class Gunfire implements Drink {
    @Override
    public String getName() {
        return "Gunfire (18+)";
    }

    @Override
    public float getPrice() {
        return 3.0f;
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
