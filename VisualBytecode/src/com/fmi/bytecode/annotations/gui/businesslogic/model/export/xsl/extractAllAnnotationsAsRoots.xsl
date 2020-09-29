<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
<xsl:output method="xml" indent="yes"/> 


<xsl:template match="projects">
	<annotations> <xsl:apply-templates/> </annotations>
</xsl:template>


<xsl:template match="/projects/project/content/package/class">
	<xsl:for-each select="annotations">
		<xsl:apply-templates> 
			<xsl:with-param name="pathToClass" select=".."/>
		</xsl:apply-templates> 
     	</xsl:for-each> 

	<xsl:for-each select="methods/method | constructors/constructor">
		<xsl:for-each select="annotations">
			<xsl:apply-templates> 
				<xsl:with-param name="pathToClass" select="../../.."/>
			</xsl:apply-templates> 
		</xsl:for-each>
		<xsl:for-each select="parameters/parameter/annotations">
			<xsl:apply-templates> 
				<xsl:with-param name="pathToClass" select="../../../../.."/>
			</xsl:apply-templates>
	     	</xsl:for-each>
	</xsl:for-each>
</xsl:template>


<xsl:template match="annotation">
	<xsl:param name="pathToClass" select="."/>

	
	<xsl:variable name="annotatedElementName" select="local-name(../..)"/> <!--method, field, constructor, parameter, class-->
    
	<!--xsl:element name="{$annotatedElementName}">		
		<xsl:attribute name="parent"/>
  	</xsl:element-->


	<xsl:variable name="projectName" select="$pathToClass/../../../@name"/>
     	<xsl:variable name="contentName" select="$pathToClass/../../@name"/>
     	<xsl:variable name="packageName" select="$pathToClass/../@name"/>
     	<xsl:variable name="className" select="$pathToClass/@name"/>
     	<xsl:variable name="parentName" select="local-name(..)"/>


	<annotation name="{@name}">
		<xsl:if test="$parentName='annotations'">
	       		<xsl:call-template name="createLocation">
		        	<xsl:with-param name="projectName" select="$projectName"/>
	                	<xsl:with-param name="contentName" select="$contentName"/>
		                <xsl:with-param name="packageName" select="$packageName"/>
	        	   	<xsl:with-param name="className" select="$className"/>
				<xsl:with-param name="annotatedElementName" select="$annotatedElementName"/>
	    		</xsl:call-template> 
	       	</xsl:if>

               	<xsl:element name="attributes">
               		<xsl:apply-templates/>
           	</xsl:element>
	</annotation>
</xsl:template>


<xsl:template name="createLocation">
     	<xsl:param name="projectName"/>
	<xsl:param name="contentName"/>
     	<xsl:param name="packageName"/>
     	<xsl:param name="className"/>
   	<xsl:param name="annotatedElementName"/>

	<xsl:element name="location">
		<xsl:call-template name="createElement1attr">
        	        <xsl:with-param name="elName">project</xsl:with-param>
			<xsl:with-param name="attrName">name</xsl:with-param>
                	<xsl:with-param name="attrValue" select="$projectName"/>
	     	</xsl:call-template>
		<xsl:call-template name="createElement1attr">
            		<xsl:with-param name="elName">content</xsl:with-param>
		        <xsl:with-param name="attrName">name</xsl:with-param>
                        <xsl:with-param name="attrValue" select="$contentName"/>
		</xsl:call-template>
		<xsl:call-template name="createElement1attr">
            	     	<xsl:with-param name="elName">package</xsl:with-param>
		        <xsl:with-param name="attrName">name</xsl:with-param>
                        <xsl:with-param name="attrValue" select="$packageName"/>
	        </xsl:call-template>
		<xsl:call-template name="createElement1attr">
            		<xsl:with-param name="elName">class</xsl:with-param>
		        <xsl:with-param name="attrName">name</xsl:with-param>
                        <xsl:with-param name="attrValue" select="$className"/>
	        </xsl:call-template>
		

		
		<xsl:if test="not($annotatedElementName='class')">
			<xsl:variable name="name" select="../../@name"/>

			
			<xsl:if test="$annotatedElementName='method' or $annotatedElementName='field'">  
				<xsl:call-template name="createElement2attr">
					<xsl:with-param name="elName" select="$annotatedElementName"/>
					<xsl:with-param name="attrName1">name</xsl:with-param>
					<xsl:with-param name="attrValue1" select="$name"/>
					<xsl:with-param name="attrName2">type</xsl:with-param>
					<xsl:with-param name="attrValue2" select="../../@type"/>
				</xsl:call-template>
			</xsl:if>
			<xsl:if test="$annotatedElementName='constructor'">  
				<xsl:call-template name="createElement1attr">
					<xsl:with-param name="elName" select="$annotatedElementName"/>
					<xsl:with-param name="attrName">name</xsl:with-param>
					<xsl:with-param name="attrValue" select="$name"/>
				</xsl:call-template>
			</xsl:if>

			<xsl:if test="$annotatedElementName='parameter'">  
				<xsl:call-template name="createElement2attr">
					<xsl:with-param name="elName" select="local-name(../../../..)"/>
					<xsl:with-param name="attrName1">name</xsl:with-param>
					<xsl:with-param name="attrValue1" select="../../../../@name"/>
					<xsl:with-param name="attrName2">type</xsl:with-param>
					<xsl:with-param name="attrValue2" select="../../../../@type"/>
				</xsl:call-template>


				<xsl:call-template name="createElement2attr">
					<xsl:with-param name="elName" select="$annotatedElementName"/>
					<xsl:with-param name="attrName1">type</xsl:with-param>
					<xsl:with-param name="attrValue1" select="../../@type"/>
					<xsl:with-param name="attrName2">order</xsl:with-param>
					<xsl:with-param name="attrValue2" select="../../@order"/>
				</xsl:call-template>
			</xsl:if>
		</xsl:if>
	</xsl:element>
</xsl:template>


<xsl:template name="createElement1attr">
     	<xsl:param name="elName"/>
     	<xsl:param name="attrName"/>
     	<xsl:param name="attrValue"/>
     	<xsl:element name="{$elName}">
	  	<xsl:attribute name="{$attrName}">
	       		<xsl:value-of select="$attrValue"/>
	  	</xsl:attribute >	   
    	</xsl:element>	  
</xsl:template>


<xsl:template name="createElement2attr">
	<xsl:param name="elName"/>
     	<xsl:param name="attrName1"/>
     	<xsl:param name="attrValue1"/>
     	<xsl:param name="attrName2"/>
     	<xsl:param name="attrValue2"/>

	<xsl:element name="{$elName}">
	  	<xsl:attribute name="{$attrName1}">
	       		<xsl:value-of select="$attrValue1"/>
	  	</xsl:attribute >
	  	<xsl:attribute name="{$attrName2}">
	       		<xsl:value-of select="$attrValue2"/>
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