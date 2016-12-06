package cz.profinit.training.springadvanced.security.service;

import cz.profinit.training.springadvanced.security.model.MagnificentListModel;

import java.util.List;

public interface MagnificentListModelService {


    MagnificentListModel getModel(int listId);

    void saveModel(MagnificentListModel model);

    List<MagnificentListModel> getLists();

}
