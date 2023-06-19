package com.nobrainsolutions.assignment.controller;


import com.nobrainsolutions.assignment.entity.Client;
import com.nobrainsolutions.assignment.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.Optional;

/**
 * The ClientController class is responsible for handling client-related API endpoints.
 * It provides operations to create, retrieve, update, and search clients.
 */

@RestController
@RequestMapping("/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;


    /**
     * This method is used to create a client.
     *
     * @param client The client object to be created.
     * @return The ResponseEntity containing the created client.
     */

    @PostMapping
    public ResponseEntity<Client> createClient(@Valid @RequestBody Client client) {
        Client createdClient = clientService.createClient(client);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdClient);
    }

    /**
     * This method is used to get a client by ID.
     *
     * @param idNumber The ID number of the client.
     * @return The ResponseEntity containing the client.
     */

    @GetMapping("/{idNumber}")
    public ResponseEntity<Client> getClientById(@PathVariable String idNumber) {
        Client client = clientService.getClientById(idNumber);
        if (client != null) {
            return ResponseEntity.ok(client);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * This method is used to update a client.
     *
     * @param idNumber The ID number of the client to update.
     * @param client   The updated client object.
     * @return The ResponseEntity containing the updated client.
     */

    @PutMapping("/{idNumber}")
    public ResponseEntity<Client> updateClient(@PathVariable String idNumber, @Valid @RequestBody Client client) {
        Client updatedClient = clientService.updateClient(idNumber, client);
        if (updatedClient != null) {
            return ResponseEntity.ok(updatedClient);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * This method is used to search for clients.
     *
     * @param firstName    The first name of the client.
     * @param idNumber     The ID number of the client.
     * @param mobileNumber The mobile number of the client.
     * @return The ResponseEntity containing the search result.
     */

    @GetMapping("/search")
    public ResponseEntity<Client> searchClient(@RequestParam(required = false) String firstName,
                                               @RequestParam(required = false) String idNumber,
                                               @RequestParam(required = false) String mobileNumber) {

        if (firstName == null && idNumber == null && mobileNumber == null) {
            return ResponseEntity.badRequest().build();
        }

        Optional<Client> optionalClient = clientService.searchClient(firstName, idNumber, mobileNumber);
        if (optionalClient.isPresent()) {
            return ResponseEntity.ok(optionalClient.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

