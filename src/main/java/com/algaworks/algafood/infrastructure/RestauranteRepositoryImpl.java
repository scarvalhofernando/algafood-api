package com.algaworks.algafood.infrastructure;

import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.infrastructure.spec.RestauranteSpecs;
import com.algaworks.algafood.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Predicate;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.algaworks.algafood.infrastructure.spec.RestauranteSpecs.comFreteGratis;

@Repository
public class RestauranteRepositoryImpl implements RestauranteRepositoryQueries{

    @PersistenceContext
    private EntityManager manager;

    @Autowired @Lazy
    private RestauranteRepository restauranteRepository;

    @Override
    public List<Restaurante> find(
            String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal){

        var builder = manager.getCriteriaBuilder();

        var criteria = builder.createQuery(Restaurante.class);
        var root = criteria.from(Restaurante.class);

        var predicates = new ArrayList<Predicate>();

        if (StringUtils.hasText(nome)) {
            predicates.add(builder.like(root.get("nome"), "%" + nome + "%"));
        }

        if (taxaFreteInicial != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get("taxaFrete"), taxaFreteInicial));
        }

        if (taxaFreteFinal != null) {
            predicates.add(builder.lessThanOrEqualTo(root.get("taxaFrete"), taxaFreteFinal));
        }

        criteria.where(predicates.toArray(new Predicate[0]));

        var query = manager.createQuery(criteria);
        return query.getResultList();
    }

    @Override
    public List<Restaurante> findComFreteGratis(String nome) {
        return restauranteRepository.findAll((Sort) comFreteGratis()
                .and(RestauranteSpecs.comNomeSemelhante(nome)));
    }
}
