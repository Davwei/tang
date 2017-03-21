package com.Davi.poems.basic;


import javafx.beans.property.SimpleStringProperty;

/**
 * Created by David on 2017/3/16.
 */
public final class tableData {

    private final SimpleStringProperty 诗人 = new SimpleStringProperty();
    private final SimpleStringProperty 题目 = new SimpleStringProperty();
    private final SimpleStringProperty 诗文 = new SimpleStringProperty();
    public tableData(tangClass tc){
        this.setAuthor(tc.getAuther());
        this.setTitle(tc.getTitle());
        this.setContext(tc.getContext());
    }
    public void setAuthor(String in){
        this.诗人.set(in);
    }
    public void setTitle(String in){
        this.题目.set(in);
    }
    public void setContext(String in){
        this.诗文.set(in);
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

    public String getContext() {
        return 诗文.get();
    }

    public SimpleStringProperty contextProperty() {
        return 诗文;
    }
}
