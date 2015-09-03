
package BetterPlaceAPI;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class FundraisingEvents {

    @Expose
    private Integer id;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @Expose
    private String title;
    @Expose
    private String description;
    @SerializedName("tax_deductible")
    @Expose
    private Boolean taxDeductible;
    @SerializedName("donations_prohibited")
    @Expose
    private Boolean donationsProhibited;
    @SerializedName("closed_at")
    @Expose
    private Object closedAt;
    @SerializedName("donor_count")
    @Expose
    private Integer donorCount;
    @SerializedName("donated_amount_in_cents")
    @Expose
    private Integer donatedAmountInCents;
    @SerializedName("requested_amount_in_cents")
    @Expose
    private Integer requestedAmountInCents;
    @SerializedName("progress_percentage")
    @Expose
    private Integer progressPercentage;
    @Expose
    private Contact contact;
    @SerializedName("profile_picture")
    @Expose
    private ProfilePicture profilePicture;
    @Expose
    private List<Link___> links = new ArrayList<Link___>();

    /**
     * 
     * @return
     *     The id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The createdAt
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * 
     * @param createdAt
     *     The created_at
     */
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * 
     * @return
     *     The updatedAt
     */
    public String getUpdatedAt() {
        return updatedAt;
    }

    /**
     * 
     * @param updatedAt
     *     The updated_at
     */
    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * 
     * @return
     *     The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * 
     * @param title
     *     The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 
     * @return
     *     The description
     */
    public String getDescription() {
        return description;
    }

    /**
     * 
     * @param description
     *     The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 
     * @return
     *     The taxDeductible
     */
    public Boolean getTaxDeductible() {
        return taxDeductible;
    }

    /**
     * 
     * @param taxDeductible
     *     The tax_deductible
     */
    public void setTaxDeductible(Boolean taxDeductible) {
        this.taxDeductible = taxDeductible;
    }

    /**
     * 
     * @return
     *     The donationsProhibited
     */
    public Boolean getDonationsProhibited() {
        return donationsProhibited;
    }

    /**
     * 
     * @param donationsProhibited
     *     The donations_prohibited
     */
    public void setDonationsProhibited(Boolean donationsProhibited) {
        this.donationsProhibited = donationsProhibited;
    }

    /**
     * 
     * @return
     *     The closedAt
     */
    public Object getClosedAt() {
        return closedAt;
    }

    /**
     * 
     * @param closedAt
     *     The closed_at
     */
    public void setClosedAt(Object closedAt) {
        this.closedAt = closedAt;
    }

    /**
     * 
     * @return
     *     The donorCount
     */
    public Integer getDonorCount() {
        return donorCount;
    }

    /**
     * 
     * @param donorCount
     *     The donor_count
     */
    public void setDonorCount(Integer donorCount) {
        this.donorCount = donorCount;
    }

    /**
     * 
     * @return
     *     The donatedAmountInCents
     */
    public Integer getDonatedAmountInCents() {
        return donatedAmountInCents;
    }

    /**
     * 
     * @param donatedAmountInCents
     *     The donated_amount_in_cents
     */
    public void setDonatedAmountInCents(Integer donatedAmountInCents) {
        this.donatedAmountInCents = donatedAmountInCents;
    }

    /**
     * 
     * @return
     *     The requestedAmountInCents
     */
    public Integer getRequestedAmountInCents() {
        return requestedAmountInCents;
    }

    /**
     * 
     * @param requestedAmountInCents
     *     The requested_amount_in_cents
     */
    public void setRequestedAmountInCents(Integer requestedAmountInCents) {
        this.requestedAmountInCents = requestedAmountInCents;
    }

    /**
     * 
     * @return
     *     The progressPercentage
     */
    public Integer getProgressPercentage() {
        return progressPercentage;
    }

    /**
     * 
     * @param progressPercentage
     *     The progress_percentage
     */
    public void setProgressPercentage(Integer progressPercentage) {
        this.progressPercentage = progressPercentage;
    }

    /**
     * 
     * @return
     *     The contact
     */
    public Contact getContact() {
        return contact;
    }

    /**
     * 
     * @param contact
     *     The contact
     */
    public void setContact(Contact contact) {
        this.contact = contact;
    }

    /**
     * 
     * @return
     *     The profilePicture
     */
    public ProfilePicture getProfilePicture() {
        return profilePicture;
    }

    /**
     * 
     * @param profilePicture
     *     The profile_picture
     */
    public void setProfilePicture(ProfilePicture profilePicture) {
        this.profilePicture = profilePicture;
    }

    /**
     * 
     * @return
     *     The links
     */
    public List<Link___> getLinks() {
        return links;
    }

    /**
     * 
     * @param links
     *     The links
     */
    public void setLinks(List<Link___> links) {
        this.links = links;
    }

}
