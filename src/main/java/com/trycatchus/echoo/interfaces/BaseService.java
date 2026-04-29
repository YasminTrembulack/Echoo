package com.trycatchus.echoo.interfaces;

import java.util.List;

public interface BaseService<REQ, REQU, RES, ID> {
    RES create(REQ payload);
    RES update(ID id, REQU payload);
    void delete(ID id);
    RES findById(ID id);
    List<RES> findAll();
}