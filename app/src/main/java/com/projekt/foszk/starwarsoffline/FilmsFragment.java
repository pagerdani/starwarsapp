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

public class FilmsFragment extends Fragment {
    private RecyclerView recyclerView;
    private FilmsAdapter filmsAdapter;
    private List<Film> filmsList = new ArrayList<Film>();
    private EditText searchEditText;

    public FilmsFragment() {
        // Required empty public constructor

    }
    final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://swapi.dev/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    final ApiInterface service = retrofit.create(ApiInterface.class);

    public void fetchFilmsData() {
        Call<Films> call = service.getFilms();

        call.enqueue(new Callback<Films>() {
            @Override
            public void onResponse(Call<Films> call, Response<Films> response) {
                Log.d("FilmsFragment", "onResponse called");
                if (response.isSuccessful() && response.body().getFilms() != null) {
                    filmsList = response.body().getFilms();
                    filmsAdapter = new FilmsAdapter(filmsList);
                    recyclerView.setAdapter(filmsAdapter);
                    Log.d("FilmsFragment", "filmsList size: " + filmsList.size());

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
            public void onFailure(Call<Films> call, Throwable t) {
                Log.e("API Error", "Error fetching data from API" + t.getMessage());
            }
        });
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState){
        Log.d("FilmsFragment", "onCreateView called");
        View view = inflater.inflate(R.layout.fragment_films, container, false);
        recyclerView = view.findViewById(R.id.recViewFilms);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        fetchFilmsData();
        filmsAdapter = new FilmsAdapter(filmsList);
        recyclerView.setAdapter(filmsAdapter);
        searchEditText = view.findViewById(R.id.filmsSearchEditText);
        Button searchButton = view.findViewById(R.id.button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchTerm = searchEditText.getText().toString().toLowerCase();
                List<Film> filteredFilms = new ArrayList<>();
                for (Film film : filmsList) {
                    if (film.getTitle().toLowerCase().matches(".*" + searchTerm + ".*")) {
                        filteredFilms.add(film);
                    }
                }
                filmsAdapter.setFilms(filteredFilms);
            }
        });
        Button clearButton = view.findViewById(R.id.clear_button);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchEditText.setText("");
                filmsAdapter.setFilms(filmsList);
            }
        });
        return view;
    }

    public class FilmsAdapter extends RecyclerView.Adapter<FilmsAdapter.ViewHolder> {
        private List<Film> films;
        public void setFilms(List<Film> films) {
            this.films = films;
            notifyDataSetChanged();
        }

        public FilmsAdapter(List<Film> films) {
            this.films = films;
        }

        @NonNull
        @Override
        public FilmsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.film_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull FilmsAdapter.ViewHolder holder, int position) {
            Film film = films.get(position);

            holder.filmTitle.setText(film.getTitle());
            holder.filmEpisode.setText(String.valueOf(film.getEpisode_id()));
            holder.filmOpening.setText(film.getOpening_crawl());
            holder.filmDirector.setText(film.getDirector());
            holder.filmProducer.setText(film.getProducer());
            holder.filmRelease.setText(film.getReleaseDate());
            holder.filmEpisodeLabel.setVisibility(View.GONE);
            holder.filmEpisode.setVisibility(View.GONE);
            holder.filmOpeningLabel.setVisibility(View.GONE);
            holder.filmOpening.setVisibility(View.GONE);
            holder.filmDirectorLabel.setVisibility(View.GONE);
            holder.filmDirector.setVisibility(View.GONE);
            holder.filmProducerLabel.setVisibility(View.GONE);
            holder.filmProducer.setVisibility(View.GONE);
            holder.filmReleaseLabel.setVisibility(View.GONE);
            holder.filmRelease.setVisibility(View.GONE);
        }

        @Override
        public int getItemCount() {
            return films.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            private final TextView filmTitle;
            private final TextView filmEpisodeLabel;
            private final TextView filmEpisode;
            private final TextView filmOpeningLabel;
            private final TextView filmOpening;
            private final TextView filmDirectorLabel;
            private final TextView filmDirector;
            private final TextView filmProducerLabel;
            private final TextView filmProducer;
            private final TextView filmReleaseLabel;
            private final TextView filmRelease;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                filmTitle = itemView.findViewById(R.id.textViewTitle);
                filmEpisodeLabel = itemView.findViewById(R.id.textViewEpisodeIdLabel);
                filmEpisode = itemView.findViewById(R.id.textViewEpisodeId);
                filmOpeningLabel = itemView.findViewById(R.id.textViewOpeningCrawlLabel);
                filmOpening = itemView.findViewById(R.id.textViewOpeningCrawl);
                filmDirectorLabel = itemView.findViewById(R.id.textViewDirectorLabel);
                filmDirector = itemView.findViewById(R.id.textViewDirector);
                filmProducerLabel = itemView.findViewById(R.id.textViewProducerLabel);
                filmProducer = itemView.findViewById(R.id.textViewProducer);
                filmReleaseLabel = itemView.findViewById(R.id.textViewReleaseDateLabel);
                filmRelease = itemView.findViewById(R.id.textViewReleaseDate);

                filmTitle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // toggle visibility of the other TextViews here
                        if (filmEpisode.getVisibility() == View.VISIBLE) {
                            filmEpisodeLabel.setVisibility(View.GONE);
                            filmEpisode.setVisibility(View.GONE);
                            filmOpeningLabel.setVisibility(View.GONE);
                            filmOpening.setVisibility(View.GONE);
                            filmDirectorLabel.setVisibility(View.GONE);
                            filmDirector.setVisibility(View.GONE);
                            filmProducerLabel.setVisibility(View.GONE);
                            filmProducer.setVisibility(View.GONE);
                            filmReleaseLabel.setVisibility(View.GONE);
                            filmRelease.setVisibility(View.GONE);
                        } else {
                            filmEpisodeLabel.setVisibility(View.VISIBLE);
                            filmEpisode.setVisibility(View.VISIBLE);
                            filmOpeningLabel.setVisibility(View.VISIBLE);
                            filmOpening.setVisibility(View.VISIBLE);
                            filmDirectorLabel.setVisibility(View.VISIBLE);
                            filmDirector.setVisibility(View.VISIBLE);
                            filmProducerLabel.setVisibility(View.VISIBLE);
                            filmProducer.setVisibility(View.VISIBLE);
                            filmReleaseLabel.setVisibility(View.VISIBLE);
                            filmRelease.setVisibility(View.VISIBLE);
                        }
                    }
                });
            }
        }
    }
}
