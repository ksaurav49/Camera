package academy.learnprogramming.pooling;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import academy.learnprogramming.pooling.ApiUrl.ApiInterface;
import academy.learnprogramming.pooling.ApiUrl.ApiUrl;
import academy.learnprogramming.pooling.response.PeopleResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class search extends AppCompatActivity  implements TextWatcher{

    AutoCompleteTextView myAutoComplete;
    ArrayList<String> mylist = new ArrayList<String>();
    Button b1;
    String sa;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        toolbar = findViewById(R.id.toolbar);
        b1 = findViewById(R.id.b1);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("सिविल लाइन वार्ड ");
        getSupportActionBar().setElevation((float) 10.0);
        //toolbar.setSubtitle("Welcome");
        toolbar.setLogo(R.drawable.kamal);


        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(ApiUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiService=retrofit.create(ApiInterface.class);

        Call<List<PeopleResponse>> call=apiService.people();
        call.enqueue(new Callback<List<PeopleResponse>>() {
            @Override
            public void onResponse(Call<List<PeopleResponse>> call, Response<List<PeopleResponse>> response) {
                try {


                    List<PeopleResponse> heros = response.body();
                    int i;
                    for (PeopleResponse h : heros) {
                        mylist.add(h.getName());

                    }
                }catch (Exception e){

                }
            }

            @Override
            public void onFailure(Call<List<PeopleResponse>> call, Throwable t) {

            }
        });



            myAutoComplete = (AutoCompleteTextView) findViewById(R.id.myautocomplete);

            myAutoComplete.addTextChangedListener(this);
            myAutoComplete.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, mylist));
            b1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String s2 = myAutoComplete.getText().toString();
                    Intent intent = new Intent(getBaseContext(), detail.class);
                    intent.putExtra("name", s2);
                    startActivity(intent);

                }
            });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String msg=" ";
        switch (item.getItemId()){

            case R.id.products:

        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
