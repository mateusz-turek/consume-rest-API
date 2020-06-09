package mat.consumerestAPI;

import mat.consumerestAPI.Model.User;
import mat.consumerestAPI.Service.DataManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

@SpringBootApplication
public class ConsumeRestApiApplication {

	private static DataManager dataManager;
	private static int variable;

	@Autowired
	public ConsumeRestApiApplication(DataManager dataManager) {
		ConsumeRestApiApplication.dataManager = dataManager;
	}

	public static void main(String[] args) throws IOException {
		SpringApplication.run(ConsumeRestApiApplication.class, args);
		Scanner scanner = new Scanner(System.in);

			try {
				do {
				variable = scanner.nextInt();
				if(variable!=0) {
					System.out.println(dataManager.getUser(DataManager.getUrl(), variable).toString());
				}else break;
				}while (variable!=0);
			}catch (FileNotFoundException e){
				System.out.println("user not found");
			}

		System.out.println("delete method");
			try {
				variable = scanner.nextInt();
				dataManager.deleteUser(variable);
			}
			catch (Exception e){
				e.printStackTrace();
			}
			dataManager.postUser(new User());
	}

}
