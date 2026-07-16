package config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;

import com.barbighaiya.Spring_First_Project.Alien;
import com.barbighaiya.Spring_First_Project.Computer;
import com.barbighaiya.Spring_First_Project.Desktop;
import com.barbighaiya.Spring_First_Project.Laptop;

@Configuration
/*
 * Tells Spring that this class contains bean definitions.
 * Spring reads this class and creates the beans.
 */
public class AppConfig {

    /*
     * @Bean creates a Spring bean and stores it in the IoC Container.
     *
     * If no name is given, the method name becomes the bean name.
     * We can also give multiple bean names using the name attribute.
     */
    @Bean(name = {"suruchi", "barbighaiya", "desktop"})

    /*
     * By default every bean is Singleton.
     * Use @Scope("prototype") if you want a new object every time.
     */
    //@Scope("prototype")
    public Desktop desktop() {
        return new Desktop();
    }

    @Bean
    @Primary
    /*
     * If more than one bean of the same type is present,
     * @Primary tells Spring to use this bean by default.
     */
    public Laptop laptop() {
        return new Laptop();
    }

    /*
     * If multiple beans of the same type exist,
     * Spring throws an error because it doesn't know which one to inject.
     *
     * We can solve this in two ways:
     * 1. @Primary -> Makes one bean the default.
     * 2. @Qualifier("beanName") -> Tells Spring exactly which bean to use.
     */

    /*
     * Spring automatically injects the required bean into
     * the parameters of a @Bean method.
     */
    @Bean
    // public Alien alien(@Qualifier("laptop") Computer comp)
    public Alien alien(Computer comp) {

        Alien obj = new Alien();
        obj.setAge(23);

        // Not recommended because it creates the dependency manually.
        // obj.setComp(desktop());

        // Recommended: Let Spring inject the dependency.
        obj.setComp(comp);

        return obj;
    }
}
