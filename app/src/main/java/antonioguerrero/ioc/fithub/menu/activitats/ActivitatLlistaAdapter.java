package antonioguerrero.ioc.fithub.menu.activitats;

/*
PENDENT D'IMPLEMENTAR

public class ActivitatLlistaAdapter extends RecyclerView.Adapter<ActivitatLlistaAdapter.ActivitatViewHolder> {

    private List<Activitat> llistaActivitats;

    public ActivitatLlistaAdapter(List<Activitat> llistaActivitats) {
        this.llistaActivitats = llistaActivitats;
    }

    @NonNull
    @Override
    public ActivitatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activitat_item, parent, false);
        return new ActivitatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ActivitatViewHolder holder, int position) {
        Activitat activitat = llistaActivitats.get(position);
        holder.nomActivitat.setText(activitat.getNom());
    }

    @Override
    public int getItemCount() {
        return llistaActivitats.size();
    }

    static class ActivitatViewHolder extends RecyclerView.ViewHolder {
        TextView nomActivitat;

        public ActivitatViewHolder(@NonNull View itemView) {
            super(itemView);
            nomActivitat = itemView.findViewById(R.id.nomActivitat);
        }
    }
}*/