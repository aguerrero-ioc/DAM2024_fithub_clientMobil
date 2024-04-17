package antonioguerrero.ioc.fithub.menu.reserves;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.List;

import antonioguerrero.ioc.fithub.R;

public class ReservesRecyclerViewAdapter extends RecyclerView.Adapter<ReservesRecyclerViewAdapter.ReservesViewHolder> {
    private final List<HashMap<String, String>> reserves;

    public ReservesRecyclerViewAdapter(List<HashMap<String, String>> reserves) {
        this.reserves = reserves;
    }

    @NonNull
    @Override
    public ReservesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reserva, parent, false);
        return new ReservesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReservesViewHolder holder, int position) {
        HashMap<String, String> reserva = reserves.get(position);
        // Aquí puedes configurar tu vista de reserva con los datos de la reserva
    }

    @Override
    public int getItemCount() {
        return reserves.size();
    }

    static class ReservesViewHolder extends RecyclerView.ViewHolder {
        // Aquí puedes inicializar tus vistas de reserva

        public ReservesViewHolder(@NonNull View itemView) {
            super(itemView);
            // Aquí puedes obtener referencias a tus vistas de reserva
        }
    }
}