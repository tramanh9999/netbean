<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : template.xsl
    Created on : December 18, 2019, 4:45 PM
    Author     : ADMIN
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <!--<xsl:output method="xml" indent="no" omit-xml-declaration="no" version="1.0"/>-->
    <xsl:output method="html"/>
    <xsl:template match="/">
        <html>
            <body>
                <xsl:apply-templates/>
            </body>
        </html>
    </xsl:template>
    <xsl:template match="CD">
        <p>
            <xsl:apply-templates select="TITLE"></xsl:apply-templates>
            <xsl:apply-templates select="ARTIST"></xsl:apply-templates>
        </p>        
    </xsl:template>
    
    <xsl:template match="TITLE">
        title: <span style="color:#ff0000">
            <xsl:value-of select="."/>
        </span>
        <br/>
    </xsl:template>
    <xsl:template match="ARTIST">
        artist: <span style="color:#AA3223">
            <xsl:value-of select="."/>
        </span>
        <br/>
    </xsl:template>
   
</xsl:stylesheet>
