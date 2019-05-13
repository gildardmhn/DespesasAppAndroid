package com.codinginflow.despesas;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class EstabelecimentoViewModel extends AndroidViewModel {

    private EstabelecimentoRepository estabelecimentoRepository;
    private LiveData<List<Estabelecimento>> allEstabelecimentos;


    public EstabelecimentoViewModel(@NonNull Application application) {
        super(application);
        estabelecimentoRepository = new EstabelecimentoRepository(application);
        allEstabelecimentos = estabelecimentoRepository.getAllEstabelecimentos();
    }

    public void insert(Estabelecimento estabelecimento){
        estabelecimentoRepository.insert(estabelecimento);
    }

    public void update(Estabelecimento estabelecimento){
        estabelecimentoRepository.update(estabelecimento);
    }

    public void delete(Estabelecimento estabelecimento){
        estabelecimentoRepository.delete(estabelecimento);
    }

    public void deleteAll(){
        estabelecimentoRepository.deleteAll();
    }

    public LiveData<List<Estabelecimento>> getAllEstabelecimentos(){
        return allEstabelecimentos;
    }
}
