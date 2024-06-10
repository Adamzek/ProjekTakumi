
package oop_project;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class BudgetApp extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    private TableView<Income> incomeTable;
    private TableView<Expense> expensesTable;
    private ObservableList<Income> incomeData;
    private ObservableList<Expense> expenseData;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Budget Overview");

        incomeData = FXCollections.observableArrayList(
            new Income("Contribution from parents", "$500"),
            new Income("Scholarships", "$400"),
            new Income("Income from part-time job", "$800"),
            new Income("Financial aid/student loans", "$800"),
            new Income("Other income", "$100")
        );

        expenseData = FXCollections.observableArrayList(
            new Expense("Rent/dorm room", "$750"),
            new Expense("Tuition & fees", "$800"),
            new Expense("Meal plan", "$375"),
            new Expense("Groceries", "$100"),
            new Expense("Dining out", "$100"),
            new Expense("Entertainment/drinks", "$100"),
            new Expense("Savings", "$75"),
            new Expense("Travel/transportation", "$75"),
            new Expense("Shopping/clothes", "$50"),
            new Expense("Miscellaneous", "$50"),
            new Expense("Utilities", "$50"),
            new Expense("Phone bill", "$50"),
            new Expense("Subscriptions", "$25")
        );

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        // Income Section
        Label incomeLabel = new Label("Incomes");
        incomeTable = createIncomeTable();
        grid.add(incomeLabel, 0, 0);
        grid.add(incomeTable, 0, 1);

        // Expenses Section
        Label expensesLabel = new Label("Expenses");
        expensesTable = createExpensesTable();
        grid.add(expensesLabel, 0, 2);
        grid.add(expensesTable, 0, 3);

        // Input Forms
        VBox inputForms = new VBox(20);
        inputForms.getChildren().addAll(createIncomeInputForm(), createExpenseInputForm());

        HBox layout = new HBox(20);
        layout.getChildren().addAll(grid, inputForms);

        Scene scene = new Scene(layout, 900, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private TableView<Income> createIncomeTable() {
        TableView<Income> table = new TableView<>();
        TableColumn<Income, String> sourceColumn = new TableColumn<>("Source");
        sourceColumn.setCellValueFactory(new PropertyValueFactory<>("source"));
        TableColumn<Income, String> amountColumn = new TableColumn<>("Monthly amount");
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        table.getColumns().add(sourceColumn);
        table.getColumns().add(amountColumn);

        table.setItems(incomeData);

        return table;
    }

    private TableView<Expense> createExpensesTable() {
        TableView<Expense> table = new TableView<>();
        TableColumn<Expense, String> expenseColumn = new TableColumn<>("Expense");
        expenseColumn.setCellValueFactory(new PropertyValueFactory<>("expense"));
        TableColumn<Expense, String> amountColumn = new TableColumn<>("Monthly amount");
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        table.getColumns().add(expenseColumn);
        table.getColumns().add(amountColumn);

        table.setItems(expenseData);

        return table;
    }

    private VBox createIncomeInputForm() {
        Label formLabel = new Label("Add New Income");

        TextField sourceField = new TextField();
        sourceField.setPromptText("Source");
        TextField amountField = new TextField();
        amountField.setPromptText("Monthly amount");

        Button addButton = new Button("Add Income");
        addButton.setOnAction(e -> {
            String source = sourceField.getText();
            String amount = amountField.getText();
            if (!source.isEmpty() && !amount.isEmpty()) {
                incomeData.add(new Income(source, amount));
                sourceField.clear();
                amountField.clear();
            }
        });

        VBox form = new VBox(10);
        form.getChildren().addAll(formLabel, sourceField, amountField, addButton);

        return form;
    }

    private VBox createExpenseInputForm() {
        Label formLabel = new Label("Add New Expense");

        TextField expenseField = new TextField();
        expenseField.setPromptText("Expense");
        TextField amountField = new TextField();
        amountField.setPromptText("Monthly amount");

        Button addButton = new Button("Add Expense");
        addButton.setOnAction(e -> {
            String expense = expenseField.getText();
            String amount = amountField.getText();
            if (!expense.isEmpty() && !amount.isEmpty()) {
                expenseData.add(new Expense(expense, amount));
                expenseField.clear();
                amountField.clear();
            }
        });

        VBox form = new VBox(10);
        form.getChildren().addAll(formLabel, expenseField, amountField, addButton);

        return form;
    }

    public static class Income {
        private String source;
        private String amount;

        public Income(String source, String amount) {
            this.source = source;
            this.amount = amount;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }
    }

    public static class Expense {
        private String expense;
        private String amount;

        public Expense(String expense, String amount) {
            this.expense = expense;
            this.amount = amount;
        }

        public String getExpense() {
            return expense;
        }

        public void setExpense(String expense) {
            this.expense = expense;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }
    }
}
