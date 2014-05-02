package pl.pawelb.svexample.dao;

import java.util.List;

import org.joda.time.LocalDate;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import pl.pawelb.svexample.ds.Task;

/**
 * Spring data repository
 * 
 * @author pawelb
 *
 */
public interface TaskRepository extends CrudRepository<Task, Long> {
	
	/**
	 * Example method
	 * 
	 * @param estimatedEndDate
	 * @return
	 */
	@Query("select t from Task t where t.actualEndDate is null and t.estimatedEndDate = ?1")
	List<Task> findTasksToWarnAboutEstimatedEndDate(LocalDate estimatedEndDate);

	/**
	 * Example method
	 * 
	 * @param author
	 * @param start
	 * @param end
	 * @return
	 */
	List<Task> findTasksByAuthorAndActualEndDateBetweenOrderByActualEndDateAsc(String author, LocalDate start, LocalDate end);

}