package cz.profinit.training.springadvanced.swf.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import cz.profinit.training.springadvanced.domain.Item;
import cz.profinit.training.springadvanced.domain.MagnificentList;
import cz.profinit.training.springadvanced.service.MagnificentListService;
import cz.profinit.training.springadvanced.swf.model.ItemModel;
import cz.profinit.training.springadvanced.swf.model.MagnificentListModel;
import cz.profinit.training.springadvanced.swf.service.MagnificentListModelService;

public class MagnificentListModelServiceImpl implements MagnificentListModelService {

    private final MagnificentListService magnificentListService;

    public MagnificentListModelServiceImpl(final MagnificentListService magnificentListService) {
        this.magnificentListService = magnificentListService;
    }

    @Override
    public List<MagnificentListModel> getLists() {
        final List<MagnificentList> lists = magnificentListService.getLists();
        final List<MagnificentListModel> ret = new ArrayList<>(lists.size());
        for (final MagnificentList ml : lists) {
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
    public MagnificentListModel getModel(final int listId) {
        return domainToModel(magnificentListService.getList(listId), magnificentListService.getListItems(listId));
    }

    @Override
    @Transactional
    public void saveModel(final MagnificentListModel model) {
        final Map<Integer, Item> dbItemsMap = getDbItemsMap(model);

        final MagnificentList domain = modelToDomain(model);

        magnificentListService.saveList(domain);
        model.setId(domain.getId());

        for (final ItemModel m : model.getItems()) {
            dbItemsMap.remove(m.getId());
            magnificentListService.saveItem(itemToDomain(model.getId(), m));
        }

        for (final Item removedItem : dbItemsMap.values()) {
            magnificentListService.deleteItem(removedItem);
        }
    }

    private Map<Integer, Item> getDbItemsMap(final MagnificentListModel model) {
        final Map<Integer, Item> dbItemsMap = new HashMap<>();
        if (model.getId() != null) {
            for (final Item i : magnificentListService.getListItems(model.getId())) {
                dbItemsMap.put(i.getId(), i);
            }
        }
        return dbItemsMap;
    }

    private MagnificentList modelToDomain(final MagnificentListModel m) {
        return new MagnificentList(m.getId(), m.getName(), m.getDescription(), m.getPrincipal());
    }

    private MagnificentListModel domainToModel(final MagnificentList m, final List<Item> items) {
        final MagnificentListModel ret = new MagnificentListModel(m.getId(), m.getDescription(), m.getName(), m.getPrincipal());
        for (final Item item : items) {
            ret.addItem(domainToItem(item));
        }
        return ret;
    }

    private Item itemToDomain(final int listId, final ItemModel m) {
        return new Item(m.getId(), listId, m.getName(), m.getDescription(), m.getPrincipal());
    }

    private ItemModel domainToItem(final Item m) {
        return new ItemModel(m.getId(), m.getName(), m.getDescription(), m.getPrincipal());
    }
}
