<%--
  - _search.jsp
  -
  - Version: $Revision: 1693 $
  --%>

<%--
  - ResearcherPage Search JSP
  --%>


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ page import="it.cilea.hku.authority.util.ResearcherPageUtils" %>
<%@ page import="java.util.List" %>
<%@page import="it.cilea.hku.authority.model.ResearcherPage"%>
<div id="content">

<h1><fmt:message key="jsp.layout.hku.search.title"/></h1>

<p class="text10">
HKU ResearcherPages sustain and enhance the research reputations of HKU authors and their institution.
They enable <a href="http://www3.hku.hk/strategic-development/eng/strategic-themes-for-09-14/promotion-knowledge-exchange-and-demonstrating-leadership.php" target="_new">Knowledge Exchange</a>
between HKU and the community in Hong Kong, China, and the World.
Read more about <a href="<%=request.getContextPath()%>/help.jsp#ResearcherPages">ResearcherPages</a>.
</p>
<form:form commandName="dto" method="post" action="${contextPath}/rp/search.htm">
<table width="98%" align="left" cellpadding="0" cellspacing="0">
	<tr>
		<td><!-- Search box -->
		
		<div id="search">
		<fieldset><legend><fmt:message key="jsp.layout.hku.search.box.label"/></legend>

			<table>
			
		
		</fieldset>
		</div>
		</td>
	</tr>


	<tr>
		<td>&nbsp;</td>
	</tr>

<%
String rpid = (String)session.getAttribute("rpid");
%>

	<tr>
		<td valign="middle">
		<div id="search">
		<div id="searchmiddle">
		<fieldset><fmt:message key="jsp.layout.hku.search.byRP"/>&nbsp;
			value="Go" />

<% if (rpid != null) { %>
	<p>Go to <a href="<%=request.getContextPath()%>/rp/<%=rpid %>">My ResearcherPage</a></p>
<%	} %>

		</fieldset>
		
		</div>
		</div>		
		</td>
	</tr>

	<!-- Enter staffNo -->

	
		<tr>
			<c:if test="${see_search_staffno}">
			<td valign="middle">
			<div id="search">
			<div id="searchmiddle">
	
			<fieldset><fmt:message key="jsp.layout.hku.search.byStaffNo"/>&nbsp;<form:input path="staffQuery" /> 
				value="Go" />
			</fieldset>
			</div>
			</div>	
			</td>
			</c:if>	


	<tr>
		<td></td>
	</tr>
		</c:if>

				<c:choose>
					<c:when test="${!empty result}">
						<display:table name="${result}" cellspacing="0" cellpadding="0"
							requestURI="" id="objectList" htmlId="objectList"  class="displaytaglikemisctable" export="false">
							<display:column titleKey="jsp.layout.table.hku.researchers.rpid">
							<display:column titleKey="jsp.layout.table.hku.researchers.academicName" property="academicName" />						
							<display:column titleKey="jsp.layout.table.hku.researchers.chineseName" property="chineseName" />
							<display:column titleKey="jsp.layout.table.hku.researchers.department" property="dept" />
							<%
							if(isAdmin) {							    
							%>
							<display:column titleKey="jsp.layout.table.hku.researchers.status" property="status"/>							
							<%		    
							}    
							%>							
							
						</display:table>
					</c:when>
					<c:otherwise>
						<p class="submitFormWarn"><fmt:message
							key="jsp.search.general.noresults" /></p>
					</c:otherwise>
				</c:choose>
</c:if>
</table>
