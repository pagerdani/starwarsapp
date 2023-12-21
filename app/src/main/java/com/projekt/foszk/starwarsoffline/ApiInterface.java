package com.projekt.foszk.starwarsoffline;

import retrofit2.Call;
import retrofit2.http.GET;

interface ApiInterface {
    @GET("films/")
    Call <Films> getFilms();

    @GET("people/")
    Call <People> getPeople();


}

