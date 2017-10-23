package com.demo.adnetwork.repository;

import com.demo.adnetwork.entity.AdNetworkSource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface AdNetworkSourceRepository extends JpaRepository<AdNetworkSource, Long> {
    AdNetworkSource findByName(String name);
}