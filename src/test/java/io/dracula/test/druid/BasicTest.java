package io.dracula.test.druid;

import io.dracula.test.druid.entity.User;
import io.dracula.test.druid.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.context.transaction.BeforeTransaction;

import javax.transaction.Transactional;

/**
 * @author dk
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Main.class)
public class BasicTest {

    private static Logger logger = LoggerFactory.getLogger(BasicTest.class);

    @Autowired
    private UserRepository userRepository;

    @BeforeTransaction
    public void beforeTransaction(){
        logger.info(userRepository.findAll().toString());
    }

    @Transactional
    @Test
    public void test(){
        User usr = new User();
        usr.setName("gxk");
        userRepository.save(usr);
        logger.info(userRepository.findAll().toString());
    }

    @AfterTransaction
    public void afterTransaction(){
        logger.info(userRepository.findAll().toString());
    }

}
