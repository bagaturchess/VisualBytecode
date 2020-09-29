<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
<xsl:output method="xml" indent="yes"/> 


<xsl:template match="projects">
     <class-annotations> <xsl:apply-templates/> </class-annotations>
</xsl:template>

<xsl:template match="/projects/project/content/package/class">
     <xsl:for-each select="annotations">
	  <xsl:apply-templates/> 
     </xsl:for-each>
</xsl:template>


<xsl:template match="annotation">
     <xsl:variable name="projectName" select="../../../../../@name"/>
     <xsl:variable name="contentName" select="../../../../@name"/>
     <xsl:variable name="packageName" select="../../../@name"/>
     <xsl:variable name="className" select="../../@name"/>
     <xsl:variable name="parentName" select="local-name(..)"/>
     
     <annotation name="{@name}">
     	   <xsl:if test="$parentName='annotations'">
	     <xsl:element name="location">
		  <xsl:call-template name="createElement">
            	     	     <xsl:with-param name="elName">project</xsl:with-param>
		             <xsl:with-param name="attrName">name</xsl:with-param>
                             <xsl:with-param name="attrValue" select="$projectName"/>
 		
	           </xsl:call-template>
 		   <xsl:call-template name="createElement">
            	     	     <xsl:with-param name="elName">content</xsl:with-param>
		             <xsl:with-param name="attrName">name</xsl:with-param>
                             <xsl:with-param name="attrValue" select="$contentName"/>
	           </xsl:call-template>
		   <xsl:call-template name="createElement">
            	     	     <xsl:with-param name="elName">package</xsl:with-param>
		             <xsl:with-param name="attrName">name</xsl:with-param>
                             <xsl:with-param name="attrValue" select="$packageName"/>
	           </xsl:call-template>
		   <xsl:call-template name="createElement">
            	     	     <xsl:with-param name="elName">class</xsl:with-param>
		             <xsl:with-param name="attrName">name</xsl:with-param>
                             <xsl:with-param name="attrValue" select="$className"/>
	           </xsl:call-template>
	     </xsl:element>	
           </xsl:if>
	  
            <xsl:element name="attributes">
                   <xsl:apply-templates/>
           </xsl:element>
     </annotation>
</xsl:template>




<xsl:template name="createElement">
     <xsl:param name="elName"/>
     <xsl:param name="attrName"/>
     <xsl:param name="attrValue"/>
     <xsl:element name="{$elName}">
	  <xsl:attribute name="{$attrName}">
	       <xsl:value-of select="$attrValue"/>
	  </xsl:attribute >	   
    </xsl:element>	  
</xsl:template>



<xsl:template match="annotation-attribute">
     <annotation-attribute name="{@name}" value="{@value}">
            <xsl:apply-templates/>
     </annotation-attribute>
</xsl:template>

<xsl:template match="array-attribute">
     <array-attribute order="{@order}" value="{@value}">
            <xsl:apply-templates/>
     </array-attribute>
</xsl:template>

</xsl:stylesheet>