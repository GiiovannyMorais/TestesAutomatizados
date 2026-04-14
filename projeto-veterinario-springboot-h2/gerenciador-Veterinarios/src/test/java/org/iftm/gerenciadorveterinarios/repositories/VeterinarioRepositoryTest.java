package org.iftm.gerenciadorveterinarios.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.util.List;

import org.iftm.gerenciadorveterinarios.entities.Veterinario;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


@DataJpaTest
public class VeterinarioRepositoryTest {


    @Autowired
    private VeterinarioRepository repository;

    @Test
    public void testarBuscarVeterinarioComIdExistenteRetornaCorreto(){
        //Arrenge
        int idExistente = 1;
        String nomeEsperado = "Conceição Evaristo";
        String emailEsperado = "conceicao@gmail.com";

       //act 
       Veterinario veterinarioEncontrado = repository.getById(idExistente);

       //assert
       assertNotNull(veterinarioEncontrado);
       assertEquals(nomeEsperado, veterinarioEncontrado.getNome());
       assertEquals(emailEsperado, veterinarioEncontrado.getEmail());

    }

    @Test
    public void testarBuscarVeterinarioExistentePeloNomeMinusculo(){

        String nomeEsperado = "carlos mendes";
    //  int idEsperado = 1;

        int quantidadeEsperada = 1;

        List<Veterinario> veterinarioExistentes = repository.findByNome(nomeEsperado);
        int quantidadeExistente = veterinarioExistentes.size();

       assertNotNull(veterinarioExistentes);
       assertEquals(quantidadeEsperada, quantidadeExistente);
    //   assertEquals(idEsperado, veterinarioExistentes.get(0).getId());

    }

    @Test
    public void testarBuscarVeterinarioExistentePeloNomeMaiusculo(){

        String nomeEsperado = "CARLOS MENDES";
        int quantidadeEsperada = 1;

        List<Veterinario> veterinarioExistentes = repository.findByNome(nomeEsperado);
        int quantidadeExistente = veterinarioExistentes.size();

       assertNotNull(veterinarioExistentes);
       assertEquals(quantidadeEsperada, quantidadeExistente);
 
    }

    @Test
    public void testarBuscarVeterinarioNãoExistenteComCaseSensitive(){

        String nomeEsperado = "caio basilio";
        int quantidadeEsperada = 1;

        List<Veterinario> veterinarioExistentes = repository.findByNome(nomeEsperado);
        int quantidadeExistente = veterinarioExistentes.size();

       assertNotNull(veterinarioExistentes);
       assertEquals(quantidadeEsperada, quantidadeExistente);

    }

    @Test
    public void testarBuscarVeterinarioExistenteComCaseSensitive(){
    String nomeEsperado = "Juliana Alves" ;
    int quantidadeEsperada = 1;

    List<Veterinario> veterinarioExistentes = repository.findByNomeIgnoreCase(nomeEsperado);
        int quantidadeExistente = veterinarioExistentes.size();

       assertNotNull(veterinarioExistentes);
       assertEquals(quantidadeEsperada, quantidadeExistente);

    
    }

    @Test
    public void testarBuscarNomePelaSilabaTesteDeveFalhar(){
        String nomeExistente = "co";

        String nomeEsperado = "Conceição Evaristo";
        String nomeEsperado2 = "Marcos Vinicius";
        int quantidadeEsperada = 2;



        List<Veterinario> veterinarioExistentes = repository.findByNomeContainsIgnoreCase(nomeExistente);
        int quantidadeExistente = veterinarioExistentes.size();

       assertNotNull(veterinarioExistentes);
       assertEquals(quantidadeEsperada, quantidadeExistente);
       assertEquals(nomeEsperado2, veterinarioExistentes);
       assertEquals(nomeEsperado, veterinarioExistentes);

    }

    @Test 
    public void testarBuscarPelaSilabaMaria(){
        String nomeEsperado = "Maria";
        int quantidadeEsperada = 1;

        List<Veterinario> veterinarioExistente = repository.findByNomeContainsIgnoreCase(nomeEsperado);
        int quantidadeExistente = veterinarioExistente.size();
    
       assertNotNull(veterinarioExistente);
       assertEquals(quantidadeEsperada, quantidadeExistente);

    }

    @Test
    public void testarBuscarParametroDeBuscaComStringVazia(){
        String nomeEsperado = " ";

        int quantidadeEsperada = 12;

        List<Veterinario> veterinarioExistente = repository.findByNomeContainsIgnoreCase(nomeEsperado);
        int quantidadeExistente = veterinarioExistente.size();
    
       assertNotNull(veterinarioExistente);
       assertEquals(quantidadeEsperada, quantidadeExistente);

    }

    @Test
    public void testarBuscarSalarioSuperiorValorInformado(){

        Double SalarioEsperado = 3000.0;
        int quantidadeEsperada = 10;

        List<Veterinario> veterinarioExistente = repository.findBySalarioGreaterThan(BigDecimal.valueOf(SalarioEsperado));
        int quantidadeExistente = veterinarioExistente.size();
    
       assertNotNull(veterinarioExistente);
       assertEquals(quantidadeEsperada, quantidadeExistente);

    }

    @Test
    public void testarBuscarSalarioInferiorValorInformado(){

        Double SalarioEsperado = 3000.0;
        int quantidadeEsperada = 10;

        List<Veterinario> veterinarioExistente = repository.findBySalarioGreaterThan(BigDecimal.valueOf(SalarioEsperado));
        int quantidadeExistente = veterinarioExistente.size();
    
       assertNotNull(veterinarioExistente);
       assertEquals(quantidadeEsperada, quantidadeExistente);

    }

    @Test
    public void testarBuscarSalarioEntreOsValorInformado(){

        Double SalarioEsperado = 3000.0;
        Double SalarioEsperado2 = 5000.0;

        int quantidadeEsperada = 7;

        List<Veterinario> veterinarioExistente = repository.findBySalarioBetween(BigDecimal.valueOf(SalarioEsperado),BigDecimal.valueOf(SalarioEsperado2));
        int quantidadeExistente = veterinarioExistente.size();
    
       assertNotNull(veterinarioExistente);
       assertEquals(quantidadeEsperada, quantidadeExistente);

    }

    @Test
    public void testeValidUpdate(){
        int idSerAlterado = 1;
        String nomeAlterado = "Caiozin";
        BigDecimal  salarioAlterado = BigDecimal.valueOf(3000.0);
        
        Veterinario veterinarioExistente  = repository.findById(idSerAlterado).get();

        veterinarioExistente.setNome(nomeAlterado);
        veterinarioExistente.setSalario(salarioAlterado);

        repository.save(veterinarioExistente);

        assertEquals(nomeAlterado, veterinarioExistente.getNome());


    }

    






}
