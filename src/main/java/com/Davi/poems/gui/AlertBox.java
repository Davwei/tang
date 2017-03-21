package com.Davi.poems.gui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Created by David on 2017/3/17.
 */
public class AlertBox {
    public AlertBox(){

    }
    public void display(String title,String author,String message){
        Stage windows = new Stage();
        windows.setTitle(author+" : "+title);
        windows.initModality(Modality.APPLICATION_MODAL);
        windows.setMinWidth(300);
        windows.setMinHeight(200);
        Button button = new Button("关闭");
        button.setOnAction(e -> windows.close());
        Label label = new Label();
        label.setText(this.formattedMessage(message));
        VBox layout = new VBox(10);
        layout.getChildren().addAll(label , button);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout);
        windows.setScene(scene);

        windows.showAndWait();
    }
    public String formattedMessage(String message){
        StringBuilder resultBuilder = new StringBuilder();
        for (int i = 0; i < message.length(); i++) {
            if ( message.charAt(i) == '。' ){
                resultBuilder.append('。');
                resultBuilder.append('\n');
            }else if(message.charAt(i) == '？'){
                resultBuilder.append('？');
                resultBuilder.append('\n');
            }else{
                resultBuilder.append(message.charAt(i));
            }
        }
        return resultBuilder.toString();
    }
}
