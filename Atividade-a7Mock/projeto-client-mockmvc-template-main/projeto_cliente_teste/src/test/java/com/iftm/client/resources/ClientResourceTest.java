package com.iftm.client.resources;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.iftm.client.dto.ClientDTO;
import com.iftm.client.entities.Client;
import com.iftm.client.services.ClientService;
import com.iftm.client.services.exceptions.ResourceNotFoundException;

//necessário para utilizar o MockMVC
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ClientResourceTest {
    @Autowired
    private MockMvc mockMVC;

    @MockBean
    private ClientService service;

    /**
     * Caso de testes : Verificar se o endpoint get/clients/ retorna todos os
     * clientes existentes
     * Arrange:
     * - camada service simulada com mockito
     * - base de dado : 3 clientes
     * new Client(7l, "Jose Saramago", "10239254871", 5000.0,
     * Instant.parse("1996-12-23T07:00:00Z"), 0);
     * new Client(4l, "Carolina Maria de Jesus", "10419244771", 7500.0,
     * Instant.parse("1996-12-23T07:00:00Z"), 0);
     * new Client(8l, "Toni Morrison", "10219344681", 10000.0,
     * Instant.parse("1940-02-23T07:00:00Z"), 0);
     * - Uma PageRequest default
     * 
     * @throws Exception
     */
    @Test
    @DisplayName("Verificar se o endpoint get/clients/ retorna todos os clientes existentes")
    public void testarEndPointListarTodosClientesRetornaCorreto() throws Exception {
        // arrange
        int quantidadeClientes = 3;
        // configurando o Mock ClientService
        List<ClientDTO> listaClientes;
        listaClientes = new ArrayList<ClientDTO>();
        listaClientes.add(new ClientDTO(
                new Client(7L, "Jose Saramago", "10239254871", 5000.0, Instant.parse("1996-12-23T07:00:00Z"), 0)));
        listaClientes.add(new ClientDTO(new Client(4L, "Carolina Maria de Jesus", "10419244771", 7500.0,
                Instant.parse("1996-12-23T07:00:00Z"), 0)));
        listaClientes.add(new ClientDTO(
                new Client(8L, "Toni Morrison", "10219344681", 10000.0, Instant.parse("1940-02-23T07:00:00Z"), 0)));

        Page<ClientDTO> page = new PageImpl<>(listaClientes);

        Mockito.when(service.findAllPaged(Mockito.any())).thenReturn(page);
        // fim configuração mockito

        // act

        ResultActions resultados = mockMVC.perform(get("/clients/").accept(MediaType.APPLICATION_JSON));

        // assign
        resultados
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").exists())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content[?(@.id == '%s')]", 7L).exists())
                .andExpect(jsonPath("$.content[?(@.id == '%s')]", 4L).exists())
                .andExpect(jsonPath("$.content[?(@.id == '%s')]", 8L).exists())
                .andExpect(jsonPath("$.content[?(@.name == '%s')]", "Toni Morrison").exists())
                .andExpect(jsonPath("$.totalElements").exists())
                .andExpect(jsonPath("$.totalElements").value(quantidadeClientes));

    }

    @Test
    @DisplayName("Verificar se o andpoint get/clients/id/{id} retorna o cliente correto")
    public void testarFindByIdRetornaClienteQuandoIdExiste() throws Exception {

        Long existingId = 7L;

        // Pegando Informações do Cliente (Criando)
        ClientDTO clientDTO = new ClientDTO(
                new Client(7L, "Clarice Lispector",
                        "10919444522", 3800.0,
                        Instant.parse("1960-04-13T07:50:00Z"), 2));

        Mockito.when(service.findById(existingId)).thenReturn(clientDTO); // Quando o mock achar o "existingId" ele
                                                                          // Retorna o DTO

        // ACT
        ResultActions resultados = mockMVC.perform(get("/clients/id/{id}", existingId)
                .accept(MediaType.APPLICATION_JSON));

        resultados
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(existingId))
                .andExpect(jsonPath("$.name").value("Clarice Lispector"))
                .andExpect(jsonPath("$.cpf").value("10919444522"))
                .andExpect(jsonPath("$.income").value(3800.0))
                .andExpect(jsonPath("$.children").value(2));

    }

    @Test
    @DisplayName("Verificar se o endpoint get/clients/id/{id} retorna not found quando o id não existe")
    public void testarFindByIdRetornaNotFoundQuandoIdNaoExiste() throws Exception {
        // arrange
        Long nonExistingId = 99L;

        Mockito.when(service.findById(nonExistingId)).thenThrow(ResourceNotFoundException.class);

        // act
        ResultActions resultados = mockMVC.perform(get("/clients/id/{id}", nonExistingId)
                .accept(MediaType.APPLICATION_JSON));

        // assert
        resultados
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Resource not found"))
                .andExpect(jsonPath("$.message").value("Entity not found"))
                .andExpect(jsonPath("$.status").value(404));
    }

}
