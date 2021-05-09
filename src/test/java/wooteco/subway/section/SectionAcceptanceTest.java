package wooteco.subway.section;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import wooteco.subway.controller.dto.request.LineRequest;
import wooteco.subway.controller.dto.request.SectionRequest;
import wooteco.subway.controller.dto.request.StationRequest;

@DisplayName("지하철 구간 관련 기능")
@Sql("classpath:initializeTable.sql")
public class SectionAcceptanceTest {

    @Test
    @DisplayName("섹션을 생성한다.")
    void createSection() {
        // given
        long upStationId = 2;
        long downStationId = 3;
        int distance = 10;
        int lineIndex = 1;

        SectionRequest sectionRequest = new SectionRequest(upStationId, downStationId, distance);

        // when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
            .body(sectionRequest)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .post("/lines/" + lineIndex + "sections")
            .then().log().all()
            .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.header("Location")).isNotBlank();
    }

    @Test
    @DisplayName("StationId 없는 것")
    void failToFindStationId() {
        // given
        long upStationId = 999;
        long downStationId = 3;
        int distance = 10;
        int lineIndex = 1;

        SectionRequest sectionRequest = new SectionRequest(upStationId, downStationId, distance);

        // when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
            .body(sectionRequest)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .post("/lines/" + lineIndex + "sections")
            .then().log().all()
            .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(response.header("Location")).isNotBlank();
    }

    @Test
    @DisplayName("LineId 없는 것")
    void failToFindLineId() {
        // given
        long upStationId = 2;
        long downStationId = 3;
        int distance = 10;
        int lineIndex = 999;

        SectionRequest sectionRequest = new SectionRequest(upStationId, downStationId, distance);

        // when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
            .body(sectionRequest)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .post("/lines/" + lineIndex + "sections")
            .then().log().all()
            .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(response.header("Location")).isNotBlank();
    }

    @BeforeEach
    void setUpTest() {
        // given
        StationRequest request_SangbongStation = new StationRequest("상봉역");
        StationRequest request_MyeonmokStation = new StationRequest("면목역");
        StationRequest request_Sagajeong = new StationRequest("사가정역");
        LineRequest request_BrownishLine= new LineRequest("7호선", "갈록색", (long) 1, (long) 2,10);

        // when
        ExtractableResponse<Response> stationResponse1 = createStationResponse(request_SangbongStation);
        ExtractableResponse<Response> stationResponse2 = createStationResponse(request_MyeonmokStation);
        ExtractableResponse<Response> stationResponse3 = createStationResponse(request_Sagajeong);
        ExtractableResponse<Response> lineResponse = createLineResponse(request_BrownishLine);
    }

    private ExtractableResponse<Response> createStationResponse(final StationRequest stationRequest) {
        return RestAssured.given().log().all()
            .body(stationRequest.getName())
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .post("/stations")
            .then().log().all()
            .extract();
    }

    private ExtractableResponse<Response> createLineResponse(final LineRequest lineRequest) {
        return RestAssured.given().log().all()
            .body(lineRequest.getName())
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .post("/lines")
            .then().log().all()
            .extract();
    }


}
