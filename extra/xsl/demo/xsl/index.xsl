<?xml version="1.0" encoding="ISO-8859-1"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output standalone="no"
  media-type="text/html" indent="no" omit-xml-declaration="no"/>


<!-- /////////////////////////////// \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\-->

<!--                                                                    -->

<!--  /////////////////////////////// \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\-->


<xsl:variable name="varimg">static/img/</xsl:variable>

<xsl:variable name="varcss">static/style.css</xsl:variable>

<xsl:template match="/">
<html>
  <head>
    <title>Statistiques</title>
    <link rel="stylesheet" href="{$varcss}" />
  </head>
  <body bgcolor = "white">
    <center>
        <xsl:apply-templates select="site"/>
        <xsl:apply-templates select="month"/>
    </center>
  </body>
</html>
</xsl:template>

<xsl:template match="site">
<table cellpadding="0" cellspacing="0" border="0" width="442" class="colorw">
    <tr>
      <td class="white" colspan="2">
        <img src="{$varimg}pix.gif" height="10" width="1"/>
      </td>
    </tr>
    <tr>
      <td class="colorw3" align="left">
        <img src="{$varimg}chl.gif" height="5" width="5"/>
      </td>
      <td class="colorw3" align="right">
        <img src="{$varimg}chr.gif" height="5" width="5"/>
      </td>
    </tr>
    <tr class="colorw3">
      <td colspan="2" class="title">
        <a class="detail-lien"><xsl:attribute name="href">http://<xsl:value-of select="name"/></xsl:attribute><xsl:value-of select="name"/></a>
      </td>
    </tr>
    <tr>
      <td colspan="2" class="colorw4" width="98%">
        <br/>
        <table cellpadding="1" cellspacing="1" border="0" width="80%">
          <tr class="colorw3">
            <td colspan="9" class="title-stat">
              &#160;General&#160;
            </td>
          </tr>
          <tr>
            <td width="67%" align="right" class="stattitle">&#160;Month&#160;</td>
            <td width="1%" align="center" class="stattitle">&#160;Hits&#160;</td>
            <td width="1%" align="center" class="stattitle">&#160;Files&#160;</td>
            <td width="1%" align="center" class="stattitle">&#160;Pages&#160;</td>
            <td width="30%"></td>
          </tr>

          <xsl:apply-templates select="month"/>

        </table>
      </td>
    </tr>
     <tr>
      <td align="left" class="colorw4">
        <img src="{$varimg}cbl.gif" height="5" width="5"/>
      </td>
      <td class="colorw4" align="right">
        <img src="{$varimg}cbr.gif" height="5" width="5"/>
      </td>
    </tr>
  </table>

</xsl:template>


<xsl:template match="month">

  <table cellpadding="0" cellspacing="0" border="0" width="442">
    <tr>
      <td colspan="2" class="white">
        <img src="{$varimg}pix.gif" height="10" width="1"/>
      </td>
    </tr>
    <tr>
      <td class="colorw3" align="left">
        <img src="{$varimg}chl.gif" height="5" width="5"/>
      </td>
      <td class="colorw3" align="right">
        <img src="{$varimg}chr.gif" height="5" width="5"/>
      </td>
    </tr>
    <tr class="colorw3">
      <td colspan="2" class="title">
        Statistics of month <i><xsl:value-of select="name"/></i><br/>
        Number of days<i><xsl:value-of select="count(hitsbyday/day)"/></i>
      </td>
    </tr>
    <tr>
      <td colspan="2" class="colorw4">

        <xsl:apply-templates select="total|hitsbyday|hitsbydayofweek|hits|status|notfound|referers|keywords|users_ips|users|users_countries|user_agents|platforms"/>

      </td>
    </tr>
    <tr>
      <td colspan="2" class="colorw4">&#160;</td>
    </tr>
    <tr>
      <td align="left" class="colorw4">
        <img src="{$varimg}cbl.gif" height="5" width="5"/>
      </td>
      <td class="colorw4" align="right">
        <img src="{$varimg}cbr.gif" height="5" width="5"/>
      </td>
    </tr>
  </table>
</xsl:template>



<xsl:template match="site/month">
  <tr>
    <td width="67%" align="right" class="white">
      &#160;<a class="link" href="view.jsp?stat={url}"><xsl:value-of select="name"/>&#160;<xsl:value-of select="year"/></a>&#160;
    </td>
    <td width="1%" align="center" class="white">
      &#160;<xsl:value-of select="hits"/>&#160;
    </td>
    <td width="1%" align="center" class="white">
      &#160;<xsl:value-of select="files"/>&#160;
    </td>
    <td width="1%" align="center" class="white">
      &#160;<xsl:value-of select="pages"/>&#160;
    </td>
    <td width="30%"></td>
  </tr>

</xsl:template>


<!-- ********************************************************************************** -->


<xsl:template match="total">

<table border="0" cellspacing="1" cellpadding="1" width="80%">
  <br/><br/>
  <tr class="colorw3">
    <td class="title-stat" colspan="3">
      Summary
    </td>
  </tr>
  <tr>
    <td align="right" class="white">&#160;<b>Hits</b>&#160;</td>
    <td align="center" class="white">&#160;<font class="statblue"><xsl:value-of select="hits"/></font>&#160;</td>
    <td width="30%"></td>
  </tr>
  <tr>
    <td align="right" class="white">&#160;<b>Files</b>&#160;</td>
    <td align="center" class="white">&#160;<font class="statblue"><xsl:value-of select="files"/></font>&#160;</td>
    <td width="30%"></td>
  </tr>
  <tr>
    <td align="right" class="white">&#160;<b>Pages</b>&#160;</td>
    <td align="center" class="white">&#160;<font class="statblue"><xsl:value-of select="pages"/></font>&#160;</td>
    <td width="30%"></td>
  </tr>
  <tr>
    <td align="right" class="white">&#160;<b>Referers</b>&#160;</td>
    <td align="center" class="white">&#160;<font class="statblue"><xsl:value-of select="referer"/></font>&#160;</td>
    <td width="30%"></td>
  </tr>
  <tr>
    <td align="right" class="white">&#160;<b>Request</b>&#160;</td>
    <td align="center" class="white">&#160;<font class="statblue"><xsl:value-of select="url"/></font>&#160;</td>
    <td width="30%"></td>
  </tr>
  <tr>
    <td align="right" class="white">&#160;<b>Remote hosts</b>&#160;</td>
    <td align="center" class="white">&#160;<font class="statblue"><xsl:value-of select="remote_ip"/></font>&#160;</td>
    <td width="30%"></td>
  </tr>
  <tr>
    <td align="right" class="white">&#160;<b>Bandwidth</b>&#160;</td>
    <td align="center" class="white">
    <xsl:choose>
      <xsl:when test="traffic &lt; (1024*1024)">
        &#160;<font class="statblue"><xsl:value-of select="round(traffic div 1024)"/> Ko</font>&#160;
      </xsl:when>
      <xsl:otherwise>
        &#160;<font class="statblue"><xsl:value-of select="round(traffic div (1024*1024))"/> Mo</font>&#160;
      </xsl:otherwise>
    </xsl:choose>
    </td>
    <td width="30%"></td>
  </tr>
</table>


</xsl:template>


<!-- ********************************************************************************** -->

<xsl:template match="hits">

<br/><br/>

  <xsl:call-template name="tablelist">
    <xsl:with-param name="tablename">Hits</xsl:with-param><!-- select="'Hits'"-->
    <xsl:with-param name="row" select="element"/>
    <xsl:with-param name="col1" select="'value'"/>
    <xsl:with-param name="col1url" select="'yes'"/>
    <xsl:with-param name="colname1">Requests</xsl:with-param><!--select="'request'"-->
    <xsl:with-param name="col2" select="'count'"/>
    <xsl:with-param name="colname2">Hits</xsl:with-param><!--select="'hits'"-->
    <xsl:with-param name="col2url" select="'no'"/>
  </xsl:call-template>
</xsl:template>

<!-- ********************************************************************************** -->

<xsl:template match="referers">

<br/><br/>

  <xsl:call-template name="tablelist">
    <xsl:with-param name="tablename">Top Referers</xsl:with-param><!-- select="'Main referers'"-->
    <xsl:with-param name="row" select="element"/>
    <xsl:with-param name="col1" select="'value'"/>
    <xsl:with-param name="colname1">referer</xsl:with-param><!--select="'referers'"-->
    <xsl:with-param name="col2" select="'count'"/>
    <xsl:with-param name="colname2">Hits</xsl:with-param><!--select="'hits'"-->
  </xsl:call-template>
</xsl:template>

<!-- ********************************************************************************** -->

<xsl:template match="users">

<br/><br/>

  <xsl:call-template name="tablelist">
    <xsl:with-param name="tablename">Users</xsl:with-param><!-- select="'users'"-->
    <xsl:with-param name="row" select="element"/>
    <xsl:with-param name="col1" select="'value'"/>
    <xsl:with-param name="colname1">user</xsl:with-param><!--select="'users'"-->
    <xsl:with-param name="col2" select="'count'"/>
    <xsl:with-param name="colname2">Hits</xsl:with-param><!--select="'hits'"-->
  </xsl:call-template>
</xsl:template>

<!-- ********************************************************************************** -->

<xsl:template match="users_ips">

<br/><br/>

  <xsl:call-template name="tablelist">
    <xsl:with-param name="tablename">Top remote host</xsl:with-param><!-- select="'Main Visitors'"-->
    <xsl:with-param name="row" select="element"/>
    <xsl:with-param name="col1" select="'value'"/>
    <xsl:with-param name="colname1">remote host</xsl:with-param><!--select="'Hosts'"-->
    <xsl:with-param name="col2" select="'count'"/>
    <xsl:with-param name="colname2">Hits</xsl:with-param><!--select="'hits'"-->
  </xsl:call-template>
</xsl:template>

<!-- ********************************************************************************** -->

<xsl:template match="keywords">

<br/><br/>

  <xsl:call-template name="tablelist">
    <xsl:with-param name="tablename">Top keywords</xsl:with-param><!-- select="'Main Search Engine Keywords'"-->
    <xsl:with-param name="row" select="element"/>
    <xsl:with-param name="col1" select="'value'"/>
    <xsl:with-param name="colname1">keyword</xsl:with-param><!--select="'keywords'"-->
    <xsl:with-param name="col2" select="'count'"/>
    <xsl:with-param name="colname2">Hits</xsl:with-param><!--select="'hits'"-->
  </xsl:call-template>
</xsl:template>

<!-- ********************************************************************************** -->

<xsl:template match="status">

<br/><br/>

  <xsl:call-template name="tablelist">
    <xsl:with-param name="tablename">Top status code</xsl:with-param><!--select="'Status Code'"-->
    <xsl:with-param name="row" select="element"/>
    <xsl:with-param name="col1" select="'value'"/>
    <xsl:with-param name="colname1">status code</xsl:with-param><!--select="'status'"-->
    <xsl:with-param name="col2" select="'count'"/>
    <xsl:with-param name="colname2">Hits</xsl:with-param><!--select="'hits'"-->
  </xsl:call-template>
</xsl:template>

<!-- ********************************************************************************** -->

<xsl:template match="notfound">

<br/><br/>

  <xsl:call-template name="tablelist">
    <xsl:with-param name="tablename">Top not found</xsl:with-param><!--select="'Status Code'"-->
    <xsl:with-param name="row" select="element"/>
    <xsl:with-param name="col1" select="'value'"/>
    <xsl:with-param name="colname1">url</xsl:with-param><!--select="'status'"-->
    <xsl:with-param name="col2" select="'count'"/>
    <xsl:with-param name="colname2">Hits</xsl:with-param><!--select="'hits'"-->
  </xsl:call-template>
</xsl:template>

<!-- ********************************************************************************** -->

<xsl:template match="users_countries">

<br/><br/>

  <xsl:call-template name="tablelist">
    <xsl:with-param name="tablename">Top countries</xsl:with-param><!-- select="'Not Found'"-->
    <xsl:with-param name="row" select="element"/>
    <xsl:with-param name="col1" select="'value'"/>
    <xsl:with-param name="colname1">Country</xsl:with-param><!--select="'request'"-->
    <xsl:with-param name="col2" select="'count'"/>
    <xsl:with-param name="colname2">Hits</xsl:with-param><!--select="'hits'"-->
  </xsl:call-template>
</xsl:template>

<!-- ********************************************************************************** -->

<xsl:template match="user_agents">

<br/><br/>

  <xsl:call-template name="tablelist">
    <xsl:with-param name="tablename">Top browser</xsl:with-param><!-- select="'Not Found'"-->
    <xsl:with-param name="row" select="element"/>
    <xsl:with-param name="col1" select="'value'"/>
    <xsl:with-param name="colname1">browser</xsl:with-param><!--select="'request'"-->
    <xsl:with-param name="col2" select="'count'"/>
    <xsl:with-param name="colname2">Hits</xsl:with-param><!--select="'hits'"-->
  </xsl:call-template>
</xsl:template>

<!-- ********************************************************************************** -->

<xsl:template match="platforms">

<br/><br/>

  <xsl:call-template name="tablelist">
    <xsl:with-param name="tablename">Top operating system</xsl:with-param><!-- select="'Not Found'"-->
    <xsl:with-param name="row" select="element"/>
    <xsl:with-param name="col1" select="'value'"/>
    <xsl:with-param name="colname1">OS</xsl:with-param><!--select="'request'"-->
    <xsl:with-param name="col2" select="'count'"/>
    <xsl:with-param name="colname2">Hits</xsl:with-param><!--select="'hits'"-->
  </xsl:call-template>
</xsl:template>

<!-- ********************************************************************************** -->

<xsl:template match="hitsbyday">

<br/><br/>

  <xsl:call-template name="graph">
    <xsl:with-param name="graphname">Hits by day</xsl:with-param>
    <xsl:with-param name="foreach" select="day"/>
    <xsl:with-param name="name" select="'number'"/>
    <xsl:with-param name="number" select="'hits'"/>
    <xsl:with-param name="colname">Day</xsl:with-param><!--select="'day'"-->
    <xsl:with-param name="colnumber">Hits</xsl:with-param><!-- select="'Hits'"-->
    <xsl:with-param name="colpercent">percent</xsl:with-param><!-- select="'percent'"-->
  </xsl:call-template>

<br/><br/>

  <xsl:call-template name="graph">
    <xsl:with-param name="graphname">Pages by day</xsl:with-param><!--select="'Pages per day'"-->
    <xsl:with-param name="foreach" select="day"/>
    <xsl:with-param name="name" select="'number'"/>
    <xsl:with-param name="number" select="'pages'"/>
    <xsl:with-param name="colname">Day</xsl:with-param><!-- select="'day'"-->
    <xsl:with-param name="colnumber">Pages</xsl:with-param><!--select="'pages'"-->
    <xsl:with-param name="colpercent">percent</xsl:with-param><!--select="'percent'"-->
  </xsl:call-template>

<br/><br/>

  <xsl:call-template name="graph">
    <xsl:with-param name="graphname">Bandwidth by day</xsl:with-param><!-- select="'Banwidth Usage per day'"-->
    <xsl:with-param name="foreach" select="day"/>
    <xsl:with-param name="name" select="'number'"/>
    <xsl:with-param name="number" select="'traffic'"/>
    <xsl:with-param name="colname">Day</xsl:with-param><!--select="'day'"-->
    <xsl:with-param name="colnumber">Bytes</xsl:with-param><!-- select="'pages'"-->
    <xsl:with-param name="colpercent">percent</xsl:with-param><!-- select="'bytes'"-->
  </xsl:call-template>

<br/><br/>

  <xsl:call-template name="comparative-graph">
    <xsl:with-param name="graphname">Hits and pages by day</xsl:with-param><!-- select="'Hits and Pages per day'"-->
    <xsl:with-param name="foreach" select="day"/>
    <xsl:with-param name="name" select="'number'"/>
    <xsl:with-param name="number1" select="'hits'"/>
    <xsl:with-param name="number2" select="'pages'"/>
    <xsl:with-param name="colname">Day</xsl:with-param><!--select="'day'"-->
    <xsl:with-param name="colnumber_h">Hits</xsl:with-param><!--select="'Hits'"-->
    <xsl:with-param name="colnumber_b">Pages</xsl:with-param><!-- select="'Pages'"-->
    <xsl:with-param name="colpercent">percent</xsl:with-param><!--select="'percent'"-->
  </xsl:call-template>

<br/><br/>

  <xsl:call-template name="graph">
    <xsl:with-param name="graphname">Remote hosts by day</xsl:with-param><!-- select="'Distinct hosts served per day'"-->
    <xsl:with-param name="foreach" select="day"/>
    <xsl:with-param name="name" select="'number'"/>
    <xsl:with-param name="number" select="'remote_ips'"/>
    <xsl:with-param name="colname">Day</xsl:with-param><!--select="'day'"-->
    <xsl:with-param name="colnumber">remote user</xsl:with-param><!--select="'Distinct hosts'"-->
    <xsl:with-param name="colpercent">percent</xsl:with-param><!--select="'percent'"-->
  </xsl:call-template>

<br/><br/>

  <xsl:call-template name="comparative-graph">
    <xsl:with-param name="graphname">remote users and pages by day</xsl:with-param><!-- select="'Distinct hosts and Pages per day'"-->
    <xsl:with-param name="foreach" select="day"/>
    <xsl:with-param name="name" select="'number'"/>
    <xsl:with-param name="number1" select="'remote_ips'"/>
    <xsl:with-param name="number2" select="'pages'"/>
    <xsl:with-param name="colname" >Day</xsl:with-param><!--select="'day'"-->
    <xsl:with-param name="colnumber_h">remote user</xsl:with-param><!--select="'Hosts'"-->
    <xsl:with-param name="colnumber_b">Pages</xsl:with-param><!--select="'Pages'"-->
    <xsl:with-param name="colpercent">percent</xsl:with-param><!--select="'percent'"-->
  </xsl:call-template>

<br/><br/>

  <xsl:call-template name="graph">
    <xsl:with-param name="graphname">files by day</xsl:with-param><!-- select="'Files served per day'"-->
    <xsl:with-param name="foreach" select="day"/>
    <xsl:with-param name="name" select="'number'"/>
    <xsl:with-param name="number" select="'files'"/>
    <xsl:with-param name="colname">Day</xsl:with-param><!--select="'day'"-->
    <xsl:with-param name="colnumber">Files</xsl:with-param><!--select="'Files served'"-->
    <xsl:with-param name="colpercent">percent</xsl:with-param><!--select="'percent'"-->
  </xsl:call-template>

<br/><br/>

  <xsl:call-template name="graph">
    <xsl:with-param name="graphname">Files</xsl:with-param><!-- select="'referers per day'"-->
    <xsl:with-param name="foreach" select="day"/>
    <xsl:with-param name="name" select="'number'"/>
    <xsl:with-param name="number" select="'referers'"/>
    <xsl:with-param name="colname">Day</xsl:with-param><!--select="'day'"-->
    <xsl:with-param name="colnumber">referers</xsl:with-param><!-- select="'referers'"-->
    <xsl:with-param name="colpercent">percent</xsl:with-param><!--select="'percent'"-->
  </xsl:call-template>

<br/><br/>

  <xsl:call-template name="graph">
    <xsl:with-param name="graphname">Distinct urls by day</xsl:with-param><!-- select="'Distinct urls served per day'"-->
    <xsl:with-param name="foreach" select="day"/>
    <xsl:with-param name="name" select="'number'"/>
    <xsl:with-param name="number" select="'urls'"/>
    <xsl:with-param name="colname">Day</xsl:with-param><!--select="'day'"-->
    <xsl:with-param name="colnumber">url</xsl:with-param><!--select="'Distinct urls'"-->
    <xsl:with-param name="colpercent">percent</xsl:with-param><!--select="'percent'"-->
  </xsl:call-template>


</xsl:template>

<!-- ********************************************************************************** -->

<xsl:template match="hitsbydayofweek">

<br/><br/>

  <xsl:call-template name="graph">
    <xsl:with-param name="graphname">Hits by day of week</xsl:with-param><!-- select="'Hits by days of the week'"-->
    <xsl:with-param name="foreach" select="day"/>
    <xsl:with-param name="name" select="'name'"/>
    <xsl:with-param name="number" select="'hits'"/>
    <xsl:with-param name="colname">day of week</xsl:with-param><!--select="'day of the week'"-->
    <xsl:with-param name="colnumber">Hits</xsl:with-param><!--select="'Hits'"-->
    <xsl:with-param name="colpercent">percent</xsl:with-param><!--select="'percent'"-->
  </xsl:call-template>

<br/><br/>

  <xsl:call-template name="graph">
    <xsl:with-param name="graphname">Pages by days of the week</xsl:with-param><!--  select="'Pages by days of the week'"-->
    <xsl:with-param name="colname">day of the week</xsl:with-param><!--select="'day of the week'"-->
    <xsl:with-param name="colnumber">Pages</xsl:with-param><!--select="'pages'"-->
    <xsl:with-param name="colpercent">percent</xsl:with-param><!-- select="'percent'"-->
    <xsl:with-param name="foreach" select="day"/>
    <xsl:with-param name="name" select="'name'"/>
    <xsl:with-param name="number" select="'pages'"/>
  </xsl:call-template>

<br/><br/>

  <xsl:call-template name="graph">
    <xsl:with-param name="graphname">Files by days of the week</xsl:with-param><!--  select="'Files by days of the week'"-->
    <xsl:with-param name="colname">day of the week</xsl:with-param><!--select="'day of the week'"-->
    <xsl:with-param name="colnumber">Files</xsl:with-param><!-- select="'files'"-->
    <xsl:with-param name="colpercent">percent</xsl:with-param><!--select="'percent'"-->
    <xsl:with-param name="foreach" select="day"/>
    <xsl:with-param name="name" select="'name'"/>
    <xsl:with-param name="number" select="'files'"/>
  </xsl:call-template>

<br/><br/>

  <xsl:call-template name="comparative-graph">
    <xsl:with-param name="graphname">Files and Pages per day</xsl:with-param><!-- select="'Files and Pages per day'"-->
    <xsl:with-param name="foreach" select="day"/>
    <xsl:with-param name="name" select="'name'"/>
    <xsl:with-param name="number1" select="'files'"/>
    <xsl:with-param name="number2" select="'pages'"/>
    <xsl:with-param name="colname">day of the week</xsl:with-param><!--select="'day of the week'"-->
    <xsl:with-param name="colnumber_h">Files</xsl:with-param><!--select="'Files'"-->
    <xsl:with-param name="colnumber_b">Pages</xsl:with-param><!--select="'Pages'"-->
    <xsl:with-param name="colpercent">percent</xsl:with-param><!--select="'percent'"-->
  </xsl:call-template>


</xsl:template>

<!-- ********************************************************************************** -->


<!-- ********************************************************************************** -->
<!-- ********************************************************************************** -->
<!-- *********************************** GRAPH **************************************** -->
<!-- ********************************************************************************** -->
<!-- ********************************************************************************** -->


<!-- ********************************************************************************** -->
<!-- *********************************** graph **************************************** -->
<!-- ********************************************************************************** -->

<xsl:template name="graph">
  <xsl:param name="graphname"/>
  <xsl:param name="colname"/>
  <xsl:param name="colnumber"/>
  <xsl:param name="colpercent"/>
  <xsl:param name="foreach"/>
  <xsl:param name="name"/>
  <xsl:param name="number"/>

  <table border="0" cellpadding="1" cellspacing="1" width="80%">

  <!--
  name:<xsl:value-of select="$name"/><br/>
  number:<xsl:value-of select="$number"/>
  ????
  -->

  <xsl:call-template name="startgraph">
      <xsl:with-param name="graphname" select="$graphname"/>
      <xsl:with-param name="colname" select="$colname"/>
      <xsl:with-param name="colnumber" select="$colnumber"/>
      <xsl:with-param name="colpercent" select="$colpercent"/>
  </xsl:call-template>

  <xsl:variable name="total" select="sum($foreach/*[name()=$number])"/>
  <xsl:for-each select="$foreach">
    <xsl:call-template name="linegraph">
      <xsl:with-param name="linegraph-name" select="*[name()=$name]"/>
      <xsl:with-param name="linegraph-number" select="*[name()=$number]"/>
      <xsl:with-param name="linegraph-percent" select="round((*[name()=$number] div $total) * 100)"/>
    </xsl:call-template>
  </xsl:for-each>

  </table>

</xsl:template>

<!-- ********************************************************************************** -->
<!-- *********************************** startgraph *********************************** -->
<!-- ********************************************************************************** -->

<xsl:template name="startgraph">
  <xsl:param name="graphname"/>
  <xsl:param name="colname" select="''"/>
  <xsl:param name="colnumber" select="''"/>
  <xsl:param name="colpercent" select="'percent'"/>

  <tr class="colorw3">
    <td colspan="3" class="title-stat" nowrap="true">
      <b><xsl:value-of select="$graphname"/></b>&#160;&#160;
    </td>
  </tr>
  <tr>
    <td nowrap="true" align="right" class="stattitle" width="40%">
      &#160;<b><xsl:value-of select="$colname"/></b>&#160;
    </td>
    <td nowrap="true" align="center" class="stattitle" width="1%">
      &#160;<b><xsl:value-of select="$colnumber"/></b>&#160;
    </td>
    <td><font class="statlegende"><xsl:value-of select="$colpercent"/></font></td>
  </tr>
  <!--
  <tr>
    <td class="colorw3" colspan="3"><img src="{$varimg}pix.gif" width="1" height="1"/></td>
  </tr>
  -->
</xsl:template>


<!-- ********************************************************************************** -->
<!-- *********************************** linegraph ************************************ -->
<!-- ********************************************************************************** -->

<xsl:template name="linegraph">
  <xsl:param name="linegraph-name"/>
  <xsl:param name="linegraph-number"/>
  <xsl:param name="linegraph-percent"/>

  <tr>
    <td align="right" class="white">
      <xsl:choose>
        <xsl:when test="url">
          &#160;<a href="{url}"><xsl:value-of select="$linegraph-name"/></a>&#160;
        </xsl:when>
        <xsl:otherwise>
          &#160;<b><xsl:value-of select="$linegraph-name"/></b>&#160;
        </xsl:otherwise>
      </xsl:choose>
    </td>
    <td align="center" class="white">
      &#160;<font class="statblue"><xsl:value-of select="$linegraph-number"/></font>&#160;
    </td>
    <td nowrap="true">

      <img width="6" height="14" alt="{$linegraph-percent} %" src="{$varimg}bar-blue-thick-ext1.gif"/><img width="{$linegraph-percent * 3}" alt="{$linegraph-percent} %" height="14" src="{$varimg}bar-blue-thick-int.gif"/><img width="10" height="14" alt="{$linegraph-percent} %" src="{$varimg}bar-blue-thick-ext.gif"/>
      &#160;<font class="statlegende"><xsl:value-of select="$linegraph-percent"/> %</font>
    </td>
  </tr>

</xsl:template>




<!-- ********************************************************************************** -->
<!-- ********************************************************************************** -->
<!-- ***************************** COMPARATIVE GRAPH ********************************** -->
<!-- ********************************************************************************** -->
<!-- ********************************************************************************** -->

<!-- ********************************************************************************** -->
<!-- ******************************* comparative-graph ******************************** -->
<!-- ********************************************************************************** -->

<xsl:template name="comparative-graph">
  <xsl:param name="graphname"/>
  <xsl:param name="colname"/>
  <xsl:param name="colnumber_h"/>
  <xsl:param name="colnumber_b"/>
  <xsl:param name="colpercent"/>
  <xsl:param name="foreach"/>
  <xsl:param name="name"/>
  <xsl:param name="number1"/>
  <xsl:param name="number2"/>

  <table border="0" cellpadding="1" cellspacing="1" width="80%">

  <xsl:call-template name="comparative-startgraph">
      <xsl:with-param name="graphname" select="$graphname"/>
      <xsl:with-param name="colname" select="$colname"/>
      <xsl:with-param name="colnumber_b" select="$colnumber_b"/>
      <xsl:with-param name="colnumber_h" select="$colnumber_h"/>
      <xsl:with-param name="colpercent" select="$colpercent"/>
  </xsl:call-template>

  <xsl:variable name="total1" select="sum($foreach/*[name()=$number1])"/>
  <xsl:variable name="total2" select="sum($foreach/*[name()=$number2])"/>
  <xsl:for-each select="$foreach">
    <xsl:call-template name="comparative-linegraph">
      <xsl:with-param name="linegraph-name" select="*[name()=$name]"/>
      <xsl:with-param name="linegraph-number1" select="*[name()=$number1]"/>
      <xsl:with-param name="linegraph-percent1" select="round((*[name()=$number1] div $total1) * 100)"/>
      <xsl:with-param name="linegraph-number2" select="*[name()=$number2]"/>
      <xsl:with-param name="linegraph-percent2" select="round((*[name()=$number2] div $total2) * 100)"/>
    </xsl:call-template>
  </xsl:for-each>

  </table>

</xsl:template>


<!-- ********************************************************************************** -->
<!-- ************************* comparative-startgraph ********************************* -->
<!-- ********************************************************************************** -->

<xsl:template name="comparative-startgraph">
  <xsl:param name="graphname"/>
  <xsl:param name="colname" select="''"/>
  <xsl:param name="colnumber_h" select="''"/>
  <xsl:param name="colnumber_b" select="''"/>
  <xsl:param name="colpercent" select="'percent'"/>

  <tr class="colorw3">
    <td colspan="3" class="title-stat">
      <xsl:value-of select="$graphname"/>
    </td>
  </tr>

  <tr>
    <td nowrap="true" align="center" rowspan="2" class="stattitle" width="40%">
      &#160;<b><xsl:value-of select="$colname"/></b>&#160;
    </td>
    <td nowrap="true" align="center" class="stattitle" width="1%">
      &#160;<b><xsl:value-of select="$colnumber_h"/></b>&#160;
    </td>
    <td rowspan="2">
      &#160;<font class="statlegende"><xsl:value-of select="$colpercent"/></font>&#160;
    </td>
  </tr>
  <tr>
    <td nowrap="true" align="center" class="stattitle">
      &#160;<b><xsl:value-of select="$colnumber_b"/></b>&#160;
    </td>
  </tr>

</xsl:template>


<!-- ********************************************************************************** -->
<!-- *************************** comparative-linegraph ******************************** -->
<!-- ********************************************************************************** -->

<xsl:template name="comparative-linegraph">
  <xsl:param name="linegraph-name"/>
  <xsl:param name="linegraph-number1"/>
  <xsl:param name="linegraph-percent1"/>
  <xsl:param name="linegraph-number2"/>
  <xsl:param name="linegraph-percent2"/>

  <tr>
    <td rowspan="2" align="right" class="white">
      &#160;<b><xsl:value-of select="$linegraph-name"/></b>&#160;
    </td>
    <td align="center" class="white">
      &#160;<font class="statlegendeblue"><xsl:value-of select="$linegraph-number1"/></font>&#160;
    </td>
    <td valign="bottom" nowrap="true">
      <img width="3" height="7" alt="{$linegraph-percent2} %" src="{$varimg}bar-blue-thin-ext1.gif"/><img width="{$linegraph-percent1 * 3}" alt="{$linegraph-percent1} %" height="7" src="{$varimg}bar-blue-thin-int.gif"/><img width="5" height="7" alt="{$linegraph-percent2} %" src="{$varimg}bar-blue-thin-ext.gif"/>
      <!--&#160;<font class="legende"><xsl:value-of select="$linegraph-percent2"/> %</font>-->
    </td>
  </tr>
  <tr>
    <td align="center" class="white">
      &#160;<font class="statlegende"><xsl:value-of select="$linegraph-number2"/></font>&#160;
    </td>
    <td valign="top" nowrap="true">
      <img width="3" height="7" alt="{$linegraph-percent2} %" src="{$varimg}bar-red-thin-ext1.gif"/><img width="{$linegraph-percent2 * 3}" alt="{$linegraph-percent2} %" height="7" src="{$varimg}bar-red-thin-int.gif"/><img width="5" height="7" alt="{$linegraph-percent2} %" src="{$varimg}bar-red-thin-ext.gif"/>
      <!--&#160;<font class="legende"><xsl:value-of select="$linegraph-percent2"/> %</font>-->
    </td>
  </tr>

</xsl:template>



<!-- ********************************************************************************** -->
<!-- ********************************************************************************** -->
<!-- ********************************* TABLE LIST ************************************* -->
<!-- ********************************************************************************** -->
<!-- ********************************************************************************** -->

<!-- ********************************************************************************** -->
<!-- ********************************* tablelist ************************************** -->
<!-- ********************************************************************************** -->

<xsl:template name="tablelist">
  <xsl:param name="tablename"/>
  <xsl:param name="row"/>
  <xsl:param name="col1"/>
  <xsl:param name="colname1"/>
  <xsl:param name="col1url"/>
  <xsl:param name="col2"/>
  <xsl:param name="colname2"/>
  <xsl:param name="col2url"/>

  <table border="0" cellpadding="1" cellspacing="1" width="80%">

  <xsl:call-template name="starttablelist">
      <xsl:with-param name="tablename" select="$tablename"/>
      <xsl:with-param name="colname1" select="$colname1"/>
      <xsl:with-param name="colname2" select="$colname2"/>
  </xsl:call-template>

  <xsl:for-each select="$row">
    <xsl:call-template name="tablerow">
      <xsl:with-param name="tablerow-col1" select="*[name()=$col1]"/>
      <xsl:with-param name="tablerow-col2" select="*[name()=$col2]"/>
      <xsl:with-param name="col1url" select="$col1url"/>
      <xsl:with-param name="col2url" select="$col2url"/>
    </xsl:call-template>
  </xsl:for-each>

  </table>

</xsl:template>

<!-- ********************************************************************************** -->
<!-- ********************************* starttablelist ********************************* -->
<!-- ********************************************************************************** -->

<xsl:template name="starttablelist">
  <xsl:param name="tablename"/>
  <xsl:param name="colname1"/>
  <xsl:param name="colname2"/>

  <tr class="colorw3">
    <td colspan="3" class="title-stat">
      &#160;<xsl:value-of select="$tablename"/>&#160;
    </td>
  </tr>
  <tr>
    <td width="67%" align="right" class="stattitle">
      &#160;<xsl:value-of select="$colname1"/>&#160;
    </td>
    <td width="1%" align="center" class="stattitle">
      &#160;<xsl:value-of select="$colname2"/>&#160;
    </td>
    <td width="30%"></td>
  </tr>

</xsl:template>


<!-- ********************************************************************************** -->
<!-- *********************************** tablerow ************************************ -->
<!-- ********************************************************************************** -->

<xsl:template name="tablerow">
  <xsl:param name="tablerow-col1"/>
  <xsl:param name="tablerow-col2"/>
  <xsl:param name="col1url"/>
  <xsl:param name="col2url"/>

  <tr>
    <td width="67%" class="white">
      <xsl:if test="$col1url = 'yes'">
        <xsl:choose>
          <xsl:when test="string-length($tablerow-col1)&gt;50">
            &#160;<a class="link" href="{$tablerow-col1}"><xsl:value-of select="substring($tablerow-col1,1,200)"/>...</a>&#160;
          </xsl:when>
          <xsl:otherwise>
            &#160;<a class="link" href="{$tablerow-col1}"><xsl:value-of select="$tablerow-col1"/></a>&#160;
          </xsl:otherwise>
        </xsl:choose>
      </xsl:if>
      <xsl:if test="$col1url != 'yes'">
        <xsl:choose>
          <xsl:when test="string-length($tablerow-col1)&gt;50">
            &#160;<b><xsl:value-of select="substring($tablerow-col1,1,200)"/>...</b>&#160;
          </xsl:when>
          <xsl:otherwise>
            &#160;<b><xsl:value-of select="$tablerow-col1"/></b>&#160;
          </xsl:otherwise>
        </xsl:choose>
      </xsl:if>
    </td>
    <td width="1%" align="center" class="white">
      <xsl:if test="$col2url = 'yes'">
        &#160;<a class="link" href="{$tablerow-col2}"><xsl:value-of select="$tablerow-col2"/></a>&#160;
      </xsl:if>
      <xsl:if test="$col2url != 'yes'">
        &#160;<font class="statblue"><xsl:value-of select="$tablerow-col2"/></font>&#160;
      </xsl:if>
    </td>
    <td width="30%"></td>
  </tr>


</xsl:template>


</xsl:stylesheet>