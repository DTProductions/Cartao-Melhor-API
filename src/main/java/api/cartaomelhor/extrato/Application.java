package api.cartaomelhor.extrato;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import api.cartaomelhor.scrappers.ExtractScrapper;

@SpringBootApplication
@RestController
public class Application {

	record ExtractRetJSON(String status, List<Map<String, String>> travels){}
	record ExtractPayload(String cardNum, List<String> fields){}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@PostMapping("/extract")
	public ExtractRetJSON extract(@RequestBody ExtractPayload payload){
		if(payload.cardNum == null){
			return new ExtractRetJSON("failure", null);
		}

		if(payload.cardNum.isEmpty()){
			return new ExtractRetJSON("failure", null);
		}

		List<Map<String, String>> travels = new ArrayList<>();
		ExtractScrapper scrapper = new ExtractScrapper(travels, payload.fields, payload.cardNum);

		try{
			scrapper.loadTravels();
			return new ExtractRetJSON("success", travels);
		} catch (IOException e){
			return new ExtractRetJSON("failure", travels);
		}
	}
}
