package gnz.julaa.kanou;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

//import com.example.a11.gzndrader.MyBounceInterpolator;
//import com.example.a11.gzndrader.football.Football;
import com.example.a11.sabou.R;
import gnz.julaa.kanou.arts.Art;
import gnz.julaa.kanou.imobillier.CardAdapter;
import gnz.julaa.kanou.imobillier.ImmoInfos;
import gnz.julaa.kanou.informatique.Informatique;
import gnz.julaa.kanou.location.Location;
//import SabouTech.Authentification.SabouTech.priority.CardAdapter;

//import Saboutec.imobillier.Config;
import gnz.julaa.kanou.imobillier.Immobilier;
import gnz.julaa.kanou.managment.Management;
import gnz.julaa.kanou.meuble.Meuble;
import gnz.julaa.kanou.pertes.Perte;
import gnz.julaa.kanou.priority.Priority_ImageAdapter;
import gnz.julaa.kanou.service.Service;
import gnz.julaa.kanou.telephonie.Telephone;
import gnz.julaa.kanou.ventes.Vente;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button billet,maison,vent,perte,service,loc,foot,devant,cours,prio,emploi;
    private DatabaseReference ref;
    private FirebaseAuth auth;
    private RecyclerView recyclerView;
    private List<ImmoInfos> infosList;
    private RecyclerView.Adapter adapter;
    private ProgressDialog progressDialog;
    //private static final String
    private RecyclerView.LayoutManager layoutManager;
    //private ListView list;
    private Priority_ImageAdapter priority_imageAdapt;
    private ListView listView;
    private String DateStr;
    private ProgressDialog dialog;
    //private Priority_ImageAdapter adapter;
    private DatabaseReference mDatabase;
    public static final String DATABASE_PATH_UPLOADS = "Messages";
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //billet=(Button)findViewById(R.id.billet);
        maison=(Button)findViewById(R.id.loyer);
        vent=(Button)findViewById(R.id.vente);
        loc=(Button)findViewById(R.id.location);
        foot=(Button)findViewById(R.id.foot);
        emploi=(Button)findViewById(R.id.emploi);
        prio=(Button)findViewById(R.id.priori);
        cours=(Button)findViewById(R.id.cours);
        devant=(Button)findViewById(R.id.prio);
       // localiser=(Button)findViewById(R.id.localiser);
        service=(Button)findViewById(R.id.service);
        perte=(Button)findViewById(R.id.annonce);
        recyclerView=(RecyclerView)findViewById(R.id.recycle);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        infosList=new ArrayList<>();
        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Loading");
       // progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setMessage("please wait...");
        progressDialog.show();
        mDatabase = FirebaseDatabase.getInstance().getReference(DATABASE_PATH_UPLOADS);
        //getData();
        SimpleDateFormat timeStampFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        Date GetDate = new Date();
        DateStr = timeStampFormat.format(GetDate);
        auth=FirebaseAuth.getInstance();
        //empl=(Button)findViewById(R.id.emploi);
       // foot=(Button)findViewById(R.id.appel);
       // loc=(Button)findViewById(R.id.location);
        ///passport=(ImageView)findViewById(R.id.passport);
       // billet.setOnClickListener(this);
        vent.setOnClickListener(this);
       // linearLayout=(ConstraintLayout) findViewById(R.id.booking);
        maison.setOnClickListener(this);
        perte.setOnClickListener(this);
        service.setOnClickListener(this);
        loc.setOnClickListener(this);
        cours.setOnClickListener(this);
        foot.setOnClickListener(this);
        prio.setOnClickListener(this);
        emploi.setOnClickListener(this);
        devant.setOnClickListener(this);
        //empl.setOnClickListener(this);
       // foot.setOnClickListener(this);
        final Animation anim= AnimationUtils.loadAnimation(this,R.anim.bouance);
        MyBounceInterpolator myBounceInterpolator=new MyBounceInterpolator(0.9,40);
        anim.setInterpolator(myBounceInterpolator);
        vent.startAnimation(anim);
        maison.startAnimation(anim);
//        billet.startAnimation(anim);
        service.startAnimation(anim);
        perte.startAnimation(anim);
        //loc.startAnimation(anim);
      //  localiser.startAnimation(anim);
        cours.startAnimation(anim);
        foot.startAnimation(anim);
        emploi.startAnimation(anim);
        loc.startAnimation(anim);
        devant.startAnimation(anim);

       // empl.startAnimation(anim);
        //foot.startAnimation(anim);

       /*
        infosList=new ArrayList<>();
        dialog=new ProgressDialog(this);
        listView=(ListView) findViewById(R.id.list_image);
        dialog.setMessage("Patientez s'il vous plait.....");
        dialog.show();
        ref= FirebaseDatabase.getInstance().getReference(Registration.FB_DATABASE_PATH);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dialog.dismiss();
                for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                    PriorityInfo img=snapshot.getValue(PriorityInfo.class);
                    infosList.add(img);

                }
                adapter=new Priority_ImageAdapter(MainActivity.this,R.layout.liste_images,infosList);
               listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this,databaseError.getMessage(),Toast.LENGTH_LONG).show();

            }
        });

    */
       ref=FirebaseDatabase.getInstance().getReference(UpladImages_Activity.DATABASE_PATH_UPLOADS);
       ref.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               progressDialog.dismiss();
               //iterating through all the values in database
               for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                   for (DataSnapshot dataSnapshot1:postSnapshot.getChildren()){
                       for (DataSnapshot dataSnapshot2:dataSnapshot1.getChildren()){
                           for (DataSnapshot dataSnapshot3:dataSnapshot2.getChildren()){
                               if (dataSnapshot3.getChildrenCount()>1){
                                   ImmoInfos locationInfo=dataSnapshot3.getValue(ImmoInfos.class);
                                   infosList.add(locationInfo);
                               }
                           }
                       }
                   }
               }
               adapter=new CardAdapter(infosList,MainActivity.this);
               recyclerView.setAdapter(adapter);
           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {
               Toast.makeText(getApplicationContext(),databaseError.getMessage(),Toast.LENGTH_LONG).show();
               progressDialog.dismiss();

           }
       });

    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
switch (item.getItemId()){
    case R.id.disconect:
        new AlertDialog.Builder(MainActivity.this).setMessage("Deconectez/Disconect?").setPositiveButton("Oui/Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                auth.signOut();
                finish();
                startActivity(new Intent(MainActivity.this,Authification.class));

            }
        }).setNegativeButton("Non/No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }).create().show();
        break;
        case R.id.gerer:
        startActivity(new Intent(MainActivity.this,Management.class));
        break;
    case R.id.contact:
        LayoutInflater layoutInflater=getLayoutInflater();
        final View popup=layoutInflater.inflate(R.layout.messa_layout,null);
        final EditText textView=(EditText) popup.findViewById(R.id.textcontact);
        new AlertDialog.Builder(MainActivity.this).setMessage("Contactez-nous/Contact us ?").setPositiveButton("Oui",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(!textView.getText().toString().isEmpty()){
                            mDatabase.push().setValue(new ImageModel(textView.getText().toString().trim(),auth.getCurrentUser().getEmail(),
                                    DateStr)).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(MainActivity.this,"Succés/Success",Toast.LENGTH_LONG).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(MainActivity.this,"Echec/Fail",Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                        else
                            Toast.makeText(MainActivity.this,"Message vide/Empty message",Toast.LENGTH_LONG).show();

                    }
                }).setNegativeButton("Non", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();

            }
        }).setView(popup).create().show();

        break;

}
        //respond to menu item selection
return super.onOptionsItemSelected(item);
    }


    /*

    Unitule pour le moment

    private void getData(){
        //Showing a progress dialog
        final ProgressDialog loading = ProgressDialog.show(this,"Loading Data", "Please wait...",false,true);

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
                        Toast.makeText(MainActivity.this, "Check your network connexion", Toast.LENGTH_SHORT).show();
                        loading.dismiss();
                    }
                });

        //Creating request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to the queue
        requestQueue.add(jsonArrayRequest);
    }
    private void parseData(JSONArray array){
        for(int i = 0; i<array.length(); i++) {
            PriorityInfo priorityInfo = new PriorityInfo();
            JSONObject json = null;
            try {
                json = array.getJSONObject(i);
                priorityInfo.setImage(json.getString(Config.TAG_IMAGE_URL));
                priorityInfo.setImage2(json.getString(Config.TAG_IMAGE_URL2));
                priorityInfo.setImage3(json.getString(Config.TAG_IMAGE_URL3));
                priorityInfo.setImage4(json.getString(Config.TAG_IMAGE_URL4));

            } catch (JSONException e) {
                e.printStackTrace();
            }
            infosList.add(priorityInfo);
        }

        //Finally initializing our adapter
        //
        adapter = new CardAdapter(infosList,this);

        //Adding adapter to recyclerview
        recyclerView.setAdapter(adapter);
    }

    */
    @Override
    public void onClick(View v) {

        /*
        if (v==billet){
            AlertDialog.Builder dialog=new AlertDialog.Builder(MainActivity.this);
            dialog.setMessage("IL vous serait demandé de payer immédiatement après livraison du billet, paiement en espece "
            );
            dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent i=new Intent(MainActivity.this,Booking.class);
                    startActivity(i);
                }
            });
            dialog.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            AlertDialog alert11 = dialog.create();
            alert11.show();

        }

        if (v==loc){
            Intent i=new Intent(MainActivity.this, Location.class);
            startActivity(i);
        }
         */

        if(v==maison){
            Intent i=new Intent(MainActivity.this,Immobilier.class);
            startActivity(i);
        }
        if(v==vent){
            Intent in=new Intent(MainActivity.this,Vente.class);
            startActivity(in);
        }
        if (v==perte){
            Intent i=new Intent(MainActivity.this, Perte.class);
            startActivity(i);
        }
        if (v==service){
            Intent i=new Intent(MainActivity.this, Service.class);
            startActivity(i);
        }

        /*
         if (v==foot){
            Intent i=new Intent(MainActivity.this, Football.class);
            startActivity(i);
        }
         */

        if (v==cours){
            Intent i=new Intent(MainActivity.this, Art.class);
            startActivity(i);
        }
        if (v==foot){
            Intent i=new Intent(MainActivity.this,Informatique.class);
            startActivity(i);
        }
        if (v==prio){
            startActivity(new Intent(MainActivity.this,Meuble.class));
        }
        if (v==loc){
            startActivity(new Intent(MainActivity.this,Location.class));
        }
        if (v==emploi){
            startActivity(new Intent(MainActivity.this,Telephone.class));
        }
        if (v==devant){
            startActivity(new Intent(MainActivity.this,UpladImages_Activity.class));
        }
    }
    @Override
    public void onBackPressed(){

    }

}
