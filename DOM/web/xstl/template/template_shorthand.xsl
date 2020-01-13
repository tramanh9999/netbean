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
            <Matches>
                <Match Name="{firstname} {lastname}"> We found a name</Match>
            </Matches>
        </xsl:template>
</xsl:stylesheet>
