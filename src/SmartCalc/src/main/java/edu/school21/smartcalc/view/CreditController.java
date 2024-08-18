package edu.school21.smartcalc.view;

import edu.school21.smartcalc.presenter.CreditPresenter;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class CreditController {

    @FXML
    public TextField sumField;
    @FXML
    public TextField termField;
    @FXML
    public TextField rateField;
    @FXML
    public Label monthlyPaymentLabel;
    @FXML
    public Label overpaymentLabel;
    @FXML
    public Label fullSumLabel;
    @FXML
    public ComboBox<String> paymentTypeComboBox;
    @FXML
    public Button calculateButton;
    @FXML
    public Label errorLabel;

    private CreditPresenter creditPresenter;

    @FXML
    public void initialize() {
        creditPresenter = new CreditPresenter();
        sumField.setText("100000");
        termField.setText("12");
        rateField.setText("20");
        paymentTypeComboBox.getItems().addAll("Annuity", "Differentiated");
        paymentTypeComboBox.getSelectionModel().select("Annuity");
    }

    @FXML
    public void handleCalculateButton() {
        errorLabel.setText(
                creditPresenter.validate(sumField.getText(),
                        termField.getText(),
                        rateField.getText(),
                        paymentTypeComboBox.getValue()));
        creditPresenter.calculate();
        monthlyPaymentLabel.setText(creditPresenter.getMonthlyPayments());
        overpaymentLabel.setText(creditPresenter.getOverpayment());
        fullSumLabel.setText(creditPresenter.getTotalPayment());
    }
}
