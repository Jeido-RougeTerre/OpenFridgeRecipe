package com.jeido.openfridgerecipe.service;

import java.util.List;
import java.util.UUID;

public interface BaseService<TReceive,TSend> {
    TSend create (TReceive received);
    TSend update (UUID id, TReceive received);
    boolean delete (UUID id);
//    TSend findById (UUID id);
    List<TSend> getAll ();
}
