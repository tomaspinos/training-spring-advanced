package cz.profinit.training.springadvanced.dao;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cz.profinit.training.springadvanced.domain.Item;

public class ItemDaoTest extends AbstractDaoTest {

    @Autowired
    ItemDao itemDao;

    @Test
    public void testSelectById() {
        final Item item = itemDao.selectById(0);
        Assert.assertEquals(Integer.valueOf(0), item.getId());
    }

    @Test
    public void testSelectByIdNegative() {
        final Item item = itemDao.selectById(100);
        Assert.assertNull(item);
    }

    @Test
    public void testInsert() {
        final Item item = new Item(null, 100, "name", "descr", "noname");
        itemDao.insert(item);
        Assert.assertNotNull(item.getId());
    }
}