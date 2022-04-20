package com.example.countries.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.countries.model.Country

//DAO - Data Access Object
@Dao
interface CountryDao {

    //Data Access Object

    // Insert -> INSERT INTO
    // suspend -> coroutineler icinde cagriliyor. Durdurulup devam ettirilebilir
    // vararg -> sayisi belli olmayan tekli objeyi farkli sayilarla verebilmemizi saglayan keyword
    // List<Long> -> primary key donduruyor

    @Insert
    suspend fun insertAll(vararg countries: Country) : List<Long>

    @Query("SELECT * FROM country")
    suspend fun getAllCountries() : List<Country>

    @Query("SELECT * FROM country WHERE uuid = :countryId")
    suspend fun getCountry(countryId: Int) : Country

    @Query("DELETE FROM country")
    suspend fun deleteAllCountries()

}