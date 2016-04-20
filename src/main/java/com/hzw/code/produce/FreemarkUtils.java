package com.hzw.code.produce;

import java.io.File;
import java.io.IOException;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.Version;

public class FreemarkUtils {
    private static Configuration cfg = null;

    private static void init() {
        try {
            String path = JavaFileProduceUtils.class.getResource("/").getFile().toString();
            Version version = new Version("2.3.23");
            cfg = new Configuration(version);
            // 指定一个加载模版的数据源
            // 这里我设置模版的根目录

            cfg.setDirectoryForTemplateLoading(new File(path + File.separator + "templates"));

            // 指定模版如何查看数据模型.这个话题是高级主题…
            // 你目前只需要知道这么用就可以了:
            cfg.setObjectWrapper(new DefaultObjectWrapper(version));
        } catch (IOException e) {
            System.out.println("start freemark Configuration errer ");
            e.printStackTrace();
        }

    }

    public static Template getTemplate(String templateName) {
        try {
            if (cfg == null)
                init();
            return cfg.getTemplate(templateName);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
}
