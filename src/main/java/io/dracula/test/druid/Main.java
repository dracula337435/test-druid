package io.dracula.test.druid;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author dk
 */
@EnableTransactionManagement
@SpringBootApplication
public class Main {

    /**
     *
     * @param args
     */
    public static void main(String[] args){
        SpringApplication.run(Main.class, args);
    }

}
