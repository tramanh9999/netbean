<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : templateHtml.xsl
    Created on : December 24, 2019, 9:56 AM
    Author     : ADMIN
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html" indent="yes"/>

    <!-- TODO customize transformation rules 
         syntax recommendation http://www.w3.org/TR/xslt 
    -->
    <xsl:template match="/">
        <html>
            <body>
                <xsl:apply-templates/>
            </body>
        </html>
    </xsl:template>


    <xsl:template match="Matches">
        <table border="2" width="75%">
            <tr>
                <th>Lastname</th>
                <th>Fullname</th>
            </tr>
            <xsl:for-each select="New">
                <tr>
                    <td><xsl:value-of select="Match"/></td>
                    <td><xsl:value-of select="Name"/></td>
                </tr>
            </xsl:for-each>
        </table>
        
    </xsl:template>

</xsl:stylesheet>
