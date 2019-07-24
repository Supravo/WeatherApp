package com.stcet.weatherapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.stcet.easyweather.Helper.ForecastCallback;
import com.stcet.easyweather.Helper.TempUnitConverter;
import com.stcet.easyweather.Helper.WeatherCallback;
import com.stcet.easyweather.WeatherMap;
import com.stcet.easyweather.retrofit.models.ForecastResponseModel;
import com.stcet.easyweather.retrofit.models.Weather;
import com.stcet.easyweather.retrofit.models.WeatherResponseModel;

public class MainActivity extends AppCompatActivity {

    public final String APP_ID = BuildConfig.OWM_API_KEY;
    String city = "Kolkata";

    @Bind(R.id.weather_title)
    TextView weatherTitle;
    @Bind(R.id.refresh)
    ImageButton refresh;
    @Bind(R.id.weather_icon)
    ImageView weatherIcon;
    @Bind(R.id.location)
    TextView location;
    @Bind(R.id.condition)
    TextView condition;
    @Bind(R.id.temp)
    TextView temp;
    @Bind(R.id.tvHumidity)
    TextView tvHumidity;
    @Bind(R.id.tvPressure)
    TextView tvPressure;
    @Bind(R.id.tvWind)
    TextView tvWind;
    @Bind(R.id.tvWindDeg)
    TextView tvWindDeg;
    @Bind(R.id.et_city)
    EditText etCity;
    @Bind(R.id.tv_go)
    TextView tvGo;
    @Bind(R.id.textLayout)
    LinearLayout textLayout;
    @Bind(R.id.humidity_desc)
    TextView humidityDesc;
    @Bind(R.id.pres_desc)
    TextView presDesc;
    @Bind(R.id.ws_desc)
    TextView wsDesc;
    @Bind(R.id.wd_desc)
    TextView wdDesc;
    @Bind(R.id.ll_extraWeather)
    LinearLayout llExtraWeather;
    @Bind(R.id.weatherCard)
    CardView weatherCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        loadWeather(city);
    }

    @OnClick(R.id.refresh)
    public void refresh() {
        loadWeather(city);
    }

    private void loadWeather(String city) {
        WeatherMap weatherMap = new WeatherMap(this, APP_ID);
        weatherMap.getCityWeather(city, new WeatherCallback() {
            @Override
            public void success(WeatherResponseModel response) {
                populateWeather(response);
            }

            @Override
            public void failure(String message) {
                Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });

        weatherMap.getCityForecast(city, new ForecastCallback() {
            @Override
            public void success(ForecastResponseModel response) {
                ForecastResponseModel responseModel = response;
            }

            @Override
            public void failure(String message) {
                Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void populateWeather(WeatherResponseModel response) {

        Weather weather[] = response.getWeather();
        condition.setText(weather[0].getMain());
        temp.setText(TempUnitConverter.convertToCelsius(response.getMain().getTemp()).intValue() + " °C");
        location.setText(response.getName());

        tvHumidity.setText(response.getMain().getHumidity() + "%");
        tvPressure.setText(response.getMain().getPressure() + " hPa");
        tvWind.setText(response.getWind().getSpeed() + "m/s");
        tvWindDeg.setText(response.getWind().getDeg() + "°");

        String link = weather[0].getIconLink();
        Picasso.with(this).load(link).into(weatherIcon);
    }

    @OnClick(R.id.tv_go)
    public void go() {
        city = etCity.getText().toString().trim();
        loadWeather(city);
    }
}
