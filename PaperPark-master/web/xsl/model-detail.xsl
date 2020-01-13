<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : model-detail.xsl
    Created on : July 15, 2019, 5:55 PM
    Author     : NhanTT
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:import href="main-model.xsl"/>
    <xsl:import href="model-list.xsl"/>
    <xsl:output method="html" indent="yes"/>
    <xsl:template match="/">
        <div class="container">
            <div class="columns">
                <xsl:apply-templates/>
            </div>
        </div>
    </xsl:template>

</xsl:stylesheet>
