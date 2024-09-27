package org.gmarquezp.junit5.ejemplos.services;

import org.gmarquezp.junit5.ejemplos.models.SmartPhone;

import java.util.List;

public interface ISmartPhoneService {

    Integer count();
    List<SmartPhone> findAll();
    SmartPhone findOne(Long id);
    List<SmartPhone> findByWifi(Boolean wifi);
    SmartPhone save(SmartPhone smartPhone);
    Boolean delete(Long id);
    void deleteAll();
}
