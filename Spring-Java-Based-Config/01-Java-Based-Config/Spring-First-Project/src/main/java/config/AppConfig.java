package config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.barbighaiya.Spring_First_Project.Desktop;

@Configuration
/*
 * @Configuration tells Spring that this class contains bean definitions.
 * Spring reads this class when the IoC container starts.*/
public class AppConfig {
	@Bean
	/*
	 * @Bean tells Spring:
	 * "The object returned by this method should be stored as a bean in the IoC Container.
	 * "Spring internally executes:
	 * Desktop obj = new Desktop();
	 * and stores that object in the container.
	 * The bean name is desktop, which is the method name.*/
	public Desktop desktop()
	{
		return new Desktop();
	}
}
