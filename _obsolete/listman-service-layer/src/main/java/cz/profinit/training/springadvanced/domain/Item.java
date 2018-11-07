package cz.profinit.training.springadvanced.domain;


public class Item implements AbstractDomainObject<Integer> {

    final String name;
    final String description;
    final String principal;
    final Integer listId;
    Integer id;


    public Item(final Integer id, final Integer listId, final String name, final String description, final String principal) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.principal = principal;
        this.listId = listId;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(final Integer primaryKey) {
        id = primaryKey;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getPrincipal() {
        return principal;
    }

    public Integer getListId() {
        return listId;
    }
}
