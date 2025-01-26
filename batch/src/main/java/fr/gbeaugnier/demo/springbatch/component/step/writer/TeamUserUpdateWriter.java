package fr.gbeaugnier.demo.springbatch.component.step.writer;

import fr.gbeaugnier.demo.springbatch.utils.SqlUtils;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class TeamUserUpdateWriter extends AbstractTeamUserWriter {

    private final static String sql = SqlUtils.getSQLFileAsString("sql/update_team_user.sql");

    public TeamUserUpdateWriter(DataSource dataSource) {
        super(dataSource, sql);
    }

}
