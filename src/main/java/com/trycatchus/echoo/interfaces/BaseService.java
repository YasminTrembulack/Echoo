package com.trycatchus.echoo.interfaces;

import java.util.List;

public interface BaseService<REQ, RES, ID> {

    RES create(REQ payload);

    RES update(ID id, REQ payload);

    void delete(ID id);

    RES findById(ID id);

    List<RES> findAll();
}