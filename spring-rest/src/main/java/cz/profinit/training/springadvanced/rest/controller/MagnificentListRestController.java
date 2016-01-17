package cz.profinit.training.springadvanced.rest.controller;

import cz.profinit.training.springadvanced.domain.MagnificentList;
import cz.profinit.training.springadvanced.rest.model.MagnificentListModel;
import cz.profinit.training.springadvanced.service.MagnificentListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;
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
    public List<MagnificentListModel> getLists() {
        List<MagnificentListModel> ret = new ArrayList<>();
        for (MagnificentList list : listService.getLists()) {
            ret.add(domain2model(list));
        }
        return ret;
    }

    @RequestMapping(value = "/{listId}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)  // 200
    public MagnificentListModel getList(@PathVariable Integer listId) {
        return domain2model(listService.getList(listId));
    }


    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Integer createNewList(@RequestBody MagnificentListModel newListModel) {
        newListModel.setId(null);
        MagnificentList domain = model2domain(newListModel);
        listService.saveList(domain);
        return domain.getId();
    }

    @RequestMapping(value = "/{listId}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Integer updateList(@PathVariable Integer listId, @RequestBody MagnificentListModel newListModel) {
        Assert.isTrue(listId.equals(newListModel.getId()), "List ids do not match");

        MagnificentList domain = model2domain(newListModel);
        listService.saveList(domain);
        return domain.getId();
    }

    private MagnificentList model2domain(MagnificentListModel model) {
        return new MagnificentList(model.getId(), model.getName(), model.getDescription(), "none");
    }

    private MagnificentListModel domain2model(MagnificentList list) {
        MagnificentListModel model = new MagnificentListModel();
        model.setId(list.getId());
        model.setName(list.getName());
        model.setDescription(list.getDescription());
        return model;
    }
}
