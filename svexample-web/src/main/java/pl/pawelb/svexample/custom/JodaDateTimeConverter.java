package pl.pawelb.svexample.custom;

import java.util.Date;
import java.util.Locale;

import org.joda.time.LocalDate;

import com.vaadin.data.util.converter.Converter;

/**
 * Joda integration
 * 
 * @author pawelb
 *
 */
public class JodaDateTimeConverter implements Converter<Date, LocalDate> {

    /**
     * 
     */
    private static final long serialVersionUID = 7719694537096373875L;

    @Override
    public LocalDate convertToModel(Date value, Class<? extends LocalDate> targetType, Locale locale)
            throws com.vaadin.data.util.converter.Converter.ConversionException {
        if (value == null) {
            return null;
        }
        return new LocalDate(value);
    }

    @Override
    public Date convertToPresentation(LocalDate value, Class<? extends Date> targetType, Locale locale)
            throws com.vaadin.data.util.converter.Converter.ConversionException {
        if (value == null) {
            return null;
        }
        return value.toDate();
    }

    @Override
    public Class<LocalDate> getModelType() {
        return LocalDate.class;
    }

    @Override
    public Class<Date> getPresentationType() {
        return Date.class;
    }
}
