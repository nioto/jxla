<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jstl/xml" %>


<c:import url="xml/${param.stat}" var="input"/>
<c:import url="xsl/index.xsl" var="xsl" scope="request"/>

<x:transform xslt="${xsl}" xml="${input}"/>
