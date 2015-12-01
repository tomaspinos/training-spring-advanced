package cz.profinit.training.springadvanced.mvc.controller;

import cz.profinit.training.springadvanced.domain.Item;
import cz.profinit.training.springadvanced.domain.MagnificentList;
import cz.profinit.training.springadvanced.mvc.model.ItemDetailForm;
import cz.profinit.training.springadvanced.mvc.model.ListDetailForm;
import cz.profinit.training.springadvanced.service.MagnificentListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/list")
public class ListController {

    @Autowired
    @Qualifier(value = "listService")
    MagnificentListService listService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getLists() {
        ModelAndView ret = new ModelAndView("listsview");
        List<MagnificentList> lists = listService.getLists();
        ret.addObject("lists", lists);
        return ret;
    }

    @RequestMapping(value = "/{listId}/edit", method = RequestMethod.GET)
    public ModelAndView editListDetails(@PathVariable Integer listId) {
        ModelAndView ret = new ModelAndView("editlistview");
        MagnificentList magnificentList = listService.getList(listId);
        ret.addObject("command", magnificentList);
        return ret;
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView addList() {
        ModelAndView ret = new ModelAndView("editlistview");
        MagnificentList magnificentList = new MagnificentList(null, "", "", "");
        ret.addObject("command", magnificentList);
        return ret;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveNewListDetails(@ModelAttribute ListDetailForm listDetailForm, BindingResult result) {

        MagnificentList m = new MagnificentList(listDetailForm.getId(), listDetailForm.getName(), listDetailForm.getDescription(), null);
        listService.saveList(m);
        return "redirect:/list";
    }


    @RequestMapping(value = "/{listId}", method = RequestMethod.GET)
    public ModelAndView getListItems(@PathVariable Integer listId) {
        ModelAndView ret = new ModelAndView("listdetailview");
        List<Item> currentItems = listService.getListItems(listId);
        ret.addObject("items", currentItems);

        MagnificentList magnificentList = listService.getList(listId);
        ret.addObject("mlist", magnificentList);
        return ret;
    }


    @RequestMapping(value = "/{listId}/addItem", method = RequestMethod.GET)
    public ModelAndView addItem(@PathVariable Integer listId) {
        ModelAndView ret = new ModelAndView("edititemview");
        Item item = new Item(null, listId, "", "", null);
        ret.addObject("command", item);
        return ret;
    }


    @RequestMapping("/{listId}/editItem/{itemId}")
    public ModelAndView editItem(@PathVariable Integer itemId) {
        ModelAndView ret = new ModelAndView("edititemview");
        Item item = listService.getItem(itemId);
        ret.addObject("command", item);

        return ret;
    }

    @RequestMapping(value = "/{listId}/saveItem", method = RequestMethod.POST)
    public String saveItem(@PathVariable Integer listId, @ModelAttribute ItemDetailForm itemDetailForm, BindingResult result) {

        Item i = new Item(itemDetailForm.getId(), itemDetailForm.getListId(), itemDetailForm.getName(), itemDetailForm.getDescription(), null);
        listService.saveItem(i);
        return "redirect:/list/" + listId;
    }


}
