package com.adjadev.stock.repository;

import com.adjadev.stock.dto.ClientDto;
import com.adjadev.stock.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Integer> {

}
