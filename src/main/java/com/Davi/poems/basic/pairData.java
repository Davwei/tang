package com.Davi.poems.basic;

import javafx.beans.property.SimpleStringProperty;

/**
 * Created by David on 2017/3/21.
 */
public class pairData {
    private final SimpleStringProperty 诗人 = new SimpleStringProperty();
    private final SimpleStringProperty 题目 = new SimpleStringProperty();
    private final SimpleStringProperty 对偶句 = new SimpleStringProperty();
    public pairData(tangClass tc){
        this.setAuthor(tc.getAuther());
        this.setTitle(tc.getTitle());
        this.setPair(tc.getPairs());
    }
    public void setAuthor(String in){this.诗人.set(in);}
    public void setTitle(String in){this.题目.set(in);}
    public void setPair(String in){
        this.对偶句.set(in);
    }
    public String getAuthor() {
        return 诗人.get();
    }

    public SimpleStringProperty authorProperty() {
        return 诗人;
    }

    public String getTitle() {
        return 题目.get();
    }

    public SimpleStringProperty titleProperty() {
        return 题目;
    }

    public String getPair() {
        return 对偶句.get();
    }

    public SimpleStringProperty pairProperty() {
        return 对偶句;
    }
}
