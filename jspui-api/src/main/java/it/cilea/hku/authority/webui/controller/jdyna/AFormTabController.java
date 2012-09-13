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

import it.cilea.osd.jdyna.controller.FormTabController;
import it.cilea.osd.jdyna.model.Containable;
import it.cilea.osd.jdyna.web.IPropertyHolder;
import it.cilea.osd.jdyna.web.Tab;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.Errors;

public abstract class AFormTabController<H extends IPropertyHolder<Containable>, T extends Tab<H>>
		extends
		FormTabController<H, T> {

	private String specificPartPath;
	
	public AFormTabController(Class<T> clazzT, Class<H> clazzB) {
		super(clazzT, clazzB);
	}

	public String getSpecificPartPath() {
		return specificPartPath;
	}

	public void setSpecificPartPath(String specificPartPath) {
		this.specificPartPath = specificPartPath;
	}

	@Override
	protected Map referenceData(HttpServletRequest request, Object command,
			Errors errors) throws Exception {
		
		Map model = super.referenceData(request, command, errors);
		model.put("specificPartPath", getSpecificPartPath());
		return model;
		
	}
}
