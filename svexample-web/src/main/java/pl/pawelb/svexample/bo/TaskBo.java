package pl.pawelb.svexample.bo;

import java.util.List;

import pl.pawelb.svexample.ds.Task;

/**
 * Simple BO interface
 * 
 * @author pawelb
 * 
 */
public interface TaskBo {

	/**
	 * Find all tasks
	 * 
	 * @return
	 */
	public abstract List<Task> findAllTasks();

	/**
	 * Find one task by ID
	 * 
	 * @param id
	 * @return
	 */
	public abstract Task findOne(Long id);

	/**
	 * Save task
	 * 
	 * @param t
	 * @return
	 */
	public abstract Task saveTask(Task t);

	/**
	 * Delete task
	 * 
	 * @param entity
	 */
	public abstract void deleteTask(Task entity);

}