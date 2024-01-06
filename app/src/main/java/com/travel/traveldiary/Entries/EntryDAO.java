package com.travel.traveldiary.Entries;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface EntryDAO {
    @Query("select * from Entry")
    List<EntryModel> getAllEntries();

    @Insert
    void addEntry(EntryModel entryModel);

    @Update
    void updateEntry(EntryModel entryModel);

    @Delete
    void deleteEntry(EntryModel entryModel);

    @Query("select * from Entry where id = :Id")
    EntryModel getEntry(int Id);

    @Query("SELECT * FROM Entry WHERE location LIKE :searchQuery")
    List<EntryModel> getEntriesByLocation(String searchQuery);
}
