package CoffeeMachineLogic.CoffeeMachine;

import CoffeeMachineLogic.Beverages.Beverage;
import CoffeeMachineLogic.Beverages.Beverages;
import CoffeeMachineLogic.Beverages.BeveragesEnum;
import CoffeeMachineLogic.CashBox.CashBox;
import CoffeeMachineLogic.CashBox.CoinEnum;
import CoffeeMachineLogic.Stock.Stock;
import CoffeeMachineLogic.Stock.StockEnum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;


public class CoffeeMachine {
    private Stock stock = new Stock();
    private CashBox cashBox = new CashBox();
    private Beverages beverages = new Beverages();
    private Boolean power = false;
    private Beverage beverage = null;
    private int milk = 5;
    private int sugarCubes = 2;

    public void powerOn() {
        System.out.println("Power On");
        power = true;
        stateOneChooseBeverage();
    }
    public void powerOff() {
        System.out.println("Power Off");
        power = false;
    }
    private void stateOneChooseBeverage(){
        System.out.println("-------------------------------------------------");
        stock.outputStocksWithFillLevel();

        sleep(15);

        beverage = beverages.getBeverage(BeveragesEnum.COFFEE); //TODO: Choose Beverage in GUI

        HashMap<StockEnum, Integer> extrasInput = extrasInput(); //TODO: Choose which extras you want
        //TODO: Check Resources

        stateTwoInputMoney(extrasInput);
    }
    private void stateTwoInputMoney(HashMap<StockEnum, Integer> extrasInput){
        float price = beverage.getPrice();
        price += 0.1f*extrasInput.get(StockEnum.MILKPOWDER) + 0.1f*extrasInput.get(StockEnum.SUGARCUBES);
        List<CoinEnum> inputCoins = coinInput();
        List<CoinEnum> exchangeMoney;
        //TODO: Check Money

        //TODO: Start or cancel
        Boolean start = true;
        if (start){
            exchangeMoney = getExchangeMoney(inputCoins,price);
            makeCoffee(beverage.getNeededResources(), extrasInput);
        }else {
            exchangeMoney = getExchangeMoney(inputCoins,0);
            System.out.println("Canceled");
        }
        outputCoins(exchangeMoney);
        stateOneChooseBeverage();
    }
    private HashMap<StockEnum, Integer> extrasInput(){
        HashMap<StockEnum, Integer> extras = new HashMap<>();
        extras.put(StockEnum.MILKPOWDER, milk);
        extras.put(StockEnum.SUGARCUBES, sugarCubes);
        return extras;
    }


    private List<CoinEnum> coinInput() {
        //TODO: Input Parameters from GUI
        List<CoinEnum> inputCoins = new ArrayList<>();
        inputCoins.add(CoinEnum.TWOEURO);
        inputCoins.add(CoinEnum.ONEEURO);
        inputCoins.add(CoinEnum.FIFTYCENT);
        return inputCoins;
    }




    private void outputCoins(List<CoinEnum> input) {
        for (CoinEnum c : input) {
            System.out.println(c);
        }
    }
    private List<CoinEnum> getExchangeMoney(List<CoinEnum> inputCoins, float price){
        List<CoinEnum> exchangeMoney = cashBox.checkout(inputCoins, price);
        //outputCoins(exchangeMoney);
        return exchangeMoney;
    }

    private void makeCoffee(HashMap<StockEnum, Integer> resources, HashMap<StockEnum, Integer> extrasInput) {
        stock.useResources(resources);
        stock.useResources(extrasInput);
        System.out.println("Finished");

    }
    private void sleep(int sec){
        try {
            Thread.sleep(sec*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
