/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 * 
 * http://www.dspace.org/license/
 * 
 * The document has moved 
 * <a href="https://svn.duraspace.org/dspace/licenses/LICENSE_HEADER">here</a>
 */
package it.cilea.hku.authority.webui.controller.jdyna;

import it.cilea.hku.authority.model.dynamicfield.BoxRPAdditionalFieldStorage;
import it.cilea.hku.authority.model.dynamicfield.EditTabRPAdditionalFieldStorage;
import it.cilea.hku.authority.model.dynamicfield.TabRPAdditionalFieldStorage;
import it.cilea.hku.authority.service.ApplicationService;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.ModelAndView;

/**
 * Concrete SpringMVC controller is used to list, delete and view detail of tab
 * 
 * @author pascarelli
 * 
 */
public class RPTabsController extends ATabsController<BoxRPAdditionalFieldStorage, TabRPAdditionalFieldStorage> {

	
	public RPTabsController(Class<TabRPAdditionalFieldStorage> tabsClass) {
		super(tabsClass);
	}


	@Override
	protected ModelAndView handleDelete(HttpServletRequest request) {
		
		String tabId = request.getParameter("id");
		Integer paramIntegerId = Integer.parseInt(tabId);
		((ApplicationService)getApplicationService()).decoupleEditTabByDisplayTab(paramIntegerId,EditTabRPAdditionalFieldStorage.class);
		return super.handleDelete(request);
		
		
	}

	
}