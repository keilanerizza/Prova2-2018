package professorangoti.com.interaocomousuario.activities.Retrofit;

import professorangoti.com.interaocomousuario.activities.dominio.Conta;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
public interface ApiEndpoint {

    @GET("/precos")
    Call<Conta> obterConta();

}



