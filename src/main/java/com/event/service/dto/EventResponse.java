package com.event.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class EventResponse {

    private List<EventDTO> eventDTOList;
    private long totalRows;

}
