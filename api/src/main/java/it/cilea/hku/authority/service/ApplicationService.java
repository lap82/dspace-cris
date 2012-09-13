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
package it.cilea.hku.authority.service;

import it.cilea.hku.authority.dao.RPSubscriptionDao;
import it.cilea.hku.authority.dao.ResearcherGrantDao;
import it.cilea.hku.authority.dao.ResearcherPageDao;
import it.cilea.hku.authority.dao.StatSubscriptionDao;
import it.cilea.hku.authority.dao.UserWSDao;
import it.cilea.hku.authority.model.RPSubscription;
import it.cilea.hku.authority.model.ResearcherGrant;
import it.cilea.hku.authority.model.ResearcherPage;
import it.cilea.hku.authority.model.StatSubscription;
import it.cilea.hku.authority.model.UserWS;
import it.cilea.hku.authority.util.ResearcherPageUtils;
import it.cilea.osd.common.dao.IApplicationDao;
import it.cilea.osd.common.model.Identifiable;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;

/**
 * This class provide access to the RP database service layer. Every methods
 * work in transactional context defined in the Spring configuration file:
 * applicationContext-rp-service.xml
 * 
 * @author cilea
 * 
 */
public class ApplicationService extends ExtendedTabService
{
    protected IApplicationDao applicationDao;

    private ResearcherPageDao researcherPageDao;

    private ResearcherGrantDao researcherGrantDao;

    private RPSubscriptionDao rpSubscriptionDao;

    private StatSubscriptionDao statSubscriptionDao;
    
    private UserWSDao userWSDao;

    /**
     * Initialization method
     */
    public void init()
    {
        researcherPageDao = (ResearcherPageDao) getDaoByModel(ResearcherPage.class);
        researcherGrantDao = (ResearcherGrantDao) getDaoByModel(ResearcherGrant.class);
        rpSubscriptionDao = (RPSubscriptionDao) getDaoByModel(RPSubscription.class);
        statSubscriptionDao = (StatSubscriptionDao) getDaoByModel(StatSubscription.class);
        userWSDao = (UserWSDao)getDaoByModel(UserWS.class);
    }

    /**
     * Setter for the applicationDao
     * 
     * @param applicationDao
     *            the dao to use for generic query
     */
    public void setApplicationDao(IApplicationDao applicationDao)
    {
        this.applicationDao = applicationDao;
    }

    /**
     * Evict a persistent object from the HibernateSession
     * 
     * @see Session#evict(Object)
     */
    public void evict(Identifiable identifiable)
    {
        applicationDao.evict(identifiable);
    }

    public long countSubscriptionsByRp(ResearcherPage rp)
    {
        return rpSubscriptionDao.countByRp(rp);
    }

    public List<ResearcherPage> getRPSubscriptionsByEPersonID(int eid)
    {
        return rpSubscriptionDao.findRPByEpersonID(eid);
    }

    public boolean isRPSubscribed(int epersonID, ResearcherPage rp)
    {
        return rpSubscriptionDao.uniqueByEpersonIDandRp(epersonID, rp) != null;
    }

    public RPSubscription getRPSubscription(int epersonID, ResearcherPage rp)
    {
        return rpSubscriptionDao.uniqueByEpersonIDandRp(epersonID, rp);
    }

    public void deleteRPSubscriptionByEPersonID(int id)
    {
        rpSubscriptionDao.deleteByEpersonID(id);
    }

    /**
     * Lookup a ResearcherPage by the unique identifier StaffNo
     * 
     * @param staffNo
     *            the staff number of the researcher
     * @return the ResearcherPage
     */
    public ResearcherPage getResearcherPageByStaffNo(String staffNo)
    {
        return researcherPageDao.uniqueResearcherPageByStaffNo(staffNo);
    }

    /**
     * Get all researcher in a specific status. If status is null all the
     * ResearcherPage will be returned
     * 
     * @param status
     *            the status to look up or null if any
     * @return the list of ResearcherPage in the supplied status or all
     */
    public List<ResearcherPage> getAllResearcherPageByStatus(Boolean status)
    {
        if (status == null)
        {
            return getList(ResearcherPage.class);
        }
        return researcherPageDao.findAllResearcherPageByStatus(status);
    }

    /**
     * Return a list of all the ResearcherPage that have at least one
     * "name form" that match exactly (as string) the supplied name.
     * 
     * @param name
     *            the name to look up
     * @return the list of ResearcherPage with a name form that match exactly
     *         the lookup text
     */
    public List<ResearcherPage> getResearcherPageByName(String name)
    {
        return researcherPageDao.findAllResearcherByName(name);
    }

    /**
     * The number of all the ResearcherPage.
     * 
     * @return the number of all the ResearcherPage
     */
    public long count()
    {
        return researcherPageDao.count();
    }

    /**
     * The number of ResearcherPage that have at least one "name form" that
     * match exactly (as string) the supplied name.
     * 
     * @param name
     *            the name to look up
     * @return the number of ResearcherPage with a name form that match exactly
     *         the lookup text
     */
    public long countNamesMatching(String name)
    {
        return researcherPageDao.countAllResearcherByName(name);
    }

    /**
     * Utility method, like the {@link #countNamesMatching(String)} the counting
     * is performed excluding a specific ResearcherPage.
     * 
     * @param name
     *            the name to look up
     * @param id
     *            the id (internal db primary key) of the ResearcherPage to
     *            exclude from the match
     * 
     * @return the number of ResearcherPage with a name form that match exactly
     *         the lookup text without take care of the ResearchePage with the
     *         provided id
     */
    public long countNamesMatchingExceptResearcher(String name, Integer id)
    {
        return researcherPageDao.countAllResearcherByNameExceptResearcher(name,
                id);
    }

    /**
     * Return the list of all the ResearcherPage that make use of an additional
     * field (ResearcherPageField).
     * 
     * @param id
     *            the id (internal db primary key) of the additional field
     * @return the list of all the ResearcherPage that make use of the
     *         additional field
     */
    public List<ResearcherPage> getResearchersPageByPageFieldId(Integer id)
    {
        return researcherPageDao.findAllResearcherByField(id);
    }

    /**
     * Return the ReseacherPage related to the supplied autority key.
     * 
     * @param authorityKey
     *            the rp authority key (i.e. rp00024)
     * @return the ResearcherPage; null if the key is invalid
     */
    public ResearcherPage getResearcherByAuthorityKey(String authorityKey)
    {
        ResearcherPage rp = null;
        try
        {
            rp = get(ResearcherPage.class,
                    ResearcherPageUtils
                            .getRealPersistentIdentifier(authorityKey));
        }
        catch (Exception e)
        {
            // do nothing
        }

        return rp;
    }

    /**
     * Return the list of ResearcherPage with name fields modified after the
     * supplied date
     * 
     * @param nameTimestampLastModified
     *            the starting date for the lookup
     * @return the list of ResearcherPage with name fields modified after the
     *         supplied date
     */
    public List<ResearcherPage> getResearchersPageByNamesTimestampLastModified(
            Date nameTimestampLastModified)
    {
        return researcherPageDao
                .findAllResearcherByNamesTimestampLastModified(nameTimestampLastModified);
    }

    /**
     * Retrieve all the ResearcherPage created in the specified timestamp range.
     * Start and end date can't be both null.
     * 
     * @param start
     *            the start date, no lower limit if null
     * @param end
     *            the end date, no upper limit if null
     * @return the list of ResearcherPage created in the specified timestamp
     *         range
     */
    public List<ResearcherPage> getRPByCriteriaOnDateCreation(Date start,
            Date end)
    {
        if (start != null && end != null)
        {
            return getRPinDateRange(start, end);
        }
        else
        {
            if (start == null && end != null)
            {
                return getRPBeforeDateCreation(end);
            }
            if (end == null && start != null)
            {
                return getRPAfterDateCreation(start);
            }
        }
        return null;
    }

    /* Private utility methods for the retrieve of RP created in a date range */
    private List<ResearcherPage> getRPinDateRange(Date start, Date end)
    {
        return researcherPageDao.findAllResearcherInDateRange(start, end);
    }

    private List<ResearcherPage> getRPBeforeDateCreation(Date end)
    {
        return researcherPageDao.findAllResearcherByCreationDateBefore(end);
    }

    private List<ResearcherPage> getRPAfterDateCreation(Date start)
    {
        return researcherPageDao.findAllResearcherByCreationDateAfter(start);
    }

    /**
     * Retrieve all the ResearcherPage with the rp identifier in the specified
     * range. Start and end identifier can't be both null. The comparation
     * algorithm is alphanumeric
     * 
     * @param start
     *            the start rp identifier, no lower limit if null
     * @param end
     *            the end rp identifier, no upper limit if null
     * @return the list of ResearcherPage with the rp identifier in the
     *         specified range
     */
    public List<ResearcherPage> getAllResearcherByCriteriaOnRPID(String start,
            String end)
    {
        if (start != null && end != null)
        {
            return getRPinRPIDRange(start, end);
        }
        else
        {
            if (start == null && end != null)
            {
                return getRPPrevRPID(end);
            }
            if (end == null && start != null)
            {
                return getRPNextRPID(start);
            }
        }
        return null;
    }

    /*
     * Private utility methods for the retrieve of RP with rp identifier in a
     * specified range
     */
    private List<ResearcherPage> getRPNextRPID(String start)
    {
        Integer s = null;
        if (start != null && !start.isEmpty())
        {
            s = ResearcherPageUtils.getRealPersistentIdentifier(start);
            return researcherPageDao.findAllNextResearcherByIDStart(s);
        }
        return null;
    }

    private List<ResearcherPage> getRPPrevRPID(String end)
    {
        Integer e = null;
        if (end != null && !end.isEmpty())
        {
            e = ResearcherPageUtils.getRealPersistentIdentifier(end);
            return researcherPageDao.findAllPrevResearcherByIDEnd(e);
        }
        return null;
    }

    private List<ResearcherPage> getRPinRPIDRange(String start, String end)
    {
        Integer s = null;
        Integer e = null;
        if (start != null && !start.isEmpty() && end != null && !end.isEmpty())
        {
            e = ResearcherPageUtils.getRealPersistentIdentifier(end);
            s = ResearcherPageUtils.getRealPersistentIdentifier(start);
            return researcherPageDao.findAllResearcherInIDRange(s, e);
        }
        return null;
    }

    /**
     * Retrieve all the ResearcherPage with the staffNo in the specified range.
     * Start and end staffNo can't be both null. The comparation algorithm is
     * alphanumeric
     * 
     * @param start
     *            the start staffNo, no lower limit if null
     * @param end
     *            the end staffNo, no upper limit if null
     * @return the list of ResearcherPage with the staffNo in the specified
     *         range
     */
    public List<ResearcherPage> getAllResearcherByCriteriaOnStaffNo(
            String start, String end)
    {
        if (start != null && end != null)
        {
            return getRPinStaffNoRange(start, end);
        }
        else
        {
            if (start == null && end != null)
            {
                return getRPPrevStaffNo(end);
            }
            if (end == null && start != null)
            {
                return getRPNextStaffNo(start);
            }
        }
        return null;
    }

    /*
     * Private utility methods for the retrieve of RP with staffNo in a
     * specified range
     */
    private List<ResearcherPage> getRPNextStaffNo(String start)
    {
        return researcherPageDao.findAllNextResearcherByStaffNoStart(start);
    }

    private List<ResearcherPage> getRPPrevStaffNo(String end)
    {
        return researcherPageDao.findAllPrevResearcherByStaffNoEnd(end);
    }

    private List<ResearcherPage> getRPinStaffNoRange(String start, String end)
    {
        return researcherPageDao.findAllResearcherInStaffNoRange(start, end);
    }

    public List<StatSubscription> getAllStatSubscriptionByFreq(int freq)
    {
        return statSubscriptionDao.findByFreq(freq);
    }

    public List<StatSubscription> getAllStatSubscriptionByEPersonID(int eid)
    {
        return statSubscriptionDao.findByEPersonID(eid);
    }

    public List<StatSubscription> getRPStatSubscriptionByEPersonID(int id,
            ResearcherPage rp)
    {
        return statSubscriptionDao.findByEPersonIDandRP(id, rp);
    }

    public List<StatSubscription> getHandleStatSubscriptionByEPersonID(int id,
            String handle)
    {
        return statSubscriptionDao.findByEPersonIDandHandle(id, handle);
    }

    public void deleteStatSubscriptionsByEPersonID(int id)
    {
        statSubscriptionDao.deleteByEPersonID(id);
    }

    public <T, PK extends Serializable> List<T> getList(Class<T> model,
            List<PK> ids)
    {
        return applicationDao.getList(model, ids);
    }

    public ResearcherGrant getResearcherGrantByCode(String projectcode)
    {
        return researcherGrantDao.uniqueRGByCode(projectcode);
    }
    
    public UserWS getUserWSByUsernameAndPassword(String username, String password) {
        return userWSDao.uniqueByUsernameAndPassword(username, password); 
    }

    public UserWS getUserWSByToken(String token) {
        return userWSDao.uniqueByToken(token); 
    }
    
}