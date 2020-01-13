<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : template.xsl
    Created on : December 18, 2019, 4:45 PM
    Author     : ADMIN
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="xml" indent="no" omit-xml-declaration="no" version="1.0"/>
    <xsl:import href="template_1.xsl"/>
    <xsl:import href="template_2.xsl"/>
    <!--1-->
    <!--    chỉ đích danh 3 phần tử , aaa, bbb ccc , nếu không tìm thấy template nào, thi áp template default là bên trong tenmplate,m nếu tìm thấy tempalte thì áp dụng luôn mà không áp dụng tem dèault
    <xsl:template match="/">
        tim node bbb áp template rõng vào bbb, còn lại ccc được áp ở
        template 1 vậy nên chỉ co 1 kết quả là ccc 
        nếu để import sau thì đầu tiên bbb cũng không có, những sau khi import thì nso lại xuất hiên
       number 1
        <xsl:apply-templates/>
        <xsl:apply-imports/>
    </xsl:template>-->
    <!--2-->
    <!--tim tất cả phần tử aaa và áp template vào đó, nếu không tìm t-->
    <!--    <xsl:template match="aaa">
        count aaa
        <xsl:apply-templates select="*"/>
    </xsl:template>-->
    
    <!--3-->
    <!--tìm node là bbb, áp template vào đó, template là cao nhất, bên dưới là import, không cần apply import thì templete vẫn được tìm và áp , từ file import-->
    <!--    <xsl:template match="bbb">
        contact-bbb
    </xsl:template>-->
    
    <!--4-->
    <!--cộng string vào node sau khi áp 1 template và 1 import-->
<!--    <xsl:template match="bbb">
        sdd
        <xsl:apply-imports/>
        <xsl:apply-templates select="*"/>
    </xsl:template>-->
    
    <xsl:template match="aaa">
        <ma>helo</ma>
        <xsl:apply-imports/>
        <xsl:apply-templates/>
    </xsl:template>
    
    
    
    
    
    
    
    
    
    
</xsl:stylesheet>
