package fr.gbeaugnier.demo.springbatch.component.step.writer;

import fr.gbeaugnier.demo.springbatch.business.persistance.TeamUserEntity;
import jakarta.annotation.PostConstruct;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;

import javax.sql.DataSource;

public class AbstractTeamUserWriter extends JdbcBatchItemWriter<TeamUserEntity> {

    private final DataSource dataSource;

    public AbstractTeamUserWriter(DataSource dataSource, String sql) {
        this.dataSource = dataSource;
        setSql(sql);
        setItemSqlParameterSourceProvider(BeanPropertySqlParameterSource::new);
    }

    @PostConstruct
    public void init() {
        setDataSource(dataSource);
        afterPropertiesSet();
    }

}
