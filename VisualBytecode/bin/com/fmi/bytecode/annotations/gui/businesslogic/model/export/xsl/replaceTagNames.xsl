<?xml version="1.0" ?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
<xsl:output method="xml" indent="yes"/> 


<xsl:template match="projects">
     <projects-data> <xsl:apply-templates/> </projects-data>
</xsl:template>


<xsl:template match="project">
     <project-data projectName="{@name}">
            <xsl:apply-templates/>
     </project-data>
</xsl:template>


<xsl:template match="content">
     <content-data contentName="{@name}">
            <xsl:apply-templates/>
     </content-data>
</xsl:template>


<xsl:template match="package">
     <package-data packageName="{@name}">
            <xsl:apply-templates/>
     </package-data>
</xsl:template>


<xsl:template match="class">
     <class-data className="{@name}">
            <xsl:apply-templates/>
     </class-data>
</xsl:template>


<xsl:template match="annotations">
     <annotations-data> <xsl:apply-templates/> </annotations-data>
</xsl:template>

<xsl:template match="methods">
     <methods-data> <xsl:apply-templates/> </methods-data>
</xsl:template>

<xsl:template match="constructors">
	<constructors-data> <xsl:apply-templates/> </constructors-data>
</xsl:template>

<xsl:template match="fields">
	<fields-data> <xsl:apply-templates/> </fields-data>
</xsl:template>

<xsl:template match="method">
     <method-data methodName="{@name}" methodType="{@type}">
            <xsl:apply-templates/>
     </method-data>
</xsl:template>

<xsl:template match="annotation">
     <annotation-data annotationName="{@name}" >
            <xsl:apply-templates/>
     </annotation-data>
</xsl:template>

<xsl:template match="constructor">
     <constructor-data constructorName="{@name}">
            <xsl:apply-templates/>
     </constructor-data>
</xsl:template>


<xsl:template match="field">
     <field-data fieldName="{@name}" fieldType="{@type}">
            <xsl:apply-templates/>
     </field-data>
</xsl:template>

<xsl:template match="parameters">
	<parameters-data> <xsl:apply-templates/> </parameters-data>
</xsl:template>

<xsl:template match="parameter">
     <parameter-data parameterType="{@type}">
            <xsl:apply-templates/>
     </parameter-data>
</xsl:template>

<xsl:template match="annotation-attribute">
     <annotationAttribute-data annotationAttributeName="{@name}" annotationAttributeValue="{@value}">
            <xsl:apply-templates/>
     </annotationAttribute-data>
</xsl:template>

<xsl:template match="array-attribute">
     <arrayAttribute-data arrayAttributeOrder="{@order}" arrayAttributeValue="{@value}">
            <xsl:apply-templates/>
     </arrayAttribute-data>
</xsl:template>


</xsl:stylesheet>
