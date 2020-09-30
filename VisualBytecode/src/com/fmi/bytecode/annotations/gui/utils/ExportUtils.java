package com.fmi.bytecode.annotations.gui.utils;

import com.fmi.bytecode.annotations.element.AnnotationRecord;
import com.fmi.bytecode.annotations.element.ClassInfo;

import com.fmi.bytecode.annotations.element.ConstructorInfo;

import com.fmi.bytecode.annotations.element.FieldInfo;
import com.fmi.bytecode.annotations.element.MethodInfo;

import com.fmi.bytecode.annotations.element.NamedMember;

import sun.awt.shell.ShellFolder;

import java.io.File;

import java.util.List;

import java.util.Map;

import java.util.Set;

import javax.xml.XMLConstants;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;

import javax.xml.transform.TransformerFactory;

import javax.xml.transform.stream.StreamSource;

import com.fmi.bytecode.annotations.gui.businesslogic.model.projects.ProjectModel;

import com.fmi.bytecode.annotations.gui.businesslogic.model.export.ExportConfiguration;
import com.fmi.bytecode.annotations.gui.businesslogic.model.export.ExportModel;

import org.w3c.dom.Document;

import org.w3c.dom.Element;

import com.fmi.bytecode.annotations.gui.businesslogic.model.export.SaveUnit;

import com.fmi.bytecode.annotations.gui.utils.xml.XMLException;
import com.fmi.bytecode.annotations.gui.utils.xml.XMLUtils;


public class ExportUtils {
    
    public static final String DEFAULT_XML_FILE_NAME = "AMExport.xml";
    public static final String DEFAULT_XML_DEFAULT_DIR = //System.getProperty("user.home");
        ((File) ShellFolder.get("fileChooserDefaultFolder")).getAbsolutePath(); //My documents
    
    public static final String SCHEME_PREFIX = "xmlns:xsi";
    //public static final String SCHEME_INST_ATTR = "http://www.w3.org/2001/XMLSchema-instance";
    public static final String SCHEME_LOC = "xsi:noNamespaceSchemaLocation";
    public static final String SCHEME = "amp.xsd";
     
    public static final String AMP_SCHEME = "/com/fmi/bytecode/annotations/gui/businesslogic/model/export/scheme/amp.xsd";
    public static final String DEFAULT_XML_SCHEME = AMP_SCHEME;
    
    public static Document createExportDoc(ExportModel exportModel, 
                                           ExportConfiguration exportConfig) throws XMLException {
        Document doc = XMLUtils.createNewDocument();
        Element root = doc.createElement(exportConfig.getTAG_PROJECTS());
        doc.appendChild(root);
        //root.setAttribute(SCHEME_LOC, SCHEME);       
        //root.setAttribute(SCHEME_PREFIX, SCHEME_INST_ATTR);   
        
         //root.setAttribute(SCHEME_LOC, SCHEME);       
         //root.setAttribute(SCHEME_PREFIX, XMLConstants.W3C_XML_SCHEMA_INSTANCE_NS_URI); 
         
                 
        List<SaveUnit> units = exportModel.getUnits();
        for (SaveUnit unit : units) {
            ProjectModel prj = unit.getProjectModel();
            File content = unit.getContentFile();
            String prjName = prj.getProjectName();
            String contentName = content.getAbsolutePath();
            
            Element projectElement = XMLUtils.getChild(root, exportConfig.getTAG_PROJECT(), 
                                                       exportConfig.getATTR_NAME(), prjName);
            if (projectElement == null) {  
                projectElement = XMLUtils.appendChild(root, exportConfig.getTAG_PROJECT());
                projectElement.setAttribute(exportConfig.getATTR_NAME(), prjName);
            } 
            
            Element contentElement = XMLUtils.getChild(projectElement, exportConfig.getTAG_CONTENT(), 
                                                       exportConfig.getATTR_NAME(), contentName);
            if (contentElement == null) {
                contentElement = XMLUtils.appendChild(projectElement, exportConfig.getTAG_CONTENT());
                contentElement.setAttribute(exportConfig.getATTR_NAME(), contentName);
            } 
             
            appendClasses(contentElement, unit.getClasses(), exportConfig);           
        }
        
        return doc;
    }
    
        
    private static void appendClasses(Element childContent, Set<ClassInfo> classInfos,
                                      ExportConfiguration exportConfig) {
        for (ClassInfo classInfo: classInfos) {
            String[] packageAndName = ProjectModel.separateNameAndPackage(classInfo.getName());
            String className = packageAndName[0];
            String packageName = packageAndName[1] == null ? "" : packageAndName[1];
            
            Element packageElement = XMLUtils.getChild(childContent, exportConfig.getTAG_PACKAGE(), 
                                                       exportConfig.getATTR_NAME(), packageName);
            if (packageElement == null) {
                packageElement = XMLUtils.appendChild(childContent, exportConfig.getTAG_PACKAGE());
                packageElement.setAttribute(exportConfig.getATTR_NAME(), packageName);
            }
            
            Element classElement = XMLUtils.getChild(packageElement, exportConfig.getTAG_CLASS(), 
                                                     exportConfig.getATTR_NAME(), className);
            if (classElement == null) {
                classElement = XMLUtils.appendChild(packageElement, exportConfig.getTAG_CLASS());
                classElement.setAttribute(exportConfig.getATTR_NAME(), className);
            } 

            appendClass(classElement, classInfo, exportConfig);
        }
    }    
    
    
    private static void appendClass(Element classElement, ClassInfo classInfo,
                                    ExportConfiguration exportConfig) {
                
        appendAnnotations(classElement, classInfo.getAnnotations(), exportConfig);
        
        //Constructor
        ConstructorInfo[] constructors = classInfo.getConstructors();
        //append tag contrcors
        Element constructorsElement = null;
        if (constructors != null && constructors.length != 0) {
            constructorsElement = XMLUtils.appendChild(classElement, exportConfig.getTAG_CONSTRUCTORS());            
        }
        //append constructor tags
        for (ConstructorInfo constructor : constructors) {
            Element constructorElement = XMLUtils.appendChild(constructorsElement, exportConfig.getTAG_CONSTRUCTOR());
            constructorElement.setAttribute(exportConfig.getATTR_NAME(), constructor.getName());
            appendAnnotations(constructorElement, constructor.getAnnotations(), exportConfig);
            appendParameters(constructorElement, constructor, exportConfig);
        }
        
        
        //Method
        MethodInfo[] methods = classInfo.getMethods();
        //append tag methods
        Element methodsElement = null;
        if (methods != null && methods.length != 0) {
            methodsElement = XMLUtils.appendChild(classElement, exportConfig.getTAG_METHODS());
        }
        //append method tags
        for (MethodInfo method : methods) {
            Element methodElement = XMLUtils.appendChild(methodsElement, exportConfig.getTAG_METHOD());
            methodElement.setAttribute(exportConfig.getATTR_NAME(), method.getName());  
            methodElement.setAttribute(exportConfig.getATTR_TYPE(), method.getReturnType());
            appendAnnotations(methodElement, method.getAnnotations(), exportConfig);
            appendParameters(methodElement, method, exportConfig);         
        }
        
        
        //Field
        FieldInfo[] fields = classInfo.getFields();
        Element fieldsElement = null;
        if (fields != null && fields.length != 0) {
            fieldsElement = XMLUtils.appendChild(classElement, exportConfig.getTAG_FIELDS());
        }
        for (FieldInfo field : fields) {
            Element fieldElement = XMLUtils.appendChild(fieldsElement, exportConfig.getTAG_FIELD());
            fieldElement.setAttribute(exportConfig.getATTR_NAME(), field.getName());  
            fieldElement.setAttribute(exportConfig.getATTR_TYPE(), field.getType());
            appendAnnotations(fieldElement, field.getAnnotations(), exportConfig);
        }
    }
    
    /*public static void appendMethods(Element classElement,
                                     MethodInfo[] methodInfos,
                                     ExportConfiguration exportConfig) {
        //Method
        //da go iznesa na gotno nivo MethodInfo[] methods = classInfo.getMethods();

        String tagMethods = (methodInfos instanceof ConstructorInfo[]) ? exportConfig.getTAG_CONSTRUCTORS() :
                                                                         exportConfig.getTAG_METHODS();
        String tagMethod = (methodInfos instanceof ConstructorInfo[]) ? exportConfig.getTAG_CONSTRUCTOR() :
                                                                        exportConfig.getTAG_METHOD();
        
        //append tag methods
        Element methodsElement = null;
        if (methodInfos != null && methodInfos.length != 0) {
            methodsElement = XMLUtils.appendChild(classElement, tagMethods);
        }
        //append method tags
        for (MethodInfo methodInfo : methodInfos) {
            Element methodElement = XMLUtils.appendChild(methodsElement, tagMethod);
            methodElement.setAttribute(exportConfig.getATTR_NAME(), methodInfo.getName());  
            if (!(methodInfo instanceof ConstructorInfo)) {
                methodElement.setAttribute(exportConfig.getATTR_TYPE(), methodInfo.getReturnType());
            }    
            appendAnnotationsTest(methodElement, methodInfo.getAnnotations(), exportConfig);
            appendParametersTest(methodElement, methodInfo, exportConfig);         
        }
    }*/
    
    public static void appendAnnotations(Element parent,
                                         Map<String, AnnotationRecord> annotations,
                                         ExportConfiguration exportConfig) {
         
        //append annotations tag
        Element annotationsElement = null;
        if (annotations != null && annotations.size() != 0) {
            annotationsElement = XMLUtils.appendChild(parent, exportConfig.getTAG_ANNOTATIONS());
        }
        //append annotation tags
        for (String currentAnnotName : annotations.keySet()) {
            AnnotationRecord currentAnnotation = annotations.get(currentAnnotName);           
            appendAnnotationMembers(annotationsElement, currentAnnotation, exportConfig);          
        }  
    }
    
    public static void appendParameters(Element methodElement,
                                            MethodInfo method,
                                            ExportConfiguration exportConfig) {
        String[] params = method.getParameters();
        Element parametersElement = null;
        if (params != null && params.length != 0)  {
            parametersElement = XMLUtils.appendChild(methodElement, exportConfig.getTAG_PARAMETERS());
        }
        
        Map<String, AnnotationRecord>[] parameterAnnotations = 
                                           method.getParameterAnnotations();
        boolean isParamMapEmpty = true;
        if (parameterAnnotations != null && parameterAnnotations.length > 0) {
            isParamMapEmpty = false;
        }
        
        //append parameter tags
        for (int i = 0; i < params.length; i++) {
            Element parameterElement = XMLUtils.appendChild(parametersElement, exportConfig.getTAG_PARAMETER());
            parameterElement.setAttribute(exportConfig.getATTR_TYPE(), params[i]);
            parameterElement.setAttribute(exportConfig.getATTR_ORDER(), "" + i);
            if (!isParamMapEmpty) {
                appendAnnotations(parameterElement, parameterAnnotations[i], exportConfig);
            }    
        }  
    }

      
    private static void appendAnnotationMembers(Element parent, 
                                                AnnotationRecord currentAnnotation,
                                                ExportConfiguration exportConfig) {
        //append annotation tag
        Element annotationElement = XMLUtils.appendChild(parent, exportConfig.getTAG_ANNOTATION());
        annotationElement.setAttribute(exportConfig.getATTR_NAME(), currentAnnotation.getTypeName());                                        
        
        //append annotation-attribute tags
        Map<String, NamedMember> namedMember = currentAnnotation.getNamedMembersMap();
        for (String currentMemberName : namedMember.keySet()) {
            NamedMember member = namedMember.get(currentMemberName);
            Element annotationAttributeElement = XMLUtils.appendChild(annotationElement, exportConfig.getTAG_ANNOTATION_ATTRIBUTE());
            processSingleMember(annotationAttributeElement, member, exportConfig, false);
        }  
    }
    
    //process annotation attributes
    private static void processSingleMember(Element currentElement, NamedMember member,
                                            ExportConfiguration exportConfig, boolean isArrayMember) {
         String memberName = (member.getName() == null || member.getName().trim().equals("")) 
                              ? exportConfig.getATTR_VALUE() : member.getName();
         Object memberValue = member.getMemberValue();
         
         int memberType = member.getMemberTag();        
         switch (memberType) {
         	case '@': //Annotation
                 //currentElement.setAttribute(exportConfig.getATTR_NAME(), memberName); //name = name
                 //currentElement.setAttribute(exportConfig.getATTR_VALUE(), exportConfig.getTAG_ANNOTATION()); //value = array
                 appendAnnotationMembers(currentElement, (AnnotationRecord) memberValue, exportConfig);
                 memberValue = exportConfig.getTAG_ANNOTATION();
                 break;
         	case '[': //Array
                 //TODO: da vidim
                 //currentElement.setAttribute(exportConfig.getATTR_NAME(), memberName); //name = name
                 //currentElement.setAttribute(exportConfig.getATTR_VALUE(), exportConfig.getATTR_ARRAY()); //value = array
                 
                 NamedMember[] members = member.getMemberArrayValue();
                 for (int i = 0; i < members.length; i++) {
                     Element arrayAttributeElement = XMLUtils.appendChild(currentElement, 
                                                        exportConfig.getTAG_ARRAY_ATTRIBUTE()); //array-attribute
                     arrayAttributeElement.setAttribute(exportConfig.getATTR_ORDER(),  "" + i);
                     NamedMember currentMember = members[i];
                     processSingleMember(arrayAttributeElement, currentMember, exportConfig, true);
                 }
                 memberValue =  exportConfig.getATTR_ARRAY();
                 break;
         	case 'e': //Enum
                  memberValue = 
                         member.getEnumConstantValue().getEnumerationLiteral();
                  break;
         }
         
         if (!isArrayMember) {
            currentElement.setAttribute(exportConfig.getATTR_NAME(), memberName);  
         }   
         currentElement.setAttribute(exportConfig.getATTR_VALUE(), memberValue.toString());                           
     }  
     
     public static void test() {
         //TransformerFactory  tFactory = TransformerFactory.newInstance();
          //object that performs a copy of the source to the result
         //Transformer transformer = tFactory.newTransformer();
         //newTemplates(Source source) is used to process the Source into a Templates object, which is a a compiled representation of the source.
         //Templates template = tFactory.newTemplates(new StreamSource("E:\\topxml\\parameterized.xsl"));
     }
}
