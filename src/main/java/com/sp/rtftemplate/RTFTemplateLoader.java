package com.sp.rtftemplate;

import freemarker.cache.TemplateLoader;
import org.apache.commons.io.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

/**
 * Created by deepu on 9/4/15.
 */
public class RTFTemplateLoader implements TemplateLoader {

    private TemplateLoader delegateTemplateLoader;

    public RTFTemplateLoader(TemplateLoader delegateTemplateLoader) {
        this.delegateTemplateLoader = delegateTemplateLoader;
    }

    @Override
    public Object findTemplateSource(String paramString) throws IOException {
        return delegateTemplateLoader.findTemplateSource(paramString);
    }

    @Override
    public long getLastModified(Object paramObject) {
        return delegateTemplateLoader.getLastModified(paramObject);
    }

    @Override
    public Reader getReader(Object paramObject, String paramString) throws IOException {
        Reader reader = delegateTemplateLoader.getReader(paramObject, paramString);
        String templateContent = IOUtils.toString(reader);
        String evaluatableTemplateContent = templateContent.replaceAll("%<(.*?)>","\\${$1}");
        IOUtils.closeQuietly(reader);
        return new StringReader(evaluatableTemplateContent);
    }

    @Override
    public void closeTemplateSource(Object paramObject) throws IOException {
        delegateTemplateLoader.closeTemplateSource(paramObject);
    }
}
