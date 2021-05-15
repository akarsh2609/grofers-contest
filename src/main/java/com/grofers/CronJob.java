package com.grofers;

import com.grofers.dao.ContestDao;
import com.grofers.dao.UsersDao;
import com.grofers.dao.TicketsDao;
import com.grofers.entity.Contest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Objects;

/**
 * Cron Job scheduler for finding the winners for the events ended.
 * This job runs everyday and update the winners in the contest table.
 */
@Configuration
@EnableScheduling
public class CronJob {

    @Autowired
    private ContestDao contestDao;

    @Autowired
    private TicketsDao ticketsDao;

    @Autowired
    private UsersDao usersDao;

    @Scheduled(cron = "5 5 8 * * ?")
    public void cronMethod() {
        Timestamp endTime = Timestamp.from(Instant.now());
        List<Contest> contestList = contestDao.findContestsEnding(endTime);
        for (Contest contest : contestList) {
            String winner = ticketsDao.findWinner(contest.getContestName());
            if (Objects.nonNull(winner)) {
                contestDao.updateWinner(contest.getContestName(), usersDao.getUserNameById(winner));
            }
        }

    }
}
