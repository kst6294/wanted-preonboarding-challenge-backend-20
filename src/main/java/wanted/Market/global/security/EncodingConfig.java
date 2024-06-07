package wanted.Market.global.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class EncodingConfig {
  @Bean
  public BCryptPasswordEncoder encoder(){
    return  new BCryptPasswordEncoder();
  }
}
