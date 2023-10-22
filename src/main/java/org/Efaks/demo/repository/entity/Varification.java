package org.Efaks.demo.repository.entity;

import lombok.Builder;

import java.time.LocalDate;

public class Varification {
    @Builder.Default
    private Boolean status=false;
    private LocalDate startedAt;
    private LocalDate endsAt;
    private String planType;
}
