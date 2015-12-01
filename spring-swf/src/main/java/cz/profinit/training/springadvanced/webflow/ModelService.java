package cz.profinit.training.springadvanced.webflow;

import cz.profinit.training.springadvanced.domain.MagnificentList;
import cz.profinit.training.springadvanced.service.MagnificentListService;
import cz.profinit.training.springadvanced.webflow.model.Item;
import cz.profinit.training.springadvanced.webflow.model.MList;


public class ModelService {

    private final MagnificentListService magnificentListService;

    public ModelService(MagnificentListService magnificentListService) {
        this.magnificentListService = magnificentListService;
    }


    public Item createItem() {
        return new Item();
    }

    public MList createList() {
        return new MList();
    }

    public Item readItem(Integer itemId) {
        cz.profinit.training.springadvanced.domain.Item i = magnificentListService.getItem(itemId);
        return domain2model(i);
    }

    public MList readMlist(Integer listId) {
        MagnificentList l = magnificentListService.getList(listId);
        return domain2model(l);
    }

    public void saveMList(MList m) {
        MagnificentList domain = model2domain(m);
        magnificentListService.saveList(domain);
    }

    public void saveItem(Item m, Integer listId) {
        cz.profinit.training.springadvanced.domain.Item domain = model2domain(m, listId);
        magnificentListService.saveItem(domain);
    }

    private cz.profinit.training.springadvanced.domain.Item model2domain(Item m, Integer listId) {
        return new cz.profinit.training.springadvanced.domain.Item(m.getId(), listId, m.getName(), m.getDescription(), null);
    }

    private MagnificentList model2domain(MList m) {
        return new MagnificentList(m.getId(), m.getName(), m.getDescription(), null);
    }

    private MList domain2model(MagnificentList l) {
        MList ret = new MList();
        ret.setName(l.getName());
        ret.setDescription(l.getDescription());
        ret.setId(l.getId());
        return ret;
    }

    private Item domain2model(cz.profinit.training.springadvanced.domain.Item i) {
        Item ret = new Item();
        ret.setDescription(i.getDescription());
        ret.setName(i.getName());
        ret.setId(i.getId());
        return ret;
    }
}
