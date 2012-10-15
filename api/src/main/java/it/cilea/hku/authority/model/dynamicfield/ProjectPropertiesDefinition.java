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
package it.cilea.hku.authority.model.dynamicfield;

import it.cilea.hku.authority.model.Project;
import it.cilea.osd.jdyna.model.PropertiesDefinition;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="model_grant_jdyna_propertiesdefinition")
@NamedQueries( {
    @NamedQuery(name = "ProjectPropertiesDefinition.findAll", query = "from ProjectPropertiesDefinition order by id"),    
    @NamedQuery(name = "ProjectPropertiesDefinition.findValoriOnCreation", query = "from ProjectPropertiesDefinition where onCreation=true"),
    @NamedQuery(name = "ProjectPropertiesDefinition.findSimpleSearch", query = "from ProjectPropertiesDefinition where simpleSearch=true"),
    @NamedQuery(name = "ProjectPropertiesDefinition.findAdvancedSearch", query = "from ProjectPropertiesDefinition where advancedSearch=true"),
    @NamedQuery(name = "ProjectPropertiesDefinition.uniqueIdByShortName", query = "select id from ProjectPropertiesDefinition where shortName = ?"),
    @NamedQuery(name = "ProjectPropertiesDefinition.uniqueByShortName", query = "from ProjectPropertiesDefinition where shortName = ?"),
    @NamedQuery(name = "ProjectPropertiesDefinition.findValoriDaMostrare", query = "from ProjectPropertiesDefinition where showInList = true")    
    
})
public class ProjectPropertiesDefinition extends PropertiesDefinition {
		
	@Transient
	public Class<Project> getAnagraficaHolderClass() {
		return Project.class;
	}

	@Transient
	public Class<ProjectProperty> getPropertyHolderClass() {
		return ProjectProperty.class;
	}
	
	@Override
	public Class<DecoratorProjectPropertiesDefinition> getDecoratorClass() {
		return DecoratorProjectPropertiesDefinition.class;
	}

}
