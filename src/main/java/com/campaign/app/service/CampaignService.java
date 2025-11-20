package com.campaign.app.service;

import com.campaign.app.model.Campaign;
import com.campaign.app.model.HistoryRecord;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CampaignService {
    private static CampaignService instance;
    private List<Campaign> campaigns;

    private List<HistoryRecord> historyRecords;

    private CampaignService() {
        campaigns = new ArrayList<>();
        historyRecords = new ArrayList<>();
    }

    public static CampaignService getInstance() {
        if (instance == null) {
            instance = new CampaignService();
        }
        return instance;
    }

    public void saveCampaign(Campaign campaign) {
        // If ID exists, update (simple replacement for now)
        Optional<Campaign> existing = campaigns.stream()
                .filter(c -> c.getId().equals(campaign.getId()))
                .findFirst();

        if (existing.isPresent()) {
            // Update fields
            Campaign c = existing.get();
            c.setName(campaign.getName());
            c.setSubject(campaign.getSubject());
            c.setDescription(campaign.getDescription());
            c.setStartDate(campaign.getStartDate());
            c.setEndDate(campaign.getEndDate());
            c.setFrequency(campaign.getFrequency());
            c.setImagePath(campaign.getImagePath());
            addHistory(c.getId(), "Atualização de Campanha");
        } else {
            campaigns.add(campaign);
            addHistory(campaign.getId(), "Criação de Campanha");
        }
    }

    public List<Campaign> getAllCampaigns() {
        return new ArrayList<>(campaigns);
    }

    public Optional<Campaign> getCampaignById(String id) {
        return campaigns.stream().filter(c -> c.getId().equals(id)).findFirst();
    }

    public void addHistory(String campaignId, String type) {
        historyRecords.add(new HistoryRecord(campaignId, type));
    }

    public List<HistoryRecord> getProcessingHistory(String campaignId) {
        if (campaignId == null || campaignId.trim().isEmpty()) {
            return new ArrayList<>(historyRecords);
        }
        return historyRecords.stream()
                .filter(h -> h.getCampaignId().equals(campaignId))
                .toList();
    }
}
