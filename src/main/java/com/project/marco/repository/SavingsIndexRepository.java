package com.project.marco.repository;

import com.project.marco.model.SavingsIndexEntity;
import com.project.marco.model.SavingsIndexId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface SavingsIndexRepository extends JpaRepository<SavingsIndexEntity, SavingsIndexId> {

    @Query("select s from SavingsIndexEntity s where s.savingsIndexId.ano in (select max(s2.savingsIndexId.ano) from SavingsIndexEntity s2)")
    public List<SavingsIndexEntity> getLastIndex();
}
