package com.codinginflow.despesas.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.codinginflow.despesas.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class AddEditDespesaActivity extends AppCompatActivity {

    public static final String EXTRA_HASH = "com.codinginflow.despesas.EXTRA_HASH";
    public static final String EXTRA_TITULO = "com.codinginflow.despesas.EXTRA_TITULO";
    public static final String EXTRA_DESCRICAO = "com.codinginflow.despesas.EXTRA_DESCRICAO";
    public static final String EXTRA_TIPO = "com.codinginflow.despesas.EXTRA_TIPO";
    public static final String EXTRA_PRECO = "com.codinginflow.despesas.EXTRA_PRECO";


    private static final int PICK_IMAGE_REQUEST = 234;

    private EditText editTextTitulo;
    private EditText editTextDescricao;
    private EditText editTextTipo;
    private EditText editTextPreco;

    // Camera e fb storage
    private Button buttonAnexo;
    private Button buttonUpluoad;
    private Uri filePath;

    private ImageView imageAnexo;
    private String pathToFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_despesa);

        editTextTitulo = (EditText) findViewById(R.id.edit_text_titulo);
        editTextDescricao = (EditText) findViewById(R.id.edit_text_descricao);
        editTextTipo = (EditText) findViewById(R.id.edit_text_tipo);
        editTextPreco = (EditText) findViewById(R.id.edit_text_preco);

        imageAnexo = (ImageView) findViewById(R.id.image_anexo);
        buttonAnexo = (Button) findViewById(R.id.btn_anexo);

        if (Build.VERSION.SDK_INT >= 23) {
            requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
        }

        buttonAnexo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diparePictureAction();
            }
        });

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        Intent intent = getIntent();

        if (intent.hasExtra(EXTRA_HASH)) {
            setTitle("Editar Despesa");
            editTextTitulo.setText(intent.getStringExtra(EXTRA_TITULO));
            editTextDescricao.setText(intent.getStringExtra(EXTRA_DESCRICAO));
            editTextTipo.setText(intent.getStringExtra(EXTRA_TIPO));
            editTextPreco.setText(intent.getStringExtra(EXTRA_PRECO));
        } else {
            setTitle("Cadastrar Despesa");
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                Bitmap bitmap = BitmapFactory.decodeFile(pathToFile);
                imageAnexo.setImageBitmap(bitmap);
            }
        }

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK != null && data.getData() != null) {
            filePath = data.getData();
        }
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Selecione uma imagem"), PICK_IMAGE_REQUEST);
    }

    private void diparePictureAction() {
        Intent takePic = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePic.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            photoFile = createPhotoFile();

            if (photoFile != null) {
                pathToFile = photoFile.getAbsolutePath();
                Uri photoUri = FileProvider.getUriForFile(AddEditDespesaActivity.this, "com.codinginflow.despesas.fileprovider", photoFile);
                takePic.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(takePic, 1);
            }
        }
    }

    private File createPhotoFile() {
//      String name = new SimpleDateFormat("ddMMyyyy").format(new Date());
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = null;
        try {
            image = File.createTempFile(editTextTitulo.getText().toString().trim(), ".jpg", storageDir);
        } catch (IOException e) {
            System.out.println("Deu ruim ao salvar imagem...");
        }
        return image;
    }

    private void delteDespesa() {
        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_HASH)) {
            String hash = intent.getStringExtra(EXTRA_HASH);
            Intent deleteHash = new Intent();
            deleteHash.putExtra(EXTRA_HASH, hash);
            setResult(RESULT_OK, deleteHash);
            finish();
        } else {
            Toast.makeText(this, "Não pode remover uma despesa inexistante", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveDespesa() {
        String titulo = editTextTitulo.getText().toString();
        String descricao = editTextDescricao.getText().toString();
        String tipo = editTextTipo.getText().toString();
        Double preco = Double.parseDouble(editTextPreco.getText().toString().replace(',', '.'));

        if (titulo.trim().isEmpty() || descricao.trim().isEmpty() || tipo.trim().isEmpty() || preco.isNaN()) {
            Toast.makeText(this, "Preencha as informações", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_TITULO, titulo);
        data.putExtra(EXTRA_DESCRICAO, descricao);
        data.putExtra(EXTRA_TIPO, tipo);
        data.putExtra(EXTRA_PRECO, preco);

        String hash = getIntent().getStringExtra(EXTRA_HASH);
        if (hash != null) {
            data.putExtra(EXTRA_HASH, hash);
        }

        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_despesa_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_despesa:
                saveDespesa();
                return true;

            case R.id.delete_despesa:
                delteDespesa();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

}
