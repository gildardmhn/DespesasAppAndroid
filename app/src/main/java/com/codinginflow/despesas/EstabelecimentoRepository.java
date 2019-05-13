package com.codinginflow.despesas;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class EstabelecimentoRepository {

    private EstabelecimentoDao estabelecimentoDao;
    private LiveData<List<Estabelecimento>> allEstabelecimentos;

    public EstabelecimentoRepository(Application application){
        DespesaDatabase database = DespesaDatabase.getInstance(application);
        estabelecimentoDao = database.estabelecimentoDao();
        allEstabelecimentos = estabelecimentoDao.getAllEstabelecimentos();
    }

    public void insert(Estabelecimento estabelecimento){
        new InsertEstabelecimentoAsyncTask(estabelecimentoDao).execute(estabelecimento);
    }

    public void update(Estabelecimento estabelecimento){
        new UpdateEstabelecimentoAsyncTask(estabelecimentoDao).execute(estabelecimento);
    }

    public void delete(Estabelecimento estabelecimento){
        new DeletetEstabelecimentoAsyncTask(estabelecimentoDao).execute(estabelecimento);
    }

    public void deleteAll(){
        new DeleteAllEstabelecimentosAsyncTask(estabelecimentoDao).execute();
    }

    public LiveData<List<Estabelecimento>> getAllEstabelecimentos() {
        return allEstabelecimentos;
    }

    private static class InsertEstabelecimentoAsyncTask extends AsyncTask<Estabelecimento, Void, Void>{

        private EstabelecimentoDao estabelecimentoDao;

        private InsertEstabelecimentoAsyncTask(EstabelecimentoDao estabelecimentoDao){
            this.estabelecimentoDao = estabelecimentoDao;
        }

        @Override
        protected Void doInBackground(Estabelecimento... estabelecimentos){
            estabelecimentoDao.insert(estabelecimentos[0]);
            return null;
        }
    }

    private static class UpdateEstabelecimentoAsyncTask extends AsyncTask<Estabelecimento, Void, Void>{

        private EstabelecimentoDao estabelecimentoDao;

        private UpdateEstabelecimentoAsyncTask(EstabelecimentoDao estabelecimentoDao){
            this.estabelecimentoDao = estabelecimentoDao;
        }

        @Override
        protected Void doInBackground(Estabelecimento... estabelecimentos){
            estabelecimentoDao.update(estabelecimentos[0]);
            return null;
        }
    }

    private static class DeletetEstabelecimentoAsyncTask extends AsyncTask<Estabelecimento, Void, Void>{

        private EstabelecimentoDao estabelecimentoDao;

        private DeletetEstabelecimentoAsyncTask(EstabelecimentoDao estabelecimentoDao){
            this.estabelecimentoDao = estabelecimentoDao;
        }

        @Override
        protected Void doInBackground(Estabelecimento... estabelecimentos){
            estabelecimentoDao.delete(estabelecimentos[0]);
            return null;
        }
    }

    private static class DeleteAllEstabelecimentosAsyncTask extends AsyncTask<Void, Void, Void>{

        private EstabelecimentoDao estabelecimentoDao;

        private DeleteAllEstabelecimentosAsyncTask(EstabelecimentoDao estabelecimentoDao){
            this.estabelecimentoDao = estabelecimentoDao;
        }

        @Override
        protected Void doInBackground(Void... voids){
            estabelecimentoDao.deleteAllEstabelecimentos();
            return null;
        }
    }
}
