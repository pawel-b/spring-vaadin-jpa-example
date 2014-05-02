package pl.pawelb.svexample.tasks;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import pl.pawelb.svexample.pages.MainPage;
import pl.pawelb.svexample.web.ViewUtils;
import ru.xpoft.vaadin.DiscoveryNavigator;

import com.vaadin.annotations.Theme;
import com.vaadin.server.DefaultErrorHandler;
import com.vaadin.server.ErrorHandler;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 * Main UI
 * 
 * @author pawelb
 * 
 */
@Component
@Scope("prototype")
@Theme("tasks")
public class TasksUI extends UI implements ErrorHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5344259492872593871L;

	@Override
	protected void init(final VaadinRequest request) {
		VaadinSession.getCurrent().setErrorHandler(this);
		setSizeFull();

		DiscoveryNavigator navigator = new DiscoveryNavigator(this, this);
	}

	/**
	 * Exception on action
	 */
	@Override
	public void error(com.vaadin.server.ErrorEvent event) {
		Throwable cause = ExceptionUtils.getRootCause(event.getThrowable());
		if (cause instanceof AccessDeniedException) {
			AccessDeniedException exception = (AccessDeniedException) cause;

			Label label = new Label(exception.getMessage());
			label.setWidth(-1, Unit.PERCENTAGE);

			Link goToMain = ViewUtils.createPageLink("Go to main", MainPage.class);

			VerticalLayout layout = new VerticalLayout();
			layout.addComponent(label);
			layout.addComponent(goToMain);
			layout.setComponentAlignment(label, Alignment.MIDDLE_CENTER);
			layout.setComponentAlignment(goToMain, Alignment.MIDDLE_CENTER);

			VerticalLayout mainLayout = new VerticalLayout();
			mainLayout.setSizeFull();
			mainLayout.addComponent(layout);
			mainLayout.setComponentAlignment(layout, Alignment.MIDDLE_CENTER);

			setContent(mainLayout);
			Notification.show(exception.getMessage(), Notification.Type.ERROR_MESSAGE);
			return;
		}

		DefaultErrorHandler.doDefault(event);
	}
}
