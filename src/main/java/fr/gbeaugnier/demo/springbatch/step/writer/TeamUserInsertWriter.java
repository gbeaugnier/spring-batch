package fr.gbeaugnier.demo.springbatch.step.writer;

import fr.gbeaugnier.demo.springbatch.utils.SqlUtils;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class TeamUserInsertWriter extends AbstractTeamUserWriter {

    private final static String sql = SqlUtils.getSQLFileAsString("sql/insert_team_user.sql");

    public TeamUserInsertWriter(DataSource dataSource) {
        super(dataSource, sql);
    }

}
