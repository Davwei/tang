package com.Davi.poems.gui;/**
 * Created by wei on 2016/12/26.
 */

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.BoxBlur;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.stage.Stage;
import javafx.util.Duration;

import static java.lang.Math.random;

public class Tang extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Group root = new Group();
        Scene scene = new Scene(root,800,600,Color.BLACK);

        Rectangle colors = new Rectangle(scene.getWidth(),scene.getHeight(),new LinearGradient(0f,1f,1f,0f,true, CycleMethod.NO_CYCLE,new Stop[]{
                new Stop(0, Color.web("#f8bd55")), new Stop(0.14, Color.web("#c0fe56")), new Stop(0.28, Color.web("#5dfbc1")), new Stop(0.43, Color.web("#64c2f8")), new Stop(0.57, Color.web("#be4af7")), new Stop(0.71, Color.web("#ed5fc2")), new Stop(0.85, Color.web("#ef504c")), new Stop(1, Color.web("#f2660f"))
        }));
        Group circles = new Group();
        for (int i = 0; i < 20; i++) {
            Circle c = new Circle(150, Color.web("white",0.05));
            c.setStrokeType(StrokeType.OUTSIDE);
            c.setStroke(Color.web("white",0.16));
            c.setStrokeWidth(4);
            circles.getChildren().add(c);
        }
        circles.setEffect(new BoxBlur(10,10,1));

        Group blendModeGroup = new Group(new Group(new Rectangle(scene.getWidth(), scene.getHeight(), Color.BLACK), circles), colors);
        colors.setBlendMode(BlendMode.OVERLAY);

        root.getChildren().add(blendModeGroup);
//      root.getChildren().add(colors);
//      root.getChildren().add(circles);
        Timeline timeline = new Timeline();
        for (Node circle:circles.getChildren()
             ) {
            timeline.getKeyFrames().addAll(
                    new KeyFrame(Duration.ZERO,
                        new KeyValue(circle.translateXProperty(),random() * 800),
                        new KeyValue(circle.translateYProperty(),random() * 600)
                    ),
                    new KeyFrame(new Duration(40000),
                            new KeyValue(circle.translateXProperty(),random() * 800),
                            new KeyValue(circle.translateYProperty(),random() * 600)
                    )
            );
        }
        timeline.play();
        primaryStage.setTitle("Tang");
        primaryStage.setScene(scene);
        primaryStage.show();
        
    }

}
