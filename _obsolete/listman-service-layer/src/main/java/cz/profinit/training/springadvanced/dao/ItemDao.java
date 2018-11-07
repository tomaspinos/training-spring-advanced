package cz.profinit.training.springadvanced.dao;

import cz.profinit.training.springadvanced.domain.Item;

import java.util.List;

public interface ItemDao extends GenericDao<Item, Integer> {

    List<Item> selectListItems(int listId);

}
