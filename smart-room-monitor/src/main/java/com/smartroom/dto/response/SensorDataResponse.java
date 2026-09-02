package com.smartroom.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SensorDataResponse {
    private Double temperature;
    private Double humidity;
    private Boolean motion;
    private Integer lightLevel;
    private Long roomId;
    private String roomName;
}
