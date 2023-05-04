package com.adjadev.stock.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.Instant;

@Data
@SuperBuilder
@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class AbstractEntity implements  Serializable{
    @Id
    @GeneratedValue
    private  Integer id;
    @CreatedDate
    //private Date creationDate;
    @Column(name ="crationDate", nullable = false, updatable = false)
    //@JsonIgnore
    private Instant creationDate;
    @LastModifiedDate
    @Column(name="lastModifiedDate")
    //@JsonIgnore
    private Instant lastModifiedDate;
}
