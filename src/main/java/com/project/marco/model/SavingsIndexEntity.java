package com.project.marco.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
public class SavingsIndexEntity implements Serializable {

    @EmbeddedId
    private SavingsIndexId savingsIndexId;

    private Double value;
}
