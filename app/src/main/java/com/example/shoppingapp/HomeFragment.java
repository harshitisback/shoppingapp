package com.example.shoppingapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Toast;

import com.example.shoppingapp.adapter.HomeAdapter;
import com.example.shoppingapp.adapter.PopularAdapter;
import com.example.shoppingapp.adapter.RecommendedAdapter;
import com.example.shoppingapp.model.HomeCategory;
import com.example.shoppingapp.model.RecommendedModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    ScrollView scrollView;
    ProgressBar progressBar;
    RecyclerView popularRec, homeCatRec, recommended_rec;
    FirebaseFirestore db;



    public HomeFragment() {
        // Required empty public constructor
    }


    // popular items

    List<PopularModel> popularModelsList;
    PopularAdapter popularAdapters;

    // home category
    List<HomeCategory> categoryList;
    HomeAdapter homeAdapter;

    // Recommended items

    List<RecommendedModel> recommendedModelList;
    RecommendedAdapter recommendedAdapter;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        db = FirebaseFirestore.getInstance();

        popularRec = root.findViewById(R.id.poprec);
        homeCatRec = root.findViewById(R.id.explore_rec);
        recommended_rec = root.findViewById(R.id.recommended_rec);
        progressBar = root.findViewById(R.id.pb_home);
        scrollView = root.findViewById(R.id.scroll_home);

        progressBar.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.GONE);



        // popular item set
        popularRec.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        popularModelsList = new ArrayList<>();
        popularAdapters = new PopularAdapter(getActivity(),popularModelsList);
        popularRec.setAdapter(popularAdapters);

        db.collection("Popularproducts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                    PopularModel popularModel = document.toObject(PopularModel.class);
                                    popularModelsList.add(popularModel);
                                    popularAdapters.notifyDataSetChanged();
                                progressBar.setVisibility(View.GONE);
                                scrollView.setVisibility(View.VISIBLE);
                            }
                        } else {
                            Toast.makeText(getActivity(),"Error",Toast.LENGTH_LONG).show();
                        }
                    }
                });


        // Home category
        homeCatRec.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        categoryList = new ArrayList<>();
        homeAdapter = new HomeAdapter(getActivity(),categoryList);
        homeCatRec.setAdapter(homeAdapter);

        db.collection("HomeCategory")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                HomeCategory homeCategory = document.toObject(HomeCategory.class);
                                categoryList.add(homeCategory);
                                homeAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Toast.makeText(getActivity(),"Error",Toast.LENGTH_LONG).show();
                        }
                    }
                });

        // Home category
        recommended_rec.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        recommendedModelList = new ArrayList<>();
        recommendedAdapter = new RecommendedAdapter(getActivity(),recommendedModelList);
        recommended_rec.setAdapter(recommendedAdapter);

        db.collection("Recommended")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                RecommendedModel recommendedModel = document.toObject(RecommendedModel.class);
                                recommendedModelList.add(recommendedModel);
                                recommendedAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Toast.makeText(getActivity(),"Error",Toast.LENGTH_LONG).show();
                        }
                    }
                });

        return root;
    }
}