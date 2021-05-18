package wooteco.subway.service.dto;

import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import wooteco.subway.web.dto.response.StationResponse;
import wooteco.subway.domain.Line;

public class ReadLineDto {

    private final Long id;
    @NotEmpty
    private final String name;
    @NotBlank
    private final String color;
    private final List<StationResponse> stationsResponses;

    public ReadLineDto(Long id, String name, String color,
        List<StationResponse> stationsResponses) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.stationsResponses = stationsResponses;
    }

    public static ReadLineDto of(Line line, List<StationResponse> stationResponses) {
        return new ReadLineDto(line.getId(), line.getName(), line.getColor(), stationResponses);
    }

    public Long getId() {

        return id;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public List<StationResponse> getStationsResponses() {
        return stationsResponses;
    }
}
