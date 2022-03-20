package kg.geektech.weatherapp.data.remote;


import kg.geektech.weatherapp.data.models.WeatherAppModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApi {


    @GET("weather?")
    Call<WeatherAppModel> getWeather(
            @Query("q") String city,
            @Query("appid") String apikey,
            @Query("units") String units

    );

    @GET("weather?")
    Call<WeatherAppModel> getWeatherByLatLon(
            @Query("lat") double lat,
            @Query("lon") double lon,
            @Query("appid") String apikey,
            @Query("units") String units
    );
}
