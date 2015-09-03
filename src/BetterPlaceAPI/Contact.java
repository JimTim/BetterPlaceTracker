
package BetterPlaceAPI;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;

@Generated("org.jsonschema2pojo")
public class Contact {

    @Expose
    private String name;
    @Expose
    private Picture picture;
    @Expose
    private List<Link_> links = new ArrayList<Link_>();

    /**
     * 
     * @return
     *     The name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name
     *     The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * @return
     *     The picture
     */
    public Picture getPicture() {
        return picture;
    }

    /**
     * 
     * @param picture
     *     The picture
     */
    public void setPicture(Picture picture) {
        this.picture = picture;
    }

    /**
     * 
     * @return
     *     The links
     */
    public List<Link_> getLinks() {
        return links;
    }

    /**
     * 
     * @param links
     *     The links
     */
    public void setLinks(List<Link_> links) {
        this.links = links;
    }

}
