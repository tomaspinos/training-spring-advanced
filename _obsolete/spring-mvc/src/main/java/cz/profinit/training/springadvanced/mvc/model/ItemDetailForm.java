package cz.profinit.training.springadvanced.mvc.model;

public class ItemDetailForm {

    private Integer id;
    private Integer listId;
    private String name;
    private String description;

    public Integer getId() {
        return id;
    }

    public void setId(final Integer listId) {
        this.id = listId;
    }

    public Integer getListId() {
        return listId;
    }

    public void setListId(final Integer listId) {
        this.listId = listId;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

}
