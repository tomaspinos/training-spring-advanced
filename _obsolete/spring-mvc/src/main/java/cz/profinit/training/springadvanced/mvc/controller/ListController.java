package cz.profinit.training.springadvanced.mvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import cz.profinit.training.springadvanced.domain.Item;
import cz.profinit.training.springadvanced.domain.MagnificentList;
import cz.profinit.training.springadvanced.mvc.model.ItemDetailForm;
import cz.profinit.training.springadvanced.mvc.model.ListDetailForm;
import cz.profinit.training.springadvanced.service.MagnificentListService;

@Controller
@RequestMapping("/list")
public class ListController {

    private final MagnificentListService listService;

    @Autowired
    public ListController(final @Qualifier(value = "listService") MagnificentListService listService) {
        this.listService = listService;
    }

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

    @RequestMapping(value = "/{listId}", method = RequestMethod.GET)
    public ModelAndView getListItems(@PathVariable final Integer listId) {
        return new ModelAndView("listdetailview").
                addObject("items", listService.getListItems(listId)).
                addObject("mlist", listService.getList(listId));
    }

    @RequestMapping(value = "/{listId}/addItem", method = RequestMethod.GET)
    public ModelAndView addItem(@PathVariable final Integer listId) {
        return new ModelAndView("edititemview").
                addObject("command", new Item(null, listId, "", "", null));
    }

    @RequestMapping("/{listId}/editItem/{itemId}")
    public ModelAndView editItem(@PathVariable final Integer itemId) {
        return new ModelAndView("edititemview").
                addObject("command", listService.getItem(itemId));
    }

    @RequestMapping(value = "/{listId}/saveItem", method = RequestMethod.POST)
    public String saveItem(@PathVariable final Integer listId, @ModelAttribute final ItemDetailForm itemDetailForm) {
        listService.saveItem(new Item(itemDetailForm.getId(), itemDetailForm.getListId(), itemDetailForm.getName(), itemDetailForm.getDescription(), null));
        return "redirect:/list/" + listId;
    }
}
