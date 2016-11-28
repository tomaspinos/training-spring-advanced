package cz.profinit.training.springadvanced.service.impl;

import org.easymock.EasyMock;
import org.easymock.EasyMockSupport;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cz.profinit.training.springadvanced.dao.ItemDao;
import cz.profinit.training.springadvanced.dao.MagnificentListDao;
import cz.profinit.training.springadvanced.domain.MagnificentList;
import cz.profinit.training.springadvanced.service.MagnificentListService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:spring/listman-mock-dao.xml",
        "classpath:spring/listman-services.xml"
})
public class MagnificentListServiceImplTest {

    @Autowired
    MagnificentListService magnificentListService;

    @Autowired
    MagnificentListDao listDaoMock;

    @Autowired
    ItemDao itemDaoMock;

    @Autowired
    EasyMockSupport easyMockSupport;

    @Before
    public void setup() {
        easyMockSupport.resetAll();
    }

    @Test
    public void testSaveNewList() throws Exception {

        final MagnificentList list = new MagnificentList(null, "name", "descr", "noname");

        listDaoMock.insert(list);
        easyMockSupport.replayAll();

        magnificentListService.saveList(list);

        easyMockSupport.verifyAll();

    }

    @Test
    public void testSaveList() throws Exception {

        final MagnificentList list = new MagnificentList(1, "name", "descr", "noname");

        listDaoMock.update(EasyMock.anyObject(MagnificentList.class));
        easyMockSupport.replayAll();

        magnificentListService.saveList(list);

        easyMockSupport.verifyAll();

    }


}