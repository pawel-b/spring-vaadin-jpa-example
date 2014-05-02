package pl.pawelb.svexample.ds;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Type;
import org.hibernate.envers.Audited;
import org.joda.time.LocalDate;

/**
 * Main entity
 * 
 * @author pawelb
 *
 */
@Entity
@Audited
@Table(name = "TASK")
public class Task implements Serializable {

    /**
	 * 
	 */
    private static final long serialVersionUID = 7833704611304619077L;

    public static final Integer[] IMPORTANCES = new Integer[] { 1, 2, 3, 4 };

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "TASK_NUMBER")
    @Size(max = 64)
    private String taskNumber;

    @Column(name = "NAME")
    @Size(min = 3, max = 1024)
    @NotNull
    private String name;

    @Column(name = "DESCRIPTION")
    @Size(max = 1024)
    private String description;

    @Column(name = "MANHOURS")
    private Integer manhours;

    @Column(name = "IMPORTANCE")
    private Integer importance;

    @Column(name = "ESTIMATED_END_DATE")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    private LocalDate estimatedEndDate;

    @Column(name = "ACTUAL_END_DATE")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    private LocalDate actualEndDate;

    @Size(min=3, max = 128)
    @Column(name = "AUTHOR")
    private String author;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTaskNumber() {
        return taskNumber;
    }

    public void setTaskNumber(String taskNumber) {
        this.taskNumber = taskNumber;
    }

    public Integer getManhours() {
        return manhours;
    }

    public void setManhours(Integer manhours) {
        this.manhours = manhours;
    }

    public Integer getImportance() {
        return importance;
    }

    public void setImportance(Integer importance) {
        this.importance = importance;
    }

    public LocalDate getEstimatedEndDate() {
        return estimatedEndDate;
    }

    public void setEstimatedEndDate(LocalDate estimatedEndDate) {
        this.estimatedEndDate = estimatedEndDate;
    }

    public LocalDate getActualEndDate() {
        return actualEndDate;
    }

    public void setActualEndDate(LocalDate actualEndDate) {
        this.actualEndDate = actualEndDate;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((actualEndDate == null) ? 0 : actualEndDate.hashCode());
        result = prime * result + ((author == null) ? 0 : author.hashCode());
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((estimatedEndDate == null) ? 0 : estimatedEndDate.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((importance == null) ? 0 : importance.hashCode());
        result = prime * result + ((manhours == null) ? 0 : manhours.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((taskNumber == null) ? 0 : taskNumber.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Task other = (Task) obj;
        if (actualEndDate == null) {
            if (other.actualEndDate != null)
                return false;
        } else if (!actualEndDate.equals(other.actualEndDate))
            return false;
        if (author == null) {
            if (other.author != null)
                return false;
        } else if (!author.equals(other.author))
            return false;
        if (description == null) {
            if (other.description != null)
                return false;
        } else if (!description.equals(other.description))
            return false;
        if (estimatedEndDate == null) {
            if (other.estimatedEndDate != null)
                return false;
        } else if (!estimatedEndDate.equals(other.estimatedEndDate))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (importance == null) {
            if (other.importance != null)
                return false;
        } else if (!importance.equals(other.importance))
            return false;
        if (manhours == null) {
            if (other.manhours != null)
                return false;
        } else if (!manhours.equals(other.manhours))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (taskNumber == null) {
            if (other.taskNumber != null)
                return false;
        } else if (!taskNumber.equals(other.taskNumber))
            return false;
        return true;
    }

}
