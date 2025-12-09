package com.brh.wetterapp_klausurversion;

import com.brh.wetterapp_klausurversion.controller.MainController;
import com.brh.wetterapp_klausurversion.view.StageManager;
import com.brh.wetterapp_klausurversion.view.StageType;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

    private static MainController mainController;

    /**
     * Startmethode zum Bezug des Stages zu Beginn
     * @param stage MainStage
     */
    @Override
    public void start(Stage stage){
        StageManager.getInstance().setMainStage(stage);
        StageManager.getInstance().setScene( StageType.Main, 100, 700 );


    }

    /**
     * Setzt den MainController
     * @param mainController MainController
     */
    public static void setMainController( MainController mainController ){
        App.mainController = mainController;
    }

    /**
     * Statische Methode zum Bezug des MainControllers
     * von Überall im Programm
     * @return aktuelle Instanz von MainController
     */
    public static MainController getMainController(){
        return mainController;
    }

    public static void main(String[] args) {
        launch();
    }
}