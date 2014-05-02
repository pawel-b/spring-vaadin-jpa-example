package pl.pawelb.svexample.pages;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ru.xpoft.vaadin.VaadinView;

/**
 * Main page :-)
 * @author pawelb
 *
 */
@Component
@Scope("prototype")
@VaadinView(MainPage.NAME)
public class MainPage extends BasePage
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 2810874256163387388L;
	public static final String NAME = "";
    
    @PostConstruct
    public void init()
    {
    	
    }

}
