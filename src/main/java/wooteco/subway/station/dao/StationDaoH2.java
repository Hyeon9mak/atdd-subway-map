package wooteco.subway.station.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import wooteco.subway.station.Station;

@Repository
public class StationDaoH2 implements StationDao{

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;
    private final RowMapper<Station> rowMapper;

    public StationDaoH2(final JdbcTemplate jdbcTemplate, final DataSource source) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(source)
            .withTableName("STATION")
            .usingGeneratedKeyColumns("id");
        this.rowMapper = (rs, rowNum) -> {
            final Long foundId = rs.getLong("id");
            final String name = rs.getString("name");
            return new Station(foundId, name);
        };
    }

    @Override
    public Station save(Station station) {
        Map<String, String> params = new HashMap<>();
        params.put("name", station.getName());

        long key = jdbcInsert.executeAndReturnKey(params).longValue();

        return new Station(key, station.getName());
    }

    @Override
    public List<Station> findAll() {
        String statement = "SELECT * FROM STAT";
        return jdbcTemplate.query(statement, rowMapper);
    }

    @Override
    public void delete(long id) {
        String statement = "DELETE FROM station WHERE id = ?";
        jdbcTemplate.update(statement, id);
    }
}
