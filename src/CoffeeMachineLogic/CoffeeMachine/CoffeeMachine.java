package CoffeeMachineLogic.CoffeeMachine;

import CoffeeMachineLogic.CashBox.CashBox;
import CoffeeMachineLogic.CashBox.CoinEnum;
import CoffeeMachineLogic.Stock.Stock;

import java.util.ArrayList;
import java.util.List;


public class CoffeeMachine {
    private Stock stock = new Stock();
    private CashBox cashBox = new CashBox();

    public void powerOn(){
        List<CoinEnum> inputCoins = new ArrayList<>();
        inputCoins.add(CoinEnum.ONEEURO);
        inputCoins.add(CoinEnum.TWOEURO);
        List<CoinEnum> exchangeMoney =  cashBox.checkout(inputCoins, 2.3f);
        outputCoins(exchangeMoney);

        cashBox.outputCashBoxContent();
    }
    private void outputCoins(List<CoinEnum> input){
        for (CoinEnum c: input){
            System.out.println(c);
        }
    }


}
