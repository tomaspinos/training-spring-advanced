package cz.profinit.training.springadvanced.domain;


public class MagnificentList implements AbstractDomainObject<Integer> {
    private final String principal;
    private final String name;
    private final String description;
    private Integer id;

    public MagnificentList(final Integer id, final String name, final String description, final String principal) {
        this.id = id;
        this.principal = principal;
        this.name = name;
        this.description = description;
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
}
