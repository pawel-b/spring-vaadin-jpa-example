package pl.pawelb.svexample.pages;

import java.util.Date;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import pl.pawelb.svexample.bo.TaskBo;
import pl.pawelb.svexample.ds.Task;
import pl.pawelb.svexample.web.ViewUtils;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.data.Container.Filter;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.filter.And;
import com.vaadin.data.util.filter.Compare;
import com.vaadin.data.util.filter.Like;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.InlineDateField;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;

/**
 * Page with task list
 * @author pawelb
 *
 */
public class BaseTaskListPage extends BasePage {

    @Autowired
    private TaskBo taskBo;

    TextField taskNumberSelect;
    String taskNumberFilter;
    TextField taskNameSelect;
    String taskNameFilter;
    CheckBox monthEstimatedEnable;
    LocalDate monthEstimatedFilter = new LocalDate();
    DateField monthEstimatedSelect;

    JPAContainer<Task> tasksContainer;
    Table tasksTable;

    public void init() {
        // selectors
        HorizontalLayout filterLayout = new HorizontalLayout();
        filterLayout.setSpacing(true);
        
        taskNumberSelect = new TextField(ViewUtils.getMessage("table.header.taskNumber"));
        taskNumberSelect.setImmediate(true);
        taskNumberSelect.addValueChangeListener(new ValueChangeListener() {
            @Override
            public void valueChange(ValueChangeEvent event) {
                Object id = event.getProperty().getValue();
                taskNumberFilter = null;
                if (id instanceof String) {
                    taskNumberFilter = (String) id;
                }
                updateFilters();
            }
        });
        filterLayout.addComponent(taskNumberSelect);

        taskNameSelect = new TextField(ViewUtils.getMessage("table.header.name"));
        taskNameSelect.setImmediate(true);
        taskNameSelect.addValueChangeListener(new ValueChangeListener() {
            @Override
            public void valueChange(ValueChangeEvent event) {
                Object id = event.getProperty().getValue();
                taskNameFilter = null;
                if (id instanceof String) {
                    taskNameFilter = (String) id;
                }
                updateFilters();
            }
        });
        filterLayout.addComponent(taskNameSelect);
        
        monthEstimatedEnable = new CheckBox(ViewUtils.getMessage("estimatedDate.chooseMonth.enabled"));
        monthEstimatedEnable.setValue(Boolean.TRUE);
        monthEstimatedEnable.addValueChangeListener(new ValueChangeListener() {
            @Override
            public void valueChange(ValueChangeEvent event) {
                updateFilters();
            }
        });
        filterLayout.addComponent(monthEstimatedEnable);

        monthEstimatedSelect = new InlineDateField(ViewUtils.getMessage("estimatedDate.chooseMonth"));
        monthEstimatedSelect.setResolution(Resolution.MONTH);
        monthEstimatedSelect.setValue(new Date());
        monthEstimatedSelect.setImmediate(true);
        monthEstimatedSelect.addValueChangeListener(new ValueChangeListener() {
            @Override
            public void valueChange(ValueChangeEvent event) {
                monthEstimatedFilter = new LocalDate(monthEstimatedSelect.getValue());
                updateFilters();
            }
        });
        filterLayout.addComponent(monthEstimatedSelect);

        baseLayout.addComponent(filterLayout);
    }

    protected void updateFilters() {
        tasksContainer.setApplyFiltersImmediately(false);
        tasksContainer.removeAllContainerFilters();
        if (StringUtils.hasText(taskNumberFilter)) {
            tasksContainer.addContainerFilter(new Like("taskNumber", taskNumberFilter, false));
        }
        if (StringUtils.hasText(taskNameFilter)) {
            tasksContainer.addContainerFilter(new Like("name", taskNameFilter, false));
        }
        if (monthEstimatedEnable.getValue() && monthEstimatedFilter != null) {
            LocalDate beginOfMonth = monthEstimatedFilter.withDayOfMonth(1);
            LocalDate endOfMonth = monthEstimatedFilter.plusMonths(1).withDayOfMonth(1).minusDays(1);
            Filter estimatedDateFilter = new And(new Compare.GreaterOrEqual("estimatedEndDate", beginOfMonth),
                    new Compare.LessOrEqual("estimatedEndDate", endOfMonth));
            tasksContainer.addContainerFilter(estimatedDateFilter);
        }
        tasksContainer.applyFilters();
    }
}
