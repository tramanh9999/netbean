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
    <xsl:output method="xml" indent="no" omit-xml-declaration="no" version="1.0"/>

    <xsl:template match="bbb">
        <mb>conflict 1 - bbb</mb>
    </xsl:template>
    <xsl:template match="ccc">
        <mc>conflict 1 - ccc</mc>
    </xsl:template>
</xsl:stylesheet>
