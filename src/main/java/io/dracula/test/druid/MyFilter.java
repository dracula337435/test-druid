package io.dracula.test.druid;

import com.alibaba.druid.filter.config.ConfigFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.proxy.jdbc.DataSourceProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * druid的Filter由druid自己实例化，见FilterManager
 * druid的spring-boot-starter中，Filter的示例化借助spring，见DruidDataSourceWrapper和DruidFilterConfiguration
 * 这里利用里这一特性，让自己的Filter顶替掉ConfigFilter的位置，以使用spring基础设施
 *
 * @author dk
 */
@Component
public class MyFilter extends ConfigFilter {

    private static Logger logger = LoggerFactory.getLogger(MyFilter.class);

    @Override
    public void init(DataSourceProxy dataSourceProxy) {
        DruidDataSource dataSource = (DruidDataSource) dataSourceProxy;
        logger.info("url为"+dataSource.getUrl());
        Properties connectionProperties = dataSource.getConnectProperties();
        logger.info(connectionProperties.toString());
        String encryptedPassword = dataSource.getPassword();
        logger.info("解密前："+encryptedPassword);
        String passwordPlainText = decrypt(encryptedPassword);
        logger.info("解密后："+passwordPlainText);
        dataSource.setPassword(passwordPlainText);
    }

    /**
     *
     * @param encryptedPassword
     * @return
     */
    private String decrypt(String encryptedPassword) {
        StringBuffer sb = new StringBuffer();
        int len = encryptedPassword.length();
        for(int i=0; i<len; i++){
            sb.append((char)(encryptedPassword.charAt(i)+1));
        }
        return sb.toString();
    }


}
