package wooteco.subway.service.dto;

public class DeleteSectionServiceDto {

    private final Long lineId;
    private final Long stationId;

    public DeleteSectionServiceDto(Long lineId, Long stationId) {
        this.lineId = lineId;
        this.stationId = stationId;
    }

    public Long getLineId() {
        return lineId;
    }

    public Long getStationId() {
        return stationId;
    }
}
