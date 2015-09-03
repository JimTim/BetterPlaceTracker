
package BetterPlaceAPI;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;

@Generated("org.jsonschema2pojo")
public class ProfilePicture {

    @Expose
    private List<Link__> links = new ArrayList<Link__>();

    /**
     * 
     * @return
     *     The links
     */
    public List<Link__> getLinks() {
        return links;
    }

    /**
     * 
     * @param links
     *     The links
     */
    public void setLinks(List<Link__> links) {
        this.links = links;
    }

}
