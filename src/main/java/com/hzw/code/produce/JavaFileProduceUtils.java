package com.hzw.code.produce;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;

import freemarker.template.Template;


public class JavaFileProduceUtils {
    // 表对应的实体类模版
    private TableDefined table;

    private String outPath;

    public JavaFileProduceUtils(TableDefined table, String outPath) {
        this.table = table;
        this.outPath = outPath;
    }

    private void createFile(String templateName, String fileName) throws Exception {
        System.out.println("start create file by:" + templateName);

        Template temp = FreemarkUtils.getTemplate(templateName);
        File file = new File(outPath + File.separator + fileName);

        Writer out = new FileWriter(file);
        // Writer out=new OutputStreamWriter(System.out);
        temp.process(table, out);
        out.flush();
        System.out.println("end create file by:" + templateName);
    }


    public void createFiles() throws Exception {
        if (table == null)
            return;
        String path = JavaFileProduceUtils.class.getResource("/").getFile().toString();
        if (outPath == null || outPath.length() < 1)
            outPath = path;

        outPath = outPath + File.separator + "out" + File.separator + table.getEntryName();
        File dir = new File(outPath);
        if (!dir.exists())
            dir.mkdirs();

        createStep();
    }

    private void createStep() throws Exception {
        File templateDir = new File(JavaFileProduceUtils.class.getResource("/templates").toURI());
        String[] templates = templateDir.list();
        for (String template : templates) {
            createFile(template,  
                    CommonUtils.firstCharUp(table.getEntryName()) + template.substring(0, template.lastIndexOf(".")));
        }
        
    }
}
