<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : firstXSL.xsl
    Created on : December 18, 2019, 11:43 AM
    Author     : ADMIN
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html"/>

    <!-- TODO customize transformation rules 
         syntax recommendation http://www.w3.org/TR/xslt 
    -->
    <xsl:template match="/">
        <html>
            <head>
                <title>firstXSL.xsl</title>
            </head>
            <body>
                <table border="2" bgcolor="yellow">
                    <tr>
                        <th>Title</th>
                        <th>Artist</th>
                    </tr>
                    <xsl:for-each select="CATALOG/CD">
                        <tr>
                            <td>
                                <xsl:value-of select="TITLE"/>
                            </td>
                            <td>
                                <xsl:value-of select="ARTIST"/>
                            </td>
                        </tr>
                    </xsl:for-each>
                </table>
            </body>
        </html>
    </xsl:template>

</xsl:stylesheet>
