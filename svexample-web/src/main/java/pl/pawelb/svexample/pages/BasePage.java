package pl.pawelb.svexample.pages;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import pl.pawelb.svexample.web.ViewUtils;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.ExternalResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Link;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

/**
 * Base page class
 * 
 * @author pawelb
 * 
 */
@Component
@Scope("prototype")
public class BasePage extends Panel implements View {

    /**
	 * 
	 */
    private static final long serialVersionUID = -5455992902910665804L;

    private Label usernameLabel = new Label();
    private Label rolesLabel = new Label();
    private List<String> userRoles = new ArrayList<String>();

    VerticalLayout baseLayout = new VerticalLayout();

    /**
     * Menu map
     * 
     * @return
     */
    private Map<String, Class<?>> getMenuMap() {
        Map<String, Class<?>> menuMap = new LinkedHashMap<String, Class<?>>();
        menuMap.put(ViewUtils.getMessage("pages.title.taskList"), TaskListPage.class);
        menuMap.put(ViewUtils.getMessage("pages.title.taskEdit"), TaskEditPage.class);
        return menuMap;
    }

    /**
     * Roles init
     */
    private void initRoles() {
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof User) {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            for (GrantedAuthority grantedAuthority : user.getAuthorities()) {
                userRoles.add(grantedAuthority.getAuthority());
            }
        }
    }

    public BasePage() {
        initRoles();
        setSizeFull();
        baseLayout.setSpacing(true);
        baseLayout.setMargin(true);

        // header
        Panel headerPanel = new Panel();

        HorizontalLayout headerLayout = new HorizontalLayout();
        headerLayout.setSizeFull();
        headerLayout.setMargin(true);
        headerLayout.setSpacing(true);
        headerLayout.addStyleName("header");

        // page links
        HorizontalLayout linksLayout = new HorizontalLayout();
        linksLayout.setSpacing(true);
        // user roles
        int menuLinkPosition = 1;
        for (Map.Entry<String, Class<?>> menuItem : getMenuMap().entrySet()) {
            boolean hasRole = false;
            for (Annotation a : menuItem.getValue().getAnnotations()) {
                if (Secured.class.equals(a.annotationType())) {
                    Secured sec = (Secured) a;
                    for (String pageRole : sec.value()) {
                        if (userRoles.contains(pageRole)) {
                            hasRole = true;
                        }
                    }
                }
            }
            if (hasRole) {
                Link menuLink = ViewUtils.createPageLink(menuItem.getKey(), menuItem.getValue());
                menuLink.addStyleName("menu-item-position-" + menuLinkPosition);
                if (menuItem.getValue().equals(this.getClass())) {
                    menuLink.addStyleName("menu-item-selected");
                }
                linksLayout.addComponent(menuLink);
                menuLinkPosition++;
            }
        }
        headerLayout.addComponent(linksLayout);
        addMenuStyle(linksLayout);

        // login, roles and logout button
        HorizontalLayout usernameLayout = new HorizontalLayout();
        usernameLayout.setSpacing(true);
        usernameLayout.addComponent(new Label(ViewUtils.getMessage("basePage.login")));
        usernameLayout.addComponent(usernameLabel);

        usernameLayout.addComponent(new Label(ViewUtils.getMessage("basePage.roles")));
        usernameLayout.addComponent(rolesLabel);

        Link logoutLink = new Link(ViewUtils.getMessage("basePage.logout"), new ExternalResource("j_spring_security_logout"));
        logoutLink.addStyleName("menu-logout");
        usernameLayout.addComponent(logoutLink);

        headerLayout.addComponent(usernameLayout);
        addMenuStyle(usernameLayout);
        headerLayout.setComponentAlignment(usernameLayout, Alignment.MIDDLE_RIGHT);

        headerPanel.setContent(headerLayout);

        baseLayout.addComponent(headerPanel);
    }
    
    private void addMenuStyle(Layout root) {
        Iterator<com.vaadin.ui.Component> iter = root.iterator();
        while (iter.hasNext()) {
            com.vaadin.ui.Component c = iter.next();
            c.addStyleName("menu-item");
        }
    }

    @Override
    public void enter(ViewChangeEvent event) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        usernameLabel.setValue(user.getUsername());
        rolesLabel.setValue(StringUtils.join(userRoles, ","));

        setContent(baseLayout);
    }

}
