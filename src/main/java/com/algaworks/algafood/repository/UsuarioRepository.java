package com.algaworks.algafood.repository;

import com.algaworks.algafood.domain.model.Usuario;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends CustomJpaRepository<Usuario, Long> {

}
