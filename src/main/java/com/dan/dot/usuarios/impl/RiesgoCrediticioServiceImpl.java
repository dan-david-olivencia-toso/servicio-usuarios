package com.dan.dot.usuarios.impl;

import com.dan.dot.usuarios.service.RiesgoCrediticioService;
import org.springframework.stereotype.Service;

@Service
public class RiesgoCrediticioServiceImpl implements RiesgoCrediticioService {

    @Override
    public Boolean reporteBCRAPositivo(String cuit) {
        return true;
    }

}
