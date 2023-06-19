package com.nobrainsolutions.assignment.service;
import com.nobrainsolutions.assignment.entity.Client;
import com.nobrainsolutions.assignment.exception.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 * The ClientService class handles the business logic for managing clients.
 * It provides methods for creating, retrieving, updating, and searching clients.
 */

@Service
public class ClientService {

    private List<Client> clients;

    public ClientService() {
        this.clients = new ArrayList<>();
    }

    public ClientService(List<Client> clients) {
        this.clients = clients;
    }

    /**
     * Creates a new client.
     *
     * @param client The client object to be created.
     * @return The created client.
     * @throws IllegalArgumentException if the ID number or mobile number is invalid or already exists.
     */

    public Client createClient(Client client) {

        if (!isSouthAfricanIdNumberValid(client.getIdNumber())) {
            throw new IllegalArgumentException(Constants.INVALID_ID_NUMBER);
        }

        if (isDuplicateIdNumber(client.getIdNumber())) {
            throw new IllegalArgumentException(Constants.DUPLICATE_ID_NUMBER);
        }

        if (isDuplicateMobileNumber(client.getMobileNumber())) {
            throw new IllegalArgumentException(Constants.DUPLICATE_MOBILE_NUMBER);
        }

        clients.add(client);
        return client;
    }

    /**
     * Retrieves a client by ID.
     *
     * @param idNumber The ID number of the client.
     * @return The client with the specified ID, or null if not found.
     */

    public Client getClientById(String idNumber) {
        return clients.stream()
                .filter(c -> c.getIdNumber().equals(idNumber))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(Constants.CLIENT_NOT_FOUND));
    }



    /**
     * Updates a client.
     *
     * @param idNumber      The ID number of the client to update.
     * @param updatedClient The updated client object.
     * @return The updated client.
     * @throws IllegalArgumentException if the ID number or mobile number is invalid or already exists,
     *                                  or if the client with the specified ID is not found.
     */
    public Client updateClient(String idNumber, Client updatedClient) {
        Client client = getClientById(idNumber);
        if (client != null) {

            if (!isSouthAfricanIdNumberValid(updatedClient.getIdNumber())) {
                throw new IllegalArgumentException(Constants.INVALID_ID_NUMBER);
            }

            if (client.getIdNumber().equals(updatedClient.getIdNumber()) && isDuplicateIdNumber(updatedClient.getIdNumber())) {
                throw new IllegalArgumentException(Constants.DUPLICATE_ID_NUMBER);
            }

            if (client.getMobileNumber().equals(updatedClient.getMobileNumber()) && isDuplicateMobileNumber(updatedClient.getMobileNumber())) {
                throw new IllegalArgumentException(Constants.DUPLICATE_MOBILE_NUMBER);
            }

            client.setFirstName(updatedClient.getFirstName());
            client.setLastName(updatedClient.getLastName());
            client.setMobileNumber(updatedClient.getMobileNumber());
            client.setIdNumber(updatedClient.getIdNumber());
            client.setPhysicalAddress(updatedClient.getPhysicalAddress());

            return client;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    /**
     Searches for clients based on the provided parameters.
     @param firstName The first name of the client.
     @param idNumber The ID number of the client.
     @param mobileNumber The mobile number of the client.
     @return The client matching the search criteria.
     @throws IllegalArgumentException If no search criteria are provided or if a client with the specified first name, ID number, or mobile number is not found.
     */

    public Optional<Client> searchClient(String firstName, String idNumber, String mobileNumber) {
        if (firstName != null) {
            return clients.stream()
                    .filter(c -> c.getFirstName().equals(firstName))
                    .findFirst();
        }

        if (idNumber != null) {
            return clients.stream()
                    .filter(c -> c.getIdNumber().equals(idNumber))
                    .findFirst();
        }

        if (mobileNumber != null) {
            return clients.stream()
                    .filter(c -> c.getMobileNumber().equals(mobileNumber))
                    .findFirst();
        }

        return Optional.empty();
    }


    /**
     * Checks if a South African ID number is valid.
     *
     * @param idNumber The ID number to validate.
     * @return true if the ID number is valid, false otherwise.
     */
    private boolean isSouthAfricanIdNumberValid(String idNumber) {
        if (idNumber.length() != 13 || !idNumber.matches("\\d{13}")) {
            return false;
        }
        char twelfthDigit = idNumber.charAt(11);

        if (twelfthDigit != '8' && twelfthDigit != '9') {
            return false;
        }
        return true;
    }

    /**
     * Checks if an ID number already exists for any client.
     *
     * @param idNumber The ID number to check.
     * @return true if the ID number is a duplicate, false otherwise.
     */
    private boolean isDuplicateIdNumber(String idNumber) {
        return clients.stream()
                .anyMatch(c -> c.getIdNumber().equals(idNumber));
    }

    /**
     * Checks if a mobile number already exists for any client.
     *
     * @param mobileNumber The mobile number to check.
     * @return true if the mobile number is a duplicate, false otherwise.
     */
    private boolean isDuplicateMobileNumber(String mobileNumber) {
        return clients.stream()
                .anyMatch(c -> c.getMobileNumber().equals(mobileNumber));
    }
}
