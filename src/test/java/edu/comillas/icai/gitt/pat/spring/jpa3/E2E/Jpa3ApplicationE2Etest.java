package edu.comillas.icai.gitt.pat.spring.jpa3.E2E;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class PistaPadelE2EFullTest {

    @Autowired
    TestRestTemplate client;

    private HttpHeaders jsonHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    private String registerUser(String email) {
        String body = """
                {
                  "nombre": "User",
                  "apellidos": "Test",
                  "telefono": "123456789",
                  "email": "%s",
                  "password": "aaaaaaA1"
                }
                """.formatted(email);

        client.exchange(
                "/pistaPadel/auth/register",
                HttpMethod.POST,
                new HttpEntity<>(body, jsonHeaders()),
                String.class
        );

        return email;
    }

    private HttpHeaders authHeaders(String email) {
        HttpHeaders headers = jsonHeaders();
        if (email.equals("admin@padel.com")) {
            headers.setBasicAuth(email, "1234");
        } else {
            headers.setBasicAuth(email, "aaaaaaA1");
        }
        return headers;
    }

    @Test
    void createCourtTest() {

        String adminEmail = "admin@padel.com"; // User created in data.sql as ADMIN

        HttpHeaders headers = authHeaders(adminEmail);

        String body = """
                {
                  "nombre": "Pista Test 1",
                  "ubicacion": "Interior",
                  "precioHora": 20,
                  "activa": true
                }
                """;

        ResponseEntity<String> response = client.exchange(
                "/pistaPadel/courts",
                HttpMethod.POST,
                new HttpEntity<>(body, headers),
                String.class
        );

        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void createReservationTest() {

        String adminEmail = "admin@padel.com";
        HttpHeaders adminHeaders = authHeaders(adminEmail);

        String email = "usertest1@email.com";
        registerUser(email);
        HttpHeaders userHeaders = authHeaders(email);

        String courtBody = """
                {
                  "nombre": "Pista Test 2",
                  "ubicacion": "Exterior",
                  "precioHora": 25,
                  "activa": true
                }
                """;

        client.exchange("/pistaPadel/courts", HttpMethod.POST,
                new HttpEntity<>(courtBody, adminHeaders), String.class);

        String reservationBody = """
                {
                  "pista": { "idPista": 1 },
                  "fechaReserva": "2026-03-10",
                  "horaInicio": "18:00:00",
                  "duracionMinutos": 60
                }
                """;

        ResponseEntity<String> response = client.exchange(
                "/pistaPadel/reservations",
                HttpMethod.POST,
                new HttpEntity<>(reservationBody, userHeaders),
                String.class
        );

        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void reservationConflictTest() {

        String adminEmail = "admin@padel.com";
        HttpHeaders adminHeaders = authHeaders(adminEmail);

        String email = "usertest2@email.com";
        registerUser(email);
        HttpHeaders userHeaders = authHeaders(email);

        String courtBody = """
                {
                  "nombre": "Pista Test 3",
                  "ubicacion": "Interior",
                  "precioHora": 20,
                  "activa": true
                }
                """;

        client.exchange("/pistaPadel/courts", HttpMethod.POST,
                new HttpEntity<>(courtBody, adminHeaders), String.class);

        String reservation = """
                {
                  "pista": { "idPista": 1 },
                  "fechaReserva": "2026-03-10",
                  "horaInicio": "18:00:00",
                  "duracionMinutos": 60
                }
                """;

        client.exchange("/pistaPadel/reservations",
                HttpMethod.POST, new HttpEntity<>(reservation, userHeaders), String.class);

        ResponseEntity<String> conflict = client.exchange(
                "/pistaPadel/reservations",
                HttpMethod.POST,
                new HttpEntity<>(reservation, userHeaders),
                String.class
        );

        Assertions.assertEquals(HttpStatus.CONFLICT, conflict.getStatusCode());
    }

    @Test
    void getReservationsTest() {

        String email = "usertest3@email.com";
        registerUser(email);
        HttpHeaders headers = authHeaders(email);

        ResponseEntity<String> response = client.exchange(
                "/pistaPadel/reservations",
                HttpMethod.GET,
                new HttpEntity<>(headers),
                String.class
        );

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void availabilityTest() {

        String adminEmail = "admin@padel.com";
        HttpHeaders adminHeaders = authHeaders(adminEmail);

        String courtBody = """
                {
                  "nombre": "Pista Disponibilidad",
                  "ubicacion": "Exterior",
                  "precioHora": 22,
                  "activa": true
                }
                """;

        client.exchange("/pistaPadel/courts", HttpMethod.POST,
                new HttpEntity<>(courtBody, adminHeaders), String.class);

        ResponseEntity<String> response = client.exchange(
                "/pistaPadel/courts/1/availability?date=2026-03-10",
                HttpMethod.GET,
                null,
                String.class
        );

        Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());
    }
}