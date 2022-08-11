package com.interview.backendfavouriterecipes.base;



import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;


import javax.persistence.*;
import java.io.Serializable;

import java.time.Instant;



@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity implements Serializable, Comparable {
    @Id
    @GeneratedValue(
            generator = "system-uuid"
    )
    @GenericGenerator(
            name = "system-uuid",
            strategy = "uuid2"
    )
    @Column(
            name = "id",
            unique = true,
            length = 50
    )
    protected String id;


    @CreatedDate
    @Column(
            name = "created_date",
            updatable = false
    )

    private Long createdDate = Instant.now().toEpochMilli();

    @LastModifiedDate
    @Column(
            name = "last_modified_date"
    )
    private Long lastModifiedDate = Instant.now().toEpochMilli();


    @Column(
            name = "deleted",
            nullable = false
    )
    @ColumnDefault("0")
    @JsonIgnore
    private boolean deleted;

    public BaseEntity(String id) {
        this.id = id;
    }

    public BaseEntity() {
    }

    public int compareTo(Object o) {
        if (o == null) {
            return -1;
        } else if (this == o) {
            return 0;
        } else {
            return o instanceof BaseEntity && this.id.equals(((BaseEntity) o).id) ? 0 : -1;
        }
    }
}



