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
    <xsl:output method="xml" indent="yes"/>
    <xsl:template match="/">
        <xsl:call-template name="startHtml"/>
    </xsl:template>
    <xsl:template name="startHtml">
        <html>
            <body>
                <h1>Persons</h1>
                <ul>
                    <xsl:apply-templates select="persons/person">
                        <xsl:sort select="lastname"></xsl:sort>
                    </xsl:apply-templates>
                </ul>
            </body>
        </html>
    </xsl:template>
    <xsl:template match="person[not(@real='no')]">
        <li style="color:blue;">
            <xsl:apply-templates/>
        </li>
    </xsl:template>
    <xsl:template match="person">
        <li style="color:red;">
            <xsl:apply-templates/>
        </li>
    </xsl:template>
    <xsl:template match="name">
        <xsl:value-of select="lastname"/>,
        <xsl:value-of select="firstname"/>
    </xsl:template>
</xsl:stylesheet>
