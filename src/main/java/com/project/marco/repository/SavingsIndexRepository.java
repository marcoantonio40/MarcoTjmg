package com.project.marco.repository;

import com.project.marco.model.SavingsIndexEntity;
import com.project.marco.model.SavingsIndexId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SavingsIndexRepository extends JpaRepository<SavingsIndexEntity, SavingsIndexId> {
}
