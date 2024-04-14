// ActivitatListFragment.java
package antonioguerrero.ioc.fithub.menu.activitats;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import antonioguerrero.ioc.fithub.R;
import antonioguerrero.ioc.fithub.objectes.Activitat;

/**
 * Fragment que mostra una llista d'activitats.
 */
public class ActivitatListFragment extends Fragment {


    private Activitat[] activitats;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_activitat_list, container, false);

        ListView listView = view.findViewById(R.id.list_view);
        String[] activitatNoms = new String[activitats.length];
        for (int i = 0; i < activitats.length; i++) {
            activitatNoms[i] = activitats[i].getNomActivitat();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, activitatNoms);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view1, position, id) -> {
            ActivitatDetailFragment fragment = new ActivitatDetailFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("activitat", activitats[position]);
            fragment.setArguments(bundle);

            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        });

        return view;

    }
}
