package com.codinginflow.despesas;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface EstabelecimentoDao {

    @Insert
    public void insert(Estabelecimento estabelecimento);

    @Update
    public void update(Estabelecimento estabelecimento);

    @Delete
    public void delete(Estabelecimento estabelecimento);

    @Query("Select * From estabelecimento_table")
    LiveData<List<Estabelecimento>> getAllEstabelecimentos();

    @Query("Delete From estabelecimento_table")
    public void deleteAllEstabelecimentos();
}
