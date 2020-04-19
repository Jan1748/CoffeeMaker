import Drinks.Drink;

public class Assortment {
    private Drink[] drinks;

    public Assortment(Drink[] drinks) {
        this.drinks = drinks;
    }

    public Drink[] getDrinks() {
        return drinks;
    }

    public Drink getDrinkByName(String name) {
        for (Drink drink : this.drinks) {
            if (drink.getName().equalsIgnoreCase(name)) {
                return drink;
            }
        }

        return null;
    }
}
