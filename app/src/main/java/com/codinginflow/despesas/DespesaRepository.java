package com.codinginflow.despesas;


import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class DespesaRepository {

    private DespesaDao despesaDao;
    private LiveData<List<Despesa>> allDespesas;

    public DespesaRepository(Application application){
        DespesaDatabase database = DespesaDatabase.getInstance(application);
        despesaDao = database.despesaDao();
        allDespesas = despesaDao.getAllDespesas();
    }

    public void insert(Despesa despesa){
        new InsertDespesaAsyncTask(despesaDao).execute(despesa);
    }

    public void update(Despesa despesa){
        new UpdateDespesaAsyncTask(despesaDao).execute(despesa);
    }

    public void delete(Despesa despesa){
        new DeleteDespesaAsyncTask(despesaDao).execute(despesa);
    }

    public void deleteAll(){
        new DeleteAllDespesasAsyncTask(despesaDao).execute();
    }

    public LiveData<List<Despesa>> getAllDespesas(){
        return allDespesas;
    }

    private static class InsertDespesaAsyncTask extends AsyncTask<Despesa, Void, Void>{

        private DespesaDao despesaDao;

        private InsertDespesaAsyncTask(DespesaDao despesaDao){
            this.despesaDao = despesaDao;
        }

        @Override
        protected Void doInBackground(Despesa... despesas) {
            despesaDao.insert(despesas[0]);
            return null;
        }
    }

    private static class UpdateDespesaAsyncTask extends AsyncTask<Despesa, Void, Void>{

        private DespesaDao despesaDao;

        private UpdateDespesaAsyncTask(DespesaDao despesaDao){
            this.despesaDao = despesaDao;
        }

        @Override
        protected Void doInBackground(Despesa... despesas) {
            despesaDao.update(despesas[0]);
            return null;
        }
    }

    private static class DeleteDespesaAsyncTask extends AsyncTask<Despesa, Void, Void>{

        private DespesaDao despesaDao;

        private DeleteDespesaAsyncTask(DespesaDao despesaDao){
            this.despesaDao = despesaDao;
        }

        @Override
        protected Void doInBackground(Despesa... despesas) {
            despesaDao.delete(despesas[0]);
            return null;
        }
    }

    private static class DeleteAllDespesasAsyncTask extends AsyncTask<Void, Void, Void>{

        private DespesaDao despesaDao;

        private DeleteAllDespesasAsyncTask(DespesaDao despesaDao){
            this.despesaDao = despesaDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            despesaDao.deleteAllDespesas();
            return null;
        }
    }
}
