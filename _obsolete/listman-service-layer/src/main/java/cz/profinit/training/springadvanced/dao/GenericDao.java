package cz.profinit.training.springadvanced.dao;


import cz.profinit.training.springadvanced.domain.AbstractDomainObject;

public interface GenericDao<T extends AbstractDomainObject<IDT>, IDT> extends GenericReadOnlyDao<T, IDT> {

    void insert(T domainObject);

    void update(T domainObject);

    void delete(IDT id);

}
