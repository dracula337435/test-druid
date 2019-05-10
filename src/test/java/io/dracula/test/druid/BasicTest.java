package io.dracula.test.druid;

import io.dracula.test.druid.entity.User;
import io.dracula.test.druid.repository.UserRepository;
import org.junit.Assert;
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
import java.util.List;

/**
 * @author dk
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Main.class)
public class BasicTest {

    private static Logger logger = LoggerFactory.getLogger(BasicTest.class);

    @Autowired
    private UserRepository userRepository;

    private int numberOfUsersBeforeTest = 0;

    @BeforeTransaction
    public void beforeTransaction(){
        logger.info("开始事务前");
        numberOfUsersBeforeTest = getAndPrintAllUsers();
        logger.info("user数="+numberOfUsersBeforeTest);
    }

    @Transactional
    @Test
    public void test(){
        User usr = new User();
        usr.setName("gxk");
        userRepository.save(usr);
        int numInTransaction = getAndPrintAllUsers();
        logger.info("事务内，增加一个user后，user数="+numInTransaction);
        logger.info("此时，期望user数比事务前的多1，即"+(numberOfUsersBeforeTest+1));
        Assert.assertEquals(numberOfUsersBeforeTest+1, numInTransaction);
    }

    @AfterTransaction
    public void afterTransaction(){
        logger.info("事务结束后");
        int numAfterTransaction = getAndPrintAllUsers();
        logger.info("user数="+numAfterTransaction);
        logger.info("此时，期望user数和事务前一样，即"+numberOfUsersBeforeTest);
        Assert.assertEquals(numberOfUsersBeforeTest, numAfterTransaction);
    }

    /**
     *
     * @return
     */
    private int getAndPrintAllUsers(){
        List<User> allUsers = userRepository.findAll();
        logger.info(allUsers.toString());
        return allUsers.size();
    }

}
