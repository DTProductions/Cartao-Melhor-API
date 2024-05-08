package api.cartaomelhor.scrappers;


import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class ExtractScrapper {

    private final List<Map<String, String>> travels;
    private final List<String> requestedFields;
    private final String cardNum;

    // ith column corresponds to the ith field in this array (extract's page)
    private final String[] orderedFields;

    public ExtractScrapper(List<Map<String, String>> travels, List<String> requestedFields, String cardNum){
        this.travels = travels;
        this.requestedFields = requestedFields;
        this.cardNum = cardNum;
        orderedFields = new String[]{"day", "time", "sequence", "line",
                "vehicle", "direction", "status", "integration", "payment", "balance"};
    }

    public void loadTravels() throws IOException{
        if(requestedFields == null){
            loadAllFields();
        } else{
            loadSelectedFields();
        }
    }

    private void loadAllFields() throws IOException{
        Elements rows = scrapeRows();

        for(int i = 1; i < rows.size(); i++){
            Map<String, String> information = new LinkedHashMap<>();
            Elements columns = rows.get(i).children();

            for(int j = 0; j < orderedFields.length; j++){
                information.put(orderedFields[j], columns.get(j).text());
            }

            travels.add(information);
        }
    }

    private void loadSelectedFields() throws IOException{
        Elements rows = scrapeRows();
        for(int i = 1; i < rows.size(); i++) {
            Map<String, String> information = new LinkedHashMap<>();
            Elements columns = rows.get(i).children();

            for(int j = 0; j < orderedFields.length; j++){
                loadFieldIfRequested(information, orderedFields[j], columns.get(j).text());
            }

            travels.add(information);
        }
    }

    private void loadFieldIfRequested(Map<String, String> information, String field, String value){
        if(requestedFields.contains(field)){
            information.put(field, value);
        }
    }

    private Elements scrapeRows() throws IOException {
        Document doc = Jsoup.connect("http://extrato.cartaomelhor.com.br:5000/")
                .userAgent("Mozilla/5.0")
                .data("card_number", cardNum).post();

        return doc.body().getElementsByTag("tr");
    }
}
