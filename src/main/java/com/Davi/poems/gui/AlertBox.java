package com.Davi.poems.gui;

import com.Davi.poems.basic.tangClass;
import com.Davi.poems.tools.toTextFile;
import javafx.event.Event;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

import java.awt.event.ActionListener;
import java.beans.EventHandler;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


/**
 * Created by David on 2017/3/17.
 */
public class AlertBox {
    public AlertBox(){

    }
    public void display(String message){
        Stage windows = new Stage();
        windows.setTitle("通知信息");
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
    public void displayPorms(tangClass input){
        Stage windows = new Stage();
        windows.setTitle(input.getAuther()+" : "+input.getTitle());
        windows.initModality(Modality.APPLICATION_MODAL);
        windows.setMinWidth(300);
        windows.setMinHeight(200);
        //Button button = new Button("关闭");
        //button.setOnAction(e -> windows.close());
        Button save = new Button("保存文字");
        save.setOnAction(new saveToFile(input));
        Label label = new Label();
        label.setText(this.formattedMessage(input.getContext()));
        VBox layout = new VBox(10);
        layout.getChildren().addAll(label , save);
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
    class saveToFile implements javafx.event.EventHandler{
        Logger logger = Logger.getLogger(saveToFile.class);
        tangClass tmp ;
        saveToFile(tangClass ts){
            this.tmp = ts;
        }
        @Override
        public void handle(Event event) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("选择文件路径");
            fileChooser.setInitialFileName("诗词结果.txt");

            Stage windows = new Stage();
            windows.setTitle("文件选择");
            windows.initModality(Modality.APPLICATION_MODAL);
            windows.setMinWidth(400);
            windows.setMinHeight(300);
            //File file = fileChooser.showOpenDialog(windows);
            File file = fileChooser.showSaveDialog(windows);
            ArrayList<tangClass> input = new ArrayList<>();
            input.add(tmp);
            if (file != null){
                toTextFile toTextFile = new toTextFile(input);
                try {
                    toTextFile.fillFile(file);
                    logger.info("生成文件:"+file.getName()+" 文件路径:"+file.getPath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
