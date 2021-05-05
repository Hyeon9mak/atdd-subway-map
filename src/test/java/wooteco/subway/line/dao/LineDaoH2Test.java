package wooteco.subway.line.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import wooteco.subway.exception.DuplicatedLineNameException;
import wooteco.subway.exception.VoidLineException;
import wooteco.subway.line.Line;

class LineDaoH2Test {

    @Autowired
    private LineDaoH2 lineDao;

    @DisplayName("노선 저장")
    @Test
    public void save() {
        //given
        Line line = new Line("10호선", "붉은색");

        //when
        Line requestedLine = lineDao.save(line);

        //then
        assertThat(requestedLine.getName()).isEqualTo(line.getName());
        assertThat(requestedLine.getColor()).isEqualTo(line.getColor());
    }

    @DisplayName("노선 중복 저장 시도")
    @Test
    public void duplicatedSave() {
        //given
        Line line1 = new Line("1호선", "초록색");
        Line line2 = new Line("2호선", "파란색");

        //then
        assertThatThrownBy(() -> lineDao.save(line1))
            .isInstanceOf(DuplicatedLineNameException.class);

        assertThatThrownBy(() -> lineDao.save(line2))
            .isInstanceOf(DuplicatedLineNameException.class);
    }

    @DisplayName("id값에 맞는 노선 반환")
    @Test
    public void findLine() {
        //given
        Line line1 = new Line("12호선", "분홍색");
        Line saveLine = lineDao.save(line1);
        long id = saveLine.getId();

        //when
        Line requestedLine = lineDao.findOne(id);

        //then
        assertThat(requestedLine.getId()).isEqualTo(id);
        assertThat(requestedLine.getName()).isEqualTo(line1.getName());
        assertThat(requestedLine.getColor()).isEqualTo(line1.getColor());
    }

    @DisplayName("존재하지 않는 id 값을 가진 노선 반환 시도")
    @Test
    void findLineVoidId() {
        //given

        //when
        long id = -1;

        //then
        assertThatThrownBy(() -> lineDao.findOne(id))
            .isInstanceOf(VoidLineException.class);
    }

    @DisplayName("전 노선 호출")
    @Test
    void findAll() {
        //given
        Line line = new Line("10호선", "붉은색");
        lineDao.save(line);

        //when
        List<Line> lines = lineDao.findAll();

        //then
        assertThat(lines.get(0)).isEqualTo(line);
    }

    @DisplayName("노선 업데이트")
    @Test
    void update() {
        //given
        Line line1 = new Line("11호선", "보라색");
        Line saveLine = lineDao.save(line1);
        long id = saveLine.getId();
        String requestName = "분당선";
        String requestColor = "노란색";
        Line requestLine = new Line(requestName, requestColor);

        //when
        lineDao.update((int) id, requestLine);
        Line responseLine = lineDao.findOne(id);

        //then
        assertThat(responseLine.getName()).isEqualTo(requestName);
        assertThat(responseLine.getColor()).isEqualTo(requestColor);
    }

    @DisplayName("노선 삭제")
    @Test
    void remove() {
        //given
        Line line1 = new Line("12호선", "분홍색");
        Line saveLine = lineDao.save(line1);
        long id = saveLine.getId();

        //when
        lineDao.delete(id);

        //then
        assertThatThrownBy(() -> lineDao.findOne(id))
            .isInstanceOf(VoidLineException.class);
    }

    @DisplayName("노선 삭제 실패")
    @Test
    void removeFail() {
        //given
        long id = -1;

        //when

        //then
        assertThatThrownBy(() -> lineDao.delete(id))
            .isInstanceOf(VoidLineException.class);
    }
}