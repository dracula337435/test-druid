package io.dracula.test.druid;

import com.alibaba.druid.filter.FilterAdapter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.proxy.jdbc.DataSourceProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

/**
 * @author dk
 */
public class MyFilter extends FilterAdapter {

    private static Logger logger = LoggerFactory.getLogger(MyFilter.class);

    @Override
    public void init(DataSourceProxy dataSourceProxy) {
        DruidDataSource dataSource = (DruidDataSource) dataSourceProxy;
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
