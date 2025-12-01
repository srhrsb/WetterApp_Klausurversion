package com.brh.wetterapp_klausurversion;

import com.brh.wetterapp_klausurversion.controller.MainController;
import com.brh.wetterapp_klausurversion.view.StageManager;
import com.brh.wetterapp_klausurversion.view.StageType;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

    private static MainController mainController;

    @Override
    public void start(Stage stage){
        StageManager.getInstance().setMainStage(stage);
        StageManager.getInstance().setScene( StageType.Main, 100, 700 );
    }

    public static void setMainController( MainController mainController ){
        App.mainController = mainController;
    }

    public static MainController getMainController(){
        return mainController;
    }

    public static void main(String[] args) {
        launch();
    }
}