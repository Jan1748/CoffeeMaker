import Drinks.Drink;

import java.util.HashMap;

public class Composition {
    private Drink drink;

    private HashMap<DrinkSize, Float> sizeMultipliers = new HashMap<>();
    private DrinkSize drinkSize = DrinkSize.SMALL;

    private int extraSugar = 0; // in cubes
    private int extraMilk = 0; // in gram

    public Composition(Drink drink) {
        this.sizeMultipliers.put(DrinkSize.SMALL, 1.0f);
        this.sizeMultipliers.put(DrinkSize.MEDIUM, 1.25f);
        this.sizeMultipliers.put(DrinkSize.LARGE, 1.5f);

        this.drink = drink;
    }

    public float getPrice() {
        Float sizeMultiplier = this.sizeMultipliers.get(this.drinkSize);
        if (sizeMultiplier == null) {
            sizeMultiplier = 1.0f;
        }

        float price = this.drink.getPrice() * sizeMultiplier;

        // price for each extra ingredients
        float extraPrice = 0.1f;

        price += extraPrice * this.extraSugar;
        price += extraPrice * this.extraMilk;

        // Round to one decimal
        return Math.round(price * 10) / 10.0f;
    }

    public Drink getDrink() {
        return drink;
    }

    public int getExtraSugar() {
        return extraSugar;
    }

    public int getExtraMilk() {
        return extraMilk;
    }

    public void setDrink(Drink drink) {
        this.drink = drink;
    }

    public void setExtraSugar(int extraSugar) {
        this.extraSugar = extraSugar;
    }

    public void setExtraMilk(int extraMilk) {
        this.extraMilk = extraMilk;
    }

    public int getTotalSugar() {
        return this.drink.getNeededSugar() + this.getExtraSugar();
    }

    public int getTotalMilk() {
        return this.drink.getNeededMilkPowder() + this.getExtraMilk();
    }

    public int getTotalCoffee() {
        return this.drink.getNeededCoffeePowder();
    }
}
