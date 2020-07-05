package gnz.julaa.kanou.ventes;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a11.sabou.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import gnz.julaa.kanou.imobillier.CardAdapter;
import gnz.julaa.kanou.imobillier.ImmoInfos;
import gnz.julaa.kanou.managment.Filtre;

public class Vente extends AppCompatActivity {

    RecyclerView recyclerView;
    public Spinner spinner;
    public static  String  GET_SPINER_TEXT;
    private DatabaseReference ref;
    private FirebaseAuth auth;
    List<ImmoInfos> venteInfoList;
    private Spinner GetCountry;
    private ImageView mView;
    private TextView txtTel, txtV, txtP,dat;
    private ProgressDialog progressDialog;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vente);
        GetCountry=(Spinner)findViewById(R.id.pays);
        recyclerView=(RecyclerView)findViewById(R.id.rv);
        recyclerView.setHasFixedSize(true);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        /*
        mView=(ImageView)findViewById(R.id.imageViewProduit);
        txtV=(TextView)findViewById(R.id.Textville);+/.?n;
        txtTel=(TextView)findViewById(R.id.tel);
        txtP=(TextView)findViewById(R.id.textPrix);
         */
        setSupportActionBar(toolbar);
        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Logind...");
        progressDialog.setMessage("Veuillez patienter");
        progressDialog.show();
        ref= FirebaseDatabase.getInstance().getReference(Produit.DATABASE_PATH_UPLOADS);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        venteInfoList =new ArrayList<>();
       // getData();
       // final FloatingActionButton flot=(FloatingActionButton)findViewById(R.id.serch);
       /* flot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initiatePopupWindow(v);
            }
        });
        */
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Vente.this,Produit.class);
                startActivity(i);
            }
        });
        GetCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (GetCountry.getItemAtPosition(position).toString().equals("---------")){
                    loadData();

                }
                else
                {
                    Bundle b=new Bundle();
                    b.putStringArray("array",new String[]{Produit.DATABASE_PATH_UPLOADS,GetCountry.getItemAtPosition(position).toString()});
                    startActivity(new Intent(Vente.this,Filtre.class).putExtras(b));
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
    private void loadData(){
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                progressDialog.dismiss();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    for (DataSnapshot  dataSnapshot1:postSnapshot.getChildren()){
                        for (DataSnapshot dataSnapshot2:dataSnapshot1.getChildren()){
                            for (DataSnapshot dataSnapshot3:dataSnapshot2.getChildren()){
                                if (dataSnapshot3.getChildrenCount()>1){
                                    ImmoInfos locationInfo=dataSnapshot3.getValue(ImmoInfos.class);
                                    venteInfoList.add(locationInfo);
                                }
                            }
                        }
                    }

                }
                adapter =new CardAdapter(venteInfoList,Vente.this);
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressDialog.dismiss();
                Toast.makeText(Vente.this,"Erreur connexion",Toast.LENGTH_LONG).show();


            }
        });

    }

   /* private void initiatePopupWindow(View v){
        try {
            LayoutInflater layoutInflater=(LayoutInflater)getBaseContext()
                    .getSystemService(LAYOUT_INFLATER_SERVICE);
            final View popupView=layoutInflater.inflate(R.layout.content_detail,null);
            final PopupWindow popupWindow=new PopupWindow(popupView, ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
            spinner=(Spinner)popupView.findViewById(R.id.spiner);
            ArrayAdapter<String> adapter =
                    new ArrayAdapter<String>(Vente.this,
                            android.R.layout.simple_spinner_item, Elt_Cherch);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    GET_SPINER_TEXT= spinner.getItemAtPosition(position).toString();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            Button close=(Button)popupView.findViewById(R.id.research);
            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getDat();
                    popupWindow.dismiss();

                }
            });

            popupWindow.showAtLocation(v, Gravity.RIGHT,0,0);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }



    private void getData(){
        final ProgressDialog loading = ProgressDialog.show(this,"Loading Data", "Please wait...",false,false);
        //Creating a json array request
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Config.urlAddress,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //Dismissing progress dialog
                        loading.dismiss();

                        //calling method to parse json array
                        parseData(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Vente.this,"Check your network connexion",Toast.LENGTH_LONG).show();
                        loading.dismiss();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to the queue
        requestQueue.add(jsonArrayRequest);
    }
    private void parseData(JSONArray array){

        for(int i = 0; i<array.length(); i++){
            PriorityInfo venteInfo=new PriorityInfo();
            JSONObject json = null;
            try {
                json = array.getJSONObject(i);
                venteInfo.setImage0(json.getString(Config.TAG_IMAGE_URL));
                venteInfo.setImage2(json.getString(Config.TAG_IMAGE_URL2));
                venteInfo.setImage3(json.getString(Config.TAG_IMAGE_URL3));
                venteInfo.setLib(json.getString(Config.TAG_LIB));
                venteInfo.setType(json.getString(Config.TAG_TYPE));
                venteInfo.setPrix(json.getString(Config.TAG_PRIX));
                venteInfo.setTel(json.getString(Config.TAG_TEL));
                venteInfo.setVille(json.getString(Config.TAG_VILLE));
                venteInfo.setDate(json.getString(Config.TAG_DATE));

            } catch (JSONException e) {
                e.printStackTrace();
            }
            venteInfoList.add(venteInfo);

        }
        adapter=new CardAdapter(venteInfoList,this);
        recyclerView.setAdapter(adapter);

    }
     */





}
