package pl.pawelb.svexample.dao;

import java.util.Date;

import org.hibernate.envers.RevisionListener;

import pl.pawelb.svexample.ds.AuditedEntity;
import pl.pawelb.svexample.web.SpringSecurityHelper;

/**
 * Auditing helper
 * 
 * @author pawelb
 *
 */
public class UsernameLoggingListener implements RevisionListener {

	/* (non-Javadoc)
	 * @see org.hibernate.envers.RevisionListener#newRevision(java.lang.Object)
	 */
	public void newRevision(Object revisionEntity) {
		AuditedEntity exampleRevEntity = (AuditedEntity) revisionEntity;
		String username = SpringSecurityHelper.getLoggedUsername();
		Date now = new Date();
		exampleRevEntity.setUsername(username);
		exampleRevEntity.setRevisionLocalDate(now);
	}

}