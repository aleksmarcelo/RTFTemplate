package com.sp.rtftemplate;

import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.Version;
import org.junit.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.*;

public class RTFTemplateLoaderTest {


    public static class DummyData{
        private double someNumber;
        private String someString;

        public DummyData(double someNumber, String someString) {
            this.someNumber = someNumber;
            this.someString = someString;
        }

        public double getSomeNumber() {
            return someNumber;
        }

        public String getSomeString() {
            return someString;
        }
    }

    @Test
    public void testRun() throws IOException, TemplateException {
        Configuration configuration = new Configuration(new Version("2.3.21"));
        configuration.setTemplateLoader(new RTFTemplateLoader(new ClassTemplateLoader(RTFTemplateLoaderTest.class,"/")));

        Template template = configuration.getTemplate("TestTemplate.rtf");
        Writer writer = new FileWriter("/tmp/test.rtf");
        Map<String,Object> data = getDummyData();
        template.process(data, writer);

        writer.close();
    }

    private HashMap<String, Object> getDummyData() {
        HashMap<String, Object> data = new HashMap<String, Object>();
        data.put("current_date", new Date());
        data.put("dummyData", getSomeList(100));
        return data;
    }

    private List<DummyData> getSomeList(int count) {
        List<DummyData> returnList = new ArrayList<DummyData>();
        for (int i = 0; i < count; i ++){
            returnList.add(new DummyData(Math.random(), UUID.randomUUID().toString()));
        }
        return returnList;
    }

}