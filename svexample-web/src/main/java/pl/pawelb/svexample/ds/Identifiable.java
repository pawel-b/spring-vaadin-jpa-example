package pl.pawelb.svexample.ds;


/**
 * Common interface for identifiable entitites
 * 
 * @author pawelb
 *
 */
public interface Identifiable {

    /**
     * @return
     */
    public Long getId();

    /**
     * @param id
     */
    public void setId(Long id);
}
