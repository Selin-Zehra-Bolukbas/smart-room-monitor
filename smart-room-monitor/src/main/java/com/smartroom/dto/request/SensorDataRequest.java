package com.smartroom.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SensorDataRequest {
    private Double temperature;
    private Double humidity;
    private Boolean motion;
    private Integer lightLevel;
    private Long roomId;
}
