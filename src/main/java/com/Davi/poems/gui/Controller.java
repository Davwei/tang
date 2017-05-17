package com.Davi.poems.gui;


import com.Davi.poems.basic.operation;
import com.Davi.poems.basic.pairData;
import com.Davi.poems.basic.tableData;
import com.Davi.poems.basic.tangClass;
import com.Davi.poems.tools.analyze;
import com.Davi.poems.tools.myException;
import com.Davi.poems.tools.toTextFile;
import javafx.application.Platform;
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
import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.*;

import static java.lang.Thread.sleep;


public class Controller {
    Logger logger = Logger.getLogger(Controller.class);

    private Main application;
    private analyze az = new analyze();
    private ExecutorService service;


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
    private TextField numberArea;
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
    private TableColumn<pairData, String> pairColumnGP;
    @FXML
    private Label exampleLabel;


    private ArrayList<tangClass> resultArray;
    private HashMap<Integer, ArrayList<tangClass>> history = new HashMap<>(9);
    private int[] isSearch = new int[3];
    private int[] possibleSearch = new int[3];
    private int[] pairSearch = new int[3];
    private int pointIsSearch = 0;
    private int pointPossibleSearch = 0;
    private int pointPairSearch = 0;
    private int lastPoint;
    public static Media media;
    public static MediaPlayer mp;
    public static boolean bgmIsOn = false;
    public int timeout = 1;
    protected getResultSetThread result1;

    protected MusicThread m = new MusicThread(this);
    protected threadPool threadPools = new threadPool();

    private int pairsNumber = 5;


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
    protected void toGetPairs(ActionEvent event) throws Exception {
        application.getPairsFrame();
    }

    @FXML
    protected void isSearch(ActionEvent event) throws Exception {
        String input = this.searchFieldArea.getText().trim();
        logger.info("输入字符串 " + input);
        resultArray = new ArrayList<>();

        result1 = new getResultSetThread(this,input,operation.isSearch);
        //result1.run();

        service = Executors.newFixedThreadPool(1);
        Future future = service.submit(result1);

        try {
            future.get(timeout*1000, TimeUnit.MILLISECONDS);//超时设置
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            logger.error("超时 取消任务");
            future.cancel(true);
        } finally {
            System.out.println("线程容器结束");
            service.shutdown();
        }

        //resultArray = az.match(input);


        /*while (iterator1.hasNext()) {
                count++;
                iterator1.next();
                //System.out.println(iterator1.next().toString());
                }*/

        //application.ObservableListInit(resultArray);
        //this.resultTable.setItems(application.gettData());


        //authorColumn.setCellValueFactory(cellData -> cellData.getValue().authorProperty());
        //titleColumn.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
        //contextColumn.setCellValueFactory(cellData -> cellData.getValue().contextProperty());


    }


    @FXML
    protected void possibleSearch(ActionEvent event) throws Exception {
        String input = this.searchFieldArea.getText().trim();
        //System.out.println(input);
        resultArray = new ArrayList<>();

        result1 = new getResultSetThread(this,input,operation.possibleSearch);
        service = Executors.newFixedThreadPool(1);
        Future future = service.submit(result1);

        try {
            future.get(timeout*1000, TimeUnit.MILLISECONDS);//超时设置
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            logger.error("超时 取消任务");
            future.cancel(true);
        } finally {
            System.out.println("线程容器结束");
            service.shutdown();
        }


        /*
        try {
            resultArray = az.PMatch(input);
            int point = (pointIsSearch + pointPairSearch + pointPossibleSearch) % 9;
            possibleSearch[pointPossibleSearch % 3] = point;
            pointPossibleSearch++;
            history.put(point, resultArray);
            //System.out.println("----------------------");
            logger.info("模糊匹配结果数量： " + resultArray.size());
            //System.out.println("模糊匹配结果数量： " + resultArray.size());
            //Iterator<tangClass> iterator1 = resultArray.iterator();
            int count = resultArray.size();

            //System.out.println("----------------------");

            application.ObservableListInit(resultArray);
            this.resultTable.setItems(application.gettData());
            authorColumn.setCellValueFactory(cellData -> cellData.getValue().authorProperty());
            titleColumn.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
            contextColumn.setCellValueFactory(cellData -> cellData.getValue().contextProperty());

            new AlertBox().display("查询结果共有: " + count + " 首");
        } catch (myException m) {
            new AlertBox().display("错误: " + m.getMessage());
        }*/


    }

    @FXML
    protected void pairsSearch(ActionEvent event) throws Exception {

        String input = this.pairFieldArea.getText().trim();
        //System.out.println(input);
        resultArray = new ArrayList<>();

        result1 = new getResultSetThread(this,input,operation.pairMatch);
        service = Executors.newFixedThreadPool(1);
        Future future = service.submit(result1);

        try {
            future.get(timeout*1000, TimeUnit.MILLISECONDS);//超时设置
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            logger.error("超时 取消任务");
            future.cancel(true);
        } finally {
            System.out.println("线程容器结束");
            service.shutdown();
        }


        /*
        try {
            resultArray = az.pairMatch(input);
            int point = (pointIsSearch + pointPairSearch + pointPossibleSearch) % 9;
            pairSearch[pointPairSearch % 3] = point;
            pointPairSearch++;
            history.put(point, resultArray);

            //System.out.println("----------------------");
            logger.info("对偶匹配结果数量： " + resultArray.size());
            //System.out.println("对偶匹配结果数量： " + resultArray.size());
            //Iterator<tangClass> iterator1 = resultArray.iterator();
            int count = resultArray.size();

            //System.out.println("----------------------");

            application.ObservableListPairInit(resultArray);
            this.resultTableP.setItems(application.getpData());
            authorColumnP.setCellValueFactory(cellData -> cellData.getValue().authorProperty());
            titleColumnP.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
            pairColumn.setCellValueFactory(cellData -> cellData.getValue().pairProperty());

            new AlertBox().display("查询结果共有: " + count + " 首");
        } catch (myException m) {
            new AlertBox().display("错误: " + m.getMessage());
        }*/


    }

    @FXML
    public void getPairsNormal(ActionEvent event) throws myException {
        //TODO 规范输入值
        String input = getPairFieldArea.getText().trim();
        resultArray = new ArrayList<>();
        result1 = new getResultSetThread(this,input,operation.pairGetNormal);
        service = Executors.newFixedThreadPool(1);
        Future future = service.submit(result1);

        try {
            future.get(timeout*1000, TimeUnit.MILLISECONDS);//超时设置
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            logger.error("超时 取消任务");
            future.cancel(true);
        } finally {
            System.out.println("线程容器结束");
            service.shutdown();
        }


        /*if (isNumeric(numberArea.getText().trim()))
            pairsNumber = Integer.valueOf(numberArea.getText().trim());
        else
            new AlertBox().display("请输入数字");
        String input = this.getPairFieldArea.getText().trim();

        try {
            for (int i = 0; i < pairsNumber; i++) {
                tangClass tmp = new tangClass();
                tmp.setPairs(az.nomalSegment(input));
                resultArray.add(tmp);
            }

            this.application.ObservableListPairInit(resultArray);
            this.resultTableGP.setItems(application.getpData());

            pairColumnGP.setCellValueFactory(cellData -> cellData.getValue().pairProperty());
        } catch (myException m) {
            new AlertBox().display("错误: " + m.getMessage());
        }*/
    }

    @FXML
    public void getPairsNLP(ActionEvent event) throws myException {
        //ArrayList<tangClass> result = new ArrayList<tangClass>();
        if (isNumeric(numberArea.getText().trim()))
            pairsNumber = Integer.valueOf(numberArea.getText().trim());
        else
            new AlertBox().display("请输入数字");
        String input = this.getPairFieldArea.getText().trim();
        try {
            for (int i = 0; i < pairsNumber; i++) {
                tangClass tmp = new tangClass();
                tmp.setPairs(az.NLPSegment(input));
                resultArray.add(tmp);
            }

            this.application.ObservableListPairInit(resultArray);
            this.resultTableGP.setItems(application.getpData());

            pairColumnGP.setCellValueFactory(cellData -> cellData.getValue().pairProperty());
        } catch (myException m) {
            new AlertBox().display("错误: " + m.getMessage());
        }
    }

    @FXML
    public void getPairsNShort(ActionEvent event) throws myException {
        //ArrayList<tangClass> result = new ArrayList<tangClass>();
        if (isNumeric(numberArea.getText().trim()))
            pairsNumber = Integer.valueOf(numberArea.getText().trim());
        else
            new AlertBox().display("请输入数字");
        String input = this.getPairFieldArea.getText().trim();
        try {
            for (int i = 0; i < pairsNumber; i++) {
                tangClass tmp = new tangClass();
                tmp.setPairs(az.NshortSegment(input));
                resultArray.add(tmp);
            }

            this.application.ObservableListPairInit(resultArray);
            this.resultTableGP.setItems(application.getpData());

            pairColumnGP.setCellValueFactory(cellData -> cellData.getValue().pairProperty());
        } catch (myException m) {
            new AlertBox().display("错误: " + m.getMessage());
        }
    }

    @FXML
    public void getPairsDijstra(ActionEvent event) throws myException {
        //ArrayList<tangClass> result = new ArrayList<tangClass>();
        if (isNumeric(numberArea.getText().trim()))
            pairsNumber = Integer.valueOf(numberArea.getText().trim());
        else
            new AlertBox().display("请输入数字");
        String input = this.getPairFieldArea.getText().trim();
        try {
            for (int i = 0; i < pairsNumber; i++) {
                tangClass tmp = new tangClass();
                tmp.setPairs(az.DijkstraShortSegment(input));
                resultArray.add(tmp);
            }

            this.application.ObservableListPairInit(resultArray);
            this.resultTableGP.setItems(application.getpData());

            pairColumnGP.setCellValueFactory(cellData -> cellData.getValue().pairProperty());
        } catch (myException m) {
            new AlertBox().display("错误: " + m.getMessage());
        }
    }


    @FXML
    public void getOnePorm(javafx.scene.input.MouseEvent e) throws Exception {

        tableData td = this.resultTable.getSelectionModel().getSelectedItem();
        if (td == null) {
            throw new myException("空输入");
        }
        String author = td.getAuthor();
        String title = td.getTitle();
        String context = td.getContext();
        if (author != null && title != null && context != null) {
            tangClass tmp = new tangClass();
            tmp.setAuther(author);
            tmp.setTitle(title);
            tmp.setContext(context);
            new AlertBox().displayPorms(tmp);

        } else {
            throw new myException("空输入");
        }


    }

    @FXML
    public void getOnePair(javafx.scene.input.MouseEvent e) throws Exception {

        pairData pd = this.resultTableP.getSelectionModel().getSelectedItem();

        if (pd == null) {
            throw new myException("空输入");
        }
        String author = pd.getAuthor();
        String title = pd.getTitle();
        String context = pd.getPair();
        if (author != null && title != null && context != null) {
            tangClass tmp = new tangClass();
            tmp.setAuther(author);
            tmp.setTitle(title);
            tmp.setContext(context);
            new AlertBox().displayPorms(tmp);
        } else
            throw new myException("空输入");

    }

    @FXML
    public void getPairFromGP(javafx.scene.input.MouseEvent e) throws Exception {

        pairData pd = this.resultTableGP.getSelectionModel().getSelectedItem();

        if (pd != null) {
            String context = pd.getPair();
            tangClass tmp = new tangClass();
            tmp.setTitle("对偶生成");
            tmp.setContext(context);
            new AlertBox().displayPorms(tmp);
        } else
            throw new myException("空输入");

    }


    @FXML
    public void playOrStopBGM() throws IOException {
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
    public void createFile() {
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

        if (file != null) {
            toTextFile toTextFile = new toTextFile(resultArray);
            try {
                toTextFile.fillFile(file);
                logger.info("生成文件:" + file.getName() + " 文件路径:" + file.getPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void clearHistory(ActionEvent event) {
        pointIsSearch = pointPairSearch = pointPossibleSearch = 0;
    }

    @FXML
    public void backTolastIsSearch(ActionEvent event) {
        if (pointIsSearch <= 0) {
            new AlertBox().display("无历史记录");
            return;
        }
        if (pointIsSearch == 1) {
            new AlertBox().display("仅有一条记录");
            return;
        }
        lastPoint = pointIsSearch - 2;
        pointIsSearch--;
        //logger.info("lastPoint is "+lastPoint);
        int historyPoint = isSearch[lastPoint % 3];
        //logger.info("historyPoint is " + historyPoint);
        resultArray = history.get(Integer.valueOf(historyPoint));
        //logger.info("result size is "+resultArray.size());

        int count = resultArray.size();

        application.ObservableListInit(resultArray);
        this.resultTable.setItems(application.gettData());


        authorColumn.setCellValueFactory(cellData -> cellData.getValue().authorProperty());
        titleColumn.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
        contextColumn.setCellValueFactory(cellData -> cellData.getValue().contextProperty());

        new AlertBox().display("查询结果共有 " + count + " 首");
    }

    @FXML
    public void backTolastPossibleSearch(ActionEvent event) {
        if (pointPossibleSearch <= 0) {
            new AlertBox().display("无历史记录");
            return;
        }
        if (pointPossibleSearch == 1) {
            new AlertBox().display("仅有一条记录");
            return;
        }
        lastPoint = pointPossibleSearch - 2;
        pointPossibleSearch--;
        //logger.info("lastPoint is "+lastPoint);
        int historyPoint = possibleSearch[lastPoint % 3];
        //logger.info("historyPoint is " + historyPoint);
        resultArray = history.get(Integer.valueOf(historyPoint));
        //logger.info("result size is "+resultArray.size());

        int count = resultArray.size();

        application.ObservableListInit(resultArray);
        this.resultTable.setItems(application.gettData());


        authorColumn.setCellValueFactory(cellData -> cellData.getValue().authorProperty());
        titleColumn.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
        contextColumn.setCellValueFactory(cellData -> cellData.getValue().contextProperty());

        new AlertBox().display("查询结果共有 " + count + " 首");
    }

    @FXML
    public void readMe(ActionEvent event) {
        String str = "本软件由Davi与2017年制作完成，@copyright reserved \n本软件有网络服务模式与单机客户端模式两种，在启动程序jar包时，\n对应后边加入-jar **.jar server 端口号可以启用网络模式，\n发送端demo位于com.Davi.poems.net.myHttpClient.java \n"
                + "更多问题欢迎email wangwei3791@163.com 谢谢";
        new AlertBox().display(str);
    }

    public static boolean isNumeric(String str) {
        if (str == null)
            return false;
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public void showAlertBox(String info) {
        Platform.runLater(()-> new AlertBox().display(info));
    }


    class getResultSetThread extends Thread {
        protected com.Davi.poems.gui.Controller c;
        String input;
        operation op;

        getResultSetThread(Controller c, String input,operation op) {
            this.c = c;
            this.input = input;
            this.op = op;
        }



        private void isSearchMethod(String input) throws myException {

            synchronized (c.resultArray) {
                //TODO 检查方法类卡住页面的原因，并改进
                c.resultArray = c.az.match(input);
                int point = (c.pointIsSearch + c.pointPairSearch + c.pointPossibleSearch) % 9;
                logger.info("point is " + point);
                c.isSearch[c.pointIsSearch % 3] = point;
                c.pointIsSearch++;
                c.history.put(point, c.resultArray);
                //System.out.println("精确匹配结果数量： " + resultArray.size());
                logger.info("精确匹配结果数量： " + c.resultArray.size());
                //Iterator<tangClass> iterator1 = resultArray.iterator();
                //new AlertBox().display("精确匹配结果数量： " + c.resultArray.size());

            }
        }
        private void possibleSearchMethod(String input) throws myException {
            synchronized (c.resultArray) {
                c.resultArray = c.az.PMatch(input);
                int point = (pointIsSearch + pointPairSearch + pointPossibleSearch) % 9;
                possibleSearch[pointPossibleSearch % 3] = point;
                pointPossibleSearch++;
                history.put(point, c.resultArray);
                //System.out.println("----------------------");
                logger.info("模糊匹配结果数量： " + c.resultArray.size());

            }
        }
        private void pairMatchMethod(String input) throws myException {
            synchronized(c.resultArray){
                c.resultArray = az.pairMatch(input);
                int point = (pointIsSearch + pointPairSearch + pointPossibleSearch) % 9;
                pairSearch[pointPairSearch % 3] = point;
                pointPairSearch++;
                history.put(point, c.resultArray);

                //System.out.println("----------------------");
                logger.info("对偶匹配结果数量： " + c.resultArray.size());
            }
        }
        private void pairGetMethodNormal(String input) throws myException {
            synchronized (c.resultArray){
                if (isNumeric(c.numberArea.getText().trim()))
                    c.pairsNumber = Integer.valueOf(c.numberArea.getText().trim());
                else
                    c.showAlertBox("请输入数字");

                for (int i = 0; i < c.pairsNumber; i++) {
                        tangClass tmp = new tangClass();
                        tmp.setPairs(c.az.nomalSegment(input));
                        c.resultArray.add(tmp);
                }
            }
        }

        private void isSearchMethodFlush() {

            synchronized (c.resultArray) {

                c.application.ObservableListInit(c.resultArray);
                c.resultTable.setItems(c.application.gettData());

                c.authorColumn.setCellValueFactory(cellData -> cellData.getValue().authorProperty());
                c.titleColumn.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
                c.contextColumn.setCellValueFactory(cellData -> cellData.getValue().contextProperty());
                //new AlertBox().display("精确匹配结果数量： " + c.resultArray.size());
                c.showAlertBox("匹配结果数量： " + c.resultArray.size());
            }
        }
        private void pairSearchMethodFlush(){
            synchronized (c.resultArray) {
                c.application.ObservableListPairInit(c.resultArray);
                c.resultTableP.setItems(c.application.getpData());
                c.authorColumnP.setCellValueFactory(cellData -> cellData.getValue().authorProperty());
                c.titleColumnP.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
                c.pairColumn.setCellValueFactory(cellData -> cellData.getValue().pairProperty());
            }

        }
        private void pairGetMethodFlush(){
            synchronized (c.resultArray){
                c.application.ObservableListPairInit(c.resultArray);
                c.resultTableGP.setItems(application.getpData());
                c.pairColumnGP.setCellValueFactory(cellData -> cellData.getValue().pairProperty());
            }
        }

        @Override
        public void run() {
            try {
                long start = System.currentTimeMillis();
                switch (op){
                    case isSearch:
                        isSearchMethod(input);
                        logger.info("执行开始时间差" + (System.currentTimeMillis() - start) + "ms");
                        isSearchMethodFlush();
                        break;
                    case pairMatch:
                        pairMatchMethod(input);
                        pairSearchMethodFlush();
                        logger.info("执行开始时间差" + (System.currentTimeMillis() - start) + "ms");
                        break;
                    case possibleSearch:
                        possibleSearchMethod(input);
                        logger.info("执行开始时间差" + (System.currentTimeMillis() - start) + "ms");
                        isSearchMethodFlush();
                        break;
                    case pairGetNormal:
                        //get number from input
                        pairGetMethodNormal(input);
                        logger.info("执行开始时间差" + (System.currentTimeMillis() - start) + "ms");
                        pairGetMethodFlush();
                        break;
                }

            } catch (myException e) {
                logger.error(e.getMessage());
                c.showAlertBox("错误: " + e.getMessage());
            }


        }
    }


    public static void main(String[] args) {

    }




}
