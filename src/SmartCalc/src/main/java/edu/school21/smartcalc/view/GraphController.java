package edu.school21.smartcalc.view;

import edu.school21.smartcalc.presenter.GraphPresenter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class GraphController {

    @FXML
    public Button graphButton;
    @FXML
    public TextField functionField;
    @FXML
    public TextField minXField;
    @FXML
    public TextField maxXField;
    @FXML
    public TextField minYField;
    @FXML
    public TextField maxYField;
    @FXML
    public Label errorField;
    @FXML
    public LineChart<Number, Number> graphArea;
    @FXML
    public NumberAxis xAxis;
    @FXML
    public NumberAxis yAxis;
    @FXML
    public Button clearGraphButton;

    private GraphPresenter graphPresenter;

    @FXML
    public void initialize() {
        graphPresenter = new GraphPresenter();
    }

    @FXML
    private void handleGraphButton(ActionEvent event) {
        String errorMessage = graphPresenter.validateAndPrepare(
                functionField.getText(),
                minXField.getText(),
                maxXField.getText(),
                minYField.getText(),
                maxYField.getText()
        );

        errorField.setText(errorMessage);

        if (errorMessage.isEmpty()) {
            setYAxisBounds();

            XYChart.Series<Number, Number> series = graphPresenter
                    .calculateGraph(functionField.getText());
            if (!series.getData().isEmpty()) {
                graphArea.getData().add(series);
            }
        }
    }

    private void setYAxisBounds() {
        try {
            double minY = Double.parseDouble(minYField.getText());
            double maxY = Double.parseDouble(maxYField.getText());

            yAxis.setAutoRanging(false);
            yAxis.setLowerBound(minY);
            yAxis.setUpperBound(maxY);
        } catch (NumberFormatException e) {
            errorField.setText("Invalid Y bounds");
        }
    }

    @FXML
    private void handleClearGraphButton(ActionEvent event) {
        graphArea.getData().clear();
    }
}
