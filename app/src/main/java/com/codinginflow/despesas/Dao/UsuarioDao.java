package com.codinginflow.despesas.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.codinginflow.despesas.Model.Usuario;

import java.util.List;

@Dao
public interface UsuarioDao {
    @Insert
    void insert(Usuario usuario);

    @Update
    void update(Usuario usuario);

    @Delete
    void delete(Usuario usuario);

    @Query("Delete From usuario_table")
    void deleteAllUsuario();

    @Query("Select * From usuario_table Order By nome Asc")
    LiveData<List<Usuario>> getAllUsuarios();
}
