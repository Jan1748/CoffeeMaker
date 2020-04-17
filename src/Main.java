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
        cashBox.addChange(CoinType.TWOEURO, 10);
        cashBox.addChange(CoinType.ONEEURO, 10);
        cashBox.addChange(CoinType.FIFTYCENT, 10);
        cashBox.addChange(CoinType.TWENTYCENT, 10);
        cashBox.addChange(CoinType.TENCENT, 10);
        machine.setCashBox(cashBox);
    }
}
