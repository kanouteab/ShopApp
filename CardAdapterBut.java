package gnz.julaa.kanou;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.a11.sabou.R;


import java.util.List;

/**
 * Created by a11 on 18/08/2017.
 */

public class CardAdapterBut extends RecyclerView.Adapter<CardAdapterBut.ViewHolder> {
    private Context context;
    private Bitmap bitmap;
    private ImageLoader imageLoader;
    List<ButteurInfos> butteurInfos;
    public CardAdapterBut(List<ButteurInfos> infoServices, Context context){
        super();
        this.butteurInfos =infoServices;
        this.context=context;

    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.infos_butteur,parent,false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        ButteurInfos infos = butteurInfos.get(position);
        imageLoader= CustomVolleyRequest.getInstance(context).getImageLoader();
        imageLoader.get(infos.getProtrait(),ImageLoader.getImageListener(holder.port,R.mipmap.ic_launcher,android.R.drawable.ic_dialog_alert));
        holder.port.setImageUrl(infos.getProtrait(),imageLoader);
        holder.Nom.setText(infos.getNom());
        holder.BM.setText(infos.getBut());
        holder.EQ.setText(infos.getEq());

    }

    @Override
    public int getItemCount() {
        return butteurInfos.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public NetworkImageView port;
        public TextView Nom;
        public TextView BM;
        public TextView EQ;
        public ViewHolder(final View itemView) {
            super(itemView);
            Nom = (TextView) itemView.findViewById(R.id.nomb);
            BM= (TextView) itemView.findViewById(R.id.bm);
            EQ= (TextView) itemView.findViewById(R.id.eq);
            port=(NetworkImageView)itemView.findViewById(R.id.imageView);
            bitmap=((BitmapDrawable)port.getDrawable()).getBitmap();
        }

    }

}
