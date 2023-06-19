package com.nobrainsolutions.assignment.service;

import com.nobrainsolutions.assignment.entity.Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * The ClientServiceTest class is responsible for testing the ClientService class.
 * It contains test cases for creating, retrieving, updating, and searching clients.
 */

public class ClientServiceTest {

    private ClientService clientService;
    private List<Client> clients;

    @BeforeEach
    void setUp() {
        clients = new ArrayList<>();
        clientService = new ClientService(clients);
    }

    @Test
    void createClientTest() {
        // Arrange
        Client client = new Client("Vishal", "Nagdev", "9284121655", "8001015009087", "Pune");
        // Act
        Client createdClient = clientService.createClient(client);

        // Assert
        assertNotNull(createdClient);
        assertEquals(client, createdClient);
        assertEquals(1, clients.size());
        assertTrue(clients.contains(client));
    }

    @Test
    void getClientByIdTest() {

        Client existingClient = new Client("Vishal", "Nagdev", "9284121655", "7878", "Pune");
        clients.add(existingClient);

        Client client = clientService.getClientById(existingClient.getIdNumber());

        assertNotNull(client);
        assertEquals(existingClient, client);
    }

    @Test
    void updateClientTest() {

        Client existingClient = new Client("Vishal", "Nagdev", "9284121655", "8001015009087", "Pune");
        clients.add(existingClient);

        String updatedFirstName = "Vishal";
        String updatedLastName = "Nagdev";
        String updatedMobileNumber = "9284121650";
        String updatedIdNumber = "8001015009080";
        String updatedAddress = "South Africa";
        Client updatedClient = new Client(updatedFirstName, updatedLastName, updatedMobileNumber, updatedIdNumber, updatedAddress);

        Client updated = clientService.updateClient(existingClient.getIdNumber(), updatedClient);

        assertNotNull(updated);
        assertEquals(existingClient, updated);
        assertEquals(updatedFirstName, updated.getFirstName());
        assertEquals(updatedLastName, updated.getLastName());
        assertEquals(updatedMobileNumber, updated.getMobileNumber());
        assertEquals(updatedIdNumber, updated.getIdNumber());
        assertEquals(updatedAddress, updated.getPhysicalAddress());
    }

    @Test
    void searchClientTest() {
        // Arrange
        Client client = new Client("Vishal", "Nagdev", "9284121655", "7878", "Pune");
        String firstName = "Vishal";
        clients.add(client);

        // Act
        Optional<Client> result = clientService.searchClient(firstName, null, null);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(firstName, result.get().getFirstName());
    }
}








