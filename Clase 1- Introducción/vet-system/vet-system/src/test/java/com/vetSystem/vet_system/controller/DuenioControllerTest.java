package com.vetSystem.vet_system.controller;

import com.vetSystem.vet_system.dto.DuenioDTO;
import com.vetSystem.vet_system.exception.ResourceNotFoundException;
import com.vetSystem.vet_system.service.DuenioService;
import com.vetSystem.vet_system.service.MascotaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(DuenioController.class)
public class DuenioControllerTest {

    @MockitoBean
    private DuenioService duenioService;

    @MockitoBean
    private MascotaService mascotaService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllDuenios_listaVacia_retorna200() throws Exception {

        when(duenioService.getAllDuenios()).thenReturn(List.of());

        mockMvc.perform(get("/api/duenios")).andExpect(status().isOk());
    }

    @Test
    void getDuenioById_cuandoExiste_retorna200() throws Exception {

        DuenioDTO duenioDTO = new DuenioDTO();
        duenioDTO.setId(1L);
        duenioDTO.setNombre("Dante");

        when(duenioService.getDuenioById(1L))
                .thenReturn(duenioDTO);
        mockMvc.perform(get("/api/duenios/1")).andExpect(status().isOk());
    }

    @Test
    void getDuenioById_cuandoNoExiste_retorna404() throws Exception {

        when(duenioService.getDuenioById(99L)).thenThrow(new ResourceNotFoundException("Duenio", 99L));

        mockMvc.perform(get("/api/duenios/99")).andExpect(status().isNotFound());
    }

    @Test
    void createDuenio_conDatosValidos_retorna201() throws Exception {

        DuenioDTO dto = new DuenioDTO();
        dto.setNombre("Dante");
        dto.setApellido("Apellido");
        dto.setDni("12345678");
        dto.setTelefono("123456789");
        dto.setEmail("dante@email.com");

        when(duenioService.createDuenio(dto)).thenReturn(dto);

        mockMvc.perform(post("/api/duenios").contentType("application/json").content(objectMapper.writeValueAsString(dto))).andExpect(status().isCreated());
    }

    @Test
    void createDuenio_conEmailInvalido_retorna400() throws Exception {

        DuenioDTO dto = new DuenioDTO();
        dto.setNombre("Dante");
        dto.setApellido("Apellido");
        dto.setDni("12345678");
        dto.setTelefono("123456789");
        dto.setEmail("");

        mockMvc.perform(post("/api/duenios").contentType("application/json").content(objectMapper.writeValueAsString(dto))).andExpect(status().isBadRequest());
    }

}