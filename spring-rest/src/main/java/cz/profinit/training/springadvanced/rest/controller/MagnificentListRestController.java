package cz.profinit.training.springadvanced.rest.controller;

import cz.profinit.training.springadvanced.rest.model.MagnificentList;
import cz.profinit.training.springadvanced.service.MagnificentListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/lists")
public class MagnificentListRestController {

    @Autowired
    @Qualifier(value = "listService")
    MagnificentListService listService;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)  // 200
    public List<MagnificentList> getLists() {
        List<cz.profinit.training.springadvanced.domain.MagnificentList> lists = listService.getLists();
        List<MagnificentList> ret = new ArrayList<MagnificentList>();
        for (cz.profinit.training.springadvanced.domain.MagnificentList list : lists) {
            MagnificentList model = domain2model(list);
            ret.add(model);
        }
        return ret;
    }

    @RequestMapping(value = "/{listId}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)  // 200
    public MagnificentList getList(@PathVariable Integer listId) {
        cz.profinit.training.springadvanced.domain.MagnificentList domain = listService.getList(listId);
        return domain2model(domain);
    }


    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Integer createNewList(@RequestBody MagnificentList newListModel) {
        newListModel.setId(null);
        cz.profinit.training.springadvanced.domain.MagnificentList domain = model2domain(newListModel);
        listService.saveList(domain);
        return domain.getId();
    }

    @RequestMapping(value = "/{listId}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Integer updateList(@PathVariable Integer listId, @RequestBody MagnificentList newListModel) {
        if (!listId.equals(newListModel.getId())) {
            throw new IllegalArgumentException("List ids do not match");
        }
        cz.profinit.training.springadvanced.domain.MagnificentList domain = model2domain(newListModel);
        listService.saveList(domain);
        return domain.getId();
    }

    private cz.profinit.training.springadvanced.domain.MagnificentList model2domain(MagnificentList model) {
        return new cz.profinit.training.springadvanced.domain.MagnificentList(model.getId(), model.getName(), model.getDescription(), "none");
    }

    private MagnificentList domain2model(cz.profinit.training.springadvanced.domain.MagnificentList list) {
        MagnificentList model = new MagnificentList();
        model.setId(list.getId());
        model.setName(list.getName());
        model.setDescription(list.getDescription());
        return model;
    }
}
