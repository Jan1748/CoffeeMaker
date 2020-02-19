package CoffeeMachineLogic.CashBox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class CashBox {
    private HashMap<CoinEnum, Integer> coinsInBox = new HashMap<>();

    public CashBox() {
        coinsInBox.put(CoinEnum.TENCENT, 10);
        coinsInBox.put(CoinEnum.TWENTYCENT, 10);
        coinsInBox.put(CoinEnum.FIFTYCENT, 10);
        coinsInBox.put(CoinEnum.ONEEURO, 10);
        coinsInBox.put(CoinEnum.TWOEURO, 10);
    }
    public void outputCashBoxContent(){
        Set<CoinEnum> keys = coinsInBox.keySet();
        for(CoinEnum c : keys){
            System.out.println("Coin: " + c + " Value: " + coinsInBox.get(c));
        }
    }
    public List<CoinEnum> checkout(List<CoinEnum> inputCoins, float price){
        List<CoinEnum> exchangeMoney;
        float exchangeMoneyFloat;

        float inputMoney = round(coinsToMoney(inputCoins));
        addCoinsToCashBox(inputCoins);

        exchangeMoneyFloat = round(inputMoney-price);
        exchangeMoney = moneyFloatToCoins(exchangeMoneyFloat);
        removeCoins(exchangeMoney);

        return exchangeMoney;

    }
    private void addCoinsToCashBox(List<CoinEnum> inputCoins){
        for (CoinEnum c : inputCoins){
            addCoins(c, 1);
        }

    }
    private float coinsToMoney(List<CoinEnum> inputCoins){
        float money = 0f;
        for (CoinEnum c : inputCoins){
            switch (c){
                case TENCENT: money += 0.1f; break;
                case TWENTYCENT: money += 0.2f;break;
                case FIFTYCENT: money += 0.5f;break;
                case ONEEURO: money += 1f;break;
                case TWOEURO: money += 2f;break;
            }
        }
        return money;
    }

    private List<CoinEnum> moneyFloatToCoins(float money){
        List<CoinEnum> coins = new ArrayList<>();
        money = round(money);
        while (money > 0){
            if (coinsInBox.get(CoinEnum.TWOEURO) > 0 && money >= 2){
                money -= 2;
                coins.add(CoinEnum.TWOEURO);
            }else if (coinsInBox.get(CoinEnum.ONEEURO) > 0 && money >= 1){
                money -= 1;
                coins.add(CoinEnum.ONEEURO);
            }else if (coinsInBox.get(CoinEnum.FIFTYCENT) > 0 && money >= 0.5){
                money -= 0.5;
                coins.add(CoinEnum.FIFTYCENT);
            }else if (coinsInBox.get(CoinEnum.TWENTYCENT) > 0 && money >= 0.2){
                money -= 0.2;
                coins.add(CoinEnum.TWENTYCENT);
            }else if (coinsInBox.get(CoinEnum.TENCENT) > 0 && money >= 0.1){
                money -= 0.1;
                coins.add(CoinEnum.TENCENT);
            }else {
                System.out.println("No Money left");
                break;
            }
            money = round(money);
        }
        return coins;

    }
    private float round(float numberToRound){
        float result = numberToRound * 100;
        result = Math.round(result);
        return result/100;

    }

    private void addCoins(CoinEnum coin, int coinsToAdd) {
        int a = coinsInBox.get(coin);
        coinsToAdd += a;
        coinsInBox.replace(coin, coinsToAdd);
    }

    private void removeCoins(List<CoinEnum> coins) {
        for (CoinEnum coin : coins) {
            int a = this.coinsInBox.get(coin);
            a -= 1;
            this.coinsInBox.replace(coin, a);
        }
    }
}
