package com.gizwits.lease.card.dto;

import java.util.List;

public class UserCardListDto {

    private List<UserCardDto> availableCards;
    private List<UserCardDto> unavailableCards;

    public UserCardListDto(List<UserCardDto> availableCards, List<UserCardDto> unavailableCards) {
        this.availableCards = availableCards;
        this.unavailableCards = unavailableCards;
    }

    public List<UserCardDto> getAvailableCards() {
        return availableCards;
    }

    public void setAvailableCards(List<UserCardDto> availableCards) {
        this.availableCards = availableCards;
    }

    public List<UserCardDto> getUnavailableCards() {
        return unavailableCards;
    }

    public void setUnavailableCards(List<UserCardDto> unavailableCards) {
        this.unavailableCards = unavailableCards;
    }
}
