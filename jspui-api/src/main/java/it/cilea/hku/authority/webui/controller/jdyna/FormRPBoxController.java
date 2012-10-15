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

import it.cilea.hku.authority.model.dynamicfield.BoxResearcherPage;
import it.cilea.hku.authority.model.dynamicfield.RPNestedObject;
import it.cilea.hku.authority.model.dynamicfield.RPNestedPropertiesDefinition;
import it.cilea.hku.authority.model.dynamicfield.RPPropertiesDefinition;
import it.cilea.hku.authority.model.dynamicfield.RPTypeNestedObject;
import it.cilea.hku.authority.model.dynamicfield.TabResearcherPage;
import it.cilea.hku.authority.service.ExtendedTabService;
import it.cilea.osd.jdyna.model.IContainable;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.NestedCheckedException;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

public class FormRPBoxController
		extends
		AFormBoxController<RPPropertiesDefinition, BoxResearcherPage, TabResearcherPage, RPNestedPropertiesDefinition, RPTypeNestedObject> {

	
	
	public FormRPBoxController(Class<BoxResearcherPage> clazzH,
			Class<RPPropertiesDefinition> clazzTP, Class<RPTypeNestedObject> clazzTTP) {
		super(clazzH, clazzTP, clazzTTP);
	}

	@Override
	protected Map referenceData(HttpServletRequest request) throws Exception {
		String paramId = request.getParameter("id");
		String tabId = request.getParameter("tabId");
		Map<String, Object> map = super.referenceData(request);
		List<IContainable> containables = new LinkedList<IContainable>();
		if (paramId != null) {
			BoxResearcherPage box = applicationService.get(BoxResearcherPage.class, Integer.parseInt(paramId));
			((ExtendedTabService)applicationService).findOtherContainablesInBoxByConfiguration(box.getShortName(), containables, RPPropertiesDefinition.class.getName());
		}
		map.put("owneredContainablesByConfiguration", containables);
		List<IContainable> containablesList = new LinkedList<IContainable>();
		containablesList = (List<IContainable>)map.get("containablesList");
		containablesList.addAll(containables);
		map.put("allContainablesList", containablesList);
		map.put("tabId", tabId);
		return map;

	}	
	
	@Override
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {

		BoxResearcherPage object = (BoxResearcherPage) command;
		String tabId = request.getParameter("tabId");
		applicationService.saveOrUpdate(BoxResearcherPage.class,
				object);
		saveMessage(
				request,
				getText("action.box.edited", new Object[] { object.getShortName() },
						request.getLocale()));
		return new ModelAndView(getSuccessView() + "?id=" + object.getId());
	}

}
