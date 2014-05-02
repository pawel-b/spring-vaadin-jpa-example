package pl.pawelb.svexample.web;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import ru.xpoft.vaadin.VaadinView;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Link;

/**
 * Just some UI utils
 * 
 * @author pawelb
 * 
 */
public class ViewUtils {

	/**
	 * Create common page link
	 * 
	 * @param caption
	 * @param pageClass
	 * @return
	 */
	public static Link createPageLink(String caption, Class<?> pageClass) {
		Link l = new Link(caption, new ExternalResource("#!" + pageClass.getAnnotation(VaadinView.class).value()));
		return l;
	}

	/**
	 * Force refresh JPA container
	 * 
	 * @param container
	 */
	public static void refreshJpaContainer(JPAContainer container) {
		try {
			container.refresh();
		} catch (RuntimeException e) {
			if (IllegalArgumentException.class.equals(e.getCause().getClass())) {
				// bug in JPAContainer
			} else {
				throw e;
			}
		}
	}

	/**
	 * Get translation message
	 * 
	 * @param key
	 * @return
	 */
	public static String getMessage(String key) {
		ResourceBundle rb = ResourceBundle.getBundle("messages", VaadinSession.getCurrent().getLocale());
		try {
			String value = rb.getString(key);
			return value;
		} catch (MissingResourceException e) {
			return key;
		}
	}

}
