<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : xslProduct.xsl
    Created on : December 18, 2019, 3:21 PM
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
            <head>
                <title>xslProduct.xsl</title>
            </head>
            <body>
                
                <xsl:apply-templates/>
            </body>
        </html>
    </xsl:template>
    
    <xsl:template match="Products">
        
        
        <table border="2" width="75%">
            
            
            <xsl:for-each select="Items">
                <tr>
                    
                    <td>
                        <xsl:value-of select="code"/>
                    </td>
                    <td>
                        <xsl:value-of select="name"/>
                    </td>
                    <td>
                        <xsl:value-of select="price"/>
                    </td>
                    <td>
                        <xsl:value-of select="description"/>
                    </td>
                    
                </tr>
                
                
                
                
                
            </xsl:for-each>
        </table>    
    </xsl:template>
</xsl:stylesheet>
