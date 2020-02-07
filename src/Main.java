import CoffeeMachine.CoffeeMachine;
import Money.CashBox;
import Money.Coin;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        //CoffeeMachine senseo = new CoffeeMachine();
        //senseo.powerOn();
        CashBox c = new CashBox();
        List<Coin> coins = new ArrayList<>();
        coins.add(Coin.TWOEURO);
        coins.add(Coin.ONEEURO);
        coins.add(Coin.FIFTYCENT);
        List<Coin> coinsBack = c.checkout(coins, 2.2f);

        for (Coin co : coinsBack){
            System.out.println(co.toString());
        }
    }
}
