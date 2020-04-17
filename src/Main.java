import Drinks.Coffee;
import Drinks.Drink;
import Drinks.Espresso;

public class Main {
    public static void main(String[] args) {
        Machine machine = new Machine();

        Drink[] drinks = new Drink[] {
                new Coffee(),
                new Espresso()
        };

        Assortment assortment = new Assortment(drinks);
        machine.setAssortment(assortment);

        Stock stock = new Stock(1000, 1000, 100);
        machine.setStock(stock);

        CashBox cashBox = new CashBox();

        // add initial change
        CoinContainer change = cashBox.getChange();
        change.addCoins(CoinType.TWOEURO, 10);
        change.addCoins(CoinType.ONEEURO, 10);
        change.addCoins(CoinType.FIFTYCENT, 10);
        change.addCoins(CoinType.TWENTYCENT, 10);
        change.addCoins(CoinType.TENCENT, 10);

        machine.setCashBox(cashBox);
    }
}
