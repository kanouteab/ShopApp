package gnz.julaa.kanou.ventes;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.a11.sabou.R;

import gnz.julaa.kanou.ImageModel;
import gnz.julaa.kanou.UserInformation;
import gnz.julaa.kanou.UplooadImageAdapter;
import gnz.julaa.kanou.imobillier.ImmoInfos;
import gnz.julaa.kanou.location.UploadImages;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Produit extends AppCompatActivity {
    private EditText details,Getel,Getauteur;
    private ImageButton GET_PICK;
    private Uri filePath;
    private StorageTask task;
    private DatabaseReference ref;
    private List<String>fileNames;
    private List<String>fileDoneList;
    private List<String> uri_data;
    Bitmap bitmap,bitmap2,bitmap3;
    private List<String>fileNameList;
    public static final String STORAGE_PATH_UPLOADS = "ventes/";
    public static final String DATABASE_PATH_UPLOADS = "ventes";
    private DatabaseReference mDatabase;
    private FirebaseStorage fstorage;
    private RecyclerView recyclerView;
    private String DateStr;
    String us_id;
    private static String Pays;
    public static  int totalSelected;
    private UploadImages uploadImages;
    private Button Valider;
    private static boolean GETPRICK_RESULT=false;
    private static String sss;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private ImageView mButton;
    //public List<PubInfos> infosLis;
    static String GET_TYPE,GET_DEVISE;
    private String fmail;
    private String country;
    private Spinner GetCountry;
    public   static String dernier;
    private int PICK_IMAGE_REQUEST = 1;
    private UplooadImageAdapter uplooadImageAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produit);
        details =(EditText)findViewById(R.id.details);
        Getel=(EditText)findViewById(R.id.tel);
        Valider=(Button)findViewById(R.id.val);
        GetCountry=(Spinner)findViewById(R.id.pays);
        mButton=(ImageView) findViewById(R.id.Id_Imgbutt);
        recyclerView=(RecyclerView)findViewById(R.id.recycle);
        Getauteur=(EditText)findViewById(R.id.auteur);
        FirebaseAuth auth=FirebaseAuth.getInstance();
        FirebaseUser firebaseUser=auth.getCurrentUser();
        us_id =firebaseUser.getEmail();
        fmail=us_id.replace(".","_");
        //mDatabase = FirebaseDatabase.getInstance().getReference(DATABASE_PATH_UPLOADS);
        fstorage=FirebaseStorage.getInstance();
        getCountry();
        //ref= FirebaseDatabase.getInstance().getReference(UserInformation.FB_DATABASE_PATH).child(us_id);
        /*
        infosLis=new ArrayList<>();

         */
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        fileDoneList=new ArrayList<>();
        fileNames=new ArrayList<>();
        uploadImages=new UploadImages(fileNames,fileDoneList);
        recyclerView.setAdapter(uploadImages);
        uri_data=new ArrayList<>();
        getCountry();
        mDatabase = FirebaseDatabase.getInstance().getReference(DATABASE_PATH_UPLOADS);
        fstorage=FirebaseStorage.getInstance();
        GetCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                country=GetCountry.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater=getLayoutInflater();
                final View popup=layoutInflater.inflate(R.layout.pub_layout,null);
                final AlertDialog.Builder builder=new AlertDialog.Builder(Produit.this);
                if (Getauteur.getText().toString().isEmpty()||details.getText().toString().isEmpty()|| (Getel.getText().toString().trim()).isEmpty())
                {
                    builder.setMessage("Un ou plusieurs champs non renseignés, continuez ?").setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if (GETPRICK_RESULT)
                            {
                                uploadData(country);
                            }
                            else {
                                builder.setMessage("Voulez-vous publier sans photo ?").setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        load(country);
                                    }
                                }).setNegativeButton("Non", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.cancel();
                                        }
                                }).setView(popup).create().show();
                            }

                        }
                    }).setNegativeButton("Non", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    }).setView(popup).create().show();
                }


                else {
                    if (GETPRICK_RESULT)
                    {
                        uploadData(country);
                    }
                    else {
                        builder.setMessage("Voulez-vous publier sans photo ?").setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                load(country);
                            }
                        }).setNegativeButton("Non", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        }).setView(popup).create().show();
                    }
                }
            }
        });
      mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showImage();
            }
        });
        //getData();
        SimpleDateFormat timeStampFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        Date GetDate = new Date();
        DateStr = timeStampFormat.format(GetDate);

    }
    private void showImage(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image From Gallery"), 1);
    }
    private void postPub(){
        Getel.setText("");
        details.setText("");
        bitmap2= BitmapFactory.decodeResource(getResources(),R.drawable.photo);
        mButton.setImageBitmap(bitmap2);
    }


/*
 fileNameList=new ArrayList<>();
        fileDoneList=new ArrayList<>();
        uplooadImageAdapter=new UplooadImageAdapter(fileNameList,fileDoneList);
        recyclerView.setAdapter(uplooadImageAdapter);
 */

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null ) {
            if( data.getData() != null){
                filePath = data.getData();
                GETPRICK_RESULT=true;
                try {

                    {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                        mButton.setImageBitmap(bitmap);

                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else if (data.getClipData()!=null ){
                if (data.getClipData().getItemCount()<6)
                {
                    GETPRICK_RESULT=true;
                    totalSelected=data.getClipData().getItemCount();
                    filePath=data.getClipData().getItemAt(0).getUri();
                    try {

                        {
                            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                            mButton.setImageBitmap(bitmap);

                        }


                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    for (int i=0;i<totalSelected;i++){
                        Uri fileUri=data.getClipData().getItemAt(i).getUri();
                        uri_data.add(i,fileUri.toString());
                        //uri_data.add(fileUri.toString());
                        fileNames.add(getFileName(fileUri));
                        fileDoneList.add(i,"uploading");
                        uploadImages.notifyDataSetChanged();
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(),"Max=6",Toast.LENGTH_LONG).show();
                }

            }


        }
    }
    public String getFileName(Uri uri){
        String result=null;
        if (uri.getScheme().equals("content")){
            Cursor cursor=getContentResolver().query(uri,null,null,null,null);
            try {
                if (cursor!=null && cursor.moveToFirst()){
                    result=cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            }finally {
                cursor.close();
            }
        }
        if (result==null){
            result=uri.getPath();
            int cut=result.lastIndexOf('/');
            if (cut!=-1){
                result=result.substring(cut+1);
            }
        }
        return result;
    }


    private void getCountry(){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(UserInformation.FB_DATABASE_PATH).child(fmail).child("pays");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Pays=dataSnapshot.getValue(String.class);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
    public void uploadData(final String pays){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        final String Img_name=""+System.currentTimeMillis();
        progressDialog.setTitle("Uploading");
        progressDialog.show();
        Query last=mDatabase.child(pays).child(fmail);
        last.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int size=(int) dataSnapshot.getChildrenCount();
                dernier=""+size;
                if (fileNames.size()>0){
                    for (int i=0;i<totalSelected;i++){
                        final int indice=i;
                        final StorageReference reference1=fstorage.getReference().child(STORAGE_PATH_UPLOADS).child(pays).child(fmail).child(dernier).child(indice + "."
                                + getFileExtension(Uri.parse(uri_data.get(i))));
                        progressDialog.setTitle("Uploading");
                        progressDialog.setMessage("please wait...");
                        progressDialog.show();
                        reference1.putFile(Uri.parse(uri_data.get(i))).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                reference1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        mDatabase.child(pays).child(fmail).child(dernier).child(""+indice).setValue(new ImageModel(uri.toString()));
                                        Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_LONG).show();

                                        fileDoneList.remove(indice);
                                        fileDoneList.add(indice,"done");
                                        uploadImages.notifyDataSetChanged();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        progressDialog.dismiss();
                                        Toast.makeText(Produit.this,e.getMessage(),Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        });
                    }
                }

                final StorageReference ref=fstorage.getReference().child(STORAGE_PATH_UPLOADS).child(pays).child(fmail).child(dernier).child(Img_name+"."+getFileExtension(filePath));
                ref.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                mDatabase.child(pays).child(fmail).child(dernier).child(Img_name).setValue(
                                        new ImmoInfos(uri.toString(),details.getText().toString().trim(),Getauteur.getText().toString().trim(),DateStr,dernier,DATABASE_PATH_UPLOADS,Getel.getText().toString().trim()
                                                ,pays, fmail,Img_name)).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                       progressDialog.dismiss();
                                        Toast.makeText(Produit.this,"Succés",Toast.LENGTH_LONG).show();
                                        postPub();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(Produit.this,"Echec :"+e,Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        });
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressDialog.dismiss();
                Toast.makeText(Produit.this,"Echec",Toast.LENGTH_LONG).show();
            }
        });
    }
    private void load(final String pays){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        final String Img_name=""+System.currentTimeMillis();
        progressDialog.setTitle("Uploading");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.show();
        Query last=mDatabase.child(pays).child(fmail);
        last.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int size=(int) dataSnapshot.getChildrenCount();
                dernier=""+size;
                mDatabase.child(pays).child(fmail).child(dernier).child(Img_name).setValue(new ImmoInfos(details.getText().toString().trim(),Getel.getText().toString().trim(),
                        DateStr,dernier, DATABASE_PATH_UPLOADS,Getauteur.getText().toString().trim(),pays, fmail)).
                        addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                progressDialog.dismiss();
                                postPub();
                                Toast.makeText(Produit.this, "File Uploaded ", Toast.LENGTH_LONG).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Produit.this,e.getMessage(),Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    // Toast.makeText(getApplicationContext(),"Mutplines selections effectuées"
    //       ,Toast.LENGTH_LONG).show();
    /*
       public String getFileName(Uri uri){
        String result=null;
        if (uri.getScheme().equals("content")){
            Cursor cursor=getContentResolver().query(uri,null,null,null,null);
            try {
                if (cursor!=null && cursor.moveToFirst()){
                    result=cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            }finally {
                cursor.close();
            }
        }
        if (result==null){
            result=uri.getPath();
            int cut=result.lastIndexOf('/');
            if (cut!=-1){
                result=result.substring(cut+1);
            }
        }
        return result;
}
     */

/* public String getFileNam(){

        for (int i=0;i<fileNameList.size();i++){
            sss=fileNameList.get(i);
            Toast.makeText(getApplicationContext(),sss,Toast.LENGTH_LONG).show();

        }
        return sss;
    }


/*

    private void loadDatas(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading");
        progressDialog.show();
        final StorageReference reference=fstorage.getReference().child(STORAGE_PATH_UPLOADS +
                System.currentTimeMillis() + "." + getFileExtension(filePath));

        task= reference.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                reference.getDownloadUrl().addOnSuccessListener(
                        new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                PriorityInfo info=new PriorityInfo(GET_TYPE,lib.getText().toString(),details.getText().toString(),
                                        tel.getText().toString(),us_id.getText().toString(),DateStr,
                                        uri.toString(),uri.toString(),uri.toString());
                                mDatabase.push().setValue(info);
                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(),"Success!!!",Toast.LENGTH_LONG).show();
                            }
                        }
                ).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                      progressDialog.dismiss();
                      Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

            }
        });
    }

*/
/*

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    public void upload(){
        final String GET_TEL=tel.getText().toString();
        final String GET_PRIX=details.getText().toString();
        final String GET_LIB=lib.getText().toString();
        final String GET_IMAGE=getStringImage(bitmap);
        final String GET_IMAGE2=getStringImage(bitmap2);
        final String GET_IM3=getStringImage(bitmap3);
        final  String GET_VILLE=us_id.getText().toString();
        class UploadImage extends AsyncTask<Void,Void,String>{
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Produit.this, "Uploading...", null,true,true);
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
                lib.setText("");
                tel.setText("");
                details.setText("");
                us_id.setText("");
                ShowSelectedImage.setImageResource(R.drawable.photo);
                ShowSelectedImage2.setImageResource(R.drawable.photo);
                ShowSelectedImage3.setImageResource(R.drawable.photo);
            }
            @Override
            protected String doInBackground(Void... voids) {
                RequestHandler rh = new RequestHandler();
                HashMap<String,String> data = new HashMap<>();
                data.put(UPLOAD_IMAGE,GET_IMAGE);
                data.put(UPLOAD_IMAGE2,GET_IMAGE2);
                data.put(UPLOAD_IMAGE3,GET_IM3);
                data.put(UPLOADLIB,GET_LIB);
                data.put(UPLOADPRIX,GET_PRIX);
                data.put(UPLOADTEL,GET_TEL);
                data.put(UPLOADVILLE,GET_VILLE);
                data.put(UPLOADTYPE,GET_TYPE);
                String result = rh.sendPostRequest(UPLOAD_URL,data);
                return result;
            }

        }
        UploadImage ui = new UploadImage();
        ui.execute();
    }
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
                        Toast.makeText(Produit.this, "Check your network connexion", Toast.LENGTH_SHORT).show();
                        loading.dismiss();
                    }
                });

        //Creating request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to the queue
        requestQueue.add(jsonArrayRequest);
    }
    private void parseData(JSONArray array){
        for(int i = 0; i<array.length(); i++){
            PubInfos infos=new PubInfos();
            JSONObject json=null;
            try {
                json = array.getJSONObject(i);
                infos.setImg_url(json.getString(Config.TAG_IMAGE_URL));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            infosLis.add(infos);
            adapter=new CardAdapter(infosLis,this);
            recyclerView.setAdapter(adapter);
        }
    }
/*
    public void uploading(){
        final String GET_TEL=tel.getText().toString();
        final String GET_PRIX=details.getText().toString();
        final String GET_LIB=lib.getText().toString();
        final  String GET_VILLE=us_id.getText().toString();
        class UploadImage extends AsyncTask<Void,Void,String>{
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Produit.this, "Uploading...", null,true,true);
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
                lib.setText("");
                tel.setText("");
                details.setText("");
                us_id.setText("");
            }
            @Override
            protected String doInBackground(Void... voids) {
                RequestHandler rh = new RequestHandler();
                HashMap<String,String> data = new HashMap<>();
                data.put(UPLOADLIB,GET_LIB);
                data.put(UPLOADPRIX,GET_PRIX);
                data.put(UPLOADTEL,GET_TEL);
                data.put(UPLOADTYPE,GET_TYPE);
                data.put(UPLOADVILLE,GET_VILLE);
                String result = rh.sendPostRequest(UPLOAD_URL,data);
                return result;
            }

        }
        UploadImage ui = new UploadImage();
        ui.execute();
    }
    */

}
