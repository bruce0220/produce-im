package com.hzw.code.produce;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TableDefined {
    
    private String tableName;

    private String entryName;

    private String key;

    private List<ColumnDefined> columns = new ArrayList<ColumnDefined>();

    private String packagePath;

    private String author;

    private String currDate;

    private List<String> importlist = new ArrayList<String>();

    public List<String> getImportlist() {
        return importlist;
    }

    public void addImport(String importValue) {
        importlist.add(importValue);
    }

    public void addColumn(ColumnDefined column) {
        columns.add(column);
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getEntryName() {
        return entryName;
    }

    public void setEntryName(String entryName) {
        this.entryName = entryName;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<ColumnDefined> getColumns() {
        return columns;
    }

    public String getPackagePath() {
        return packagePath;
    }

    public void setPackagePath(String packagePath) {
        this.packagePath = packagePath;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCurrDate() {
        return currDate;
    }

    public void setCurrDate(String currDate) {
        this.currDate = currDate;
    }

}
