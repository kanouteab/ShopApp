package gnz.julaa.kanou;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.a11.sabou.R;


import java.util.Calendar;
import java.util.List;

import static java.lang.Integer.parseInt;

/**
 * Created by a11 on 18/08/2017.
 */

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {
    private Context context;
    static int T;
   // private ImageLoader imageLoader,imageLoader2;
    List<MatchInfos> matchInfos;
    public final static String Param1="parm";
    public final static String Param2="parm2";
    public final static String PARAM3="id";
    public final static String Scor="score";
    public CardAdapter(List<MatchInfos> matchInfos, Context context){
        super();
        this.matchInfos =matchInfos;
        this.context=context;


    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.foot_info,parent,false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

       MatchInfos infos = matchInfos.get(position);

       /*
        imageLoader=CustomVolleyRequest.getInstance(context).getImageLoader();
       imageLoader.get(infos.getSigleA(),ImageLoader.getImageListener(holder.sigleA,R.mipmap.ic_launcher,android.R.drawable.ic_dialog_alert));

        imageLoader2=CustomVolleyRequest.getInstance(context).getImageLoader();
        imageLoader2.get(infos.getSigleB(),ImageLoader.getImageListener(holder.sigleB,R.mipmap.ic_launcher,android.R.drawable.ic_dialog_alert));

        holder.sigleA.setImageUrl(infos.getSigleA(),imageLoader);
        holder.sigleB.setImageUrl(infos.getSigleB(),imageLoader2);
        */
        Glide.with(context).load(infos.getSigleA()).into(holder.sigleA);
        Glide.with(context).load(infos.getSigleB()).into(holder.sigleB);
        holder.EquipeA.setText(infos.getEqpuipeA());
        holder.EquipeB.setText(infos.getEquipeB());
        holder.score.setText(infos.getInfos2()+" \n "+infos.getInfos1());
        //holder.DH.setText(infos.getInfos1());
        holder.statut.setText(infos.getInfo3());
        //holder.id.setText(infos.getId());
    }

    @Override
    public int getItemCount() {
        return matchInfos.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView EquipeA;
        TextView EquipeB;
        TextView score;
        TextView DH;
        TextView id;
       ImageView sigleA;
        ImageView sigleB;
        TextView statut;
        ImageButton oeil;
        public ViewHolder(final View itemView) {
            super(itemView);
            sigleA=(ImageView)itemView.findViewById(R.id.siglea);
            sigleB=(ImageView)itemView.findViewById(R.id.sigleb);
            EquipeA=(TextView)itemView.findViewById(R.id.eA);
            EquipeB=(TextView)itemView.findViewById(R.id.eB);
            score =(TextView)itemView.findViewById(R.id.info);
            DH=(TextView)itemView.findViewById(R.id.time);
            statut=(TextView)itemView.findViewById(R.id.statut);
            oeil=(ImageButton)itemView.findViewById(R.id.oeil);
            id=(TextView)itemView.findViewById(R.id.id);
            oeil.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    loadParams(EquipeA.getText().toString(),EquipeB.getText().toString(),score.getText().toString(),id.getText().toString(),sigleA,sigleB);

                }
            });
           // int val=parseInt(DH.getText().toString());
           //gettime(val);
        }
        private void  loadParams(String param1, String param2, String Score, String ID, ImageView image, ImageView image2){
            String prm1=param1;
            String prm2=param2;
            String score=Score;
            String id=ID;
            Intent intent=new Intent(context, Details.class);
            intent.putExtra(Param1,prm1);
            intent.putExtra(Param2,prm2);
            intent.putExtra(Scor,score);
            intent.putExtra(PARAM3,id);
            ImageView image1=image;
            ImageView Image2=image2;
            image1.buildDrawingCache();
            image2.buildDrawingCache();
            Bundle extra=new Bundle();
            Bitmap img=image1.getDrawingCache();
            Bitmap img2=Image2.getDrawingCache();
            extra.putParcelable("IMG",img);
            extra.putParcelable("IMG2",img2);
            intent.putExtras(extra);
            context.startActivity(intent);

        }
        private void gettime(int time){
            int t=time;
            Calendar now=Calendar.getInstance();
            int min=now.get(Calendar.MINUTE) + now.get(Calendar.HOUR_OF_DAY)*60;
             T= min-t;

        }

        @Override
        public void onClick(View view) {
            if (view==EquipeA || view==EquipeB){
                loadParams(EquipeA.getText().toString(),EquipeB.getText().toString(),
                        score.getText().toString(), id.getText().toString(), sigleA,sigleB);
            }
        }
    }

}
