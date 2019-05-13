package com.codinginflow.despesas.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.codinginflow.despesas.Model.Usuario;
import com.codinginflow.despesas.Repository.UsuarioRepository;

import java.util.List;

public class UsuarioViewModel extends AndroidViewModel {

    private UsuarioRepository usuarioRepository;
    private LiveData<List<Usuario>> allUsuarios;

    public UsuarioViewModel(@NonNull Application application) {
        super(application);
        usuarioRepository = new UsuarioRepository(application);
        allUsuarios = usuarioRepository.getAllUsuarios();
    }

    public void insert(Usuario usuario){
        usuarioRepository.insert(usuario);
    }

    public void update(Usuario usuario){
        usuarioRepository.update(usuario);
    }

    public void delete(Usuario usuario){
        usuarioRepository.delete(usuario);
    }

    public void deleteAllUsuarios(){
        usuarioRepository.deleteAllUsuarios();
    }

    public LiveData<List<Usuario>> getAllUsuarios(){
        return allUsuarios;
    }
}
