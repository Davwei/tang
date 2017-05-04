package com.Davi.poems.gui;

import com.Davi.poems.basic.tangClass;
import com.Davi.poems.tools.analyze;
import com.Davi.poems.tools.myException;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.Thread.sleep;

/**
 * Created by David on 2017/3/22.
 */
public class threadPool {
    //保存结果适用于线程操作
    //因为GUI的原因，没有办法同时进行两次以上操作
    ExecutorService fixExecutorService = Executors.newFixedThreadPool(5);
    public threadPool(){
    }

    public isSearchThread createIsSerachThread(String input){
        isSearchThread ist = new isSearchThread(input);
        fixExecutorService.execute(ist);
        return ist;
    }
    public possiblySearchThread createPossiblySerachThread(String input){
        possiblySearchThread pst = new possiblySearchThread(input);
        fixExecutorService.execute(pst);
        return pst;
    }

    class possiblySearchThread implements Runnable {
        String status = "EMPTY";
        ArrayList<tangClass> result = new ArrayList<>();
        analyze az = new analyze();
        String input ;
        public possiblySearchThread(String input){
            this.input = input;
        }
        @Override
        public void run() {
            try {
                status = "DOING";
                result = az.PMatch(input);
                status = "FINISH";
            } catch (myException e) {
                new AlertBox().display(e.getMessage());
            }

        }
        public String getStatus(){
            return status;
        }
        public ArrayList<tangClass> getResult(){
                return result;
        }
    }
    class isSearchThread implements Runnable{
        ArrayList<tangClass> result = new ArrayList<>();
        analyze az = new analyze();
        String input ;

        public isSearchThread(String input){
            this.input = input;
        }
        @Override
        public void run() {
            try {
                result = az.match(input);

            } catch (myException e) {
                e.printStackTrace();
            }
        }
        public ArrayList<tangClass> getResult(){
            return result;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        threadPool th = new threadPool();
        possiblySearchThread pst = th.createPossiblySerachThread("春晓");
        System.out.println(pst.getStatus());
        while (pst.getStatus() == "EMPTY"){
            System.out.println("wait");
            sleep(1000);
        }
        System.out.println("PASS");
        while (pst.getStatus() == "DOING"){
            System.out.println("wait");
            sleep(1000);
        }

        System.out.println("PASS");
    }
}

