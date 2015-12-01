package cz.profinit.training.springadvanced.domain;


import java.io.Serializable;

public interface AbstractDomainObject<IDT> extends Serializable {

    public IDT getId();

    public void setId(IDT primaryKey);

}
