package edu.school21.smartcalc.view;

import edu.school21.smartcalc.presenter.CalculatorPresenter;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.net.URL;

public class CalculatorController {

    @FXML
    public Button tanButton;
    @FXML
    public Button sevenButton;
    @FXML
    public Button lnButton;
    @FXML
    public Button sqrtButton;
    @FXML
    public Button xButton;
    @FXML
    public Button asinButton;
    @FXML
    public Button acosButton;
    @FXML
    public Button atanButton;
    @FXML
    public Button logButton;
    @FXML
    public Button powerButton;
    @FXML
    public Button modButton;
    @FXML
    public Button fourButton;
    @FXML
    public Button oneButton;
    @FXML
    public Button zeroButton;
    @FXML
    public Button leftBracketButton;
    @FXML
    public Button eightButton;
    @FXML
    public Button fiveButton;
    @FXML
    public Button twoButton;
    @FXML
    public Button dotButton;
    @FXML
    public Button rightBracketButton;
    @FXML
    public Button nineButton;
    @FXML
    public Button sixButton;
    @FXML
    public Button threeButton;
    @FXML
    public Button equalButton;
    @FXML
    public Button mulButton;
    @FXML
    public Button divButton;
    @FXML
    public Button minusButton;
    @FXML
    public Button plusButton;
    @FXML
    public Button delButton;
    @FXML
    public Button clearInputButton;
    @FXML
    public Button setXButton;
    @FXML
    public Label xIsField;
    @FXML
    public Button clearJournalButton;
    @FXML
    public Label outputField;
    @FXML
    public TextArea journalTextArea;
    @FXML
    private TextField setXField;
    @FXML
    public Button graphButton;
    @FXML
    public Button creditButton;
    @FXML
    public Button helpButton;
    @FXML
    public Button sinButton;
    @FXML
    public Button cosButton;
    @FXML
    private TextArea inputField;

    private CalculatorPresenter calculatorPresenter;

    @FXML
    public void initialize() {
        calculatorPresenter = new CalculatorPresenter();
        String history = calculatorPresenter.getOperations();
        if (history.isEmpty()) {
            journalTextArea.setText("There's no record in the history");
        } else {
            journalTextArea.setText(history);
        }
    }


    @FXML
    private void handleSinButton(ActionEvent event) {
        inputField.appendText("sin(");
    }

    @FXML
    private void handleCosButton(ActionEvent event) {
        inputField.appendText("cos(");
    }

    @FXML
    private void handleTanButton(ActionEvent event) {
        inputField.appendText("tan(");
    }

    @FXML
    private void handleAsinButton(ActionEvent event) {
        inputField.appendText("asin(");
    }

    @FXML
    private void handleAcosButton(ActionEvent event) {
        inputField.appendText("acos(");
    }

    @FXML
    private void handleAtanButton(ActionEvent event) {
        inputField.appendText("atan(");
    }

    @FXML
    private void handleLnButton(ActionEvent event) {
        inputField.appendText("ln(");
    }

    @FXML
    private void handleLogButton(ActionEvent event) {
        inputField.appendText("log(");
    }

    @FXML
    private void handleSqrtButton(ActionEvent event) {
        inputField.appendText("sqrt(");
    }

    @FXML
    private void handlePowerButton(ActionEvent event) {
        inputField.appendText("^");
    }

    @FXML
    private void handleXButton(ActionEvent event) {
        inputField.appendText("x");
    }


    @FXML
    private void handleModButton(ActionEvent event) {
        inputField.appendText("mod");
    }

    @FXML
    private void handleLeftBracketButton(ActionEvent event) {
        inputField.appendText("(");
    }

    @FXML
    private void handleRightBracketButton(ActionEvent event) {
        inputField.appendText(")");
    }

    @FXML
    private void handleZeroButton(ActionEvent event) {
        inputField.appendText("0");
    }

    @FXML
    private void handleOneButton(ActionEvent event) {
        inputField.appendText("1");
    }

    @FXML
    private void handleTwoButton(ActionEvent event) {
        inputField.appendText("2");
    }

    @FXML
    private void handleThreeButton(ActionEvent event) {
        inputField.appendText("3");
    }

    @FXML
    private void handleFourButton(ActionEvent event) {
        inputField.appendText("4");
    }

    @FXML
    private void handleFiveButton(ActionEvent event) {
        inputField.appendText("5");
    }

    @FXML
    private void handleSixButton(ActionEvent event) {
        inputField.appendText("6");
    }

    @FXML
    private void handleSevenButton(ActionEvent event) {
        inputField.appendText("7");
    }

    @FXML
    private void handleEightButton(ActionEvent event) {
        inputField.appendText("8");
    }

    @FXML
    private void handleNineButton(ActionEvent event) {
        inputField.appendText("9");
    }

    @FXML
    private void handleDotButton(ActionEvent event) {
        inputField.appendText(".");
    }

    @FXML
    private void handleMulButton(ActionEvent event) {
        inputField.appendText("*");
    }

    @FXML
    private void handleDivButton(ActionEvent event) {
        inputField.appendText("/");
    }

    @FXML
    private void handlePlusButton() {
        inputField.appendText("+");
    }

    @FXML
    private void handleMinusButton(ActionEvent event) {
        inputField.appendText("-");
    }

    @FXML
    private void handleDelButton(ActionEvent event) {
        String currentText = inputField.getText();

        if (currentText != null && !currentText.isEmpty()) {
            inputField.deleteText(currentText.length() - 1, currentText.length());
        }
    }

    @FXML
    private void handleClearInputButton(ActionEvent event) {
        String currentText = inputField.getText();

        if (currentText != null && !currentText.isEmpty()) {
            inputField.deleteText(0, currentText.length());
        }
    }

    @FXML
    private void handleEqualButton(ActionEvent event) {
        String result = calculatorPresenter.setX(setXField.getText());
        outputField.setText("");
        if (result.equals("Invalid x")) {
            outputField.setText(result);
        }
        calculatorPresenter.calculate(inputField.getText());
        String resultStr = calculatorPresenter.getResultStr();
        outputField.setText(resultStr);
        journalTextArea.setText(calculatorPresenter.getOperations());
    }

    @FXML
    private void handleGraphButton(ActionEvent event) throws Exception {
        URL fxmlLocation = getClass().getResource("/edu/school21/smartcalc/view/graph-view.fxml");
        assert fxmlLocation != null;
        Parent root = FXMLLoader.load(fxmlLocation);
        Stage graphStage = new Stage();
        graphStage.setScene(new Scene(root));
        graphStage.setTitle("Graph");
        graphStage.centerOnScreen();
        graphStage.show();
    }

    @FXML
    private void handleCreditButton(ActionEvent event) throws Exception {
        URL fxmlLocation = getClass().getResource("/edu/school21/smartcalc/view/credit-view.fxml");
        assert fxmlLocation != null;
        Parent root = FXMLLoader.load(fxmlLocation);
        Stage creditStage = new Stage();
        creditStage.setScene(new Scene(root));
        creditStage.setTitle("Credit");
        creditStage.centerOnScreen();
        creditStage.show();
    }

    @FXML
    private void handleHelpButton(ActionEvent event) throws Exception {
        URL fxmlLocation = getClass().getResource("/edu/school21/smartcalc/view/help-view.fxml");
        assert fxmlLocation != null;
        Parent root = FXMLLoader.load(fxmlLocation);
        Stage creditStage = new Stage();
        creditStage.setScene(new Scene(root));
        creditStage.setTitle("Help");
        creditStage.centerOnScreen();
        creditStage.show();
    }

    @FXML
    private void handleClearJournalButton(ActionEvent event) {
        journalTextArea.setText("There's no record in the history");
        calculatorPresenter.clearHistory();
    }
}
