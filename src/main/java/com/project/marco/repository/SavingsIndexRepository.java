package com.project.marco.repository;

import com.project.marco.model.SavingsIndexEntity;
import com.project.marco.model.SavingsIndexId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SavingsIndexRepository extends JpaRepository<SavingsIndexEntity, SavingsIndexId> {

    @Query(value = "select ano, max(mes) from savings_index_entity where ano in ( selec max(ano) from savings_index_entity)")
    SavingsIndexEntity getLastIndex();
}
