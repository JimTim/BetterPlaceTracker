
package BetterPlaceAPI;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;

@Generated("org.jsonschema2pojo")
public class Picture {

    @Expose
    private Boolean fallback;
    @Expose
    private List<Link> links = new ArrayList<Link>();

    /**
     * 
     * @return
     *     The fallback
     */
    public Boolean getFallback() {
        return fallback;
    }

    /**
     * 
     * @param fallback
     *     The fallback
     */
    public void setFallback(Boolean fallback) {
        this.fallback = fallback;
    }

    /**
     * 
     * @return
     *     The links
     */
    public List<Link> getLinks() {
        return links;
    }

    /**
     * 
     * @param links
     *     The links
     */
    public void setLinks(List<Link> links) {
        this.links = links;
    }

}
