package pl.pawelb.svexample.pages;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.context.annotation.Scope;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Component;

import pl.pawelb.svexample.custom.CustomTable;
import pl.pawelb.svexample.ds.Task;
import ru.xpoft.vaadin.VaadinView;

import com.vaadin.addon.jpacontainer.JPAContainerFactory;

/**
 * Task list for every logged in user
 * 
 * @author pawelb
 * 
 */
@Component
@Scope("prototype")
@VaadinView(TaskListPage.NAME)
@Secured("ROLE_USER")
public class TaskListPage extends BaseTaskListPage {

	@PersistenceContext
	private EntityManager em;

	public static final String NAME = "taskList";

	@PostConstruct
	public void init() {
		super.init();

		// data table
		tasksContainer = JPAContainerFactory.makeReadOnly(Task.class, em);

		tasksTable = new CustomTable("table.taskList", tasksContainer);
		tasksTable.setVisibleColumns(new String[] { "author", "taskNumber", "name", "manhours", "importance",
				"estimatedEndDate", "actualEndDate", "description" });
		tasksTable.setColumnWidth("name", 200);
		tasksTable.setWidth("100%");
		tasksTable.setSelectable(true);
		baseLayout.addComponent(tasksTable);
	}

}
