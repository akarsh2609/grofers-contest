package com.grofers.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * Entity for Contest
 */
@Entity
@Table(name = "contest")
public class Contest implements Serializable {

    @Id
    @Column(name = "contest_name")
    private String contestName;

    @Column(name = "contest_prize")
    private String contestPrize;

    @Column(name = "winner", nullable = true)
    private String winner;

    @Column(name = "start_time", nullable = true)
    private Date startTime;

    @Column(name = "end_time")
    private Date endTime;

    public String getContestName() {
        return contestName;
    }

    public void setContestName(String contestName) {
        this.contestName = contestName;
    }

    public String getContestPrize() {
        return contestPrize;
    }

    public void setContestPrize(String contestPrize) {
        this.contestPrize = contestPrize;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

}
