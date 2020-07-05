package gnz.julaa.kanou.ventes;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.a11.sabou.R;

import java.util.List;

import uk.co.senab.photoview.PhotoViewAttacher;
//import com.example.a11.gzndrader.R;


/**
 * Created by a11 on 17/08/2017.
 */

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

    //extends RecyclerView.Adapter<CardAdapter>
    private Context context;
    private List<PriorityInfo> infos;
    public CardAdapter(Context context,List<PriorityInfo> infos){
        this.context=context;
        this.infos=infos;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.liste_prduit, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        PriorityInfo info=infos.get(position);
        holder.detail.setText(info.getDetail());
        holder.Tel.setText("Tel : "+info.getTel());
        holder.Dat.setText(info.getDat());
        holder.idpub.setText((info.getAuteur()));
        Glide.with(context).load(info.getImage0()).into(holder.pro_Img);
    }

    @Override
    public int getItemCount() {
        return infos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView Tel, detail, idpub,Dat;
        public ImageView pro_Img;
        public Button GetPlus;
        public ViewHolder(View itemView) {
            super(itemView);
            Tel=(TextView)itemView.findViewById(R.id.tel);
            detail =(TextView)itemView.findViewById(R.id.details);
            idpub =(TextView)itemView.findViewById(R.id.id_pub);
            Dat=(TextView)itemView.findViewById(R.id.dat);
            GetPlus=(Button)itemView.findViewById(R.id.plus);
            pro_Img=(ImageView)itemView.findViewById(R.id.imageViewProduit);
           // IdPub=(TextView)itemView.findViewById(R.id.id_pub);
            pro_Img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    zoomPhoto(pro_Img);
                }
            });
        }
    }
    private void zoomPhoto(ImageView Img){
        PhotoViewAttacher photo=new PhotoViewAttacher(Img);
        photo.update();
    }
    /*
    private ImageLoader imageLoader,imageLoader0,imageLoader2,imageLoader3;
    private Context context;


    List<PriorityInfo> venteInfos;
    public CardAdapter(List<PriorityInfo> venteInfos, Context context){
        super();
        this.venteInfos =venteInfos;
        this.context=context;

    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.liste_prduit,parent,false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final PriorityInfo infos = venteInfos.get(position);

        imageLoader0 = CustomVolleyRequest.getInstance(context).getImageLoader();
        imageLoader0.get(infos.getImage(), ImageLoader.getImageListener(holder.imageView0, R.mipmap.ic_launcher, android.R.drawable.ic_dialog_alert));

        holder.imageView0.setImageUrl(infos.getImage(), imageLoader0);

        imageLoader2 = CustomVolleyRequest.getInstance(context).getImageLoader();
        imageLoader2.get(infos.getImage2(), ImageLoader.getImageListener(holder.imageView2, R.mipmap.ic_launcher, android.R.drawable.ic_dialog_alert));
        holder.imageView2.setImageUrl(infos.getImage2(), imageLoader2);

        imageLoader3 = CustomVolleyRequest.getInstance(context).getImageLoader();
        imageLoader3.get(infos.getImage3(), ImageLoader.getImageListener(holder.imageView3, R.mipmap.ic_launcher, android.R.drawable.ic_dialog_alert));
        holder.imageView3.setImageUrl(infos.getImage3(), imageLoader3);

        holder.textDat.setText(infos.getDetail());
        holder.Tel.setText(infos.getEn_tel());
        holder.Lib.setText(infos.getLib());
        holder.Type.setText(infos.getType());
        holder.detail.setText(infos.getEn_ville());
        holder.Date.setText(infos.getDate());

    }

    @Override
    public int getItemCount() {
        return venteInfos.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public NetworkImageView imageView,imageView0,imageView2,imageView3;
        public TextView textDat;
        public TextView Tel;
        public TextView Lib;
        public TextView Type;
        public TextView Date,detail;
        public Button plus,moins;
        public Gallery gallery;
        //public ImageView mac,ecran,congelo;

        public ViewHolder(final View itemView) {
            super(itemView);
            //imageView = (NetworkImageView) itemView.findViewById(R.id.imageViewProduit);
            imageView0=(NetworkImageView) itemView.findViewById(R.id.imageViewProduit0);
            imageView2=(NetworkImageView) itemView.findViewById(R.id.imageViewProduit2);
            imageView3=(NetworkImageView) itemView.findViewById(R.id.imageViewProduit3);
            textDat = (TextView) itemView.findViewById(R.id.textPrix);
            Tel= (TextView) itemView.findViewById(R.id.TextTel);
            Lib= (TextView) itemView.findViewById(R.id.textLib);
            Date=(TextView)itemView.findViewById(R.id.TextTime);
            detail=(TextView)itemView.findViewById(R.id.Textville);
            plus=(Button) itemView.findViewById(R.id.plus);
            Type=(TextView)itemView.findViewById(R.id.Type);
            gallery=(Gallery)itemView.findViewById(R.id.galleri);
           /* int W=imageView.getWidth();
          //  int H=imageView.getHeight();
            moins=(Button) itemView.findViewById(R.id.moins);
            plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LinearLayout.LayoutParams params=(LinearLayout.LayoutParams)imageView.getLayoutParams();
                    int width=imageView.getWidth();
                    int heith=imageView.getHeight();
                    params.width=120+width;
                    params.height=120+heith;
                    imageView.setLayoutParams(params);

                }
            });
            moins.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LinearLayout.LayoutParams params=(LinearLayout.LayoutParams)imageView.getLayoutParams();
                    //params.width=W;
                    int width=imageView.getWidth();
                    int heith=imageView.getHeight();
                    if(width-120<width){
                        params.width=width-120;
                        params.height=heith-120;
                    }
                    imageView.setLayoutParams(params);
                }
            });
            final Animation animation= AnimationUtils.loadAnimation(context,R.anim.bouance);

            imageView.startAnimation(animation);
           /*
            imageView0.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    loadPhoto(imageView0);
                    return false;
                }
            });
            imageView2.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    loadPhoto(imageView2);
                    return false;
                }
            });
            imageView3.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    loadPhoto(imageView3);
                    return false;
                }
            });

*/


    /*
        }
        private void  loadPhoto(NetworkImageView image){
            NetworkImageView tempImage=image;
            tempImage.buildDrawingCache();
            Bundle extra=new Bundle();
            Bitmap img=tempImage.getDrawingCache();
            extra.putParcelable("IMG",img);
            Intent intent=new Intent(context, Full_Screen.class);
            intent.putExtras(extra);
            context.startActivity(intent);
        }



    }

*/

}