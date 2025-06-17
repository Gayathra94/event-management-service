package com.event.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
public class EventResponse implements Serializable {

    private List<EventDTO> eventDTOList;
    private long totalRows;

}
