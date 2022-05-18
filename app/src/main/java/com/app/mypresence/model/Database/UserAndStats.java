package com.app.mypresence.model.Database;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Relation;

import java.util.List;

@Entity
public class UserAndStats {
    @Embedded public User user;

    @Relation(
            parentColumn = "userId",
            entityColumn = "userOwnerOfStat"
    )

    public List<DateInfo> stats;
}
