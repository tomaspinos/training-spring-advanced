package cz.profinit.training.springadvanced.swf.service;

import cz.profinit.training.springadvanced.swf.model.ItemModel;
import cz.profinit.training.springadvanced.swf.model.MagnificentListModel;

import java.util.List;

public interface MagnificentListModelService {

    MagnificentListModel createEmptyModel();

    ItemModel createNewItem();

    MagnificentListModel getModel(int listId);

    void saveModel(MagnificentListModel model);

    List<MagnificentListModel> getLists();

}
