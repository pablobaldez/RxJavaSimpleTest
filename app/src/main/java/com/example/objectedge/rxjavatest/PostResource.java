package com.example.objectedge.rxjavatest;

import java.util.List;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by objectedge on 3/21/16.
 */
public interface PostResource {

    @GET("/posts")
    Observable<List<MyEntity>> findAll();



}
