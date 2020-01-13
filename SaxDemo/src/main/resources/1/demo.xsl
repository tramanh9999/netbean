<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : demo.xsl
    Created on : December 29, 2019, 11:27 PM
    Author     : ADMIN
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html"/>
    
    <!--
    tìm kiếm element gốc, thay element trong file bằng các thẻ trên -->
    <xsl:template match="/">
        <html>
            <body>
                <h1>My final template</h1>
                <xsl:apply-templates select="persons/person">
                    <xsl:sort select="lastname"></xsl:sort>
                </xsl:apply-templates>
            </body>
        </html>
    </xsl:template>
    <xsl:template match="lastname">
        <span style="color:red">
            <xsl:apply-templates select="/"/>
        </span>
    </xsl:template>
</xsl:stylesheet>
