package br.com.meta3.java.scaffold.api.controllers;

import br.com.meta3.java.scaffold.application.services.ArquivoService;
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
 * Tests for ArquivoController GET /api/arquivos/{id}/nome endpoint.
 *
 * Decision notes:
 * - Use @WebMvcTest to slice only the web layer and instantiate ArquivoController in isolation.
 * - ArquivoService is @MockBean'd so we can control service behavior without touching persistence.
 * - Verify:
 *    - Existing resource -> 200 + JSON body with legacy property "nomearquivo".
 *    - Missing resource -> 404 as the controller maps ArquivoService.ArquivoNotFoundException to NOT_FOUND.
 *
 * TODO: (REVIEW) Consider adding tests for invalid inputs (e.g., non-numeric id path variables)
 *       or centralized exception handling behavior once @ControllerAdvice is introduced.
 */
@WebMvcTest(ArquivoController.class)
public class ArquivoControllerNomeEndpointTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ArquivoService arquivoService;

    @Test
    @DisplayName("GET /api/arquivos/{id}/nome - existing id returns nomearquivo in response")
    public void getNomeById_existingId_returnsNomearquivo() throws Exception {
        Integer id = 1;

        // Arrange: mock service to return the expected legacy-styled nome value.
        when(arquivoService.getNomearquivoById(id)).thenReturn("meu_arquivo.txt");

        // Act & Assert: expect 200 OK and JSON payload containing legacy property "nomearquivo".
        mockMvc.perform(get("/api/arquivos/{id}/nome", id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.nomearquivo").value("meu_arquivo.txt"));
    }

    @Test
    @DisplayName("GET /api/arquivos/{id}/nome - missing id returns 404 Not Found")
    public void getNomeById_missingId_returns404() throws Exception {
        Integer missingId = 999;

        // Arrange: mock service to throw the domain-specific not-found exception.
        // Note: ArquivoService.ArquivoNotFoundException is a nested static exception type.
        when(arquivoService.getNomearquivoById(missingId))
                .thenThrow(new ArquivoService.ArquivoNotFoundException("Arquivo not found for id: " + missingId));

        // Act & Assert: controller should map the exception to 404 Not Found.
        mockMvc.perform(get("/api/arquivos/{id}/nome", missingId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    // TODO: (REVIEW) Add tests for:
    // - Service throwing IllegalArgumentException -> expect 400 Bad Request.
    // - Unexpected exceptions -> expect 500 Internal Server Error (or centralized error payload when added).
    // - Content negotiation and response shape consistency (e.g., when nomearquivo is null).
}