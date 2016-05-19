package prv.mark.prv.mark.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import prv.mark.domain.Soldier;
import prv.mark.util.AppStringUtils;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Spring Row mapper example.
 * <p>
 * The RowMapper implementation uses a JDBC {@link ResultSet} to map the results.
 * </p>
 * @author mlglenn
 */
public class ExampleRowMapper implements RowMapper<Soldier> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExampleRowMapper.class);

    /**
     * Maps a row from the XXXXXXXXXXXXXX table to @{link Soldier}.
     *
     * @param resultSet @{link ResultSet}
     * @param rowNumber integer
     * @return @{link BatteryOrderDto}
     * @throws SQLException
     */
    @Override
    public final Soldier mapRow(final ResultSet resultSet, final int rowNumber) throws SQLException {
        Soldier soldier = new Soldier();
        soldier.setName(AppStringUtils.getSafeString(resultSet.getString("column_name"))); //TODO set column name
        soldier.setRank(AppStringUtils.getSafeString(resultSet.getString("column_name"))); //TODO set column name
        soldier.setSerialNumber(AppStringUtils.getSafeString(resultSet.getString("column_name"))); //TODO set column name
        return soldier;
    }
}
