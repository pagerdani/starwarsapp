package com.projekt.foszk.starwarsoffline;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.widget.TextView;
import java.util.List;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Callback;
import retrofit2.Response;
import android.util.Log;
import java.io.IOException;
import android.widget.EditText;
import android.widget.Button;

public class PeopleFragment extends Fragment {
    private RecyclerView recyclerView;
    private PeopleAdapter peopleAdapter;
    private List<Person> peopleList = new ArrayList<Person>();
    private EditText searchEditText;

    public PeopleFragment() {
        // Required empty public constructor
    }

    final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://swapi.dev/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    final ApiInterface service = retrofit.create(ApiInterface.class);

    public void fetchPeopleData() {
        Call<People> call = service.getPeople();

        call.enqueue(new Callback<People>() {
            @Override
            public void onResponse(Call<People> call, Response<People> response) {
                Log.d("PeopleFragment", "onResponse called");
                if (response.isSuccessful() && response.body().getPeople() != null) {
                    peopleList = response.body().getPeople();
                    peopleAdapter = new PeopleAdapter(peopleList);
                    recyclerView.setAdapter(peopleAdapter);
                    Log.d("PeopleFragment", "peopleList size: " + peopleList.size());

                } else {
                    try {
                        Log.e("API Error", "Error fetching data from API. Code: " + response.code() + " Message: " + response.errorBody().string());
                    } catch (IOException e) {
                        Log.e("API Error", "Error fetching data from API. Failed to read error body.");
                    }
                    Log.e("API Error", "Error fetching data from API. Headers: " + response.headers());
                }
            }

            @Override
            public void onFailure(Call<People> call, Throwable t) {
                Log.e("API Error", "Error fetching data from API" + t.getMessage());
            }
        });
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState){
        Log.d("PeopleFragment", "onCreateView called");
        View view = inflater.inflate(R.layout.fragment_peoples, container, false);
        recyclerView = view.findViewById(R.id.recViewPeople);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        fetchPeopleData();
        peopleAdapter = new PeopleAdapter(peopleList);
        recyclerView.setAdapter(peopleAdapter);
        searchEditText = view.findViewById(R.id.peopleSearchEditText);
        Button searchButton = view.findViewById(R.id.button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchTerm = searchEditText.getText().toString().toLowerCase();
                List<Person> filteredPeople = new ArrayList<>();
                for (Person person : peopleList) {
                    if (person.getName().toLowerCase().matches(".*" + searchTerm + ".*")) {
                        filteredPeople.add(person);
                    }
                }
                peopleAdapter.setPeople(filteredPeople);
            }
        });
        Button clearButton = view.findViewById(R.id.clear_button);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchEditText.setText("");
                peopleAdapter.setPeople(peopleList);
            }
        });
        return view;
    }

    public class PeopleAdapter extends RecyclerView.Adapter<PeopleAdapter.ViewHolder> {
        private List<Person> people;
        public void setPeople(List<Person> people) {
            this.people = people;
            notifyDataSetChanged();
        }

        public PeopleAdapter(List<Person> people) {
            this.people = people;
        }

        @NonNull
        @Override
        public PeopleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.person_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull PeopleAdapter.ViewHolder holder, int position) {
            Person person = people.get(position);

            holder.personName.setText(person.getName());
            holder.personBirthYear.setText(person.getBirth_year());
            holder.personEyeColor.setText(person.getEye_color());
            holder.personGender.setText(person.getGender());
            holder.personHairColor.setText(person.getHair_color());
            holder.personHeight.setText(person.getHeight());
            holder.personMass.setText(person.getMass());
            holder.personSkinColor.setText(person.getSkin_color());
            holder.personBirthYearLabel.setVisibility(View.GONE);
            holder.personBirthYear.setVisibility(View.GONE);
            holder.personEyeColorLabel.setVisibility(View.GONE);
            holder.personEyeColor.setVisibility(View.GONE);
            holder.personGenderLabel.setVisibility(View.GONE);
            holder.personGender.setVisibility(View.GONE);
            holder.personHairColorLabel.setVisibility(View.GONE);
            holder.personHairColor.setVisibility(View.GONE);
            holder.personHeightLabel.setVisibility(View.GONE);
            holder.personHeight.setVisibility(View.GONE);
            holder.personMassLabel.setVisibility(View.GONE);
            holder.personMass.setVisibility(View.GONE);
            holder.personSkinColorLabel.setVisibility(View.GONE);
            holder.personSkinColor.setVisibility(View.GONE);
        }

        @Override
        public int getItemCount() {
            return people.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            private final TextView personName;
            private final TextView personBirthYearLabel;
            private final TextView personBirthYear;
            private final TextView personEyeColorLabel;
            private final TextView personEyeColor;
            private final TextView personGenderLabel;
            private final TextView personGender;
            private final TextView personHairColorLabel;
            private final TextView personHairColor;
            private final TextView personHeightLabel;
            private final TextView personHeight;
            private final TextView personMassLabel;
            private final TextView personMass;
            private final TextView personSkinColorLabel;
            private final TextView personSkinColor;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                personName = itemView.findViewById(R.id.textViewName);
                personBirthYearLabel = itemView.findViewById(R.id.textViewBirthYearLabel);
                personBirthYear = itemView.findViewById(R.id.textViewBirthYear);
                personEyeColorLabel = itemView.findViewById(R.id.textViewEyeColorLabel);
                personEyeColor = itemView.findViewById(R.id.textViewEyeColor);
                personGenderLabel = itemView.findViewById(R.id.textViewGenderLabel);
                personGender = itemView.findViewById(R.id.textViewGender);
                personHairColorLabel = itemView.findViewById(R.id.textViewHairColorLabel);
                personHairColor = itemView.findViewById(R.id.textViewHairColor);
                personHeightLabel = itemView.findViewById(R.id.textViewHeightLabel);
                personHeight = itemView.findViewById(R.id.textViewHeight);
                personMassLabel = itemView.findViewById(R.id.textViewMassLabel);
                personMass = itemView.findViewById(R.id.textViewMass);
                personSkinColorLabel = itemView.findViewById(R.id.textViewSkinColorLabel);
                personSkinColor = itemView.findViewById(R.id.textViewSkinColor);


                personName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                // toggle visibility of the other TextViews here
                        if (personBirthYear.getVisibility() == View.VISIBLE) {
                            personBirthYearLabel.setVisibility(View.GONE);
                            personBirthYear.setVisibility(View.GONE);
                            personEyeColorLabel.setVisibility(View.GONE);
                            personEyeColor.setVisibility(View.GONE);
                            personGenderLabel.setVisibility(View.GONE);
                            personGender.setVisibility(View.GONE);
                            personHairColorLabel.setVisibility(View.GONE);
                            personHairColor.setVisibility(View.GONE);
                            personHeightLabel.setVisibility(View.GONE);
                            personHeight.setVisibility(View.GONE);
                            personMassLabel.setVisibility(View.GONE);
                            personMass.setVisibility(View.GONE);
                            personSkinColorLabel.setVisibility(View.GONE);
                            personSkinColor.setVisibility(View.GONE);
                        } else {
                            personBirthYearLabel.setVisibility(View.VISIBLE);
                            personBirthYear.setVisibility(View.VISIBLE);
                            personEyeColorLabel.setVisibility(View.VISIBLE);
                            personEyeColor.setVisibility(View.VISIBLE);
                            personGenderLabel.setVisibility(View.VISIBLE);
                            personGender.setVisibility(View.VISIBLE);
                            personHairColorLabel.setVisibility(View.VISIBLE);
                            personHairColor.setVisibility(View.VISIBLE);
                            personHeightLabel.setVisibility(View.VISIBLE);
                            personHeight.setVisibility(View.VISIBLE);
                            personMassLabel.setVisibility(View.VISIBLE);
                            personMass.setVisibility(View.VISIBLE);
                            personSkinColorLabel.setVisibility(View.VISIBLE);
                            personSkinColor.setVisibility(View.VISIBLE);
                        }
                    }
                });
            }
        }
    }
}