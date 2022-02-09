package kg.geektech.weatherapp.ui.weather;


import static kg.geektech.weatherapp.common.Status.ERROR;
import static kg.geektech.weatherapp.common.Status.LOADING;
import static kg.geektech.weatherapp.common.Status.SUCCESS;

import android.annotation.SuppressLint;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import kg.geektech.weatherapp.R;
import kg.geektech.weatherapp.base.BaseFragment;
import kg.geektech.weatherapp.common.Resource;
import kg.geektech.weatherapp.data.local.WeatherDao;
import kg.geektech.weatherapp.data.models.Main;
import kg.geektech.weatherapp.data.models.Sys;
import kg.geektech.weatherapp.data.models.Weather;
import kg.geektech.weatherapp.data.models.WeatherAppModel;
import kg.geektech.weatherapp.data.models.Wind;
import kg.geektech.weatherapp.data.remote.WeatherApi;
import kg.geektech.weatherapp.databinding.FragmentWeatherBinding;

@AndroidEntryPoint
public class WeatherFragment extends BaseFragment<FragmentWeatherBinding> {

    private WeatherViewModel viewModel;
    private WeatherFragmentArgs args;
    private NavController controller;
    private WeatherAppModel weather;
    private Main main;
    private Wind wind;
    private Sys sys;
    private ArrayList<Weather> weatherList = new ArrayList<>();
    @Inject
    WeatherDao dao;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null){
            args = WeatherFragmentArgs.fromBundle(getArguments());
        }
    }
    @Inject
    WeatherApi api;

    @Override
    protected FragmentWeatherBinding bind() {
        return FragmentWeatherBinding.inflate(getLayoutInflater());
    }


    @Override
    protected void callRequest() {

    }

    @Override
    protected void setupObservers() {
        viewModel.weatherLiveData.observe(getViewLifecycleOwner(), response -> {
            switch (response.status) {
                case SUCCESS:
                    wind = response.data.getWind();
                    weather = response.data;
                    main = response.data.getMain();
                    sys =  response.data.getSys();
                    weatherList = (ArrayList<Weather>) response.data.getWeather();
                    viewBinding.progress.setVisibility(View.GONE);
                    setWeather();
                    break;
                case LOADING:
                    viewBinding.progress.setVisibility(View.VISIBLE);
                    break;

                case ERROR:
                    viewBinding.progress.setVisibility(View.GONE);
                    Toast.makeText(requireActivity(),"Нет Интернета , скачиваю последние загруженные данные", Toast.LENGTH_LONG).show();
                    viewBinding.progress.setVisibility(View.GONE);
                    wind = dao.getWeather().getWind();
                    main = dao.getWeather().getMain();
                    sys = dao.getWeather().getSys();
                    weather = dao.getWeather();
                    weatherList = (ArrayList<Weather>) dao.getWeather().getWeather();
                    setWeather();
                    break;
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void setWeather() {

        Date date = Calendar.getInstance().getTime();
        @SuppressLint("SimpleDateFormat") DateFormat formatter = new SimpleDateFormat("EEEE, dd MMM yyyy | hh:mm");
        String today = formatter.format(date);
        viewBinding.tvCurrentDate.setText(today);


        viewBinding.currentCity.setText(weather.getName());
        viewBinding.tvWeatherStatus.setText(weather.getWeather().get(0).getMain());
        viewBinding.tvCurrentTemp.setText(String.valueOf((int) Math.round(main.getTemp())));
        viewBinding.tvCrntTempUp.setText((int) Math.round(main.getTempMax()) +" °C");
        viewBinding.tvCrntTempDown.setText((int) Math.round(main.getTempMin()) +" °C");
        viewBinding.tvHumidityPercents.setText(main.getHumidity() +"%");
        viewBinding.tvHumidity.setText("Humidity");
        viewBinding.tvMBar.setText(main.getPressure() +"mBar");
        viewBinding.tvPressure.setText("Pressure");
        viewBinding.tvWindSpeed.setText((int) Math.round(wind.getSpeed()) +"km/h");
        viewBinding.tvWind.setText("Wind");

        Integer sunrise = sys.getSunrise();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(sunrise);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("h:mm");

        viewBinding.tvSunriseTime.setText(dateFormat.format(calendar.getTime()));
        viewBinding.tvSunrise.setText("Sunrise");

        Integer sunset = sys.getSunrise();
        Calendar cal = Calendar.getInstance();
        calendar.setTimeInMillis(sunset);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat2 = new SimpleDateFormat("hh:mm");

        viewBinding.tvSunsetTime.setText(dateFormat2.format(cal.getTime()));
        viewBinding.tvSunset.setText("Sunset");
        viewBinding.tvDaytimeDuration.setText("13h 1m");
        viewBinding.tvDaytime.setText("Daytime");
        viewBinding.degree.setText("°C");
        viewBinding.ivHumidity.setImageResource(R.drawable.ic_humidity);
        viewBinding.ivDaytime.setImageResource(R.drawable.ic_daytime);
        viewBinding.ivPressure.setImageResource(R.drawable.ic__pressure);
        viewBinding.ivSunrise.setImageResource(R.drawable.ic__sunrise);
        viewBinding.ivSunset.setImageResource(R.drawable.ic__sunset);
        viewBinding.ivWind.setImageResource(R.drawable.ic_wind);



        String urlImg = "https://openweathermap.org/img/wn/" + weatherList.get(0).getIcon() + ".png";
        Glide.with(viewBinding.getRoot())
                .load(urlImg)
                .centerCrop()
                .into(viewBinding.ivWeatherIcon);

        Glide.with(viewBinding.getRoot())
                .load(R.drawable.img_afternoon_city)
                .centerCrop()
                .into(viewBinding.image);
    }


    @Override
    protected void setupListeners() {


    }

    @Override
    protected void setupViews() {
        viewModel = new ViewModelProvider(requireActivity()).get(WeatherViewModel.class);
        viewModel.setCityName(args.getCity());
        viewModel.getWeather();
        controller = Navigation.findNavController(requireActivity(),R.id.nav_host);
        viewBinding.currentCity.setOnClickListener(v
                -> controller.navigate(R.id.action_weatherFragment_to_cityFragment));
        // adapter = new WeatherAdapter();
        // viewBinding.rvForecast.setAdapter(adapter);
    }
}