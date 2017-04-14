package com.Davi.poems.gui;


import com.Davi.poems.basic.pairData;
import com.Davi.poems.basic.tableData;
import com.Davi.poems.basic.tangClass;
import com.Davi.poems.tools.analyze;
import com.Davi.poems.tools.myException;
import com.Davi.poems.tools.toTextFile;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import sun.audio.AudioData;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import sun.audio.ContinuousAudioDataStream;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;


public class Controller {
    Logger logger = Logger.getLogger(Controller.class);

    private Main application;


    public void setApp(Main application) {
        this.application = application;
    }

    @FXML
    private Pane welcomePane, searchPane;
    @FXML
    private TextField searchFieldArea;
    @FXML
    private TextField pairFieldArea;
    @FXML
    private TextField getPairFieldArea;
    @FXML
    private TableView<tableData> resultTable;
    @FXML
    private TableView<pairData> resultTableP;
    @FXML
    private TableView<pairData> resultTableGP;
    @FXML
    private TableColumn<tableData, String> authorColumn, titleColumn, contextColumn;
    @FXML
    private TableColumn<pairData, String> authorColumnP, titleColumnP, pairColumn;
    @FXML
    private TableColumn<pairData,String> pairColumnGP;
    @FXML
    private Label exampleLabel;
    private ArrayList<tangClass> resultArray;
    public static Media media;
    public static MediaPlayer mp;
    public static boolean bgmIsOn = false;

    protected MusicThread m = new MusicThread(this);
    protected threadPool threadPools = new threadPool();


    @FXML
    protected void toMainFrame(ActionEvent event) throws Exception {
        //welcomePane.getChildren().clear();
        application.mainFrame();
    }

    @FXML
    protected void toClearTextArea(ActionEvent event) throws Exception {
        this.searchFieldArea.setText("");
        //this.resultTable.setVisible(false);

    }

    @FXML
    protected void toClearPairFieldArea(ActionEvent event) throws Exception {
        this.pairFieldArea.setText("");
        //this.resultTableP.refresh();
    }
    @FXML
    protected void toClearGetPairsFieldArea(ActionEvent event) throws Exception {
        this.getPairFieldArea.setText("");
        //this.resultTableP.refresh();
    }



    @FXML
    protected void toPairFrame(ActionEvent event) throws Exception {
        application.pairFrame();
    }
    @FXML
    protected  void toGetPairs(ActionEvent event )throws Exception{
        application.getPairsFrame();
    }

    @FXML
    protected void isSearch(ActionEvent event) throws Exception {
        String input = this.searchFieldArea.getText().trim();
        System.out.println(input);
        logger.info("输入字符串 "+input);
        analyze az = new analyze();
        try {
            resultArray = az.match(input);

            System.out.println("----------------------");
            System.out.println("精确匹配结果数量： " + resultArray.size());
            logger.info("精确匹配结果数量： " + resultArray.size());
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
        } catch (myException m) {
            new AlertBox().display("错误", "", m.getMessage());
        }


    }

    @FXML
    protected void possibleSearch(ActionEvent event) throws Exception {
        String input = this.searchFieldArea.getText().trim();
        System.out.println(input);
        analyze az = new analyze();
        try {
            resultArray = az.PMatch(input);
            System.out.println("----------------------");
            logger.info("模糊匹配结果数量： " + resultArray.size());
            System.out.println("模糊匹配结果数量： " + resultArray.size());
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


        } catch (myException m) {
            new AlertBox().display("错误", "", m.getMessage());
        }


    }

    @FXML
    protected void pairsSearch(ActionEvent event) throws Exception {
        //选择线程进行table刷新

        String input = this.pairFieldArea.getText().trim();
        System.out.println(input);
        analyze az = new analyze();
        try {
            resultArray = az.pairMatch(input);

            System.out.println("----------------------");
            logger.info("对偶匹配结果数量： " + resultArray.size());
            System.out.println("对偶匹配结果数量： " + resultArray.size());
            Iterator<tangClass> iterator1 = resultArray.iterator();
            int count = 0;
            while (iterator1.hasNext()) {
                count++;
                System.out.println(iterator1.next().toString());
            }
            System.out.println("----------------------");

            application.ObservableListPairInit(resultArray);
            this.resultTableP.setItems(application.getpData());
            authorColumnP.setCellValueFactory(cellData -> cellData.getValue().authorProperty());
            titleColumnP.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
            pairColumn.setCellValueFactory(cellData -> cellData.getValue().pairProperty());

            new AlertBox().display("对偶句数量", "", "查询结果共有 " + count + " 首");


        } catch (myException m) {
            new AlertBox().display("错误", "", m.getMessage());
        }


    }
    @FXML
    public void getPairs(ActionEvent event) throws myException {
        analyze az = new analyze();
        ArrayList<tangClass> result = new ArrayList<tangClass>();
        String input = this.getPairFieldArea.getText().trim();
        try {
            for (int i = 0; i < 10; i++) {
                tangClass tmp = new tangClass();
                tmp.setPairs(az.getPair(input));
                result.add(tmp);
            }

            this.application.ObservableListPairInit(result);
            this.resultTableGP.setItems(application.getpData());

            pairColumnGP.setCellValueFactory(cellData -> cellData.getValue().pairProperty());
        }catch (myException m){
            new AlertBox().display("错误","",m.getMessage());
        }
    }

    @FXML
    public void getOnePorm(javafx.scene.input.MouseEvent e) throws Exception {

        tableData td = this.resultTable.getSelectionModel().getSelectedItem();

        String author = td.getAuthor();
        String title = td.getTitle();
        String context = td.getContext();
        if (author != null && title != null && context != null)
            new AlertBox().display(title, author, context);
        else{
            throw new myException("空输入");
        }



    }

    @FXML
    public void getOnePair(javafx.scene.input.MouseEvent e) throws Exception {

        pairData pd = this.resultTableP.getSelectionModel().getSelectedItem();

        String author = pd.getAuthor();
        String title = pd.getTitle();
        String context = pd.getPair();
        if (author != null && title != null && context != null)
            new AlertBox().display(title, author, context);
        else if (author != null && title != null && context != null){
            new AlertBox().display("","",context);
        }else
            throw new myException("空输入");

    }
    @FXML
    public void getPairFromGP(javafx.scene.input.MouseEvent e) throws Exception {

        pairData pd = this.resultTableGP.getSelectionModel().getSelectedItem();

        String context = pd.getPair();
        if (context != null)
            new AlertBox().display("", "", context);
        else
            throw new myException("空输入");

    }


    @FXML public void playOrStopBGM() throws IOException {
        //String PATH = "A:/ssdworkspace/tang/src/main/resources/Loveless.mp3";

        if (bgmIsOn) {
            bgmIsOn = false;
            m.stop();
        } else if (bgmIsOn == false || mp.getStatus() == MediaPlayer.Status.STOPPED) {
            bgmIsOn = true;
            m.run();
        }


    }
    @FXML
    public void createFile(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("选择文件路径");
        Stage windows = new Stage();
        windows.setTitle("文件选择");
        windows.initModality(Modality.APPLICATION_MODAL);
        windows.setMinWidth(400);
        windows.setMinHeight(300);
        File file = fileChooser.showOpenDialog(windows);
        if (file != null){
            toTextFile toTextFile = new toTextFile(resultArray);
            try {
                toTextFile.fillFile(file);
                logger.info("生成文件:"+file.getName()+" 文件路径:"+file.getPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) {

    }


}
