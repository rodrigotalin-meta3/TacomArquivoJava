package br.com.meta3.java.scaffold.api.controllers;

import br.com.meta3.java.scaffold.application.services.EscolaService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Tests for EscolaController GET /api/escolas/{id}/codigoescola endpoint.
 *
 * Decision notes:
 * - Use @WebMvcTest to slice only the web layer and instantiate EscolaController in isolation.
 * - EscolaService is @MockBean'd so we can control service behavior without touching persistence.
 * - Verify:
 *    - Existing resource -> 200 + JSON body with legacy property "codigoescola".
 *    - Missing resource -> 404 as the controller maps EscolaService.EscolaNotFoundException to NOT_FOUND.
 *
 * TODO: (REVIEW) Consider adding tests for:
 * - Service throwing IllegalArgumentException -> expect 400 Bad Request.
 * - Unexpected exceptions -> expect 500 Internal Server Error (or centralized error payload when added).
 */
@WebMvcTest(EscolaController.class)
public class EscolaControllerCodigoEndpointTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EscolaService escolaService;

    @Test
    @DisplayName("GET /api/escolas/{id}/codigoescola - existing id returns codigoescola in response")
    public void getCodigoescolaById_existingId_returnsCodigoescola() throws Exception {
        Integer id = 1;

        // Arrange: mock service to return the expected legacy-styled codigo value.
        when(escolaService.getCodigoescolaById(id)).thenReturn("ESC123");

        // Act & Assert: expect 200 OK and JSON payload containing legacy property "codigoescola".
        mockMvc.perform(get("/api/escolas/{id}/codigoescola", id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigoescola").value("ESC123"));
    }

    @Test
    @DisplayName("GET /api/escolas/{id}/codigoescola - missing id returns 404 Not Found")
    public void getCodigoescolaById_missingId_returns404() throws Exception {
        Integer missingId = 999;

        // Arrange: mock service to throw the domain-specific not-found exception.
        when(escolaService.getCodigoescolaById(missingId))
                .thenThrow(new EscolaService.EscolaNotFoundException("Escola not found for id: " + missingId));

        // Act & Assert: controller should map the exception to 404 Not Found.
        mockMvc.perform(get("/api/escolas/{id}/codigoescola", missingId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    // TODO: (REVIEW) Add tests for:
    // - Service throwing IllegalArgumentException -> expect 400 Bad Request.
    // - Unexpected exceptions -> expect 500 Internal Server Error (or centralized error payload when added).
}