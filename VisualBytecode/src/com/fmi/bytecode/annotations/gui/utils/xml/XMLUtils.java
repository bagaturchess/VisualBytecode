package com.fmi.bytecode.annotations.gui.utils.xml;

import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import java.io.PrintWriter;
import java.io.StringWriter;

import java.util.ArrayList;

import java.util.List;

import java.util.Vector;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import javax.xml.validation.Validator;

import com.fmi.bytecode.annotations.gui.businesslogic.model.projects.ProjectModel;


import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import org.xml.sax.SAXException;

import com.fmi.bytecode.annotations.gui.utils.ExportUtils;
import com.fmi.bytecode.annotations.gui.utils.ResourceUtils;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;


public class XMLUtils {
    /**
     * Header tag for saving XML Documents
     */
    public static final String XML_HEAD = "<?xml version='1.0' encoding='utf-8'?>";
    public static final String INDENT = "    ";
  
   
    public static Document createNewDocument() throws XMLException {
        DocumentBuilder builder = getDocumentBuilder();
        Document doc = builder.newDocument();
        return doc;
    }

    private static DocumentBuilder getDocumentBuilder() throws XMLException {
        DocumentBuilderFactory fac = getDocumentBuilderFactory(false);
        DocumentBuilder builder;
        try {
            builder = fac.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new XMLException("Error due to creating DocumentBuilder", e);
        }
        return builder;
    }
    
    private static DocumentBuilderFactory getDocumentBuilderFactory(boolean validate) throws XMLException {
        DocumentBuilderFactory fac = DocumentBuilderFactory.newInstance();
        fac.setValidating(validate);
        return fac;
    }
   
    public static Element appendChild(Element parent, String name, String value) {
        Document doc = parent.getOwnerDocument();
        Element child = doc.createElement(name);
        if (value != null) {
            child.setTextContent(value);
        }    
        parent.appendChild(child);
        return child;
    }
    
    public static Element appendChild(Element parent, String name) {
        return appendChild(parent, name, null);
    }
  
    public static void save(File output, Document doc) throws IOException {
        if (!output.exists()) {
            File homeDir = output.getParentFile();
            homeDir.mkdirs();
        }
        String xml = XML_HEAD + "\r\n" + toString(doc);
        DataOutputStream dos = null;
        try {
            byte[] buff = xml.getBytes("windows-1251");

            dos = new DataOutputStream(new FileOutputStream(output));
            dos.write(buff);
        } catch (IOException ex) {
            throw ex;
        } finally {
            try {
                dos.close();
            } catch (IOException e) {
                throw e;//new IOException(e.getMessage(), e);
            }
        }
    }
    
    private static String toString(Document doc) throws IOException {
        String result = "";
        StringWriter sw = new StringWriter();
        PrintWriter out = new PrintWriter(sw);
        printElement(null, null, (Element) doc.getFirstChild(), out, 0);
        result = sw.toString();
        return result.trim();
    }
                 
    private static void printElement(String nsPrefix, String uri, Element e, 
                                     PrintWriter out, int level) throws IOException {
        String prefix = (nsPrefix == null) ? "" : nsPrefix + ":";
        String suffix = (nsPrefix == null) ? "" : ":" +  nsPrefix;
        
        // Begin on new line
        out.println();

        // Print indent
        for (int i = 0; i < level; i++) {
            out.print(INDENT);
        }

        // Print tag open
        out.print("<" + prefix + e.getTagName());

        boolean nsAttribute = false;
        NamedNodeMap attributes = e.getAttributes();
        for (int i = 0; i < attributes.getLength(); i++) {
            String aName = attributes.item(i).getNodeName();
            out.print(" " + prefix + aName + "=\"" + 
                      formatValue(attributes.item(i).getNodeValue()) + "\"");
            if (aName.equals("xmlns" + suffix)) {
                nsAttribute = true;
            }
        }
        if (level == 0 && uri != null && !nsAttribute) {
            out.print(" xmlns" + suffix + "=\"" + uri + "\"");
        }

        NodeList childNodes = e.getChildNodes();
        // Close opening tag
        if (childNodes.getLength() == 0) {
            out.print("/>");
        } else {
            out.print(">");
            // Print child nodes
            for (int i = 0; i < childNodes.getLength(); i++) {
                if (childNodes.item(i).getNodeType() == Node.ELEMENT_NODE) {
                    printElement(nsPrefix, uri, (Element) childNodes.item(i), out, level + 1);
                } else if (childNodes.item(i).getNodeType() == Node.COMMENT_NODE){
                    out.println("\n");
                    // Print indent
                    for (int j = 0; j < level + 1; j++) {
                        out.print(INDENT);
                    }
                    out.print(
                        "<!--"+((Comment) childNodes.item(i)).getData()+"-->"
                    );
                } else {
                    String txt = ((Text) childNodes.item(i)).getData();
                    if (txt != null && !txt.trim().equals("")) {
                        if (hasChildElements(e)) {
                            out.println();
                            for (int j = 0; j < level + 1; j++)
                                out.print(INDENT);
                        }
                        out.print(formatValue(txt));
                    }
                }
            }
            if (hasChildElements(e)) {
                out.println();
                // Print indent
                for (int i = 0; i < level; i++)
                    out.print(INDENT);
            }

            // Print tag close
            out.print("</" + prefix + e.getTagName() + ">");
        }
    }

    private static boolean hasChildElements(Element e) {
        NodeList childNodes = e.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            if (childNodes.item(i).getNodeType() == Node.ELEMENT_NODE) {
                return true;
            }
        }
        return false;
    }
    
    private static String formatValue(String v) {
       return v;//return StringEscapeUtils.escapeXml(StringEscapeUtils.unescapeXml(v));
    }
    
   
    public static Document loadDocument(File projectFile) throws XMLException {
        DocumentBuilder builder = getDocumentBuilder();
        Document doc;

        try {
            doc = builder.parse(projectFile.getAbsolutePath());
        } catch (SAXException e) {
             throw new XMLException("Error due to loading project " + 
                                    projectFile.getAbsolutePath(), e);
        } catch (IOException e) {
             throw new XMLException("Error due to loading project " + 
                                    projectFile.getAbsolutePath(), e);
        }
        return doc;
    }
    
    public static List<Element> getChildren(Element node, String childName) {
        NodeList children = node.getChildNodes();
        Vector<Element> childrenVector = new Vector<Element>(children.getLength());

        for (int i = 0; i < children.getLength(); i++) {
            Node currentChild = children.item(i);
            if (currentChild.getNodeType() == Node.ELEMENT_NODE) {
                //if (childName == null) { childrenVector.add(currentChild); } else {//all children
                if (currentChild.getNodeName().equals(childName))
                    childrenVector.add((Element) currentChild); // }
            }
        }
        return childrenVector;
    }
    
    public static Element getChild(Element node, String childName,
                                   String attrName, String attrValue) {
        Element result = null;
        List<Element> children = getChildren(node, childName);
        for (Element child : children) {
            String attrVal = child.getAttribute(attrName);
            //System.out.println("attrValue= '" + attrValue + "'" + "-" +
              //                 "attrVal= '" + attrVal + "'");
            
            if (attrVal != null && attrVal.equals(attrValue)) {
                result = child;
                break;
            }
        }
        return result;
    }
      
    public static void validate(Document document) throws Exception {
         // parse an XML document into a DOM tree
         //DocumentBuilder parser = DocumentBuilderFactory.newInstance().newDocumentBuilder();
         //Document document = parser.parse(xmlFile);

         // create a SchemaFactory capable of understanding WXS schemas
         SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

         // load a WXS schema, represented by a Schema instance
         byte[] xsdData = ResourceUtils.readResourceData(ExportUtils.AMP_SCHEME);
         Source schemaFile = new StreamSource(new ByteArrayInputStream(xsdData));
         Schema schema = factory.newSchema(schemaFile);

         // create a Validator instance, which can be used to validate an instance document
         Validator validator = schema.newValidator();

         // validate the DOM tree
         try {
             validator.validate(new DOMSource(document));
         } catch (SAXException e) {
             // instance document is invalid!
         }
    }

    public static void transformXML(Document sourceDOM, String xslFilePath, File destination) throws Exception {
        TransformerFactory tFactory = TransformerFactory.newInstance();
        Source xslSource = new StreamSource(new FileInputStream(xslFilePath));
        Transformer transformer = tFactory.newTransformer(xslSource);
        DOMSource source = new DOMSource(sourceDOM);
        StreamResult result = new StreamResult(destination);
        transformer.transform(source, result);
    }
    
    /*public static void main(String[] args) {
        ProjectModel  prj;
        try {
            List<File> content = new ArrayList<File>();
            content.add(new File("C:"));
            prj = new ProjectModel("C:","Test",  content);
            //Document doc = createProjectDocument(prj);
            //save(new File("c:\\test.xml"), doc);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
}
