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
package it.cilea.hku.authority.dao;

import it.cilea.hku.authority.model.UserWS;
import it.cilea.osd.common.dao.PaginableObjectDao;

/**
 * This interface define the methods available to retrieve User for web services
 * 
 * @author cilea
 * 
 */
public interface UserWSDao extends
        PaginableObjectDao<UserWS, Integer>
{

    UserWS uniqueByUsernameAndPassword(String username, String password);

    UserWS uniqueByToken(String token);
   
}