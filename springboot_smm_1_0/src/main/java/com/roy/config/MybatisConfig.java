/**
 * 
 */
package com.roy.config;

import java.util.Properties;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import com.github.pagehelper.PageHelper;

/**
 * @author Roy
 *
 */
@Configuration
@EnableTransactionManagement
public class MybatisConfig implements TransactionManagementConfigurer {
	@Resource(name = "dataSource")
	DataSource dataSource;

	@Bean(name = "sqlSessionFactory")
	public SqlSessionFactory sqlSessionFactoryBean() {

		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		bean.setDataSource(dataSource);
		bean.setTypeAliasesPackage("com.roy.domain");

		// PageHelper plugin
		PageHelper pageHelper = new PageHelper();
		Properties properties = new Properties();

		properties.setProperty("reasonable", "true");
		properties.setProperty("supportMethodsArguments", "true");
		properties.setProperty("returnPageInfo", "check");
		properties.setProperty("params", "count=countSql");
		pageHelper.setProperties(properties);
		
		// Add plugin to bean.
		bean.setPlugins(new Interceptor[] { pageHelper });

		// Add XML path if need.
		ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		try {
			// set xml path.
			bean.setMapperLocations(resolver.getResources("classpath:/com/roy/mapper/*.xml"));
			return bean.getObject();
		} catch (Exception e) {
			throw new RuntimeException("Error : sqlSessionFactory initialize failed :", e);
		}
	}

	/**
	 * Uncomment for traditional DAO development.
	 * No need in mapper proxy model.
	 * 
	 * @param sqlSessionFactory
	 * @return
	 */
	@Bean(name = "sqlSessionTemplate")
	public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}

	/**
	 * Transaction Manager
	 */
	@Bean(name = "transactionManager")
	@Override
	public PlatformTransactionManager annotationDrivenTransactionManager() {
		return new DataSourceTransactionManager(dataSource);
	}
}
