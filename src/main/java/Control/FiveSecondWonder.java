package Control;

import BetterPlaceAPI.BetterPlaceDonator;
import BetterPlaceAPI.Donator;
import BetterPlaceAPI.Opinions;
import Tools.ISO8601;
import com.google.gson.Gson;
import javafx.collections.ObservableList;

import java.util.Date;
import java.util.List;

/**
 * Created by karl on 12.09.15.
 */
public class FiveSecondWonder {
    private Date lastDonationDate = null;

    /**
     *
     * @param betterPlaceUrl
     * @param donatorList
     * @throws Exception
     */
    public void givemefivesconds(String betterPlaceUrl,ObservableList<Donator> donatorList) throws Exception {
        String json;
        json = UrlReader.readUrl(betterPlaceUrl + "opinions.json?order=created_at:DESC&per_page=1");

        Gson gson = new Gson();
        Opinions opinionsTotalEntries = gson.fromJson(json, Opinions.class);
        int total_entries = opinionsTotalEntries.getTotalEntries();

        json = UrlReader.readUrl(betterPlaceUrl + "opinions.json?order=created_at:DESC&facets=has_donation:true&per_page=" + total_entries);
        Opinions opinions = gson.fromJson(json, Opinions.class);
        List<BetterPlaceDonator> donators = opinions.getData();
        if (lastDonationDate != null) {
            for (BetterPlaceDonator donator : donators) {
                Date date = ISO8601.toDate(donator.getUpdatedAt());
                long lastDonationTimestamp=lastDonationDate.getTime();
                if (lastDonationDate != null && date.getTime() > lastDonationTimestamp) {
                    if (donator.getDonatedAmountInCents() != null) {
                        Donator newDonator=new Donator();
                        newDonator.setId(donator.getId());
                        newDonator.setCount(donator.getDonatedAmountInCents() / 100);
                        if (donator.getAuthor() != null && donator.getAuthor().getName() != null && !donator.getAuthor().getName().equals("")) {
                            newDonator.setName(donator.getAuthor().getName());

                            if (donator.getMessage() != null && !donator.getMessage().equals("")) {
                                newDonator.setMessage(donator.getMessage());
                            }
                        } else {
                            newDonator.setName("Anonym");
                        }

                        //still exist?
                        boolean found=false;
                        for (int i=0; i< donatorList.size();i++){
                            if (donatorList.get(i).getId() == newDonator.getId()) {
                                donatorList.set(i, newDonator);
                                found=true;
                            }
                        }
                        if (!found){
                            donatorList.add(newDonator);
                        }
                    }
                }
            }
        }
        lastDonationDate = new Date();
    }
}
