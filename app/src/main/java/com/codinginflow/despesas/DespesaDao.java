package com.codinginflow.despesas;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DespesaDao {

    @Insert
    void insert(Despesa despesa);

    @Update
    void update(Despesa despesa);

    @Delete
    void delete(Despesa despesa);

    @Query("Delete From despesa_table")
    void deleteAllDespesas();

    @Query("Select * From despesa_table Order By titulo Asc")
    LiveData<List<Despesa>> getAllDespesas();
}
