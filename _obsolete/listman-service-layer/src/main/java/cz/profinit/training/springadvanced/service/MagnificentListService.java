package cz.profinit.training.springadvanced.service;


import cz.profinit.training.springadvanced.domain.Item;
import cz.profinit.training.springadvanced.domain.MagnificentList;

import java.util.List;

public interface MagnificentListService {

    List<MagnificentList> getLists();

    MagnificentList getList(int listId);

    void saveList(MagnificentList list);

    List<Item> getListItems(MagnificentList magnificentList);

    List<Item> getListItems(int listId);

    Item getItem(int itemId);

    void saveItem(Item item);

    void deleteItem(Item item);

}
