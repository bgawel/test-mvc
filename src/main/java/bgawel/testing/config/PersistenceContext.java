package bgawel.testing.config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories("bgawel.testing.fruit.usecases.impl")
@EnableTransactionManagement
public class PersistenceContext {

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
       LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
       entityManagerFactory.setDataSource(dataSource());
       entityManagerFactory.setPackagesToScan("bgawel.testing.fruit.usecases.impl");
       entityManagerFactory.setJpaProperties(hibernateProperties());
       entityManagerFactory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
       return entityManagerFactory;
    }

    @Bean
    public DataSource dataSource() {
       DriverManagerDataSource dataSource = new DriverManagerDataSource();  // this class is not an actual connection pool; do not use in production
       dataSource.setDriverClassName("org.h2.Driver");
       dataSource.setUrl("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1"); // this data source is also used for integration testing; do not use in production
       dataSource.setUsername("sa");
       dataSource.setPassword("");
       return dataSource;
    }

    @Bean
    public PlatformTransactionManager transactionManager(final EntityManagerFactory emf){
       JpaTransactionManager transactionManager = new JpaTransactionManager();
       transactionManager.setEntityManagerFactory(emf);
       return transactionManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
       return new PersistenceExceptionTranslationPostProcessor();
    }

    @SuppressWarnings("serial")
    Properties hibernateProperties() {
       return new Properties() {
          {
             setProperty("hibernate.hbm2ddl.auto", "create-drop");  // easy to maintain; do not use in production
             setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
             setProperty("hibernate.globally_quoted_identifiers", "true");
          }
       };
    }
}
