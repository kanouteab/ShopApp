package gnz.julaa.kanou;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.a11.sabou.R;

import java.util.List;

/**
 * Created by a11 on 18/08/2017.
 */

public class CardAdapterCls extends RecyclerView.Adapter<CardAdapterCls.ViewHolder> {
    private Context context;
    List<InfosClassement> classements;
    public CardAdapterCls(List<InfosClassement> infoServices, Context context){
        super();
        this.classements =infoServices;
        this.context=context;


    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.infos_classement,parent,false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        InfosClassement infos = classements.get(position);
        holder.Eq.setText(infos.getEq());
        holder.Pts.setText(infos.getPoints());
        holder.BM.setText(infos.getBm());
        holder.BC.setText(infos.getBc());
        holder.DIF.setText(infos.getDif());

    }

    @Override
    public int getItemCount() {
        return classements.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public TextView Eq;
        public TextView Pts;
        public TextView BM;
        public TextView BC;
        public TextView DIF;
        public TextView Tel;
        public TextView Ville;

        public ViewHolder(final View itemView) {
            super(itemView);
            Eq = (TextView) itemView.findViewById(R.id.eq);
            Pts= (TextView) itemView.findViewById(R.id.pt);
            BM= (TextView) itemView.findViewById(R.id.bm);
            BC= (TextView) itemView.findViewById(R.id.bc);
            DIF=(TextView)itemView.findViewById(R.id.dif);

        }
    }

}
