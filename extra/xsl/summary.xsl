<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:template match="/site">
<html>
  <head>
    <title>Statistic for <xsl:value-of select="name"/></title>
  </head>
  <body bgcolor = "white">
    <center>
			<table cellpadding="0" cellspacing="0" border="1" width="442">
				<tr>
					<td>Month</td>
					<td>Hits</td>
					<td>Pages</td>
					<td>Files</td>
					<td>Traffic</td>
				</tr>
				<xsl:apply-templates select="month"/>
			</table>
    </center>
  </body>
</html>
</xsl:template>

<xsl:template match="month">
	<tr>
		<td>
			<a>
			<xsl:attribute name="href">view.jsp?file=<xsl:value-of select="url"/></xsl:attribute>
			<xsl:value-of select="name"/> <xsl:apply-templates select="year"/>
			</a>
		</td>
		<td><xsl:value-of select="hits"/></td>
		<td><xsl:value-of select="pages"/></td>
		<td><xsl:value-of select="files"/></td>
		<td>
			<xsl:choose>
				<xsl:when test="traffic &lt; (1024*1024)">
					&#160;<font class="statblue"><xsl:value-of select="round(traffic div 1024)"/> Ko</font>&#160;
				</xsl:when>
				<xsl:otherwise>
					&#160;<font class="statblue"><xsl:value-of select="round(traffic div (1024*1024))"/> Mo</font>&#160;
				</xsl:otherwise>
			</xsl:choose>
		</td>
	</tr>
</xsl:template>


</xsl:stylesheet>