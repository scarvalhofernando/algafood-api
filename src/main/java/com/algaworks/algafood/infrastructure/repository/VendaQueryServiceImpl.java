package com.algaworks.algafood.infrastructure.repository;

import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.model.dto.VendaDiaria;
import com.algaworks.algafood.domain.service.VendaQueryService;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class VendaQueryServiceImpl implements VendaQueryService {

    @Override
    public List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro){
        return null;
    }
}
