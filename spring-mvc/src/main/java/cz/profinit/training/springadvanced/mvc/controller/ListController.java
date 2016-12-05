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
    private MagnificentListService listService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getLists() {
        return new ModelAndView("listsview").
                addObject("lists", listService.getLists());
    }

    @RequestMapping(value = "/{listId}/edit", method = RequestMethod.GET)
    public ModelAndView editListDetails(@PathVariable final Integer listId) {
        return new ModelAndView("editlistview").
                addObject("command", listService.getList(listId));
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView addList() {
        return new ModelAndView("editlistview").
                addObject("command", new MagnificentList(null, "", "", ""));
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveNewListDetails(@ModelAttribute final ListDetailForm listDetailForm, final BindingResult result) {
        final MagnificentList list = new MagnificentList(listDetailForm.getId(), listDetailForm.getName(), listDetailForm.getDescription(), null);
        listService.saveList(list);
        return "redirect:/list";
    }

    // TODO /{listId}
    public ModelAndView getListItems(final Integer listId) {
        List<Item> items = listService.getListItems(listId);
        MagnificentList mlist = listService.getList(listId);
        return new ModelAndView("listdetailview");
    }

    // TODO /{listId}/addItem
    public ModelAndView addItem(final Integer listId) {
        Item command = new Item(null, listId, "", "", null);
        return new ModelAndView("edititemview");
    }

    // TODO /{listId}/editItem/{itemId}
    public ModelAndView editItem(final Integer itemId) {
        Item command = listService.getItem(itemId);
        return new ModelAndView("edititemview");
    }

    @RequestMapping(value = "/{listId}/saveItem", method = RequestMethod.POST)
    public String saveItem(@PathVariable final Integer listId, @ModelAttribute final ItemDetailForm itemDetailForm) {
        listService.saveItem(new Item(itemDetailForm.getId(), itemDetailForm.getListId(), itemDetailForm.getName(), itemDetailForm.getDescription(), null));
        return "redirect:/list/" + listId;
    }
}
