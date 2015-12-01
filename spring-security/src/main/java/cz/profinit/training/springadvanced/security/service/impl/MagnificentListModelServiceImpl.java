package cz.profinit.training.springadvanced.security.service.impl;

import cz.profinit.training.springadvanced.domain.Item;
import cz.profinit.training.springadvanced.domain.MagnificentList;
import cz.profinit.training.springadvanced.security.model.ItemModel;
import cz.profinit.training.springadvanced.security.model.MagnificentListModel;
import cz.profinit.training.springadvanced.security.service.MagnificentListModelService;
import cz.profinit.training.springadvanced.service.MagnificentListService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @Transactional(readOnly = true)
    @Secured(value = {"ROLE_USER", "ROLE_ADMIN"})
    public List<MagnificentListModel> getLists() {
        List<MagnificentList> lists = magnificentListService.getLists();
        List<MagnificentListModel> ret = new ArrayList<MagnificentListModel>(lists.size());
        for (MagnificentList ml : lists) {
            ret.add(domainToModel(ml, Collections.<Item>emptyList()));
        }
        return ret;
    }

    @Override
    @Transactional(readOnly = true)
    @Secured(value = {"ROLE_USER", "ROLE_ADMIN"})
    public MagnificentListModel getModel(int listId) {
        MagnificentList domain = magnificentListService.getList(listId);
        List<Item> domainItems = magnificentListService.getListItems(listId);
        MagnificentListModel magnificentListModel = domainToModel(domain, domainItems);
        return magnificentListModel;
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN') or principal.username.equals(#model.principal)")
    public void saveModel(MagnificentListModel model) {

        Map<Integer, Item> dbItemsMap = getDbItemsMap(model);

        MagnificentList domain = modelToDomain(model);

        magnificentListService.saveList(domain);

        for (ItemModel m : model.getItems()) {
            dbItemsMap.remove(m.getId());
            magnificentListService.saveItem(itemToDomain(model.getId(), m));
        }


        for (Item removedItem : dbItemsMap.values()) {
            magnificentListService.deleteItem(removedItem);
        }


    }

    private Map<Integer, Item> getDbItemsMap(MagnificentListModel model) {
        Map<Integer, Item> dbItemsMap = new HashMap<Integer, Item>();
        if (model.getId() != null) {
            for (Item i : magnificentListService.getListItems(model.getId())) {
                dbItemsMap.put(i.getId(), i);
            }
        }
        return dbItemsMap;
    }


    private MagnificentList modelToDomain(MagnificentListModel m) {
        MagnificentList ret = new MagnificentList(m.getId(), m.getName(), m.getDescription(), m.getPrincipal());
        return ret;
    }

    private MagnificentListModel domainToModel(MagnificentList m, List<Item> items) {
        MagnificentListModel ret = new MagnificentListModel(m.getPrincipal());
        ret.setId(m.getId());
        ret.setDescription(m.getDescription());
        ret.setName(m.getName());

        for (Item item : items) {
            ret.addItem(domainToItem(item));
        }
        return ret;
    }

    private Item itemToDomain(int listId, ItemModel m) {
        Item ret = new Item(m.getId(), listId, m.getName(), m.getDescription(), m.getPrincipal());
        return ret;
    }

    private ItemModel domainToItem(Item m) {
        ItemModel ret = new ItemModel();
        ret.setId(m.getId());
        ret.setName(m.getName());
        ret.setDescription(m.getDescription());
        ret.setPrincipal(m.getPrincipal());
        return ret;
    }


}
