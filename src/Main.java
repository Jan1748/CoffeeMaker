import Drinks.*;
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
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.util.LinkedHashMap;

public class Main extends Application {
    private Machine machine = new Machine();

    private Stage pStage;

    private StringProperty priceDisplayState = new SimpleStringProperty("0 €");
    private StringProperty stateDisplayState = new SimpleStringProperty("Beep Boop");

    private BooleanProperty greenLampState = new SimpleBooleanProperty(false);
    private BooleanProperty orangeLampState = new SimpleBooleanProperty(false);
    private BooleanProperty machineReadyState = new SimpleBooleanProperty(true);
    private BooleanProperty makingCoffeeState = new SimpleBooleanProperty(false);

    private void playSound(String fileName) {
        Media sound = new Media(getClass().getResource("resources/" + fileName).toExternalForm());
        MediaPlayer player = new MediaPlayer(sound);
        player.play();
    }

    private void setStateDisplayFor(String text, long time) {
        String original = stateDisplayState.get();
        stateDisplayState.set(text);
        Task<Void> sleeper = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    Thread.sleep(time);
                } catch (InterruptedException e) {}
                return null;
            }
        };
        sleeper.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                stateDisplayState.set(original);
            }
        });
        new Thread(sleeper).start();
    }

    private void updateState() {
        float inputValue = machine.getCashBox().getInput().getTotalValue(machine.getCashBox().getCoinValues());

        if (!machine.getCashBox().hasSufficientChange(machine.getComposition())) {
            stateDisplayState.set("--- Out of Change ---");
            orangeLampState.set(true);
            greenLampState.set(false);
            machineReadyState.set(false);
        } else if (!machine.getStock().canConsume(machine.getComposition())) {
            stateDisplayState.set("--- Out of Stock ---");
            orangeLampState.set(true);
            greenLampState.set(false);
            machineReadyState.set(false);
        } else if (!machine.getCashBox().hasSufficientInput(machine.getComposition())) {
            stateDisplayState.set("Insert Money: " + inputValue + " / " + machine.getComposition().getPrice() + "€");
            orangeLampState.set(false);
            greenLampState.set(false);
            machineReadyState.set(false);
        } else {
            stateDisplayState.set("Press \"Start\" to get your coffee");
            orangeLampState.set(false);
            greenLampState.set(true);
            machineReadyState.set(true);
        }

        priceDisplayState.set(machine.getComposition().getPrice() + " €");
    }

    public void handleFullScreenToggle(ObservableValue obs, boolean oldValue, boolean newValue) {
        pStage.setFullScreen(newValue);
    }

    public void handleStartButtonClicked(MouseEvent t) {
        machine.getStock().consume(machine.getComposition());
        CoinContainer returned = machine.getCashBox().process(machine.getComposition());
        updateState();

        stateDisplayState.set("Making your coffee ...");
        makingCoffeeState.set(true);
        playSound("coffee.mp3");

        Task<Void> sleeper = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {}
                return null;
            }
        };
        sleeper.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                float returnValue = returned.getTotalValue(machine.getCashBox().getCoinValues());
                updateState();
                makingCoffeeState.set(false);
                if (returnValue > 0) {
                    playSound("return.mp3");
                    setStateDisplayFor("Returning " + returnValue + "€ to you.", 2000);
                }
            }
        });
        new Thread(sleeper).start();
    }

    public void handleCancelButtonClicked(MouseEvent t) {
        CoinContainer returned = machine.getCashBox().returnInput();
        float value = returned.getTotalValue(machine.getCashBox().getCoinValues());
        updateState();
        if (value > 0) {
            setStateDisplayFor("Returning " + value + "€ to you.", 2000);
            playSound("return.mp3");
        }
    }

    public void handleDrinkChanged(ObservableValue obs, String oldValue, String newValue) {
        Drink newDrink = machine.getAssortment().getDrinkByName(newValue);
        machine.getComposition().setDrink(newDrink);
        updateState();
    }

    public void handleSizeChanged(ObservableValue obs, String oldValue, String newValue) {
        DrinkSize size = DrinkSize.valueOf(newValue.toUpperCase());
        machine.getComposition().setDrinkSize(size);
        updateState();
    }

    public void handleSugarChanged(ObservableValue obs, Integer oldValue, Integer newValue) {
        machine.getComposition().setExtraSugar(newValue);
        updateState();
    }

    public void handleMilkChanged(ObservableValue obs, Integer oldValue, Integer newValue) {
        machine.getComposition().setExtraMilk(newValue);
        updateState();
    }

    public void handleCoinInserted(CoinType coin) {
        machine.getCashBox().getInput().addCoins(coin, 1);
        playSound("insert.mp3");
        updateState();
    }

    public static void main(String[] args) {
        launch();
    }

    public void initMachine() {
        Drink[] drinks = new Drink[] {
                new Coffee(),
                new Espresso(),
                new Cappuccino(),
                new LatteMacchiato(),
                new MilkCoffee(),
                new IceCoffee(),
                new Gunfire(),
                new CoffeeCorretto()
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
        Menu fileMenu = new Menu("File");
        CheckMenuItem fullScreenToggle = new CheckMenuItem("Fullscreen");
        fullScreenToggle.selectedProperty().addListener(this::handleFullScreenToggle);
        fileMenu.getItems().add(fullScreenToggle);


        Menu adminMenu = new Menu("Admin");
        MenuItem refillStock = new MenuItem("Refill Stock");
        refillStock.setOnAction(e -> {
            machine.getStock().refill(1000, 1000, 100);
            setStateDisplayFor("Refilled Stock successfully!", 2000);
        });

        MenuItem refillChange = new MenuItem("Refill Change");
        refillChange.setOnAction(e -> {
            CoinContainer change = machine.getCashBox().getChange();
            change.addCoins(CoinType.TWOEURO, 10);
            change.addCoins(CoinType.ONEEURO, 10);
            change.addCoins(CoinType.FIFTYCENT, 10);
            change.addCoins(CoinType.TWENTYCENT, 10);
            change.addCoins(CoinType.TENCENT, 10);
            setStateDisplayFor("Refilled Change successfully!", 2000);
        });

        adminMenu.getItems().addAll(refillStock, refillChange);

        MenuBar menuBar = new MenuBar(fileMenu, adminMenu);
        root.setTop(menuBar);

        // --- Center Part ---
        GridPane displayGrid = new GridPane();
        displayGrid.setHgap(10);
        displayGrid.setVgap(25);
        displayGrid.setPadding(new Insets(25));
        displayGrid.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(25), new BorderWidths(2), Insets.EMPTY)));
        ColumnConstraints colCon = new ColumnConstraints();

        // 10 column layout
        colCon.setPercentWidth(10);
        displayGrid.getColumnConstraints().addAll(colCon, colCon, colCon, colCon, colCon, colCon, colCon, colCon, colCon, colCon);

        Circle greenLamp = new Circle(25);
        greenLamp.setFill(Color.DARKGREEN);
        displayGrid.add(greenLamp, 0, 0, 2, 1);
        this.greenLampState.addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                greenLamp.setFill(Color.LIGHTGREEN);
            } else {
                greenLamp.setFill(Color.DARKGREEN);
            }
        });

        Circle orangeLamp = new Circle(25);
        orangeLamp.setFill(Color.DARKORANGE);
        displayGrid.add(orangeLamp, 2, 0, 2, 1);
        this.orangeLampState.addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                orangeLamp.setFill(Color.YELLOW);
            } else {
                orangeLamp.setFill(Color.DARKORANGE);
            }
        });

        Label priceDisplay = new Label();
        priceDisplay.textProperty().bind(this.priceDisplayState);
        priceDisplay.setFont(new Font(30));
        priceDisplay.setPadding(new Insets(10));
        priceDisplay.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        priceDisplay.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        displayGrid.add(priceDisplay, 7, 0, 3, 1);

        VBox terminal = new VBox();
        terminal.setPadding(new Insets(25));
        terminal.setAlignment(Pos.CENTER);
        terminal.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        terminal.setPrefWidth(Double.MAX_VALUE);
        terminal.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        terminal.setSpacing(25);

        Label stateDisplay = new Label();
        stateDisplay.setWrapText(true);
        stateDisplay.textProperty().bind(this.stateDisplayState);
        stateDisplay.setFont(new Font(20));
        stateDisplay.setTextFill(Color.WHITE);
        stateDisplay.setTextAlignment(TextAlignment.JUSTIFY);
        stateDisplay.setPadding(new Insets(5));
        stateDisplay.prefWidthProperty().bind(terminal.widthProperty().multiply(0.8));
        stateDisplay.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));

        Label terminalTitle = new Label("Terminal");
        terminalTitle.setFont(new Font(26));

        terminal.getChildren().addAll(terminalTitle, stateDisplay);
        displayGrid.add(terminal, 1, 1, 8, 1);

        // Coffee Visualization
        VBox visualization = new VBox();
        visualization.setSpacing(15);

        VBox topPart = new VBox();
        topPart.setAlignment(Pos.CENTER);

        Rectangle rec1 = new Rectangle();
        rec1.widthProperty().bind(visualization.widthProperty().multiply(0.6));
        rec1.setHeight(75);
        rec1.setFill(Color.GRAY);
        topPart.getChildren().add(rec1);

        HBox rectContainer = new HBox();
        rectContainer.setAlignment(Pos.CENTER);
        rectContainer.setSpacing(40);

        Rectangle rec2 = new Rectangle();
        rec2.setFill(Color.GREY);
        rec2.setWidth(20);
        rec2.setHeight(40);
        rectContainer.getChildren().add(rec2);

        Rectangle rec3 = new Rectangle();
        rec3.setFill(Color.GREY);
        rec3.setWidth(20);
        rec3.setHeight(40);
        rectContainer.getChildren().add(rec3);

        topPart.getChildren().add(rectContainer);

        visualization.getChildren().add(topPart);

        HBox centerPart = new HBox();
        centerPart.setAlignment(Pos.CENTER);
        centerPart.setSpacing(50);

        Rectangle rec4 = new Rectangle();
        rec4.setFill(Color.TRANSPARENT);
        rec4.setWidth(10);
        rec4.setHeight(120);
        centerPart.getChildren().add(rec4);

        Rectangle rec5 = new Rectangle();
        rec5.setFill(Color.TRANSPARENT);
        rec5.setWidth(10);
        rec5.setHeight(120);
        centerPart.getChildren().add(rec5);

        makingCoffeeState.addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                rec4.setFill(Color.SADDLEBROWN);
                rec5.setFill(Color.SADDLEBROWN);
            } else {
                rec4.setFill(Color.TRANSPARENT);
                rec5.setFill(Color.TRANSPARENT);
            }
        });

        visualization.getChildren().add(centerPart);

        VBox bottomPart = new VBox();
        bottomPart.setAlignment(Pos.CENTER);

        ImageView coffeeCup = new ImageView(getClass().getResource("./resources/coffee.png").toExternalForm());
        coffeeCup.fitWidthProperty().bind(visualization.widthProperty().multiply(0.7));
        coffeeCup.setPreserveRatio(true);
        bottomPart.getChildren().add(coffeeCup);

        Rectangle rec6 = new Rectangle();
        rec6.widthProperty().bind(visualization.widthProperty().multiply(0.8));
        rec6.setHeight(75);
        rec6.setFill(Color.GRAY);
        bottomPart.getChildren().add(rec6);

        visualization.getChildren().add(bottomPart);

        displayGrid.add(visualization, 1, 2, 8, 1);

        StackPane coffeeDisplay = new StackPane(displayGrid);
        coffeeDisplay.prefWidthProperty().bind(root.widthProperty().multiply(0.7));
        coffeeDisplay.setPadding(new Insets(20));
        root.setCenter(coffeeDisplay);

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
        startButton.disableProperty().bind(machineReadyState.not());
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
        this.pStage = primaryStage;
        initMachine();

        Scene mainScene = new Scene(buildLayout(), 900, 1080);

        primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        primaryStage.setFullScreenExitHint("Use the \"File\" menu to exit the fullscreen.");
        primaryStage.setResizable(true);

        primaryStage.setTitle("Coffee Machine");
        primaryStage.setScene(mainScene);
        primaryStage.show();

        updateState();
    }
}
