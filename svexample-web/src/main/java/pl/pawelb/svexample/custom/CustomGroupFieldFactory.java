package pl.pawelb.svexample.custom;

import org.joda.time.LocalDate;

import com.vaadin.data.fieldgroup.DefaultFieldGroupFieldFactory;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.ui.AbstractTextField;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Field;

/**
 * Custom fields for Vaadin
 * 
 * @author pawelb
 *
 */
public class CustomGroupFieldFactory extends DefaultFieldGroupFieldFactory {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6917816926883548799L;
	
	private IndexedContainer ic;
	
	public CustomGroupFieldFactory(IndexedContainer ic) {
        super();
        this.ic = ic;
    }

    public CustomGroupFieldFactory() {
        super();
    }

    /* (non-Javadoc)
     * @see com.vaadin.data.fieldgroup.DefaultFieldGroupFieldFactory#createField(java.lang.Class, java.lang.Class)
     */
    @Override
	public <T extends Field> T createField(Class<?> type, Class<T> fieldType) {
        if (LocalDate.class.isAssignableFrom(type)) {
            return createLocalDateField(type, fieldType);
        }
		return super.createField(type, fieldType);
	}

	/**
	 * Date field integration
	 * 
	 * @param type
	 * @param fieldType
	 * @return
	 */
	private <T extends Field> T createLocalDateField(Class<?> type, Class<T> fieldType) {
		DateField field = new DateField();
		field.setConverter(new JodaDateTimeConverter());
		field.setImmediate(true);
		return (T) field;
	}

	/* (non-Javadoc)
	 * @see com.vaadin.data.fieldgroup.DefaultFieldGroupFieldFactory#createAbstractTextField(java.lang.Class)
	 */
	@Override
	protected <T extends AbstractTextField> T createAbstractTextField(Class<T> fieldType) {
		T field = super.createAbstractTextField(fieldType);
		field.setNullRepresentation("");
		field.setWidth("300px");
		return field;
	}

}