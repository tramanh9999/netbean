<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : applymutltemplate.xsl
    Created on : December 24, 2019, 9:45 AM
    Author     : ADMIN
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="xml" indent="yes"  version="1.0"  />

    <!-- TODO customize transformation rules 
         syntax recommendation http://www.w3.org/TR/xslt 
    -->
    <xsl:template match="/">
        <Matches>
            <xsl:apply-templates  select="*"/>
        </Matches>
    </xsl:template>
    <xsl:template match="name">
        <New>
            <Match>
                <xsl:value-of select="lastname"/>
            </Match>
            <Name>
                <xsl:value-of select="."/>
            </Name>
        </New>
    </xsl:template>
</xsl:stylesheet>
