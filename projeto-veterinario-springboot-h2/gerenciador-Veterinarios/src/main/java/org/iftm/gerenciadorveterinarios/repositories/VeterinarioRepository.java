package org.iftm.gerenciadorveterinarios.repositories;

import java.math.BigDecimal;
import java.util.List;

import org.iftm.gerenciadorveterinarios.entities.Veterinario;
import org.springframework.data.jpa.repository.JpaRepository;


public interface VeterinarioRepository extends JpaRepository<Veterinario, Integer> {


   public List<Veterinario> findByNome(String nomeEsperado);

   public List<Veterinario> findByNomeIgnoreCase(String nomeEsperado);

   public List<Veterinario> findByNomeContainsIgnoreCase(String nomeExistente);

   public List<Veterinario> findByNomeContains(String nome);

   public List<Veterinario> findBySalarioGreaterThan(BigDecimal valueOf);

   public List<Veterinario> findBySalarioBetween(BigDecimal valueOf, BigDecimal valueOf2);

 
}
