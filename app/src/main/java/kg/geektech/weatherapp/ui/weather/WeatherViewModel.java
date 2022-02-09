package kg.geektech.weatherapp.ui.weather;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import javax.inject.Inject;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kg.geektech.weatherapp.common.Resource;
import kg.geektech.weatherapp.data.models.WeatherAppModel;
import kg.geektech.weatherapp.data.repository.MainRepository;

@HiltViewModel
public class WeatherViewModel  extends ViewModel {

    public LiveData<Resource<WeatherAppModel>> weatherLiveData;
    private MainRepository repository;
    private String city;


    public void setCityName(String city) {
        this.city = city;
    }

    @Inject
    public WeatherViewModel(MainRepository repository) {
        this.repository = repository;
    }


    public void getWeather(){
        repository.setCityName(city);
        weatherLiveData = repository.getWeather();
    }


}
