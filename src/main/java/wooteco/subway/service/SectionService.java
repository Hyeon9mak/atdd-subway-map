package wooteco.subway.service;

import java.util.List;
import javax.validation.Valid;
import org.springframework.stereotype.Service;
import wooteco.subway.dao.SectionDao;
import wooteco.subway.domain.Section;
import wooteco.subway.exception.section.InvalidSectionOnLineException;
import wooteco.subway.service.dto.DeleteSectionServiceDto;
import wooteco.subway.service.dto.SectionServiceDto;

@Service
public class SectionService {

    private final SectionDao sectionDao;

    public SectionService(final SectionDao sectionDao) {
        this.sectionDao = sectionDao;
    }

    public SectionServiceDto save(@Valid final SectionServiceDto dto) {
        Section section = new Section(dto.getLineId(), dto.getUpStationId(), dto.getDownStationId(),
            dto.getDistance());
        List<Section> sections = sectionDao.findSectionsByLineId(section.getLineId());
        checkAvailableSaveSectionOnLine(section, sections);
        updateMidIntervalSection(section, sections);
        Section saveSection = sectionDao.save(section);

        return SectionServiceDto.from(saveSection);
    }

    private void checkAvailableSaveSectionOnLine(final Section section, final List<Section> sections) {
        if (existedUpStation(section, sections) == existedDownStation(section, sections)) {
            throw new InvalidSectionOnLineException();
        }
    }

    private boolean existedUpStation(Section section, List<Section> sections) {
        return sections.stream()
            .map(Section::getUpStationId)
            .anyMatch(
                id -> id.equals(section.getUpStationId()) ^ id.equals(section.getDownStationId()));
    }

    private boolean existedDownStation(Section section, List<Section> sections) {
        return sections.stream()
            .map(Section::getDownStationId)
            .anyMatch(
                id -> id.equals(section.getUpStationId()) ^ id.equals(section.getDownStationId()));
    }

    private void updateMidIntervalSection(Section section, List<Section> sections) {
        Section upStationSection = sections.stream()
            .filter(element-> element.getUpStationId().equals(section.getUpStationId()))
            .findAny()
            .orElse(null);
        if (upStationSection != null) {
            if (upStationSection.getDistance() <= section.getDistance()) {
                throw new InvalidSectionOnLineException();
            }
            Section newSection = new Section(upStationSection.getId(), upStationSection.getLineId(),
                section.getDownStationId(), upStationSection.getDownStationId(),
                upStationSection.getDistance() - section.getDistance());
            sectionDao.update(newSection);
            return;
        }

        Section downStationSection = sections.stream()
            .filter(element-> element.getDownStationId().equals(section.getDownStationId()))
            .findAny()
            .orElse(null);
        if (downStationSection != null) {
            if (downStationSection.getDistance() <= section.getDistance()) {
                throw new InvalidSectionOnLineException();
            }
            Section newSection = new Section(downStationSection.getId(), downStationSection.getLineId(),
                downStationSection.getUpStationId(), section.getUpStationId(),
                downStationSection.getDistance() - section.getDistance());
            sectionDao.update(newSection);
            return;
        }
    }

    public void delete(final DeleteSectionServiceDto deleteSectionServiceDto) {
//        sectionDao.deleteSectionById
    }
}

