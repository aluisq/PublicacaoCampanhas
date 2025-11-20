package com.campaign.app.model;

import java.time.LocalDate;
import java.util.UUID;

public class Campaign {
    private String id;
    private String name;
    private String subject;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private String frequency; // "imediata", "diária", "mensal"
    private String imagePath;
    private String status; // "pendente", "ativa", "finalizada"

    public Campaign(String name, String subject, String description, LocalDate startDate, LocalDate endDate, String frequency, String imagePath) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.subject = subject;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.frequency = frequency;
        this.imagePath = imagePath;
        this.status = "pendente";
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public String getFrequency() { return frequency; }
    public void setFrequency(String frequency) { this.frequency = frequency; }

    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
