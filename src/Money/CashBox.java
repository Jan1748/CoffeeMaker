package Money;

import java.util.ArrayList;
import java.util.List;

public class CashBox {
    private List<Coin> twoEuroCoins;
    private List<Coin> oneEuroCoins;
    private List<Coin> fiftyCentCoins;
    private List<Coin> twentyCentCoins;
    private List<Coin> tenCentCoins;

    public CashBox() {
        twoEuroCoins = new ArrayList<>();
        oneEuroCoins = new ArrayList<>();
        fiftyCentCoins = new ArrayList<>();
        twentyCentCoins = new ArrayList<>();
        tenCentCoins = new ArrayList<>();
        addCoinsFromEachType(10);
    }

    public Boolean checkIfEnouthMoney(List<Coin> inputCoins, float price) {
        float money = parseCoinsToMoney(inputCoins);
        money = money - price;
        if (money >= 0) {
            return true;
        } else {
            return false;
        }
    }

    public List<Coin> checkout(List<Coin> inputCoins, float price) {
        List<Coin> changedMoney;
        addCoinsToBank(inputCoins);

        float inputCoinsWorth = parseCoinsToMoney(inputCoins);
        inputCoinsWorth -= price;
        changedMoney = parseMoneyToCoins(inputCoinsWorth);

        return changedMoney;
    }

    private void removeCoinFromBank(Coin c) {
        switch (c) {
            case TWOEURO:
                twoEuroCoins.remove(twoEuroCoins.size() - 1);
                break;
            case ONEEURO:
                oneEuroCoins.remove(oneEuroCoins.size() - 1);
                break;
            case FIFTYCENT:
                fiftyCentCoins.remove(fiftyCentCoins.size() - 1);
                break;
            case TWENTYCENT:
                twentyCentCoins.remove(twentyCentCoins.size() - 1);
                break;
            case TENCENT:
                tenCentCoins.remove(tenCentCoins.size() - 1);
                break;
        }

    }

    private void addCoinsToBank(List<Coin> coins) {
        for (Coin c : coins) {
            switch (c) {
                case TWOEURO:
                    twoEuroCoins.add(Coin.TWOEURO);
                    break;
                case ONEEURO:
                    oneEuroCoins.add(Coin.ONEEURO);
                    break;
                case FIFTYCENT:
                    fiftyCentCoins.add(Coin.FIFTYCENT);
                    break;
                case TWENTYCENT:
                    twentyCentCoins.add(Coin.TWENTYCENT);
                    break;
                case TENCENT:
                    tenCentCoins.add(Coin.TENCENT);
                    break;
            }
        }
    }


    private void addCoinsFromEachType(int numOfCoinsFromEachType) {
        for (int j = 0; j < numOfCoinsFromEachType; j++) {
            twoEuroCoins.add(Coin.TWOEURO);
            oneEuroCoins.add(Coin.ONEEURO);
            fiftyCentCoins.add(Coin.FIFTYCENT);
            twentyCentCoins.add(Coin.TWENTYCENT);
            tenCentCoins.add(Coin.TENCENT);
        }
    }

    private List<Coin> parseMoneyToCoins(float money) {
        List<Coin> coins = new ArrayList<>();
        while (money > 0) {
            money = money * 100;
            money = Math.round(money);
            money = money / 100;
            System.out.println(money + " " + twoEuroCoins.size());
            if (money >= 2 && twoEuroCoins.size() != 0) {
                coins.add(Coin.TWOEURO);
                removeCoinFromBank(Coin.TWOEURO);
                money -= 2;
            } else if (money >= 1 && oneEuroCoins.size() != 0) {
                coins.add(Coin.ONEEURO);
                removeCoinFromBank(Coin.ONEEURO);

                money -= 1;
            } else if (money >= 0.5 && fiftyCentCoins.size() != 0) {
                coins.add(Coin.FIFTYCENT);
                removeCoinFromBank(Coin.FIFTYCENT);

                money -= 0.5;
            } else if (money >= 0.2 && twentyCentCoins.size() != 0) {
                coins.add(Coin.TWENTYCENT);
                removeCoinFromBank(Coin.TWENTYCENT);

                money -= 0.2;
            } else if (money >= 0.1 && tenCentCoins.size() != 0) {
                coins.add(Coin.TENCENT);
                removeCoinFromBank(Coin.TENCENT);

                money -= 0.1;
            }

        }
        return coins;

    }


    private float parseCoinsToMoney(List<Coin> coins) {
        float money = 0;
        for (Coin c : coins) {
            switch (c) {
                case TWOEURO:
                    money += 2f;
                    break;
                case ONEEURO:
                    money += 1f;
                    break;
                case FIFTYCENT:
                    money += 0.5f;
                    break;
                case TWENTYCENT:
                    money += 0.2f;
                    break;
                case TENCENT:
                    money += 0.1f;
                    break;
            }
        }
        return money;
    }
}
