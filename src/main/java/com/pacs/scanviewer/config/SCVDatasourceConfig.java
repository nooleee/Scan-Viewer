//package com.pacs.scanviewer.config;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.orm.jpa.JpaTransactionManager;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
//import org.springframework.transaction.PlatformTransactionManager;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//
//import javax.persistence.EntityManagerFactory;
//import javax.sql.DataSource;
//
//@Configuration
//@EnableJpaRepositories(
//        basePackages = "com.pacs.scanviewer.SCV",
//        entityManagerFactoryRef = "SCVEntityManager",
//        transactionManagerRef = "SCVTransactionManager"
//)
//@EnableTransactionManagement
//class SCVDatasourceConfig {
//
//    @Value("${spring.datasource.SCV.driver-class-name}")
//    private String driverClassName;
//
//    @Value("${spring.datasource.SCV.url}")
//    private String url;
//
//    @Value("${spring.datasource.SCV.username}")
//    private String username;
//
//    @Value("${spring.datasource.SCV.password}")
//    private String password;
//
//
//    @Bean(name = "SCVDatasource")
//    public DataSource dataSource() {
//        return DataSourceBuilder.create()
//                .driverClassName(driverClassName)
//                .url(url)
//                .username(username)
//                .password(password)
//                .build();
//    }
//
//    @Primary
//    @Bean(name = "SCVEntityManager")
//    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
//
//        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
//        vendorAdapter.setGenerateDdl(true);
//
//        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
//        factory.setJpaVendorAdapter(vendorAdapter);
//        factory.setPackagesToScan("com.pacs.scnaviewer.SCV");
//        factory.setDataSource(dataSource());
//        return factory;
//    }
//
//    @Primary
//    @Bean(name = "SCVTransactionManager")
//    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
//
//        JpaTransactionManager txManager = new JpaTransactionManager();
//        txManager.setEntityManagerFactory(entityManagerFactory);
//        return txManager;
//    }
//}
//
