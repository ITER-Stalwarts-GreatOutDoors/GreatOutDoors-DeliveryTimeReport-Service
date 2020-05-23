package com.capgemini.go;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.capgemini.go.dto.RetailerInventoryDTO;
import com.capgemini.go.repository.RetailerInventoryRepository;
import com.capgemini.go.repository.UserRepository;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableDiscoveryClient
@SpringBootApplication
@EnableSwagger2
public class GreatOutdoorDeliveryTimeReportApplication {

	@Autowired
	RetailerInventoryRepository repo;
	@Autowired
	UserRepository userRepo;
	@PostConstruct
	public void initUsers() {
	
		
		try {
			
			
			Date day1 = new SimpleDateFormat("dd/MM/yyyy").parse("11/05/2020");
			Date day2 = new SimpleDateFormat("dd/MM/yyyy").parse("14/05/2020");
			Date day3 = new SimpleDateFormat("dd/MM/yyyy").parse("20/05/2020");
			
			
			Calendar calendar1 = Calendar.getInstance();
			calendar1.setTime(day1);
			Calendar calendar2 = Calendar.getInstance();
			calendar2.setTime(day2);
			Calendar calendar3 = Calendar.getInstance();
			calendar3.setTime(day3);
			
			repo.save(new RetailerInventoryDTO("1234" ,(byte)0, "4562",
					"45", calendar1,
					calendar2,
					calendar3));
			
			repo.save(new RetailerInventoryDTO("1242" ,(byte)0, "6462",
					"46", calendar1,
					calendar2,
					calendar3));
			
			repo.save(new RetailerInventoryDTO("1254" ,(byte)0, "9562",
					"47", calendar1,
					calendar2,
					calendar3));
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		
	
		
	}
	@Bean
	@LoadBalanced
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(GreatOutdoorDeliveryTimeReportApplication.class, args);
	}
	

	
}
