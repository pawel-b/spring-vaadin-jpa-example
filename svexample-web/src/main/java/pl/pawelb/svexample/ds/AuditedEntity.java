package pl.pawelb.svexample.ds;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionEntity;

import pl.pawelb.svexample.dao.UsernameLoggingListener;

/**
 * Audit entity
 * 
 * @author pawelb
 *
 */
@Entity
@Table(name = "REVINFO")
@RevisionEntity(UsernameLoggingListener.class)
public class AuditedEntity extends DefaultRevisionEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5171412135947505587L;

	@Column(name = "USERNAME")
	private String username;

	@Column(name = "REVISION_LocalDate")
	private Date revisionLocalDate;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Date getRevisionLocalDate() {
		return revisionLocalDate;
	}

	public void setRevisionLocalDate(Date revisionLocalDate) {
		this.revisionLocalDate = revisionLocalDate;
	}

}
