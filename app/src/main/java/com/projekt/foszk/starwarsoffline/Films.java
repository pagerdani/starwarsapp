package com.projekt.foszk.starwarsoffline;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Films {
    @SerializedName("results")
    private List<Film> films;

    public Films(List<Film> results) {
        this.films = films;
    }

    public List<Film> getFilms() {
        return films;
    }

    public void setFilms(List<Film> results) {
        this.films = films;
    }
}

    class Film {
        private String title;
        private int episode_id;
        private String opening_crawl;
        private String director;
        private String producer;
        private String release_date;

        public Film(String title, int episode_id, String opening_crawl, String director, String producer, String release_date) {
            this.title = title;
            this.episode_id = episode_id;
            this.opening_crawl = opening_crawl;
            this.director = director;
            this.producer = producer;
            this.release_date = release_date;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getEpisode_id() {
            return episode_id;
        }

        public void setEpisode_id(int episode_id) {
            this.episode_id = episode_id;
        }

        public String getOpening_crawl() {
            return opening_crawl;
        }

        public void setOpening_crawl(String opening_crawl) {
            this.opening_crawl = opening_crawl;
        }

        public String getDirector() {
            return director;
        }

        public void setDirector(String director) {
            this.director = director;
        }

        public String getProducer() {
            return producer;
        }

        public void setProducer(String producer) {
            this.producer = producer;
        }

        public String getReleaseDate() {
            return release_date;
        }

        public void setReleaseDate(String release_date) {
            this.release_date = release_date;
        }
    }

