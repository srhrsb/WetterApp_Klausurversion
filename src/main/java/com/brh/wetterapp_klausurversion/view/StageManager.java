package com.brh.wetterapp_klausurversion.view;

import com.brh.wetterapp_klausurversion.App;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class StageManager {

    private static StageManager instance;
    private Stage[] stages = new Stage[3];
    private final String[] SCENE_NAMES = {"main-view.fxml", "option-view.fxml", "chart-view.fxml" };


    private StageManager(){}

    public static StageManager getInstance(){
        if(instance == null){
            instance = new StageManager();
        }

        return instance;
    }

    public void setMainStage( Stage stage ){
        stages[0] = stage;
        stages[0].setOnCloseRequest(
                (ev) -> Platform.exit()
        );
    }

    public void setScene(StageType stageType, int width, int height){

        if(stages[stageType.ordinal()] == null){
            stages[stageType.ordinal()] = new Stage();
            stages[stageType.ordinal()].setOnCloseRequest(
                    (ev) ->  stages[stageType.ordinal()] = null
            );
        }

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

    private Scene getScene(String viewName, int width, int height){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource( viewName ));
            return new Scene(fxmlLoader.load(), width, height);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Stage getStageByStageType( StageType type ){
        return stages[ type.ordinal() ];
    }

    public void closeStageByStageType( StageType type ){
        stages[ type.ordinal() ].close();
        stages[ type.ordinal()] = null;
    }
}
