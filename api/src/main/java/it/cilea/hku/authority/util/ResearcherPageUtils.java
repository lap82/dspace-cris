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
package it.cilea.hku.authority.util;

import it.cilea.hku.authority.model.ResearcherPage;
import it.cilea.hku.authority.model.RestrictedField;
import it.cilea.hku.authority.model.VisibilityConstants;
import it.cilea.hku.authority.model.dynamicfield.RPProperty;
import it.cilea.hku.authority.service.ApplicationService;
import it.cilea.osd.jdyna.web.Tab;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.activation.MimetypesFileTypeMap;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.WildcardFilter;
import org.dspace.core.ConfigurationManager;
import org.dspace.core.Utils;
import org.springframework.web.multipart.MultipartFile;

/**
 * This class provides some static utility methods to extract information from
 * the rp identifier quering the RPs database.
 * 
 * @author cilea
 * 
 */
public class ResearcherPageUtils
{
    public static final String DIRECTORY_TAB_ICON = "icon";

	public static final String PREFIX_TAB_ICON = "tab_";

	/**
     * Formatter to build the rp identifier
     */
    private static DecimalFormat persIdentifierFormat = new DecimalFormat(
            "00000");

    /**
     * the applicationService to query the RP database, injected by Spring IoC
     */
    private static ApplicationService applicationService;

    /**
     * Constructor use by Spring IoC
     * 
     * @param applicationService
     *            the applicationService to query the RP database, injected by
     *            Spring IoC
     */
    public ResearcherPageUtils(ApplicationService applicationService)
    {
        ResearcherPageUtils.applicationService = applicationService;
    }

    /**
     * Build the rp identifier of the supplied ResearcherPage
     * 
     * @param rp
     *            the researcher page
     * @return the rp identifier of the supplied ResearhcerPage
     */
    public static String getPersistentIdentifier(ResearcherPage rp)
    {
        return "rp" + persIdentifierFormat.format(rp.getId());
    }

    /**
     * Build the rp identifier starting from the db internal primary key
     * 
     * @param rp
     *            the internal db primary key of the researcher page
     * @return the rp identifier of the supplied ResearhcerPage
     */
    public static String getPersistentIdentifier(Integer rp)
    {
        return "rp" + persIdentifierFormat.format(rp);
    }

    /**
     * Extract the db primary key from a rp identifier
     * 
     * @param authorityKey
     *            the rp identifier
     * @return the db primary key
     */
    public static Integer getRealPersistentIdentifier(String authorityKey) 
    {
        try
        {
            return Integer.parseInt(authorityKey.substring(2));
        }
        catch (NumberFormatException e)
        {
            return null;
        }
    }

    /**
     * Return a label to use with a specific form of the name of the researcher.
     * 
     * @param alternativeName
     *            the form of the name to use
     * @param rp
     *            the researcher page
     * @return the label to use
     */
    public static String getLabel(String alternativeName, ResearcherPage rp)
    {        
        if (alternativeName.equals(rp.getFullName()))
        {
            return rp.getFullName() + " " + rp.getChineseName().getValue();
        }
        else
        {
            return alternativeName + " See \"" + rp.getFullName();
        }
    }

    /**
     * Return a label to use with a specific form of the name of the researcher.
     * 
     * @param alternativeName
     *            the form of the name to use
     * @param rpKey
     *            the rp identifier of the ResearcherPage
     * @return the label to use
     */
    public static String getLabel(String alternativeName, String rpkey)
    {
        if (rpkey != null && rpkey.startsWith("rp"))
        {
            ResearcherPage rp = applicationService.get(ResearcherPage.class,
                    Integer.parseInt(rpkey.substring(2)));
            return getLabel(alternativeName, rp);
        }
        return alternativeName;
    }
    
    /**
     * Check if the supplied form is the fullname of the ResearcherPage
     * 
     * @param alternativeName
     *            the string to check
     * @param rpkey
     *            the rp identifier
     * @return true, if the form is the fullname of the ResearcherPage with the
     *         supplied rp identifier
     */
    public static boolean isFullName(String alternativeName, String rpkey)
    {
        if (alternativeName != null && rpkey != null && rpkey.startsWith("rp"))
        {
            ResearcherPage rp = applicationService.get(ResearcherPage.class,
                    getRealPersistentIdentifier(rpkey));            
            return alternativeName.equals(rp.getFullName());
        }
        return false;
    }

    /**
     * Check if the supplied form is the Chinese name of the ResearcherPage
     * 
     * @param alternativeName
     *            the string to check
     * @param rpkey
     *            the rp identifier
     * @return true, if the form is the Chinese name of the ResearcherPage with
     *         the supplied rp identifier
     */
    public static boolean isChineseName(String alternativeName, String rpkey)
    {
        if (alternativeName != null && rpkey != null && rpkey.startsWith("rp"))
        {
            ResearcherPage rp = applicationService.get(ResearcherPage.class,
                    getRealPersistentIdentifier(rpkey));            
            return alternativeName.equals(rp.getChineseName().getValue());
        }
        return false;
    }
    
    /**
     * Get the fullname of the ResearcherPage
     * 
     * @param rpkey
     *            the rp identifier
     * @return the fullname of the ResearcherPage
     */
    public static String getFullName(String rpkey)
    {
        if (rpkey != null && rpkey.startsWith("rp"))
        {
            ResearcherPage rp = applicationService.get(ResearcherPage.class,
                    getRealPersistentIdentifier(rpkey));            
            return rp.getFullName();
        }
        return null;
    }
    
        
    
    /**
     * Get the staff number of the ResearcherPage
     * 
     * @param rpkey
     *            the rp identifier
     * @return the staff number of the ResearcherPage
     */
    public static String getStaffNumber(String rpkey)
    {
        if (rpkey != null && rpkey.startsWith("rp"))
        {
            ResearcherPage rp = applicationService.get(ResearcherPage.class,
                    getRealPersistentIdentifier(rpkey));            
            return rp != null?rp.getStaffNo():null;
        }
        return null;
    }

    /**
     * Get the rp identifier of the ResearcherPage with a given staffno
     * 
     * @param staffno
     *            the staffno
     * @return the rp identifier of the ResearcherPage or null
     */
    public static String getRPIdentifierByStaffno(String staffno)
    {
        if (staffno != null)
        {
            ResearcherPage rp = applicationService.getResearcherPageByStaffNo(staffno);
            if (rp != null)
            {
            	return getPersistentIdentifier(rp);
            }
        }
        return null;
    }
    
    /**
     * Get the ChineseName of the ResearcherPage
     * 
     * @param rpkey
     *            the rp identifier
     * @return the Chinese name of the ResearcherPage
     */
    public static String getChineseName(String rpkey)
    {
        if (rpkey != null && rpkey.startsWith("rp"))
        {
            ResearcherPage rp = applicationService.get(ResearcherPage.class,
                    getRealPersistentIdentifier(rpkey));            
            return VisibilityConstants.PUBLIC == rp.getChineseName().getVisibility()?
                    rp.getChineseName().getValue():"";
        }
        return null;
    }
	
	/**
	 * 
	 * Load tab icon and copy to default directory.
	 * 
	 * @param researcher
	 * @param rp
	 * @param itemImage MultipartFile to use in webform
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public static void loadTabIcon(Tab tab, String iconName,
			MultipartFile itemImage) throws IOException, FileNotFoundException {
		String pathImage = ConfigurationManager
		        .getProperty("researcherpage.file.path");
		String ext = itemImage.getOriginalFilename().substring(
		        itemImage.getOriginalFilename().lastIndexOf(".") + 1);
		File dir = new File(pathImage + File.separatorChar + DIRECTORY_TAB_ICON);
		dir.mkdir();
		File file = new File(dir, PREFIX_TAB_ICON + iconName + "." + ext);
		file.createNewFile();
		FileOutputStream out = new FileOutputStream(file);
		Utils.bufferedCopy(itemImage.getInputStream(), out);
		out.close();
		tab.setExt(ext);
		tab.setMime(itemImage.getContentType());
	}
	
	/**
	 * Remove tab icon from the server.
	 * 
	 * @param researcher
	 */
	public static void removeTabIcon(Tab tab) {
		File image = new File(ConfigurationManager
		        .getProperty("researcherpage.file.path")
		        + File.separatorChar
		        + DIRECTORY_TAB_ICON
		        + File.separatorChar + PREFIX_TAB_ICON + tab.getId() + "." + tab.getExt());
		image.delete();   
		tab.setExt(null);
		tab.setMime(null);
	}
}
