package config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.barbighaiya.Spring_First_Project.Desktop;

@Configuration
/*
 * @Configuration tells Spring that this class contains bean definitions.
 * Spring reads this class when the IoC container starts.*/
public class AppConfig {
	
	/*
	 * @Bean tells Spring:
	 * "The object returned by this method should be stored as a bean in the IoC Container.
	 * "Spring internally executes:
	 * Desktop obj = new Desktop();
	 * and stores that object in the container.
	 * The bean name is desktop, which is the method name.*/
	@Bean(name={"suruchi","barbighaiya","desktop"})
	/*
	 * If we will not add name attribute in @Bean Annotation
	 * then Spring will treat method name as a bean name
	 * once we explicitly specify the name attribute,
	 * Spring uses those names as the bean names/aliases 
	 * instead of automatically using the method name.
	 * We can add the method name also in name attributes*/
	public Desktop desktop()
	{
		return new Desktop();
	}
}
