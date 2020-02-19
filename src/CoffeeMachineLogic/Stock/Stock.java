package CoffeeMachineLogic.Stock;

public class Stock {
    private CoffeePowder coffeePowder = new CoffeePowder();

    public int getFillLevel(StockEnum stockEnum){
        Recource currentRecource = getRecource(stockEnum);
        return currentRecource.getFillLevel();
    }
    private Recource getRecource(StockEnum stockEnum){
        switch (stockEnum){
            case COFFEEPOWDER: return coffeePowder;
        }
        return null;
    }
}
