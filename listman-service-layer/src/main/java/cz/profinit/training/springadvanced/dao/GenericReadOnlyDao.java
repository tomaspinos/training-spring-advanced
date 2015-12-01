package cz.profinit.training.springadvanced.dao;


import cz.profinit.training.springadvanced.domain.AbstractDomainObject;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface GenericReadOnlyDao<T extends AbstractDomainObject<IDT>, IDT> {

    T selectById(IDT id);

    List<T> selectAll();

    List<T> selectAllPg(RowBounds rowBounds);

    long countAll();
}
