package cz.profinit.training.springadvanced.mvc.model;

public class ListDetailForm {

    private Integer listId;
    private String name;
    private String description;

    public Integer getId() {
        return listId;
    }

    public void setId(final Integer listId) {
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
