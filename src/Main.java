import Drinks.Coffee;
import Drinks.Drink;
import Drinks.Espresso;
import Enums.CoinType;
import Enums.DrinkSize;
import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.LinkedHashMap;

public class Main extends Application {
    private Machine machine = new Machine();

    private StringProperty priceDisplay = new SimpleStringProperty("0 €");
    private StringProperty stateDisplay = new SimpleStringProperty("Beep Boop");

    private BooleanProperty greenLamp = new SimpleBooleanProperty(false);
    private BooleanProperty orangeLamp = new SimpleBooleanProperty(false);

    private void playSound(String fileName) {
        Media sound = new Media(getClass().getResource("resources/" + fileName).toExternalForm());
        MediaPlayer player = new MediaPlayer(sound);
        player.play();
    }

    public void updatePriceDisplay() {
        priceDisplay.set(machine.getComposition().getPrice() + " €");
    }

    public void handleStartButtonClicked(MouseEvent t) {
        System.out.println("asdas");
    }

    public void handleCancelButtonClicked(MouseEvent t) {
        CoinContainer returned = machine.getCashBox().returnInput();
        float value = returned.getTotalValue(machine.getCashBox().getCoinValues());
        if (value > 0) {
            stateDisplay.set("Returning " + value + "€ to you.");
            playSound("return.mp3");
        }
    }

    public void handleDrinkChanged(ObservableValue obs, String oldValue, String newValue) {
        Drink newDrink = machine.getAssortment().getDrinkByName(newValue);
        machine.getComposition().setDrink(newDrink);
        updatePriceDisplay();
    }

    public void handleSizeChanged(ObservableValue obs, String oldValue, String newValue) {
        DrinkSize size = DrinkSize.valueOf(newValue.toUpperCase());
        machine.getComposition().setDrinkSize(size);
        updatePriceDisplay();
    }

    public void handleSugarChanged(ObservableValue obs, Integer oldValue, Integer newValue) {
        machine.getComposition().setExtraSugar(newValue);
        updatePriceDisplay();
    }

    public void handleMilkChanged(ObservableValue obs, Integer oldValue, Integer newValue) {
        machine.getComposition().setExtraMilk(newValue);
        updatePriceDisplay();
    }

    public void handleCoinInserted(CoinType coin) {
        machine.getCashBox().getInput().addCoins(coin, 1);
        playSound("insert.mp3");
    }

    public static void main(String[] args) {
        launch();
    }

    public void initMachine() {
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

    public Pane buildLayout() {
        BorderPane root = new BorderPane();

        // --- Top Part ---
        Menu helpMenu = new Menu("Help");
        Menu adminMenu = new Menu("Admin");
        MenuBar menuBar = new MenuBar(helpMenu, adminMenu);
        root.setTop(menuBar);

        // --- Center Part ---
        GridPane displayGrid = new GridPane();
        displayGrid.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        root.setCenter(displayGrid);

        Label display = new Label();
        display.textProperty().bind(stateDisplay);

        VBox coffeeDisplay = new VBox(display);
        coffeeDisplay.prefWidthProperty().bind(root.widthProperty().multiply(0.7));

        // --- Right Part ---

        // Coffee Selection
        GridPane selectionGrid = new GridPane();
        selectionGrid.setHgap(10);
        selectionGrid.setVgap(15);

        selectionGrid.add(new Label("Drink:"), 0, 1);
        ObservableList<String> availableCofees = FXCollections.observableArrayList();
        for (Drink drink : this.machine.getAssortment().getDrinks()) {
            availableCofees.add(drink.getName());
        }

        ChoiceBox<String> coffeeCoice = new ChoiceBox<>(availableCofees);
        coffeeCoice.setValue(availableCofees.get(0));
        coffeeCoice.setPadding(new Insets(2));
        coffeeCoice.valueProperty().addListener(this::handleDrinkChanged);
        selectionGrid.add(coffeeCoice, 1, 1);

        selectionGrid.add(new Label("Sugar:"), 0, 2);
        Spinner<Integer> sugarSpinner = new Spinner<>(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 5, 0));
        sugarSpinner.setPadding(new Insets(2));
        sugarSpinner.setMaxWidth(100);
        sugarSpinner.valueProperty().addListener(this::handleSugarChanged);
        selectionGrid.add(sugarSpinner, 1, 2);

        selectionGrid.add(new Label("Milk:"), 0, 3);
        Spinner<Integer> milkSpinner = new Spinner<>(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 5, 0));
        milkSpinner.setPadding(new Insets(2));
        milkSpinner.setMaxWidth(100);
        milkSpinner.valueProperty().addListener(this::handleMilkChanged);
        selectionGrid.add(milkSpinner, 1, 3);

        selectionGrid.add(new Label("Size:"), 0, 4);
        ObservableList<String> availableSizes = FXCollections.observableArrayList();
        for (DrinkSize drinkSize : DrinkSize.values()) {
            availableSizes.add(drinkSize.name().toLowerCase());
        }

        Spinner<String> sizeSpinner = new Spinner<>(new SpinnerValueFactory.ListSpinnerValueFactory<String>(availableSizes));
        sizeSpinner.setPadding(new Insets(2));
        sizeSpinner.setMaxWidth(100);
        sizeSpinner.valueProperty().addListener(this::handleSizeChanged);
        selectionGrid.add(sizeSpinner, 1, 4);

        // Insert Money
        FlowPane coinButtons = new FlowPane();

        LinkedHashMap<String, CoinType> availableCoins = new LinkedHashMap<>();
        availableCoins.put("0_10.png", CoinType.TENCENT);
        availableCoins.put("0_20.png", CoinType.TWENTYCENT);
        availableCoins.put("0_50.png", CoinType.FIFTYCENT);
        availableCoins.put("1.png", CoinType.ONEEURO);
        availableCoins.put("2.png", CoinType.TWOEURO);


        for (String img : availableCoins.keySet()) {
            ImageView coinImg = new ImageView(getClass().getResource("./resources/" + img).toExternalForm());
            coinImg.addEventFilter(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
                this.handleCoinInserted(availableCoins.get(img));
            });
            coinImg.setFitWidth(50);
            coinImg.setPreserveRatio(true);
            coinButtons.getChildren().add(coinImg);
        }

        // Confirmation
        Button startButton = new Button("Start");
        startButton.addEventFilter(MouseEvent.MOUSE_CLICKED, this::handleStartButtonClicked);
        Button cancelButton = new Button("Cancel");
        cancelButton.addEventFilter(MouseEvent.MOUSE_CLICKED, this::handleCancelButtonClicked);
        HBox buttons = new HBox(startButton, cancelButton);
        buttons.setSpacing(5);

        Label selectionTitle = new Label("1. Choose A Drink");
        selectionTitle.setFont(new Font(20));

        Label moneyTitle = new Label("2. Insert Money");
        moneyTitle.setFont(new Font(20));

        Label confirmTitle = new Label("3. Confirm");
        confirmTitle.setFont(new Font(20));

        VBox coffeeSelection = new VBox(
                selectionTitle,
                selectionGrid,
                moneyTitle,
                coinButtons,
                confirmTitle,
                buttons
        );
        coffeeSelection.prefWidthProperty().bind(root.widthProperty().multiply(0.3));
        coffeeSelection.setPadding(new Insets(25));
        coffeeSelection.setSpacing(20);
        root.setRight(coffeeSelection);

        return root;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        initMachine();

        Scene mainScene = new Scene(buildLayout(), 720, 900);

        primaryStage.setTitle("Coffee Machine");
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }
}
