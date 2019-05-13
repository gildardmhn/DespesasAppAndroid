package com.codinginflow.despesas;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class DespesaViewModel extends AndroidViewModel {


    private DespesaRepository despesaRepository;
    private LiveData<List<Despesa>> allDespesas;


    public DespesaViewModel(@NonNull Application application) {
        super(application);
        despesaRepository = new DespesaRepository(application);
        allDespesas = despesaRepository.getAllDespesas();
    }

    public void insert(Despesa despesa){
        despesaRepository.insert(despesa);
    }

    public void update(Despesa despesa){
        despesaRepository.update(despesa);
    }

    public void delete(Despesa despesa){
        despesaRepository.delete(despesa);
    }

    public void deleteAllDespesas(){
        despesaRepository.deleteAll();
    }

    public LiveData<List<Despesa>> getAllDespesas() {
        return allDespesas;
    }
}
