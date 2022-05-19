package com.app.mypresence.model.database;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Relation;

import com.app.mypresence.model.database.user.User;

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
