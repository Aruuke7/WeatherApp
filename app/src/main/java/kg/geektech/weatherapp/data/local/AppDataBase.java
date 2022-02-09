package kg.geektech.weatherapp.data.local;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import kg.geektech.weatherapp.data.local.converter.CloudsConverter;
import kg.geektech.weatherapp.data.local.converter.CoordConverter;
import kg.geektech.weatherapp.data.local.converter.MainConverter;
import kg.geektech.weatherapp.data.local.converter.SysConverter;
import kg.geektech.weatherapp.data.local.converter.WeatherAppModelConverter;
import kg.geektech.weatherapp.data.local.converter.WeatherConverter;
import kg.geektech.weatherapp.data.local.converter.WindConverter;
import kg.geektech.weatherapp.data.models.WeatherAppModel;

@Database(entities = {WeatherAppModel.class},version = 3)
@TypeConverters({MainConverter.class,WindConverter.class,CloudsConverter.class,CoordConverter.class,WeatherConverter.class,
        WeatherAppModelConverter.class, SysConverter.class})

public abstract class AppDataBase extends RoomDatabase {

    public abstract WeatherDao weatherDao();}
