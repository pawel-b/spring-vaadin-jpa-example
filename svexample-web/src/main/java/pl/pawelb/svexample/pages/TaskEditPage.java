package pl.pawelb.svexample.pages;

import java.util.Arrays;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Component;

import pl.pawelb.svexample.bo.TaskBo;
import pl.pawelb.svexample.custom.CustomGroupFieldFactory;
import pl.pawelb.svexample.custom.CustomTable;
import pl.pawelb.svexample.ds.Task;
import pl.pawelb.svexample.web.SpringSecurityHelper;
import pl.pawelb.svexample.web.ViewUtils;
import ru.xpoft.vaadin.VaadinView;

import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.addon.jpacontainer.JPAContainerItem;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Field;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.Panel;

/**
 * Task edit page - only for managers
 * 
 * @author pawelb
 * 
 */
@Component
@Scope("prototype")
@VaadinView(TaskEditPage.NAME)
@Secured("ROLE_MANAGER")
public class TaskEditPage extends BaseTaskListPage {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3528384016248265027L;
	public static final String NAME = "taskEdit";

	@Autowired
	private TaskBo tasksBo;

	@PersistenceContext
	private EntityManager em;

	FormLayout formLayout;
	BeanFieldGroup<Task> fieldGroup;

	ComboBox importanceSelect;

	@PostConstruct
	public void init() {
		super.init();
		HorizontalLayout tableLayout = new HorizontalLayout();
		tableLayout.setSpacing(true);

		// data table
		tasksContainer = JPAContainerFactory.makeReadOnly(Task.class, em);

		tasksTable = new CustomTable(ViewUtils.getMessage("pages.title.taskList"), tasksContainer);
		tasksTable.setVisibleColumns(new String[] { "author", "taskNumber", "name", "manhours", "importance",
				"estimatedEndDate", "actualEndDate" });
		tasksTable.setColumnWidth("name", 200);
		tasksTable.setWidth("90%");
		tasksTable.setSelectable(true);
		tableLayout.addComponent(tasksTable);

		// edit form
		final Panel formPanel = new Panel();
		formLayout = new FormLayout();
		formLayout.setMargin(true);
		formLayout.setSpacing(true);
		formLayout.setSizeFull();
		formPanel.setEnabled(false);
		formPanel.setVisible(false);
		formPanel.setCaption(ViewUtils.getMessage("pages.title.taskEdit"));

		fieldGroup = new BeanFieldGroup<Task>(Task.class);
		fieldGroup.setFieldFactory(new CustomGroupFieldFactory());

		// other fields
		formLayout.addComponent(fieldGroup.buildAndBind(ViewUtils.getMessage("table.header.number"), "taskNumber"));
		formLayout.addComponent(fieldGroup.buildAndBind(ViewUtils.getMessage("table.header.name"), "name"));
		formLayout.addComponent(fieldGroup.buildAndBind(ViewUtils.getMessage("table.header.manhours"), "manhours"));
		importanceSelect = new ComboBox(ViewUtils.getMessage("table.header.importance"),
				Arrays.asList(Task.IMPORTANCES));
		fieldGroup.bind(importanceSelect, "importance");
		formLayout.addComponent(importanceSelect);
		formLayout.addComponent(fieldGroup.buildAndBind(ViewUtils.getMessage("table.header.estimatedEndDate"),
				"estimatedEndDate"));
		formLayout.addComponent(fieldGroup.buildAndBind(ViewUtils.getMessage("table.header.actualEndDate"),
				"actualEndDate"));
		Field f = fieldGroup.buildAndBind(ViewUtils.getMessage("table.header.description"), "description");
		f.setWidth("500px");
		formLayout.addComponent(f);

		fieldGroup.setBuffered(true);
		HorizontalLayout buttonLayout = new HorizontalLayout();
		buttonLayout.setSpacing(true);
		buttonLayout.addComponent(new Button(ViewUtils.getMessage("commons.save"), new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				try {
					Task taskToSave = fieldGroup.getItemDataSource().getBean();
					LocalDate previousDate = taskToSave.getEstimatedEndDate();
					fieldGroup.commit();
					boolean isNew = taskToSave.getId() == null;
					taskToSave = tasksBo.saveTask(taskToSave);
					if (isNew) {
						ViewUtils.refreshJpaContainer(tasksContainer);
					}
					tasksTable.refreshRowCache();
					Notification.show(ViewUtils.getMessage("commons.ok"), Type.HUMANIZED_MESSAGE);
					formPanel.setEnabled(false);
					formPanel.setVisible(false);
					tasksTable.focus();
				} catch (CommitException e) {
					Notification.show(e.getMessage(), Type.WARNING_MESSAGE);
				}
			}
		}));
		buttonLayout.addComponent(new Button(ViewUtils.getMessage("commons.delete"), new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				Task taskToDelete = fieldGroup.getItemDataSource().getBean();
				tasksBo.deleteTask(taskToDelete);
				ViewUtils.refreshJpaContainer(tasksContainer);
				tasksTable.refreshRowCache();
				formPanel.setEnabled(false);
				formPanel.setVisible(false);
				tasksTable.focus();
			}
		}));
		formLayout.addComponent(buttonLayout);
		formPanel.setContent(formLayout);

		// edit behaviour
		tasksTable.addItemClickListener(new ItemClickListener() {
			@Override
			public void itemClick(ItemClickEvent event) {
				JPAContainerItem<Task> item = (JPAContainerItem<Task>) event.getItem();
				fieldGroup.setItemDataSource((Task) item.getEntity());
				formPanel.setEnabled(true);
				formPanel.setVisible(true);
				formPanel.focus();
			}
		});

		// new button
		Button newButton = new Button(ViewUtils.getMessage("commons.new"), new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				Task t = new Task();
				t.setImportance(1);
				t.setAuthor(SpringSecurityHelper.getLoggedUsername());
				fieldGroup.setItemDataSource(t);
				formPanel.setEnabled(true);
				formPanel.setVisible(true);
				formPanel.focus();
			}
		});
		tableLayout.addComponent(newButton);

		baseLayout.addComponent(tableLayout);
		baseLayout.addComponent(formPanel);
	}
}
