<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:template match="month">
<html>
  <head>
    <title>Statistic for <xsl:value-of select="name"/> <xsl:apply-templates select="year"/></title>
  </head>
  <body bgcolor = "white">
    <center>
			<table cellpadding="0" cellspacing="0" border="1" width="442">
				<tr>
					<td colspan="2" align="center">Statistic for <xsl:value-of select="name"/> <xsl:apply-templates select="year"/></td>
				</tr>
				<tr>
					<td>Hits</td><td><xsl:apply-templates select="total/hits"/></td>
				</tr>
				<tr>
					<td>Files</td><td><xsl:apply-templates select="total/files"/></td>
				</tr>
				<tr>
					<td>Pages</td><td><xsl:apply-templates select="total/pages"/></td>
				</tr>
				<tr>
					<td>Referers</td><td><xsl:apply-templates select="total/referer"/></td>
				</tr>
				<tr>
					<td>URL</td><td><xsl:apply-templates select="total/url"/></td>
				</tr>
				<tr>
					<td>Remote users</td><td><xsl:apply-templates select="total/remote_ip"/></td>
				</tr>
				<tr>
					<td>Traffic</td>
					<td>
						<xsl:choose>
							<xsl:when test="total/traffic &lt; (1024*1024)">
								&#160;<xsl:value-of select="round(total/traffic div 1024)"/> Ko
							</xsl:when>
							<xsl:otherwise>
								&#160;<xsl:value-of select="round(total/traffic div (1024*1024))"/> Mo
							</xsl:otherwise>
						</xsl:choose>
					</td>
				</tr>
			</table>
			
			
			<hr/>
			
			<table cellpadding="0" cellspacing="0" border="1" width="442">
				<tr>
					<td>Day of month</td>
					<td>Hits</td>
					<td>Files</td>
					<td>Pages</td>
					<td>Referers</td>
					<td>Urls</td>
					<td>Remote users</td>
					<td>Traffic</td>
				</tr>
			  <xsl:for-each select="hitsbyday/day">
					<tr>
						<td><xsl:apply-templates select="number"/></td>
						<td><xsl:apply-templates select="hits"/></td>
						<td><xsl:apply-templates select="files"/></td>
						<td><xsl:apply-templates select="pages"/></td>
						<td><xsl:apply-templates select="referers"/></td>
						<td><xsl:apply-templates select="urls"/></td>
						<td><xsl:apply-templates select="remote_ips"/></td>
						<td>
							<xsl:choose>
								<xsl:when test="traffic &lt; (1024*1024)">
									&#160;<xsl:value-of select="round(traffic div 1024)"/> Ko
								</xsl:when>
								<xsl:otherwise>
									&#160;<xsl:value-of select="round(traffic div (1024*1024))"/> Mo
								</xsl:otherwise>
							</xsl:choose>
						</td>
					</tr>
			  </xsl:for-each>
			</table>

	<hr />

			<table cellpadding="0" cellspacing="0" border="1" width="442">
				<tr>
					<td>Day of week</td>
					<td>Hits</td>
					<td>Files</td>
					<td>Pages</td>
				</tr>
			  <xsl:for-each select="hitsbydayofweek/day">
					<tr>
						<td><xsl:apply-templates select="name"/></td>
						<td><xsl:apply-templates select="hits"/></td>
						<td><xsl:apply-templates select="files"/></td>
						<td><xsl:apply-templates select="pages"/></td>
					</tr>
			  </xsl:for-each>
			</table>
			
			
			<hr />

			<xsl:call-template name="table">
				<xsl:with-param name="title" select="'Statistics by status'"/>
				<xsl:with-param name="foreach" select="status"/>
			</xsl:call-template>
		
			<hr />

			<xsl:call-template name="table">
				<xsl:with-param name="title" select="'Statistics hits by url'"/>
				<xsl:with-param name="foreach" select="hits"/>
			</xsl:call-template>
		
			<hr />

			<xsl:call-template name="table">
				<xsl:with-param name="title" select="'Statistics of Not Found errors'"/>
				<xsl:with-param name="foreach" select="notfound"/>
			</xsl:call-template>
		
			<hr />

			<xsl:call-template name="table">
				<xsl:with-param name="title" select="'Referers'"/>
				<xsl:with-param name="foreach" select="referers"/>
			</xsl:call-template>
		
			<hr />

			<xsl:call-template name="table">
				<xsl:with-param name="title" select="'Keywords'"/>
				<xsl:with-param name="foreach" select="keywords"/>
			</xsl:call-template>
		
			<hr />

			<xsl:call-template name="table">
				<xsl:with-param name="title" select="'Statistics remote users'"/>
				<xsl:with-param name="foreach" select="users_ips"/>
			</xsl:call-template>
		
			<hr />

			<xsl:call-template name="table">
				<xsl:with-param name="title" select="'Statistics by Country'"/>
				<xsl:with-param name="foreach" select="users_countries"/>
			</xsl:call-template>
		
			<hr />

			<xsl:call-template name="table">
				<xsl:with-param name="title" select="'Usernames'"/>
				<xsl:with-param name="foreach" select="users"/>
			</xsl:call-template>
		
			<hr />

			<xsl:call-template name="table">
				<xsl:with-param name="title" select="'User agents'"/>
				<xsl:with-param name="foreach" select="user_agents"/>
			</xsl:call-template>
		
			<hr />

			<xsl:call-template name="table">
				<xsl:with-param name="title" select="'operating system'"/>
				<xsl:with-param name="foreach" select="platforms"/>
			</xsl:call-template>
		
		
    </center>
  </body>
</html>
</xsl:template>



<xsl:template name="table">
  <xsl:param name="title"/>
  <xsl:param name="foreach"/>

	<table cellpadding="0" cellspacing="0" border="1" width="442">
	<tr><td colspan="2"><xsl:value-of select="$title"/></td></tr>
  <xsl:for-each select="$foreach/element">
  	<tr>
  		<td><xsl:value-of select="value"/></td>
  		<td><xsl:value-of select="count"/></td>
  	</tr>
  </xsl:for-each>
	</table>  
</xsl:template>  

</xsl:stylesheet>