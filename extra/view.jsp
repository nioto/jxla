<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jstl/xml" %>


<c:import url="xml/${param.file}" var="xml" scope="request"/>
<c:import url="xsl/month.xsl" var="xsl" scope="request"/>
<x:transform xslt="${xsl}" xml="${xml}"/>
