package com.grofers.dao;

import com.grofers.entity.Contest;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Dao class for Contest table DB related queries
 */
@Transactional
@Repository
public class ContestDao {

    @PersistenceContext
    private EntityManager entityManager;

    public Contest findContest(String contestName) {
        return entityManager.find(Contest.class, contestName);
    }

    /**
     * gets the upcoming contest for 1 week
     *
     * @return the Map of Contest EndTime and their prizes
     */
    public Map<String, String> upComingContests() {
        Timestamp currentTime = Timestamp.from(Instant.now());
        Timestamp endTime = new Timestamp(currentTime.getTime() + 7 * 24 * 60 * 60 * 1000);
        String hql = String.format("SELECT * FROM Contest WHERE END_TIME BETWEEN '%s' AND '%s'",
                currentTime, endTime);
        return (Map<String, String>) entityManager.
                createNativeQuery(hql, Contest.class).getResultList().stream().collect(Collectors.
                toMap(Contest::getEndTime, Contest::getContestPrize));
    }

    /**
     * to find the winners of the last week contests
     *
     * @return map of contest name with their winners.
     */
    public Map<String, String> lastWeekWinners() {
        Timestamp endTime = Timestamp.from(Instant.now());
        Timestamp startTime = new Timestamp(endTime.getTime() - 7 * 24 * 60 * 60 * 1000);
        String hql = String.format("SELECT * FROM Contest WHERE END_TIME BETWEEN '%s' AND '%s'",
                startTime, endTime);
        return (Map<String, String>) entityManager.createNativeQuery(hql, Contest.class).getResultList().
                stream().collect(Collectors.
                toMap(Contest::getContestName, Contest::getWinner));
    }

    /**
     * to find the list of contests ending till time
     *
     * @param time the endTime
     * @return the list of
     */
    public List<Contest> findContestsEnding(Timestamp time) {
        String hql = String.format("SELECT * FROM Contest WHERE END_TIME < '%s'", time);
        return entityManager.createNativeQuery(hql, Contest.class).getResultList();
    }

    /**
     * to update the name of the winner
     *
     * @param contestName the name of the contesr
     * @param winner      the name of the winner
     */
    public void updateWinner(String contestName, String winner) {
        String hql = String.format("UPDATE Contest SET WINNER = '%s' WHERE CONTEST_NAME = '%s'", winner, contestName);
        entityManager.createNativeQuery(hql, Contest.class).executeUpdate();
    }

}
