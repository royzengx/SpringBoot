/**
 * 
 */
package com.roy.config;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.ApplicationContextException;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.StringUtils;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;

/**
 * Configuration class for database related.<BR>
 * Connection pool(Druid) setting.<BR>
 * Monitor plugin.<BR>
 * sessionFactory, Transaction etc.
 * 
 * @author Roy
 *
 */
@Configuration
@EnableTransactionManagement
public class DatabaseConfiguration implements EnvironmentAware {

    private Environment environment;
    private RelaxedPropertyResolver datasourcePropertyResolver;

    //read properties from application.yml
    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
        this.datasourcePropertyResolver = new RelaxedPropertyResolver(environment, "spring.datasource.");
    }

    //datasource
    @Bean(initMethod = "init", destroyMethod = "close")
    public DataSource dataSource() throws SQLException {
        if (StringUtils.isEmpty(datasourcePropertyResolver.getProperty("url"))) {
            System.out.println("Please check your setting, current profiles are:"+
            Arrays.toString(environment.getActiveProfiles()));
            throw new ApplicationContextException("Error in database connection pool configration.");
        }
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(datasourcePropertyResolver.getProperty("url"));
        dataSource.setUsername(datasourcePropertyResolver.getProperty("username"));
        dataSource.setPassword(datasourcePropertyResolver.getProperty("password"));
        //Max active connections.
        dataSource.setMaxActive(20);
        //initial connection size.
        dataSource.setInitialSize(1);
        //Minimal active connections.
        dataSource.setMinIdle(1);
        //Time out.
        dataSource.setMaxWait(60000);
        //interval monitor for on bench connection.
        dataSource.setTimeBetweenEvictionRunsMillis(60000);
        //Minimal keep alive time
        dataSource.setMinEvictableIdleTimeMillis(300000);
        //sql validation
        dataSource.setValidationQuery("select 'x'");
        dataSource.setTestWhileIdle(true);
        dataSource.setTestOnBorrow(false);
        dataSource.setTestOnReturn(false);
        //Turn on PSCache and set size.
        dataSource.setPoolPreparedStatements(true);
        dataSource.setMaxOpenPreparedStatements(20);
        //SQL monitor filter
        dataSource.setFilters("stat,wall,log4j");
        try {
            dataSource.init();
        } catch (SQLException e) {
            throw new RuntimeException("druid datasource initialize failed.");
        }
        return dataSource;
    }
    
    /**
     * druid monitor
     * @return
     */
    @Bean
    public ServletRegistrationBean druidServlet() {
        ServletRegistrationBean reg = new ServletRegistrationBean();
        reg.setServlet(new StatViewServlet());
        reg.addUrlMappings("/druid/*");
        reg.addInitParameter("allow", "127.0.0.1");
        reg.addInitParameter("deny","");
        //reg.addInitParameter("loginUsername", "roy");
        //reg.addInitParameter("loginPassword", "111111");
        return reg;
    }

    /**
     * druid monitor filter
     * @return
     */
    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new WebStatFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        return filterRegistrationBean;
    }

    //sessionFactory
    @Bean
    public LocalSessionFactoryBean sessionFactory() throws SQLException{
        LocalSessionFactoryBean localSessionFactoryBean = new LocalSessionFactoryBean();
        localSessionFactoryBean.setDataSource(this.dataSource());
        Properties properties1 = new Properties();
        properties1.setProperty("hibernate.dialect","org.hibernate.dialect.MySQL5Dialect");
        properties1.setProperty("hibernate.show_sql","true");
        localSessionFactoryBean.setHibernateProperties(properties1);
        localSessionFactoryBean.setPackagesToScan("*");
        return localSessionFactoryBean;
    }

    //turn on txManager transaction.
    @Bean
    public HibernateTransactionManager txManager() throws SQLException {
        HibernateTransactionManager hibernateTransactionManager = new HibernateTransactionManager();
        hibernateTransactionManager.setSessionFactory(sessionFactory().getObject());
        return hibernateTransactionManager;
    }
}