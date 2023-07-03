package com.packtpub.mmj.card;

import java.util.Locale;

import com.packtpub.mmj.service.persistence.ServicePersistenceConfig;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;


@ComponentScan("application")
@EnableJpaRepositories("healthchecker")
@EntityScan("healthchecker")
//@Configuration
//@ComponentScan("com.packtpub.mmj.*")
@Import( ServicePersistenceConfig.class )
public class CardAppConfig {//implements WebMvcConfigurer {
 
   @Bean
   public LocaleResolver localeResolver() {
	   AcceptHeaderLocaleResolver localeResolver = new AcceptHeaderLocaleResolver();
       localeResolver.setDefaultLocale(Locale.US);
       return localeResolver;
   }
 
   @Bean
   public LocaleChangeInterceptor localeChangeInterceptor() {
       LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
       localeChangeInterceptor.setParamName("lang");
       return localeChangeInterceptor;
   }
 
//   @Override
//   public void addInterceptors(InterceptorRegistry registry) {
//       registry.addInterceptor(localeChangeInterceptor());
//   }
}