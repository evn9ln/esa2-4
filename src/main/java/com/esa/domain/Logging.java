package com.esa.domain;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "logging")
public class Logging {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Type(type="org.hibernate.type.UUIDCharType")
    @Column(name = "id")
    private UUID id;

    @Column(name = "entity")
    private String entity;

    @Column(name = "event_type")
    private String eventType;

    @Column(name = "substance")
    private String substance;


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getSubstance() {
        return substance;
    }

    public void setSubstance(String substance) {
        this.substance = substance;
    }

    @Override
    public String toString() {
        return "Logging{" +
                "entity='" + entity + '\'' +
                ", eventType='" + eventType + '\'' +
                ", substance='" + substance + '\'' +
                '}';
    }
}
