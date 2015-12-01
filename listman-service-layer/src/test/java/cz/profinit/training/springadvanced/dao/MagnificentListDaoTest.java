package cz.profinit.training.springadvanced.dao;

import cz.profinit.training.springadvanced.domain.MagnificentList;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


public class MagnificentListDaoTest extends AbstractDaoTest {
    @Autowired
    MagnificentListDao listDao;

    @Test
    public void insert() {

        MagnificentList mlist = new MagnificentList(null, "mlist", "descr", "noname");
        listDao.insert(mlist);
        Assert.assertNotNull(mlist.getId());
    }


}