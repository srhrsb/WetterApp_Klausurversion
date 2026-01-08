package com.brh.wetterapp_klausurversion.view;

import com.brh.wetterapp_klausurversion.App;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

/**
 * Singleton Klasse zur Verwaltung der 3 Stages (Main, Option, Chart)
 */
public class StageManager {

    private static StageManager instance;
    private Stage[] stages = new Stage[4];
    private final String[] SCENE_NAMES = {"main-view.fxml", "option-view.fxml", "chart-view.fxml", "info-view.fxml"  };

    private StageManager(){}

    public static StageManager getInstance(){
        if(instance == null){
            instance = new StageManager();
        }

        return instance;
    }

    /**
     * Setzt den MainStage und sorgt dafür, dass das Programm endet, falls
     * die MainStage beendet wird
     * @param stage Stage
     */
    public void setMainStage( Stage stage ){
        stages[0] = stage;
        stages[0].setOnCloseRequest(
                (ev) -> Platform.exit()
        );
    }

    /**
     * JeweiligeScene wird geladen und ein Stage wird erzeugt mit der gegebenen
     * Breite und Höhe
     * @param stageType Enum Stage Type
     * @param width Breite
     * @param height Höhe
     */
    public void setScene(StageType stageType, int width, int height){

        if(stages[stageType.ordinal()] == null){
            stages[stageType.ordinal()] = new Stage();


            stages[stageType.ordinal()].setOnCloseRequest(
                    (ev) ->  stages[stageType.ordinal()] = null
            );
        }

        //Scene holen
        Scene scene = getScene(SCENE_NAMES[stageType.ordinal()], width, height);

        if(stageType == StageType.Main) {
            scene.setFill(Color.TRANSPARENT);
            stages[stageType.ordinal()].initStyle(StageStyle.TRANSPARENT);
            stages[stageType.ordinal()].setAlwaysOnTop(true);
        }

        stages[stageType.ordinal()].setTitle("Wetterapp Klausurversion");
        stages[stageType.ordinal()].setScene(scene);
        stages[stageType.ordinal()].show();
    }

    /**
     * Lädt die Scene mit gegebenem Namen und gibt diese zurück
     * @param viewName Name der Scene
     * @param width Breite
     * @param height Höhe
     * @return geladene Scene
     */
    private Scene getScene(String viewName, int width, int height){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource( viewName ));
            return new Scene(fxmlLoader.load(), width, height);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Holt den jeweiligen Stage unter Angabe des StageTypes
     * @param type
     * @return Stage
     */
    public Stage getStageByStageType( StageType type ){
        return stages[ type.ordinal() ];
    }

    /**
     * Schließt und löscht den jeweilgen Stage unter Angabe des StageTypes
     * @param type
     */
    public void closeStageByStageType( StageType type ){
        stages[ type.ordinal() ].close();
        stages[ type.ordinal()] = null;
    }
}
