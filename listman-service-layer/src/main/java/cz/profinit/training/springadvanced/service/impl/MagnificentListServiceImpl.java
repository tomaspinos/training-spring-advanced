package cz.profinit.training.springadvanced.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cz.profinit.training.springadvanced.dao.ItemDao;
import cz.profinit.training.springadvanced.dao.MagnificentListDao;
import cz.profinit.training.springadvanced.domain.Item;
import cz.profinit.training.springadvanced.domain.MagnificentList;
import cz.profinit.training.springadvanced.service.MagnificentListService;

public class MagnificentListServiceImpl implements MagnificentListService {

    private final MagnificentListDao magnificentListDao;
    private final ItemDao itemDao;

    public MagnificentListServiceImpl(final MagnificentListDao magnificentListDao, final ItemDao itemDao) {
        this.magnificentListDao = magnificentListDao;
        this.itemDao = itemDao;
    }

    @Override
    @Transactional(readOnly = true)
    public List<MagnificentList> getLists() {
        return magnificentListDao.selectAll();
    }

    @Override
    @Transactional(readOnly = true)
    public MagnificentList getList(final int listId) {
        return magnificentListDao.selectById(listId);
    }

    @Override
    @Transactional
    public void saveList(final MagnificentList list) {
        if (list.getId() == null) {
            magnificentListDao.insert(list);
        } else {
            magnificentListDao.update(list);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Item> getListItems(final MagnificentList magnificentList) {
        return getListItems(magnificentList.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Item> getListItems(final int listId) {
        return itemDao.selectListItems(listId);
    }

    @Override
    @Transactional(readOnly = true)
    public Item getItem(final int itemId) {
        return itemDao.selectById(itemId);
    }

    @Override
    @Transactional
    public void saveItem(final Item item) {
        if (item.getId() == null) {
            itemDao.insert(item);
        } else {
            itemDao.update(item);
        }
    }

    @Override
    public void deleteItem(final Item item) {
        itemDao.delete(item.getId());
    }
}
