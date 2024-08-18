package edu.school21.smartcalc.app;

import edu.school21.smartcalc.model.CalculationException;
import edu.school21.smartcalc.model.CalculatorJNI;
import edu.school21.smartcalc.model.CalculatorModel;
import edu.school21.smartcalc.model.CreditModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

import static javafx.application.Application.launch;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/edu/school21/smartcalc/view/calculator-view.fxml"));

        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("SmartCalc!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}