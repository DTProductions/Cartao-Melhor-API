package api.cartaomelhor.extrato;

import org.jsoup.nodes.Document;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SpringBootApplication
@RestController
public class Application {

	record AllTravelsJSON(String status, List<Travel> travels){}
	record Travel(String day, String time, String sequence, String line, String vehicle, String direction, String status, String integration, String payment, String balance){}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@PostMapping("/extract")
	public AllTravelsJSON extract(@RequestBody Map<String, String> payload){
		if(payload.get("cardNum").isEmpty()){
			return new AllTravelsJSON("failure", null);
		}

		List<Travel> travels = new ArrayList<>();

		try{
			Document doc = Jsoup.connect("http://extrato.cartaomelhor.com.br:5000/")
								.userAgent("Mozilla/5.0")
								.data("card_number", payload.get("cardNum")).post();

			Elements rows = doc.body().getElementsByTag("tr");

			for(int i = 1; i < rows.size(); i++){
				Elements columns = rows.get(i).children();

				String day = columns.get(0).text();
				String time = columns.get(1).text();
				String sequence = columns.get(2).text();
				String line = columns.get(3).text();
				String vehicle = columns.get(4).text();
				String direction = columns.get(5).text();
				String status = columns.get(6).text();
				String integration = columns.get(7).text();
				String payment = columns.get(8).text();
				String balance = columns.get(9).text();

				Travel travel = new Travel(day, time, sequence, line, vehicle, direction, status, integration, payment, balance);
				travels.add(travel);
			}

			return new AllTravelsJSON("success", travels);

		} catch (IOException e){
			return new AllTravelsJSON("failure", null);
		}
	}
}
