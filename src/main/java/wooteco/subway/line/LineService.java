package wooteco.subway.line;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import wooteco.subway.line.dao.LineDao;
import wooteco.subway.line.dto.LineDto;

@Service
public class LineService {

    private final LineDao lineDao;

    public LineService(LineDao lineDao) {
        this.lineDao = lineDao;
    }

    public LineDto createLine(LineDto lineDto) {
        Line line = new Line(lineDto.getName(), lineDto.getColor());
        Line saveLine = lineDao.save(line);
        return new LineDto(saveLine.getId(), saveLine.getName(), saveLine.getColor());
    }

    public List<LineDto> findAll() {
        return lineDao.findAll()
            .stream()
            .map(it -> new LineDto(it.getId(), it.getName(), it.getColor()))
            .collect(Collectors.toList());
    }

    public LineDto findOne(LineDto lineDto) {
        Line line = lineDao.findOne(lineDto.getId());
        return new LineDto(line.getId(), line.getName(), line.getColor());
    }

    public void update(LineDto lineDto) {
        Line line = new Line(lineDto.getName(), lineDto.getColor());
        lineDao.update(lineDto.getId(), line);
    }

    public void delete(LineDto lineDto) {
        lineDao.delete(lineDto.getId());
    }
}
