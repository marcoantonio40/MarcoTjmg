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

    @Query("SELECT s " +
            "FROM   SavingsIndexEntity s " +
            "WHERE  s.savingsIndexId.ano IN (SELECT max(sb.savingsIndexId.ano) " +
            "               FROM   SavingsIndexEntity sb) ")
   public List<SavingsIndexEntity> getLastIndex();
}
