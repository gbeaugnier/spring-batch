package fr.gbeaugnier.demo.springbatch.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BatchName {

    INSERT_TEAM_USER_FLOW ("insertTeamUserFlow"),
    UPDATE_TEAM_USER_FLOW ("updateTeamUserFlow"),
    PARALLEL_FLOWS ("parallelFlows"),
    UPDATE_TEAM_USER ("updateTeamUser"),
    DOWNLOAD_FILE_TO_TEMPORARY_FILE ("downloadFileToTemporaryFile"),
    DOWNLOAD_FILE_FLOW ("downloadFileFlow"),
    INSERT_TEAM_USER_STEP ("insertTeamUserStep"),
    UPDATE_TEAM_USER_STEP ("updateTeamUserStep"),
    DELETE_TEMPORARY_FILE_TASKLET ("deleteTemporaryFileTasklet"),
    READER_NAME ("TeamUserItemReader");
    
    private final String name;

}
