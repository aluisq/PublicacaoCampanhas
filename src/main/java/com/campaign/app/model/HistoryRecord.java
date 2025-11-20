package com.campaign.app.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class HistoryRecord {
    private String campaignId;
    private LocalDateTime timestamp;
    private String type; // "Imediato", "Agendado", "Criação", etc.

    public HistoryRecord(String campaignId, String type) {
        this.campaignId = campaignId;
        this.timestamp = LocalDateTime.now();
        this.type = type;
    }

    public String getCampaignId() {
        return campaignId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getFormattedTimestamp() {
        return timestamp.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
    }

    public String getType() {
        return type;
    }
}
