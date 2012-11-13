package it.cilea.hku.authority.discovery;

import it.cilea.hku.authority.model.ACrisObject;

import java.util.List;
import java.util.Map;

/**
 * This class is used to add dynamically to the item information from linked
 * CRIS entities as it was metadata of the item
 * 
 * @author bollini
 * 
 */
public class CrisItemEnhancer
{
    // the metadata list to lookup for link to CRIS entities
    // (dc.contributor.author, dc.contributor.editor, etc.)
    private List<String> metadata;

    // the Class of the linked CRIS entity
    private Class<? extends ACrisObject> clazz;

    // the "element" of the virtual metadata (author)
    private String alias;

    // the path in the CRIS entity property to map to a specific virtual "qualifier"
    // crisitem.author.dept = dept 
    private Map<String, String> qualifiers2path;

    public List<String> getMetadata()
    {
        return metadata;
    }

    public void setMetadata(List<String> metadata)
    {
        this.metadata = metadata;
    }

    public Class<? extends ACrisObject> getClazz()
    {
        return clazz;
    }

    public void setClazz(Class<? extends ACrisObject> clazz)
    {
        this.clazz = clazz;
    }

    public String getAlias()
    {
        return alias;
    }

    public void setAlias(String alias)
    {
        this.alias = alias;
    }

    public Map<String, String> getQualifiers2path()
    {
        return qualifiers2path;
    }

    public void setQualifiers2path(Map<String, String> qualifiers2path)
    {
        this.qualifiers2path = qualifiers2path;
    }
}
