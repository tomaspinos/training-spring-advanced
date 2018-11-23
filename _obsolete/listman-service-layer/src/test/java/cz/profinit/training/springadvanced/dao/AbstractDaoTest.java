package cz.profinit.training.springadvanced.dao;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:spring/listman-dao.xml",
        "classpath:spring/dataSource.xml"
})
@Transactional

public abstract class AbstractDaoTest {


}