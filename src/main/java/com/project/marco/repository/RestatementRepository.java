package com.project.marco.repository;

import com.project.marco.model.RestatementEntity;
import com.project.marco.model.RestatementId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestatementRepository extends JpaRepository<RestatementEntity, RestatementId> {
    Double findByMonth(String mesString, int yearFactor);
}
