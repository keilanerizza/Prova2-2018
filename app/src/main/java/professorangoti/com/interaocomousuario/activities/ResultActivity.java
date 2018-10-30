package professorangoti.com.interaocomousuario.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import professorangoti.com.interaocomousuario.R;
import professorangoti.com.interaocomousuario.activities.Retrofit.ApiEndpoint;
import professorangoti.com.interaocomousuario.activities.dominio.Conta;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ResultActivity extends AppCompatActivity implements View.OnKeyListener {

    private TextView entrada;
    private TextView saida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preparacaoInicial();

    }

    private void preparacaoInicial() {

        saida = (TextView) findViewById(R.id.valor_pedido);
        saida.setOnKeyListener(this);
    }

    @Override
    public boolean onKey(View view, int i, KeyEvent event) {
        if (i == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {
            Call<Conta> call = getPostCall();

            call.enqueue(new Callback<Conta>() { //chamada assíncrona

                public void onResponse(Call<Conta> call, Response<Conta> response) {
                    int statusCode = response.code();
                    Conta conta = response.body();

                    saida.setText(conta.valorConta());
                }

                public void onFailure(Call<Conta> call, Throwable t) {
                    // Log error here since request failed
                    Log.i("teste", t.toString());
                }
            });
            return true;
        }
        return false;
    }

    private Call<Conta> getPostCall() {
        Gson gson = new GsonBuilder()

                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        Retrofit retrofit = new Retrofit.Builder()

                .baseUrl("http://provaddm2018.atwebpages.com")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        ApiEndpoint apiService = retrofit.create(ApiEndpoint.class);
        return apiService.obterConta();
    }
}