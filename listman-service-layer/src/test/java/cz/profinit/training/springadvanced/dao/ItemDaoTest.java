package cz.profinit.training.springadvanced.dao;

import cz.profinit.training.springadvanced.domain.Item;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ItemDaoTest extends AbstractDaoTest {

    @Autowired
    ItemDao itemDao;

    @Test
    public void testSelectById() {
        Item item = itemDao.selectById(0);
        Assert.assertEquals(Integer.valueOf(0), item.getId());
    }

    @Test
    public void testSelectByIdNegative() {
        Item item = itemDao.selectById(100);
        Assert.assertNull(item);
    }

    @Test
    public void testInsert() {
        Item item = new Item(null, 100, "name", "descr", "noname");
        itemDao.insert(item);
        Assert.assertNotNull(item.getId());
    }
}