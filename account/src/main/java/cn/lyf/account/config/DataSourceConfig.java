package cn.lyf.account.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = "cn.lyf.account.dao" , sqlSessionFactoryRef="")
public class DataSourceConfig {


    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean(name = "dataSource")
    @Primary
    public DataSource getDataSource() {
        return DataSourceBuilder.create().build();
    }
    /**
     * @methodDesc: 功能描述:(会话工厂)
     */
    @Bean(name = "sqlSessionFactory")
    @Primary
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSource") DataSource dataSource)
            throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        return bean.getObject();
    }

    /**
     * 当存在多于1个数据源的时候，必须选择一个作为主数据源（Primary DataSource），
     * 即如果数据库操作没有指明使用哪个数据源的时候，默认使用主数据源。
     * 同时，把数据源绑定到不同的JdbcTemplate上。
     * 用@Primary把其中某一个Bean标识为“主要的”，使用@Autowired注入时会首先使用被标记为@Primary的Bean。
     */
    /**
     * @methodDesc: 功能描述:(test1 事物管理)
     */
    @Bean(name = "transactionManager")
    @Primary
    public DataSourceTransactionManager transactionManager(@Qualifier("dataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    /**
     * 使用@Autowired注释进行byType注入，如果需要byName（byName就是通过id去标识）注入，
     * 增加@Qualifier注释。一般在候选Bean数目不为1时应该加@Qualifier注释。
     * 在默认情况下使用 @Autowired 注释进行自动注入时，Spring 容器中匹配的候选 Bean 数目必须有且仅有一个。
     * 当找不到一个匹配的 Bean 时，Spring 容器将抛出
     * BeanCreationException 异常，并指出必须至少拥有一个匹配的 Bean。
     * 和找不到一个类型匹配 Bean 相反的一个错误是：如果 Spring 容器中拥有多个候选 Bean，
     * Spring 容器在启动时也会抛出 BeanCreationException 异常。
     * Spring 允许我们通过 @Qualifier 注释指定注入 Bean 的名称，这样歧义就消除了，可以通过下面的方法解决异常：
     *
     * @param sqlSessionFactory
     * @return
     * @throws Exception
     */
    @Bean(name = "sqlSessionTemplate")
    @Primary
    public SqlSessionTemplate testSqlSessionTemplate(
            @Qualifier("sqlSessionFactory") SqlSessionFactory sqlSessionFactory)
            throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
