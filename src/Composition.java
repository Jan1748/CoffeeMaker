import Drinks.Drink;

public class Composition {
    private Drink drink;

    private int extraSugar = 0; // in cubes
    private int extraMilk = 0; // in gram

    public float getPrice() {
        float price = this.drink.getPrice();

        // price for each extra ingredients
        float extraPrice = 0.1f;

        price += extraPrice * this.extraSugar;
        price += extraPrice * this.extraMilk;

        return price;
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
}
