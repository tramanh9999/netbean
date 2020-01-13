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
    <xsl:output method="xml" indent="no" version="1.0"/>
    <!-- TODO customize transformation rules 
         syntax recommendation http://www.w3.org/TR/xslt 
    -->
        <xsl:template match="name">
            <xsl:element name="Match">
                <xsl:attribute name="Name">
                    <xsl:value-of select="firstname"/>
                    <xsl:text> </xsl:text>
                    <xsl:value-of select="lastname"/>
                </xsl:attribute>
                We found a name
            </xsl:element>
        </xsl:template>
<!--    <xsl:template match="/">
    <html>
        <head>
            <title>template.xsl</title>
        </head>
        <body>
        </body>
    </html>
</xsl:template>-->

</xsl:stylesheet>
