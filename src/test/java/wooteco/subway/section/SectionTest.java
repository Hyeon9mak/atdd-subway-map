package wooteco.subway.section;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import wooteco.subway.domain.Line;
import wooteco.subway.domain.Section;
import wooteco.subway.domain.Station;

public class SectionTest {

    @Test
    @DisplayName("섹션 생성 테스트")
    void create() {
        // give
        Line mockLine = mock(Line.class);
        Station mockUpStation = mock(Station.class);
        Station mockDownStation = mock(Station.class);
        int distance = 10;

        // when
        Section section = new Section(mockLine, mockUpStation, mockDownStation, distance);

        // then
        assertThat(section).isInstanceOf(Section.class);
        assertThat(section.getLine()).isEqualTo(mockLine);
        assertThat(section.getUpStation()).isEqualTo(mockUpStation);
        assertThat(section.getDownStation()).isEqualTo(mockDownStation);
        assertThat(section.getDistance()).isEqualTo(distance);
    }
}
