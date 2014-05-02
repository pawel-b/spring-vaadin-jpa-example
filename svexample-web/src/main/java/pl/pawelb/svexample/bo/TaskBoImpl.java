package pl.pawelb.svexample.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.pawelb.svexample.dao.TaskRepository;
import pl.pawelb.svexample.ds.Task;

/**
 * BO implementation
 * 
 * @author pawelb
 *
 */
@Service
@Transactional
public class TaskBoImpl implements TaskBo {

    @Autowired
    private TaskRepository taskRepository;

    /* (non-Javadoc)
     * @see pl.pawelb.svexample.bo.TaskBo#findAllTasks()
     */
    @Override
    public List<Task> findAllTasks() {
        return (List<Task>) taskRepository.findAll();
    }

    /* (non-Javadoc)
     * @see pl.pawelb.svexample.bo.TaskBo#findOne(java.lang.Long)
     */
    @Override
    public Task findOne(Long id) {
        return taskRepository.findOne(id);
    }

    /* (non-Javadoc)
     * @see pl.pawelb.svexample.bo.TaskBo#saveTask(pl.pawelb.svexample.ds.Task)
     */
    @Override
    @Secured("ROLE_MANAGER")
    public Task saveTask(Task entity) {
        return taskRepository.save(entity);
    }

    /* (non-Javadoc)
     * @see pl.pawelb.svexample.bo.TaskBo#deleteTask(pl.pawelb.svexample.ds.Task)
     */
    @Override
    @Secured("ROLE_MANAGER")
    public void deleteTask(Task entity) {
        taskRepository.delete(entity);
    }
}
