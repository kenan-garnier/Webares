package com.webares.webares;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.media.AudioClip;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.IOException;
import java.util.Objects;

public class MainIHM extends Application {
    private AudioClip exit;

    @Override
    public void start(Stage stage) throws IOException {
        exit = new AudioClip(Objects.requireNonNull(getClass().getResource("/com/webares/webares/sons/EXIT.wav")).toString());
        FXMLLoader fxmlLoader = new FXMLLoader(MainIHM.class.getResource("ihm.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("WebAreas");
        stage.setResizable(false);
        Screen screen = Screen.getPrimary();
        stage.setWidth(screen.getVisualBounds().getWidth());
        stage.setHeight(screen.getVisualBounds().getHeight());
        stage.setScene(scene);
        stage.setFullScreenExitHint("");
        stage.setFullScreen(true);
        stage.show();
        init_event(stage,scene,fxmlLoader);
        ((Controller) fxmlLoader.getController()).starter();
    }

    private void init_event(Stage stage, Scene scene, FXMLLoader fxmlLoader) {
        Timeline timeline;
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            if (!stage.isFocused()) {
                stage.toFront();
            }
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
        stage.setOnCloseRequest(event -> {
            exit.play();
            try {
                Thread.sleep(1300);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.exit(0);
        });
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.F11) {
                stage.setFullScreen(!stage.isFullScreen());
            }
        });
        stage.fullScreenProperty().addListener((obs, wasFullScreen, isNowFullScreen) -> {
            if (stage.isFullScreen()) {
                    ((Controller) fxmlLoader.getController()).cl0.setLayoutY(((Controller) fxmlLoader.getController()).cl0.getLayoutY() + 34);
                    ((Controller) fxmlLoader.getController()).cl1.setLayoutY(((Controller) fxmlLoader.getController()).cl1.getLayoutY() + 34);
                    ((Controller) fxmlLoader.getController()).cl2.setLayoutY(((Controller) fxmlLoader.getController()).cl2.getLayoutY() + 34);
                    ((Controller) fxmlLoader.getController()).cl3.setLayoutY(((Controller) fxmlLoader.getController()).cl3.getLayoutY() + 34);
                    ((Controller) fxmlLoader.getController()).cl00.setLayoutY(((Controller) fxmlLoader.getController()).cl00.getLayoutY() + 34);
                    ((Controller) fxmlLoader.getController()).cl11.setLayoutY(((Controller) fxmlLoader.getController()).cl11.getLayoutY() + 34);
                    ((Controller) fxmlLoader.getController()).cl22.setLayoutY(((Controller) fxmlLoader.getController()).cl22.getLayoutY() + 34);
                    ((Controller) fxmlLoader.getController()).cl33.setLayoutY(((Controller) fxmlLoader.getController()).cl33.getLayoutY() + 34);
                    ((Controller) fxmlLoader.getController()).stl1.setLayoutY(((Controller) fxmlLoader.getController()).stl1.getLayoutY() + 34);
                    ((Controller) fxmlLoader.getController()).stl11.setLayoutY(((Controller) fxmlLoader.getController()).stl11.getLayoutY() + 34);
                    ((Controller) fxmlLoader.getController()).stl2.setLayoutY(((Controller) fxmlLoader.getController()).stl2.getLayoutY() + 34);
                    ((Controller) fxmlLoader.getController()).stl22.setLayoutY(((Controller) fxmlLoader.getController()).stl22.getLayoutY() + 34);
                    ((Controller) fxmlLoader.getController()).stl3.setLayoutY(((Controller) fxmlLoader.getController()).stl3.getLayoutY() + 34);
                    ((Controller) fxmlLoader.getController()).stl33.setLayoutY(((Controller) fxmlLoader.getController()).stl33.getLayoutY() + 34);
            } else {
                ((Controller) fxmlLoader.getController()).cl0.setLayoutY(((Controller) fxmlLoader.getController()).cl0.getLayoutY() - 34);
                ((Controller) fxmlLoader.getController()).cl1.setLayoutY(((Controller) fxmlLoader.getController()).cl1.getLayoutY() - 34);
                ((Controller) fxmlLoader.getController()).cl2.setLayoutY(((Controller) fxmlLoader.getController()).cl2.getLayoutY() - 34);
                ((Controller) fxmlLoader.getController()).cl3.setLayoutY(((Controller) fxmlLoader.getController()).cl3.getLayoutY() - 34);
                ((Controller) fxmlLoader.getController()).cl00.setLayoutY(((Controller) fxmlLoader.getController()).cl00.getLayoutY() - 34);
                ((Controller) fxmlLoader.getController()).cl11.setLayoutY(((Controller) fxmlLoader.getController()).cl11.getLayoutY() - 34);
                ((Controller) fxmlLoader.getController()).cl22.setLayoutY(((Controller) fxmlLoader.getController()).cl22.getLayoutY() - 34);
                ((Controller) fxmlLoader.getController()).cl33.setLayoutY(((Controller) fxmlLoader.getController()).cl33.getLayoutY() - 34);
                ((Controller) fxmlLoader.getController()).stl1.setLayoutY(((Controller) fxmlLoader.getController()).stl1.getLayoutY() - 34);
                ((Controller) fxmlLoader.getController()).stl11.setLayoutY(((Controller) fxmlLoader.getController()).stl11.getLayoutY() - 34);
                ((Controller) fxmlLoader.getController()).stl2.setLayoutY(((Controller) fxmlLoader.getController()).stl2.getLayoutY() - 34);
                ((Controller) fxmlLoader.getController()).stl22.setLayoutY(((Controller) fxmlLoader.getController()).stl22.getLayoutY() - 34);
                ((Controller) fxmlLoader.getController()).stl3.setLayoutY(((Controller) fxmlLoader.getController()).stl3.getLayoutY() - 34);
                ((Controller) fxmlLoader.getController()).stl33.setLayoutY(((Controller) fxmlLoader.getController()).stl33.getLayoutY() - 34);
            }
        });
    }
    public static void main(String[] args) {
        launch();
    }
}