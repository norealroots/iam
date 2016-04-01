package it.infn.mw.iam.config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.boot.autoconfigure.orm.jpa.JpaBaseConfiguration;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.AbstractJpaVendorAdapter;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class JpaConfig extends JpaBaseConfiguration {

  @Autowired
  DataSource dataSource;

  @Override
  protected AbstractJpaVendorAdapter createJpaVendorAdapter() {

    return new EclipseLinkJpaVendorAdapter();
  }

  @Override
  protected Map<String, Object> getVendorProperties() {

    Map<String, Object> map = new HashMap<String, Object>();

    map.put("eclipselink.weaving", "false");
    map.put("eclipselink.logging.level", "INFO");
    map.put("eclipselink.logging.level.sql", "FINE");
    map.put("eclipselink.cache.shared.default", "false");
    map.put("eclipselink.ddl-generation", "drop-and-create-tables");
    
    return map;

  }

  @Override
  public LocalContainerEntityManagerFactoryBean entityManagerFactory(
    EntityManagerFactoryBuilder factoryBuilder) {
    
    LocalContainerEntityManagerFactoryBean emf = factoryBuilder.dataSource(dataSource).packages("org.mitre","it.infn.mw.iam.persistence")
      .persistenceUnit("defaultPersistenceUnit")
      .properties(getVendorProperties()).build();
    
      
    return emf; 

  }
  
  @Bean(name="defaultTransactionManager")
  public PlatformTransactionManager defaultTransactionManager(){
    return new JpaTransactionManager();
  }

  @Bean
  public FlywayMigrationStrategy flywayMigrationStrategy(){
    return new FlywayMigrationStrategy() {
      
      @Override
      public void migrate(Flyway flyway) {
        
        return;
        
      }
    };
  }
}