package Drinks;

public class LatteMacchiato implements Drink {
    @Override
    public String getName() {
        return "Latte Macchiato";
    }

    @Override
    public float getPrice() {
        return 3.0f;
    }

    @Override
    public int getNeededCoffeePowder() {
        return 10;
    }

    @Override
    public int getNeededMilkPowder() {
        return 40;
    }

    @Override
    public int getNeededWater() {
        return 300;
    }

    @Override
    public int getNeededSugar() {
        return 0;
    }
}
