import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Set;

class CoinContainer {
    public HashMap<CoinType, Integer> coins = new HashMap<>();

    public CoinContainer() {}

    public CoinContainer(HashMap<CoinType, Integer> coins) {
        this.coins = coins;
    }

    public void clear() {
        this.coins.clear();
    }

    public Set<CoinType> getCoinTypes() {
        return this.coins.keySet();
    }

    public CoinContainer getClone() {
        return new CoinContainer((HashMap<CoinType, Integer>) this.coins.clone());
    }

    public void add(CoinContainer other) {
        for (CoinType coinType : other.getCoinTypes()) {
            this.addCoins(coinType, other.getCoinCount(coinType));
        }
    }

    public void subtract(CoinContainer other) {
        for (CoinType coinType : other.getCoinTypes()) {
            this.removeCoins(coinType, other.getCoinCount(coinType));
        }
    }

    public CoinContainer getCoinsForValue(float value) {
        return null;
    }

    public float getTotalValue(HashMap<CoinType, Float> coinValues) {
        float value = 0;
        for (CoinType coinType : this.coins.keySet()) {
            value += coinValues.get(coinType) * this.coins.get(coinType);
        }

        return value;
    }

    public int getCoinCount(CoinType coinType) {
        Integer count = this.coins.get(coinType);
        if (count == null) {
            count = 0;
        }

        return count;
    }

    public void addCoins(CoinType coinType, int count) {
        Integer existing = this.coins.get(coinType);
        if (existing == null) {
            existing = 0;
        }

        this.coins.put(coinType, existing + count);
    }

    public void removeCoins(CoinType coinType, int count) {
        Integer existing = this.coins.get(coinType);
        if (existing == null) {
            existing = 0;
        }

        this.coins.put(coinType, existing - count);
    }

    public void setCoins(CoinType coinType, int count) {
        this.coins.put(coinType, count);
    }
}


public class CashBox {
    // Use a linked hashmap to maintain insertion order
    private HashMap<CoinType, Float> coinValues = new LinkedHashMap<>();

    private CoinContainer change = new CoinContainer();
    private CoinContainer input = new CoinContainer();

    public CashBox() {
        this.coinValues.put(CoinType.TWOEURO, 2.0f);
        this.coinValues.put(CoinType.ONEEURO, 1.0f);
        this.coinValues.put(CoinType.FIFTYCENT, 0.5f);
        this.coinValues.put(CoinType.TWENTYCENT, 0.2f);
        this.coinValues.put(CoinType.TENCENT, 0.1f);
    }

    private CoinContainer getCoinsForValue(CoinContainer coins, float value) {
        // Modifies the passed coin container!

        CoinContainer resultingCoins = new CoinContainer();

        while (value > 0) {
            CoinType[] coinOrder = new CoinType[] {
                    CoinType.TWOEURO,
                    CoinType.ONEEURO,
                    CoinType.FIFTYCENT,
                    CoinType.TWENTYCENT,
                    CoinType.TENCENT
            };

            for (CoinType coinType : coinOrder) {
                float coinValue = this.coinValues.get(coinType);
                if (value >= coinValue && coins.getCoinCount(coinType) > 0) {
                    value -= coinValue;
                    coins.removeCoins(coinType, 1);
                    resultingCoins.addCoins(coinType, 1);
                    break;
                }

                // No coin for the lowest coin value; no possible result
                if (coinType == coinOrder[coinOrder.length - 1]) {
                    return null;
                }
            }
        }

        return resultingCoins;
    }

    public boolean hasSufficientInput(Composition composition) {
        float inputValue = this.input.getTotalValue(this.coinValues);
        return inputValue >= composition.getPrice();
    }

    public boolean hasSufficientChange(Composition composition) {
        float inputValue = this.input.getTotalValue(this.coinValues);
        float toReturn = inputValue - composition.getPrice();

        // Input can also be used as change
        CoinContainer totalChange = this.change.getClone();
        totalChange.add(this.input);

        CoinContainer returnChange = this.getCoinsForValue(totalChange, toReturn);
        return returnChange != null;
    }

    public CoinContainer process(Composition composition) {
        float inputValue = this.input.getTotalValue(this.coinValues);
        float toReturn = inputValue - composition.getPrice();

        // Move over the input to the change
        this.change.add(this.input);
        this.input.clear();

        return this.getCoinsForValue(this.change, toReturn);
    }

    public CoinContainer returnInput() {
        // return and clear current input
        CoinContainer currentInput = this.input;
        this.input = new CoinContainer();
        return currentInput;
    }

    public CoinContainer getChange() {
        return change;
    }

    public CoinContainer getInput() {
        return input;
    }
}
