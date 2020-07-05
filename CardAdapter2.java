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

public class CardAdapter2 extends RecyclerView.Adapter<CardAdapter2.ViewHolder> {
    private Context context;
    List<MatchInfo> matchInfos;
    public CardAdapter2(List<MatchInfo> matchInfos, Context context){
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

       MatchInfo infos = matchInfos.get(position);
        holder.EquipeA.setText(infos.getEqpuipeA());
        holder.EquipeB.setText(infos.getEquipeB());
        holder.score.setText(infos.getInfos2());
        holder.DH.setText(infos.getInfos1());
    }

    @Override
    public int getItemCount() {
        return matchInfos.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView EquipeA;
        TextView EquipeB;
        TextView score;
        TextView DH;
        public ViewHolder(final View itemView) {
            super(itemView);
            EquipeA=(TextView)itemView.findViewById(R.id.eA);
            EquipeB=(TextView)itemView.findViewById(R.id.eB);
            score =(TextView)itemView.findViewById(R.id.info);
            DH=(TextView)itemView.findViewById(R.id.time);
        }
    }

}
