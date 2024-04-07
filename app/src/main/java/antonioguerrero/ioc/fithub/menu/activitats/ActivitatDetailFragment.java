package antonioguerrero.ioc.fithub.menu.activitats;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import antonioguerrero.ioc.fithub.R;
import antonioguerrero.ioc.fithub.objectes.Activitat;

public class ActivitatDetailFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_activitat_detail, container, false);

        Activitat activitat = (Activitat) getArguments().getSerializable("activitat");

        TextView nomTextView = view.findViewById(R.id.nom);
        TextView descripcioTextView = view.findViewById(R.id.descripcio);
        //ImageView fotoImageView = view.findViewById(R.id.foto); // PENDENT

        nomTextView.setText(activitat.getNom());
        descripcioTextView.setText(activitat.getDescripcio());
        // Glide.with(this).load(activitat.getFoto()).into(fotoImageView); // PENDENT

        return view;
    }
}