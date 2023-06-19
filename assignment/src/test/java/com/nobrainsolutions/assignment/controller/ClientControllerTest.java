package com.nobrainsolutions.assignment.controller;
import com.nobrainsolutions.assignment.entity.Client;
import com.nobrainsolutions.assignment.service.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


/**
 *
 * The ClientControllerTest class is responsible for testing the ClientController class.
 * It contains test cases for creating, retrieving, updating, and searching clients.
 */

public class ClientControllerTest {

    @Mock
    private ClientService clientService;

    @InjectMocks
    private ClientController clientController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createClientTest() {

        Client client = new Client("Vishal", "Nagdev", "9284121655", "7878", "Pune");
        Client createdClient = new Client("Vishal", "Nagdev", "9284121655", "7878", "Pune");
        when(clientService.createClient(client)).thenReturn(createdClient);

        ResponseEntity<Client> response = clientController.createClient(client);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(createdClient, response.getBody());
        verify(clientService, times(1)).createClient(client);
    }

    @Test
    void getClientByIdTest() {

        String idNumber = "ID123";
        Client client = new Client("Vishal", "Nagdev", "9284121655", "7878", "Pune");
        when(clientService.getClientById(idNumber)).thenReturn(client);

        ResponseEntity<Client> response = clientController.getClientById(idNumber);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(client, response.getBody());
        verify(clientService, times(1)).getClientById(idNumber);
    }

    @Test
    void updateClientTest() {

        String idNumber = "ID123";
        Client client = new Client("Vishal", "Nagdev", "9284121655", "7878", "Pune");
        Client updatedClient = new Client("Vishal", "Nagdev", "9284121655", "7878", "South Africa");
        when(clientService.updateClient(idNumber, client)).thenReturn(updatedClient);

        ResponseEntity<Client> response = clientController.updateClient(idNumber, client);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedClient, response.getBody());
        verify(clientService, times(1)).updateClient(idNumber, client);
    }

    @Test
    void searchClientTest() {

        String firstName = "Vishal";
        String idNumber = "7878";
        String mobileNumber = "9284121655";
        Client client = new Client(firstName, "Nagdev", mobileNumber, idNumber, "Pune");
        when(clientService.searchClient(firstName, idNumber, mobileNumber)).thenReturn(Optional.of(client));

        ResponseEntity<Client> response = clientController.searchClient(firstName, idNumber, mobileNumber);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(client, response.getBody());
        verify(clientService, times(1)).searchClient(firstName, idNumber, mobileNumber);
    }

}

