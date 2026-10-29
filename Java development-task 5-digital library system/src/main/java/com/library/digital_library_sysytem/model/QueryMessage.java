package com.library.digital_library_sysytem.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "query_messages")
@Data
public class QueryMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String message;
}

