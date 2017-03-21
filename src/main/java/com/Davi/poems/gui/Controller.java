package com.Davi.poems.gui;


import com.Davi.poems.basic.pairData;
import com.Davi.poems.basic.tableData;
import com.Davi.poems.basic.tangClass;
import com.Davi.poems.tools.analyze;
import com.Davi.poems.tools.myException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import sun.audio.AudioData;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import sun.audio.ContinuousAudioDataStream;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;


public class Controller {

    private Main application;

    public void setApp(Main application){
        this.application = application;
    }

    @FXML private Pane welcomePane,searchPane;
    @FXML private TextField searchFieldArea;
    @FXML private TextField pairFieldArea;
    @FXML private TableView<tableData> resultTable;
    @FXML private TableView<pairData> resultTableP;
    @FXML private TableColumn<tableData,String> authorColumn,titleColumn,contextColumn;
    @FXML private TableColumn<pairData,String> authorColumnP,titleColumnP,pairColumn;
    @FXML private Label exampleLabel;
    //ContinuousAudioDataStream cads;
    static boolean bgmIsOn = false;


    @FXML protected void toMainFrame(ActionEvent event) throws Exception {
        //welcomePane.getChildren().clear();
        application.mainFrame();
    }
    @FXML protected void toClearTextArea(ActionEvent event) throws Exception{
            this.searchFieldArea.setText("");

    }
    @FXML protected void toClearPairFieldArea(ActionEvent event) throws Exception{
        this.pairFieldArea.setText("");
    }



    @FXML protected void toPairFrame(ActionEvent event) throws Exception{
        application.pairFrame();
    }

    @FXML protected void isSearch(ActionEvent event) throws Exception{
        String input = this.searchFieldArea.getText().trim();
        System.out.println(input);
        analyze az = new analyze();
        try {
            ArrayList<tangClass> resultArray = az.match(input);

            System.out.println("----------------------");

            System.out.println("匹配结果数量： " + resultArray.size());
            Iterator<tangClass> iterator1 = resultArray.iterator();
            int count = 0;
            while (iterator1.hasNext()) {
                count++;
                System.out.println(iterator1.next().toString());
            }
            System.out.println("----------------------");

            application.ObservableListInit(resultArray);
            this.resultTable.setItems(application.gettData());


            authorColumn.setCellValueFactory(cellData -> cellData.getValue().authorProperty());
            titleColumn.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
            contextColumn.setCellValueFactory(cellData -> cellData.getValue().contextProperty());

            new AlertBox().display("诗词数量", "", "查询结果共有 " + count + " 首");
        }catch (myException m){
            new AlertBox().display("错误","",m.getMessage());
        }


    }
    @FXML protected void possibleSearch(ActionEvent event) throws Exception{
        String input = this.searchFieldArea.getText().trim();
        System.out.println(input);
        analyze az = new analyze();
        try {
            ArrayList<tangClass> resultArray = az.PMatch(input);

            System.out.println("----------------------");
            System.out.println("匹配结果数量： "+resultArray.size());
            Iterator<tangClass> iterator1 = resultArray.iterator();
            int count = 0;
            while ( iterator1.hasNext()){
                count++;
                System.out.println(iterator1.next().toString());
            }
            System.out.println("----------------------");

            application.ObservableListInit(resultArray);
            this.resultTable.setItems(application.gettData());
            authorColumn.setCellValueFactory(cellData -> cellData.getValue().authorProperty());
            titleColumn.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
            contextColumn.setCellValueFactory(cellData -> cellData.getValue().contextProperty());

            new AlertBox().display("诗词数量","","查询结果共有 "+count+" 首");


        }catch (myException m){
            new AlertBox().display("错误","",m.getMessage());
        }







    }
    @FXML protected void pairsSearch(ActionEvent event) throws Exception{
        String input = this.pairFieldArea.getText().trim();
        System.out.println(input);
        analyze az = new analyze();
        try {
            ArrayList<tangClass> resultArray = az.pairMatch(input);

            System.out.println("----------------------");
            System.out.println("匹配结果数量： "+resultArray.size());
            Iterator<tangClass> iterator1 = resultArray.iterator();
            int count = 0;
            while ( iterator1.hasNext()){
                count++;
                System.out.println(iterator1.next().toString());
            }
            System.out.println("----------------------");

            application.ObservableListPairInit(resultArray);
            this.resultTableP.setItems(application.getpData());
            authorColumnP.setCellValueFactory(cellData -> cellData.getValue().authorProperty());
            titleColumnP.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
            pairColumn.setCellValueFactory(cellData -> cellData.getValue().pairProperty());

            new AlertBox().display("对偶句数量","","查询结果共有 "+count+" 首");


        }catch (myException m){
            new AlertBox().display("错误","",m.getMessage());
        }







    }

    @FXML public void getOnePorm(javafx.scene.input.MouseEvent e)throws Exception{

        tableData td = this.resultTable.getSelectionModel().getSelectedItem();

        String author = td.getAuthor();
        String title = td.getTitle();
        String context = td.getContext();
        if (author != null && title != null && context != null)
            new AlertBox().display(title,author,context);
        else
            throw new myException("空输入");

    }
    @FXML public void getOnePair(javafx.scene.input.MouseEvent e)throws Exception{

        pairData pd = this.resultTableP.getSelectionModel().getSelectedItem();

        String author = pd.getAuthor();
        String title = pd.getTitle();
        String context = pd.getPair();
        if (author != null && title != null && context != null)
            new AlertBox().display(title,author,context);
        else
            throw new myException("空输入");

    }
    @FXML public void playOrStopBGM() {
        String PATH = "src/main/resources/Loveless.mp3";
        if (!bgmIsOn) {
            bgmIsOn = true;



        }
    }


}
