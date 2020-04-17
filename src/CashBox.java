import java.util.HashMap;

public class CashBox {
    private HashMap<CoinType, Float> coinValues = new HashMap<>();

    private HashMap<CoinType, Integer> change = new HashMap<>();
    private HashMap<CoinType, Integer> input = new HashMap<>();

    public CashBox() {
        this.coinValues.put(CoinType.TWOEURO, 2.0f);
        this.coinValues.put(CoinType.ONEEURO, 1.0f);
        this.coinValues.put(CoinType.FIFTYCENT, 0.5f);
        this.coinValues.put(CoinType.TWENTYCENT, 0.2f);
        this.coinValues.put(CoinType.TENCENT, 0.1f);
    }

    private HashMap<CoinType, Integer> getChange(float amount) {
        return null;
    }

    public boolean canProcess(Composition composition) {
        return true;
    }

    public HashMap<CoinType, Integer> process(Composition composition) {
        // returns change
        return null;
    }

    public HashMap<CoinType, Integer> returnInput() {
        // return and clear current input
        HashMap<CoinType, Integer> currentInput = this.input;
        this.input = new HashMap<>();
        return currentInput;
    }

    public void addChange(CoinType coinType, int count) {
        Integer existing = this.change.get(coinType);
        if (existing == null) {
            existing = 0;
        }

        this.change.put(coinType, existing + count);
        System.out.println(this.change);
    }

    public void addInput(CoinType coinType, int count) {
        Integer existing = this.input.get(coinType);
        if (existing == null) {
            existing = 0;
        }

        this.input.put(coinType, existing + count);
    }
}
