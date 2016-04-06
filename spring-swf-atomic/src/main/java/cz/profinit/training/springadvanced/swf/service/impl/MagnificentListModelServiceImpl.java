package cz.profinit.training.springadvanced.swf.service.impl;

import cz.profinit.training.springadvanced.domain.Item;
import cz.profinit.training.springadvanced.domain.MagnificentList;
import cz.profinit.training.springadvanced.service.MagnificentListService;
import cz.profinit.training.springadvanced.swf.model.ItemModel;
import cz.profinit.training.springadvanced.swf.model.MagnificentListModel;
import cz.profinit.training.springadvanced.swf.service.MagnificentListModelService;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MagnificentListModelServiceImpl implements MagnificentListModelService {

    private final MagnificentListService magnificentListService;

    public MagnificentListModelServiceImpl(MagnificentListService magnificentListService) {
        this.magnificentListService = magnificentListService;
    }

    @Override
    public List<MagnificentListModel> getLists() {
        List<MagnificentList> lists = magnificentListService.getLists();
        List<MagnificentListModel> ret = new ArrayList<>(lists.size());
        for (MagnificentList ml : lists) {
            ret.add(domainToModel(ml, Collections.<Item>emptyList()));
        }
        return ret;
    }

    @Override
    public MagnificentListModel createEmptyModel() {
        return new MagnificentListModel();
    }

    @Override
    public ItemModel createNewItem() {
        return new ItemModel();
    }

    @Override
    @Transactional(readOnly = true)
    public MagnificentListModel getModel(int listId) {
        return domainToModel(magnificentListService.getList(listId), magnificentListService.getListItems(listId));
    }

    @Override
    @Transactional
    public void saveModel(MagnificentListModel model) {
        Map<Integer, Item> dbItemsMap = getDbItemsMap(model);

        MagnificentList domain = modelToDomain(model);

        magnificentListService.saveList(domain);
        model.setId(domain.getId());

        for (ItemModel m : model.getItems()) {
            dbItemsMap.remove(m.getId());
            magnificentListService.saveItem(itemToDomain(model.getId(), m));
        }

        for (Item removedItem : dbItemsMap.values()) {
            magnificentListService.deleteItem(removedItem);
        }
    }

    private Map<Integer, Item> getDbItemsMap(MagnificentListModel model) {
        Map<Integer, Item> dbItemsMap = new HashMap<>();
        if (model.getId() != null) {
            for (Item i : magnificentListService.getListItems(model.getId())) {
                dbItemsMap.put(i.getId(), i);
            }
        }
        return dbItemsMap;
    }

    private MagnificentList modelToDomain(MagnificentListModel m) {
        return new MagnificentList(m.getId(), m.getName(), m.getDescription(), m.getPrincipal());
    }

    private MagnificentListModel domainToModel(MagnificentList m, List<Item> items) {
        MagnificentListModel ret = new MagnificentListModel(m.getId(), m.getDescription(), m.getName(), m.getPrincipal());
        for (Item item : items) {
            ret.addItem(domainToItem(item));
        }
        return ret;
    }

    private Item itemToDomain(int listId, ItemModel m) {
        return new Item(m.getId(), listId, m.getName(), m.getDescription(), m.getPrincipal());
    }

    private ItemModel domainToItem(Item m) {
        return new ItemModel(m.getId(), m.getName(), m.getDescription(), m.getPrincipal());
    }
}
