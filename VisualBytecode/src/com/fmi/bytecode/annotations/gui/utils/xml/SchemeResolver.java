package com.fmi.bytecode.annotations.gui.utils.xml;

import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;

public class SchemeResolver implements EntityResolver {
    
    public SchemeResolver() {
    }

    public InputSource resolveEntity(String publicId, String systemId) {
        //System.out.println("publicId = " + publicId);
        //System.out.println("systemId" + systemId);
        return null;
    }
}
