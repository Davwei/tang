package com.Davi.poems.gui;


import com.Davi.poems.basic.pairData;
import com.Davi.poems.basic.tableData;
import com.Davi.poems.basic.tangClass;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;


public class Main extends Application {
    private Stage stage;
    private ObservableList<tableData> tData = FXCollections.observableArrayList();
    private ObservableList<pairData> pData = FXCollections.observableArrayList();
    protected Class Controller;
    private final double WINDOW_WIDTH = 800.0;
    private final double WINDOW_HEIGH = 400.0;


    @Override
    public void start(Stage primaryStage) throws Exception{
        //Parent root = FXMLLoader.load(getClass().getResource("welcome.fxml"));
        stage = primaryStage;
        stage.setTitle("First Job");
        //primaryStage.setScene(new Scene(root, 600, 400));
        stage.setMinHeight(WINDOW_HEIGH);
        stage.setMinWidth(WINDOW_WIDTH);
        weclome();

        stage.show();
    }
    public void weclome(){
        try {
            Controller controller = replaceSceneContent("/welcome.fxml");
            //Controller controller = FXMLLoader.load(getClass().getResource("/welcome.fxml"));
            controller.setApp(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void mainFrame(){
        try {
            Controller controller = replaceSceneContent("/mainFrame.fxml");
            controller.setApp(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void pairFrame(){
        try {
            Controller controller = replaceSceneContent("/pairsFrame.fxml");
            controller.setApp(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    public Controller replaceSceneContent(String fxml) throws Exception{
        FXMLLoader loader = new FXMLLoader();
        InputStream in = Main.class.getResourceAsStream(fxml);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(Main.class.getResource(fxml));
        BorderPane page;
        try {
            page = (BorderPane) loader.load(in);
        } finally {
            in.close();
        }
        Scene scene = new Scene(page,WINDOW_WIDTH,WINDOW_HEIGH);
        stage.setScene(scene);
        stage.sizeToScene();

        return loader.getController();
    }
    public ObservableList<tableData> gettData(){
        return tData;
    }
    public void ObservableListInit(ArrayList<tangClass> input){
        tData.clear();
        Iterator<tangClass> iterator = input.iterator();
        while ( iterator.hasNext() ){
            tangClass tmp = iterator.next();
            tData.add(new tableData(tmp));
        }
    }

    public ObservableList<pairData> getpData(){return  pData;}
    public void ObservableListPairInit(ArrayList<tangClass> input){
        pData.clear();
        Iterator<tangClass> iterator = input.iterator();
        while (iterator.hasNext()){
            tangClass tmp = iterator.next();
            pData.add(new pairData(tmp));
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
