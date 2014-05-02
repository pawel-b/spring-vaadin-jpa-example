package pl.pawelb.svexample.custom;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import pl.pawelb.svexample.web.ViewUtils;

import com.vaadin.data.Container;
import com.vaadin.data.Property;
import com.vaadin.ui.Table;

/**
 * Custom table for Vaadin
 * 
 * @author pawelb
 * 
 */
public class CustomTable extends Table {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8017168602054647342L;

	public static final String HEADER_MESSAGE_PREFIX = "table.header.";
	public static final String TABLE_BOOLEAN_MESSAGE_PREFIX = "boolean.";

	public CustomTable(String caption, Container dataSource) {
		super(ViewUtils.getMessage(caption), dataSource);
	}

	/* (non-Javadoc)
	 * @see com.vaadin.ui.Table#formatPropertyValue(java.lang.Object, java.lang.Object, com.vaadin.data.Property)
	 */
	@Override
	protected String formatPropertyValue(Object rowId, Object colId, Property<?> property) {
		Object v = property.getValue();
		if (v instanceof LocalDate) {
			LocalDate dateValue = (LocalDate) v;
			DateTimeFormatter df = DateTimeFormat.forPattern("yyyy-MM-dd");
			return df.print(dateValue);
		}
		if (v instanceof Boolean) {
			Boolean booleanValue = (Boolean) v;
			return ViewUtils.getMessage(TABLE_BOOLEAN_MESSAGE_PREFIX + booleanValue.toString().toLowerCase());
		}
		return super.formatPropertyValue(rowId, colId, property);
	}

	/* (non-Javadoc)
	 * @see com.vaadin.ui.Table#setVisibleColumns(java.lang.Object[])
	 */
	@Override
	public void setVisibleColumns(Object... visibleColumns) {
		super.setVisibleColumns(visibleColumns);
		if (visibleColumns.length > 0) {
			String[] translated = new String[visibleColumns.length];
			for (int i = 0; i < visibleColumns.length; i++) {
				translated[i] = ViewUtils.getMessage(HEADER_MESSAGE_PREFIX + (String) visibleColumns[i]);
			}
			super.setColumnHeaders(translated);
		}
	}

}
