package org.socialnet.likesservice.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ProjectBeans {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
