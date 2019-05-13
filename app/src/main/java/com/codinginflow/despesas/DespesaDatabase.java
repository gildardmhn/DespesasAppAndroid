package com.codinginflow.despesas;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Despesa.class, Estabelecimento.class, Usuario.class}, version = 1, exportSchema = false)
public abstract class DespesaDatabase extends RoomDatabase {

    private static DespesaDatabase instanceDatabase;

    public abstract DespesaDao despesaDao();
    public abstract EstabelecimentoDao estabelecimentoDao();
    public abstract UsuarioDao usuarioDao();

    public static synchronized DespesaDatabase getInstance(Context context){
        if (instanceDatabase == null){
            instanceDatabase = Room.databaseBuilder(context.getApplicationContext(),
                    DespesaDatabase.class, "despesa_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instanceDatabase;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instanceDatabase).execute();
        }
    };


    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {

        private DespesaDao despesaDao;
        private EstabelecimentoDao estabelecimentoDao;

        private PopulateDbAsyncTask(DespesaDatabase db){
            despesaDao = db.despesaDao();
            estabelecimentoDao = db.estabelecimentoDao();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            despesaDao.insert(new Despesa("Internet", "Internet da Brisa", "Fixa", 85.5 ));
            despesaDao.insert(new Despesa("Energia", "Fatura da Enel", "Nao Fixa", 83.2 ));
            despesaDao.insert(new Despesa("Gás", "Brasil Gas", "Fixa", 70.5 ));
            despesaDao.insert(new Despesa("Agua", "Fatura da Enel", "Nao Fixa", 70.4 ));

            estabelecimentoDao.insert(new Estabelecimento("Gago", "Rua rodrigues juniro", "222222"));
            estabelecimentoDao.insert(new Estabelecimento("Agua", "Praça do leao", "33333"));
            estabelecimentoDao.insert(new Estabelecimento("JHJKHKJhk", "praça do chalé", "22555"));

            return null;
        }
    }


}
