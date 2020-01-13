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
    <xsl:output method="html"/>
    <xsl:template match="/">
        
        <html>
            <head>Persons</head>
            
            <body>
                <table border="1">
                    
                    <tr>
                        <th>Firstname</th>
                        <th>Lastname</th>
                    </tr>
                    <xsl:for-each select="//person">
                        <xsl:sort select="name/firstname" order=""/>
                        <tr>
                            <td>    
                                <xsl:if test="not(@real='no')">
                                    <a href="{@link}">
                                        <xsl:value-of select="name/firstname"/>
                                    </a>
                                </xsl:if>                    
                                <xsl:if test="@real='no'">
                                    <xsl:value-of select="name/firstname"/>
                                </xsl:if>
                            </td>
                            <td>                        
                                <xsl:value-of select="name/lastname"/>
                            </td>
                        </tr>
                    </xsl:for-each>
                </table>
            </body>
        </html>
    </xsl:template>
   
</xsl:stylesheet>
