package com.fmi.bytecode.annotations.gui.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.net.URL;

public class ResourceUtils {

    public static final byte[] readResourceData(String resourcePath) {
        InputStream is = getResourceStream(resourcePath);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        
        int i;

        try {
            while ((i = is.read()) != -1) {
                baos.write(i);
            }
        } catch (IOException e) {
            throw new IllegalStateException("Cannot load resource " + resourcePath);
        }
        
        return baos.toByteArray();
    }
    
    public static final InputStream getResourceStream(String resourcePath) {
        InputStream is = ResourceUtils.class.getResourceAsStream(resourcePath);
        return is;
    }

    public static final URL getResourceURL(String resourcePath) {
        return ResourceUtils.class.getResource(resourcePath);
    }
}
