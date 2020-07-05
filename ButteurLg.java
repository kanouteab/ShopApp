package gnz.julaa.kanou;

import android.app.ProgressDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.a11.sabou.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ButteurLg extends AppCompatActivity {
    RecyclerView.Adapter adapter;
    List<ButteurInfos> infosList;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerView;
    //static int T;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_butteur_lg);
        recyclerView=(RecyclerView)findViewById(R.id.recycle);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        infosList=new ArrayList<>();
        getData();
        /*TextView Time=(TextView)findViewById(R.id.time);
        temp(150);
        if (T<10){
            Time.setText("0"+T);
        }
        else {
            Time.setText(""+T);
        }
        String time = "3:30 PM";

        SimpleDateFormat date12Format = new SimpleDateFormat("hh:mm a");

        SimpleDateFormat date24Format = new SimpleDateFormat("HH:mm");
        try {
            Toast.makeText(this,""+date24Format.format(date12Format.parse(time)),Toast.LENGTH_LONG).show();
        } catch (ParseException e) {
            e.printStackTrace();
        }


    }
    public void temp(int t){
        Calendar now=Calendar.getInstance();
        int min=now.get(Calendar.MINUTE) + now.get(Calendar.HOUR_OF_DAY)*60;
        T=min - t;
    */
    }
    public void getData(){
        final ProgressDialog loading=ProgressDialog.show(this,"Looding data","Plaise wait",false,false);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(ConfigB.URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                //Dismissing progress dialog
                loading.dismiss();
                //calling method to parse json array
                parseData(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ButteurLg.this,"Check your network connexion",Toast.LENGTH_LONG).show();
                loading.dismiss();

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to the queue
        requestQueue.add(jsonArrayRequest);
    }
    private void parseData(JSONArray reponse){
        for (int i=0;i<reponse.length();i++){
            ButteurInfos info=new ButteurInfos();
            JSONObject json=null;
            try {
                json=reponse.getJSONObject(i);
                info.setNom(json.getString(ConfigB.TAG_NOM));
                info.setBut(json.getString(ConfigB.TAG_BM));
                info.setEq(json.getString(ConfigB.TAG_EQ));
                info.setProtrait(json.getString(ConfigB.TAG_PORT));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            infosList.add(info);
            adapter=new CardAdapterBut(infosList,this);
            recyclerView.setAdapter(adapter);

        }
    }
}
