package antonioguerrero.ioc.fithub.missatges;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

import antonioguerrero.ioc.fithub.R;

public class MissatgesAdapter extends RecyclerView.Adapter<MissatgesAdapter.ViewHolder> {

    private Context context;
    private List<Missatge> missatgesList;

    public MissatgesAdapter(Context context, List<Missatge> missatgesList) {
        this.context = context;
        this.missatgesList = missatgesList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_missatge, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Missatge missatge = missatgesList.get(position);
        holder.textViewRemitent.setText(missatge.getRemitent());
        holder.textViewContingut.setText(missatge.getContingut());
        holder.textViewData.setText(missatge.getData());
    }

    @Override
    public int getItemCount() {
        return missatgesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewRemitent, textViewContingut, textViewData;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewRemitent = itemView.findViewById(R.id.text_remitent);
            textViewContingut = itemView.findViewById(R.id.text_contingut);
            textViewData = itemView.findViewById(R.id.text_data);
        }
    }
}
