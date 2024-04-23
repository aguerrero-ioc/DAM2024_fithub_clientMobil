package antonioguerrero.ioc.fithub.menu.activitats;

/*
PENDENT D'IMPLEMENTAR



public class ActivitatQuadriculaAdapter extends RecyclerView.Adapter<ActivitatQuadriculaAdapter.ActivitatViewHolder> {

    private Activitat[][] llistaActivitats;

    public ActivitatQuadriculaAdapter(Activitat[][] llistaActivitats) {
        this.llistaActivitats = llistaActivitats;
    }

    @NonNull
    @Override
    public ActivitatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_activitat, parent, false);
        return new ActivitatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ActivitatViewHolder holder, int position) {
        int dia = position % 7;
        int hora = position / 7;
        Activitat activitat = llistaActivitats[hora][dia];
        if (activitat != null) {
            holder.nomActivitat.setText(activitat.getNomActivitat());
            // Configurar les altres vistes del ViewHolder
        } else {
            holder.nomActivitat.setText("");
            // Netejar les altres vistes del ViewHolder
        }
    }

    @Override
    public int getItemCount() {
        return 14 * 7; // 14 franjas horarias, 7 días a la semana
    }

    static class ActivitatViewHolder extends RecyclerView.ViewHolder {
        TextView nomActivitat;
        // Altres vistes segons sigui necessari

        public ActivitatViewHolder(@NonNull View itemView) {
            super(itemView);
            nomActivitat = itemView.findViewById(R.id.nomActivitat);
            // Inicialitzar altres vistes
        }
    }

*/