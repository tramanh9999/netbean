<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : result-models.xsl
    Created on : July 15, 2019, 5:08 PM
    Author     : NhanTT
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:import href="model-list.xsl"/>
    <xsl:output method="html" indent="yes"/>
    
    <xsl:param name="pageSize" select="60"/>
    
    <xsl:template match="/">
        <div class="container">
            <xsl:apply-templates/>
        </div>
    </xsl:template>

</xsl:stylesheet>
