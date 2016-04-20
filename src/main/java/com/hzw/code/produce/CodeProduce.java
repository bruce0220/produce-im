package com.hzw.code.produce;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class CodeProduce {
    
    private final static String JDBC_DRIVER = "jdbc.driver";

    private final static String JDBC_URL = "jdbc.url";

    private final static String JDBC_USERNAME = "jdbc.username";

    private final static String JDBC_PASSWORD = "jdbc.password";

    private final static String JDBC_VALIDQUERY = "jdbc.validQuery";




    String driver;
    String url;
    String username;
    String password;
    String validQuery;
    String default_db_conf = "jdbc.properties";

    String filePath = "D:\\code-produced";

    TableDefined table;
    //
    String packagePath;

    public CodeProduce(String tableName, String entryName, String packagePath, String author)
            throws IOException {
        table = new TableDefined();
        table.setEntryName(entryName);
        table.setTableName(tableName);
        table.setPackagePath(packagePath);
        table.setAuthor(author);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        table.setCurrDate(format.format(new Date()));
        this.packagePath = packagePath;
        init();
    }

    public void doProduce() throws Exception {
        tableToDefined();
        JavaFileProduceUtils uti = new JavaFileProduceUtils(table, filePath);
        uti.createFiles();
    }

    private void init() throws IOException {
        System.out.println(this.getClass().getName() + ".init start");
        Properties pro = new Properties();
        pro.load(this.getClass().getClassLoader().getResourceAsStream(default_db_conf));
        driver = pro.getProperty(JDBC_DRIVER);
        url = pro.getProperty(JDBC_URL);
        username = pro.getProperty(JDBC_USERNAME);
        password = pro.getProperty(JDBC_PASSWORD);
        validQuery = pro.getProperty(JDBC_VALIDQUERY);
        System.out.println(this.getClass().getName() + ".init end");
    }

    private void tableToDefined() throws Exception {
        System.out.println(this.getClass().getName() + ".tableToDefined start");
        if (table == null)
            return;
        Class.forName(driver);
        Connection con = DriverManager.getConnection(url, username, password);
        Statement stmt = con.createStatement();

        ResultSet rs = stmt.executeQuery("show full FIELDS from " + table.getTableName());
        Map<String, String> columnCommentMap = new HashMap<String, String>();
        while (rs.next()) {
            String field = rs.getString("Field");
            String comment = rs.getString("Comment");
            if (comment == null || comment.length() < 1)
                comment = field;

            columnCommentMap.put(field, comment);
        }

        rs.close();
        stmt.close();

        stmt = con.createStatement();
        rs = stmt.executeQuery("select * from " + table.getTableName());
        ResultSetMetaData metaData = rs.getMetaData();

        int metaCount = metaData.getColumnCount();
        for (int i = 1; i <= metaCount; i++) {
            String columnName = metaData.getColumnName(i);
            String columnType = metaData.getColumnClassName(i);
            boolean isAutoIncrement = metaData.isAutoIncrement(i);

            // 判断字段是否时间类型，如是则替换成data类型
            if (columnType.equals(Timestamp.class.getName()))
                columnType = Date.class.getName();

            // 把列字段类型放入表定义的引入集合
            table.addImport(columnType);

            // 初始化列定义
            ColumnDefined column = new ColumnDefined();
            column.setColumnName(columnName);
            column.setColumnType(columnType);
            column.setAutoIncrement(isAutoIncrement);
            // 获得属性的类型缩写
            column.setColumnTypeName(columnType.substring(columnType.lastIndexOf(".") + 1));
            // 字段名称转成属性名称
            column.setPropertyName(CommonUtils.toCamel(columnName));

            column.setComment(columnCommentMap.get(columnName));
            // 设置表定义的Key
            if (i == 1 || isAutoIncrement)
                table.setKey(columnName);

            // 增加字段到表定义中
            table.addColumn(column);
        }
        System.out.println(this.getClass().getName() + ".tableToDefined end:table="
                + table.getTableName() + ";cols=" + table.getColumns().size());
    }

}
