<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.dspace.org/dspace-tags.tld" prefix="dspace" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@ taglib uri="researchertags" prefix="researcher"%>
<%@ taglib uri="jdynatags" prefix="dyna"%>
<%@ taglib uri="http://ajaxtags.org/tags/ajax" prefix="ajax"%>

<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="it.cilea.osd.jdyna.model.PropertiesDefinition"%>
<%@page
	import="it.cilea.osd.jdyna.model.ADecoratorPropertiesDefinition"%>
<%@page
	import="it.cilea.hku.authority.model.dynamicfield.DecoratorRestrictedField"%>
	
<%@page import="it.cilea.osd.jdyna.model.AccessLevelConstants"%>
<%@ page import="java.net.URL"%>
<%@ page import="org.dspace.eperson.EPerson" %>

<%
    // Is anyone logged in?
    EPerson user = (EPerson) request.getAttribute("dspace.current.user");

    // Is the logged in user an admin
    Boolean admin = (Boolean)request.getAttribute("is.admin");
    boolean isAdmin = (admin == null ? false : admin.booleanValue());

%>
<c:set var="root"><%=request.getContextPath()%></c:set>
<c:set var="admin"><%=isAdmin%></c:set>
<c:set var="HIGH_ACCESS"><%=AccessLevelConstants.HIGH_ACCESS%></c:set>
<c:set var="ADMIN_ACCESS"><%=AccessLevelConstants.ADMIN_ACCESS%></c:set>
<c:set var="LOW_ACCESS"><%=AccessLevelConstants.LOW_ACCESS%></c:set>
<c:set var="STANDARD_ACCESS"><%=AccessLevelConstants.STANDARD_ACCESS%></c:set>
<c:set var="tabId" value="${anagraficadto.tabId}" />

<c:forEach items="${tabList}" var="areaIter" varStatus="rowCounter">
	<c:if test="${areaIter.id == tabId}">
	<c:set var="currTabIdx" scope="request" value="${rowCounter.count}" />
	</c:if>
</c:forEach>
	
<c:set var="commandObject" value="${anagraficadto}" scope="request" />

<c:set var="simpleNameAnagraficaObject"
	value="${simpleNameAnagraficaObject}" scope="page" />

<c:set var="disabledfield" value=" disabled=\"disabled\" "></c:set>

<c:set var="dspace.layout.head" scope="request">	
	<link href="<%= request.getContextPath() %>/css/researcher.css" type="text/css" rel="stylesheet" />
	<link href="<%= request.getContextPath() %>/css/jdyna.css" type="text/css" rel="stylesheet" />
	<link href="<%= request.getContextPath() %>/css/redmond/jquery-ui-1.8.24.custom.css" type="text/css" rel="stylesheet" />
    <link href="<%=request.getContextPath()%>/js/jscalendar/calendar-blue.css" type="text/css" rel="stylesheet" />
	<script type="text/javascript" src="<%= request.getContextPath() %>/js/jscalendar/calendar.js"> </script>
	<script type="text/javascript" src="<%= request.getContextPath() %>/js/jscalendar/lang/calendar-en.js"> </script>
	<script type="text/javascript" src="<%= request.getContextPath() %>/js/jscalendar/calendar-setup.js"> </script>
	<script type="text/javascript" src="<%= request.getContextPath() %>/js/jquery-1.8.2.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-ui-1.8.24.custom.min.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath() %>/js/jquery.form.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/jdyna/jdyna.js"></script>

    <script type="text/javascript"><!--

		var j = jQuery.noConflict();
    	var ajaxurlnavigation = "<%=request.getContextPath()%>/json/cris/navigation";
    	
    	var activeTab = function(){
    		j(".box:not(.expanded)").accordion({
    			autoHeight: false,
    			navigation: true,
    			collapsible: true,
    			active: 0
    		});
    		j(".box.expanded").accordion({
    			autoHeight: false,
    			navigation: true,
    			collapsible: true,
    			active: 0
    		});
    		
    		var ajaxurlrelations = "<%=request.getContextPath()%>/cris/${specificPartPath}/viewNested.htm";
			j('.nestedinfo').each(function(){
				var id = j(this).html();
				j.ajax( {
					url : ajaxurlrelations,
					data : {																			
						"parentID" : ${anagraficadto.objectId},
						"typeNestedID" : id,
						"pageCurrent": j('#nested_'+id+"_pageCurrent").html(),
						"limit": j('#nested_'+id+"_limit").html(),
						"editmode": true,
						"totalHit": j('#nested_'+id+"_totalHit").html(),
						"admin": ${admin}
					},
					success : function(data) {																										
						j('#viewnested_'+id).html(data);
						var ajaxFunction = function(page){
							j.ajax( {
								url : ajaxurlrelations,
								data : {																			
									"parentID" : ${anagraficadto.objectId},
									"typeNestedID" : id,													
									"pageCurrent": page,
									"limit": j('#nested_'+id+"_limit").html(),
									"editmode": true,
									"totalHit": j('#nested_'+id+"_totalHit").html(),
									"admin": ${admin}
								},
								success : function(data) {									
									j('#viewnested_'+id).html(data);
									postfunction();
								},
								error : function(data) {
								}
							});		
						};
						var postfunction = function(){
							j('#nested_'+id+'_next').click(
									function() {
								    	ajaxFunction(j('#nested_'+id+"_pageCurrent").html()+1);
										
							});
							j('#nested_'+id+'_prev').click(
									function() {
										ajaxFunction(j('#nested_'+id+"_pageCurrent").html()-1);
							});
							j('.nested_'+id+'_nextprev').click(
									function(){
										ajaxFunction(j(this).attr('id').substr(('nested_'+id+'_nextprev_').length));
							});
						};
						postfunction();
					},
					error : function(data) {
					}
				});
			});
    	};
    	
		j(document).ready(function()
		{
			j("#log3").dialog({closeOnEscape: true, modal: true, autoOpen: false, resizable: false, open: function(event, ui) { j(".ui-dialog-titlebar").hide();}});
			
			j('input:submit').button();
			j("#tabs").tabs({
				selected: ${currTabIdx-1},
				select: function(event, ui){
					j('#newTabId').val(j(ui.tab).attr('href').substr(5));
					j('#submit_save').click();
				}
			});
			
			j('.navigation-tabs:not(.expanded)').accordion({
				collapsible: true,
				active: 0,
				event: "click mouseover"
			});
			j('.navigation-tabs.expanded').accordion({
				collapsible: true,
				active: 0,
				event: "click mouseover"
			});
			
			activeTab();
		});
		-->
	</script>
	
</c:set>
<dspace:layout titlekey="jsp.researcher-page.primary-data-form" navbar="off">

<h1>${researcher.fullName}</h1>


<c:if test="${not empty messages}">
	<div class="message" id="successMessages"><c:forEach var="msg"
		items="${messages}">
		<div id="authority-message">${msg}</div>
	</c:forEach></div>
	<c:remove var="messages" scope="session" />
</c:if>


<div id="researcher">
<form:form commandName="anagraficadto"
	action="" method="post" enctype="multipart/form-data">
	<%-- if you need to display all errors (both global and all field errors,
		 use wildcard (*) in place of the property name --%>
	<spring:bind path="anagraficadto.*">
		<c:if test="${!empty status.errorMessages}">
			<div id="errorMessages">
		</c:if>
		<c:forEach items="${status.errorMessages}" var="error">
			<span class="errorMessage"><fmt:message
				key="jsp.layout.hku.prefix-error-code" /> ${error}</span>
			<br />
		</c:forEach>
		<c:if test="${!empty status.errorMessages}">
			</div>
		</c:if>
	</spring:bind>


	<dyna:hidden propertyPath="anagraficadto.objectId" />
	<input type="hidden" id="newTabId" name="newTabId" />
	
	
	<p style="color: red; text-decoration: underline; font-weight: bold; text-align: center;"><fmt:message key='jsp.rp.edit-tips'/></p>

				<div id="tabs">
		<ul>
					<c:forEach items="${tabList}" var="area" varStatus="rowCounter">
			<li id="bar-tab-${area.id}">
				<a href="#tab-${area.id}"><img style="width: 16px;vertical-align: middle;" border="0" 
					src="<%=request.getContextPath()%>/cris/researchertabimage/${area.id}" alt="icon">
				${area.title}</a>
			</li>
					</c:forEach>
		</ul>

<c:forEach items="${tabList}" var="area" varStatus="rowCounter">
	<c:if test="${area.id != tabId}">
	<div id="tab-${area.id}">
	Saving data... tab data will be shown soon
	</div>
	</c:if>
</c:forEach>		
		<div id="tab-${tabId}">
	
				<c:forEach items="${propertiesHolders}" var="holder">
				
				<c:set value="${researcher:isThereMetadataNoEditable(holder.shortName, holder.class)}" var="isThereMetadataNoEditable"></c:set>
					
					
							<%!public URL fileURL;%>

							<c:set var="urljspcustom"
								value="/authority/jdyna/custom/edit${holder.shortName}.jsp" scope="request" />
								
							<%
								String filePath = (String)pageContext.getRequest().getAttribute("urljspcustom");

										fileURL = pageContext.getServletContext().getResource(
												filePath);
							%>

							<%
								if (fileURL != null) {
							%>
				
											
				
				
						<c:set var="holder" value="${holder}" scope="request"/>
						<c:set var="isThereMetadataNoEditable" value="${isThereMetadataNoEditable}" scope="request"/>												
						<c:import url="${urljspcustom}" />
					
					
							<%
								} else {
							%>
					
						<div id="hidden_first${holder.shortName}">&nbsp;</div>
						<div id="${holder.shortName}" class="box ${holder.collapsed?"":"expanded"}">
						  <h3><a href="#">${holder.title}</a></h3>
						  <div>
								<fmt:message
										key='jsp.layout.hku.researcher.message.personsandemail.${holder.shortName}' />

						<c:forEach
							items="${propertiesDefinitionsInHolder[holder.shortName]}"
							var="tipologiaDaVisualizzare">
							<c:set var="hideLabel">${fn:length(propertiesDefinitionsInHolder[holder.shortName]) le 1}</c:set>
						
							<c:set var="show" value="true" />
							<c:choose>							
							<c:when
								test="${admin or (tipologiaDaVisualizzare.accessLevel eq HIGH_ACCESS)}">
								<c:set var="disabled" value="" />
								<c:set var="visibility" value="true" />
							</c:when>
							<c:when 
								test="${(tipologiaDaVisualizzare.accessLevel eq LOW_ACCESS)}">
								<c:set var="disabled" value="${disabledfield}" />
								<c:set var="visibility" value="false" />
							</c:when>
							<c:when 
								test="${(tipologiaDaVisualizzare.accessLevel eq STANDARD_ACCESS)}">
								<c:set var="disabled" value="${disabledfield}" />
								<c:set var="visibility" value="true" />
							</c:when>
							<c:otherwise>
								<c:set var="show" value="false" />
							</c:otherwise>
							</c:choose>	
							<c:if
								test="${show && dyna:instanceOf(tipologiaDaVisualizzare,'it.cilea.osd.jdyna.model.ADecoratorTypeDefinition')}">

								<c:set var="totalHit" value="0"/>
								<c:set var="limit" value="5"/>
								<c:set var="offset" value="0"/>											
								<c:set var="pageCurrent" value="0"/>	
								<c:set var="editmode" value="true"/>
								
								<div
									id="viewnested_${tipologiaDaVisualizzare.real.id}" class="viewnested">
										<img src="<%=request.getContextPath()%>/image/cris/bar-loader.gif" class="loader" />
											<fmt:message key="jsp.jdyna.nestedloading" />
								<span class="spandatabind nestedinfo">${tipologiaDaVisualizzare.real.id}</span>
								<span id="nested_${tipologiaDaVisualizzare.real.id}_totalHit" class="spandatabind">0</span>
								<span id="nested_${tipologiaDaVisualizzare.real.id}_limit" class="spandatabind">5</span>
								<span id="nested_${tipologiaDaVisualizzare.real.id}_pageCurrent" class="spandatabind">0</span>
								<span id="nested_${tipologiaDaVisualizzare.real.id}_editmode" class="spandatabind">false</span>
								</div>
							</c:if>


							<c:if
								test="${show && (dyna:instanceOf(tipologiaDaVisualizzare,'it.cilea.osd.jdyna.model.ADecoratorPropertiesDefinition'))}">
								
								<%
								List<String> parameters = new ArrayList<String>();
												parameters.add(pageContext.getAttribute(
														"simpleNameAnagraficaObject").toString());
												parameters
														.add(((ADecoratorPropertiesDefinition) pageContext
																.getAttribute("tipologiaDaVisualizzare"))
																.getShortName());
												pageContext.setAttribute("parameters", parameters);
								%>
								<dyna:edit tipologia="${tipologiaDaVisualizzare.object}" disabled="${disabled}"
									propertyPath="anagraficadto.anagraficaProperties[${tipologiaDaVisualizzare.shortName}]"
									ajaxValidation="validateAnagraficaProperties" hideLabel="${hideLabel}"
									validationParams="${parameters}" visibility="${visibility}"/>
									
							</c:if>

						</c:forEach>
		</div>	
</div>	
				
<% } %>
				</c:forEach>
<br/>
<div class="jdyna-form-button">
				<input id="submit_save" type="submit"
					value="<fmt:message key="jsp.layout.hku.researcher.button.save"/>" />
				<input type="submit" name="cancel"
					value="<fmt:message key="jsp.layout.hku.researcher.button.cancel"/>" />
					</div>
</div>			
</div>				
				
</form:form>
</div>

</dspace:layout>