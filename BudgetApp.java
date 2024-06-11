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

    private TableView<FinancialEntry> incomeTable;
    private TableView<FinancialEntry> expensesTable;
    private ObservableList<FinancialEntry> incomeData;
    private ObservableList<FinancialEntry> expenseData;

    private Label totalIncomeLabel;
    private Label totalExpensesLabel;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Budget Overview");

        incomeData = FXCollections.observableArrayList();
        expenseData = FXCollections.observableArrayList();

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        // Income Section
        Label incomeLabel = new Label("Incomes");
        incomeTable = createIncomeTable();
        grid.add(incomeLabel, 0, 0);
        grid.add(incomeTable, 0, 1);

        totalIncomeLabel = new Label("Total Income: $0");
        grid.add(totalIncomeLabel, 0, 2);

        // Expenses Section
        Label expensesLabel = new Label("Expenses");
        expensesTable = createExpensesTable();
        grid.add(expensesLabel, 0, 3);
        grid.add(expensesTable, 0, 4);

        totalExpensesLabel = new Label("Total Expenses: $0");
        grid.add(totalExpensesLabel, 0, 5);

        // Input Forms
        VBox inputForms = new VBox(20);
        inputForms.getChildren().addAll(createIncomeInputForm(), createExpenseInputForm());

        HBox layout = new HBox(20);
        layout.getChildren().addAll(grid, inputForms);

        Scene scene = new Scene(layout, 900, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private TableView<FinancialEntry> createIncomeTable() {
        TableView<FinancialEntry> table = new TableView<>();
        TableColumn<FinancialEntry, String> sourceColumn = new TableColumn<>("Source");
        sourceColumn.setCellValueFactory(new PropertyValueFactory<>("source"));
        TableColumn<FinancialEntry, String> amountColumn = new TableColumn<>("Monthly amount");
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        table.getColumns().add(sourceColumn);
        table.getColumns().add(amountColumn);

        table.setItems(incomeData);

        return table;
    }

    private TableView<FinancialEntry> createExpensesTable() {
        TableView<FinancialEntry> table = new TableView<>();
        TableColumn<FinancialEntry, String> expenseColumn = new TableColumn<>("Expense");
        expenseColumn.setCellValueFactory(new PropertyValueFactory<>("source"));
        TableColumn<FinancialEntry, String> amountColumn = new TableColumn<>("Monthly amount");
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
                updateTotalIncome();
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
                updateTotalExpenses();
                expenseField.clear();
                amountField.clear();
            }
        });

        VBox form = new VBox(10);
        form.getChildren().addAll(formLabel, expenseField, amountField, addButton);

        return form;
    }

    private void updateTotalIncome() {
        double total = incomeData.stream()
            .mapToDouble(entry -> Double.parseDouble(entry.getAmount().replace("$", "")))
            .sum();
        totalIncomeLabel.setText(String.format("Total Income: $%.2f", total));
    }

    private void updateTotalExpenses() {
        double total = expenseData.stream()
            .mapToDouble(entry -> Double.parseDouble(entry.getAmount().replace("$", "")))
            .sum();
        totalExpensesLabel.setText(String.format("Total Expenses: $%.2f", total));
    }

    public abstract static class FinancialEntry {
        private String source;
        private String amount;

        public FinancialEntry(String source, String amount) {
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

    public static class Income extends FinancialEntry {
        public Income(String source, String amount) {
            super(source, amount);
        }
    }

    public static class Expense extends FinancialEntry {
        public Expense(String source, String amount) {
            super(source, amount);
        }
    }
}
