package kg.geektech.weatherapp.data.local;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import kg.geektech.weatherapp.data.models.WeatherAppModel;

@Dao
public interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(WeatherAppModel weatherAppModel);

    @Query("SELECT * FROM weatherappmodel")
    WeatherAppModel getWeather();
}
