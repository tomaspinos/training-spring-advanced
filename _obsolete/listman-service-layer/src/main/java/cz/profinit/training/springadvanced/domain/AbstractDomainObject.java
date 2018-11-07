package cz.profinit.training.springadvanced.domain;


import java.io.Serializable;

public interface AbstractDomainObject<IDT> extends Serializable {

    IDT getId();

    void setId(IDT primaryKey);

}
