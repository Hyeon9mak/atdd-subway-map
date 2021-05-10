package wooteco.subway.dao;

import java.util.List;
import org.springframework.stereotype.Repository;
import wooteco.subway.domain.Section;

@Repository
public class SectionDao {

    public Section save(final Section section) {
        return null;
    }

    public int update(final Section section) {
        return 0;
    }

    public List<Section> findSectionsByLineId(final Long lineId) {
        return null;
    }

    public int delete(final Section section) {
        return 0;
    }
}
