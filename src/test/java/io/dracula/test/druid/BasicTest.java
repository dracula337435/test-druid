package io.dracula.test.druid;

import io.dracula.test.druid.entity.User;
import io.dracula.test.druid.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author dk
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Main.class)
public class BasicTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void test(){
        User usr = new User();
        usr.setName("gxk");
        userRepository.save(usr);
        System.out.println(userRepository.findAll());
    }

}
