package com.adjadev.stock.repository;

import com.adjadev.stock.model.Client;
import com.adjadev.stock.model.CommandeClient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface CommandeClientRepository extends JpaRepository<CommandeClient, Integer> {
     List<CommandeClient> finAllByClientId(Integer idClient);
     Optional<CommandeClient> findCommandeClientByCode(String code);
}
