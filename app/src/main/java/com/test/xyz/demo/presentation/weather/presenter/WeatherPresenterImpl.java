package com.test.xyz.demo.presentation.weather.presenter;

import com.test.xyz.demo.domain.interactor.weather.WeatherInteractor;

import javax.inject.Inject;

public class WeatherPresenterImpl implements WeatherPresenter, WeatherInteractor.WeatherInfoActionCallback {
    private WeatherView mainView;
    private WeatherInteractor weatherInteractor;

    @Inject
    public WeatherPresenterImpl(WeatherView mainView, WeatherInteractor weatherInteractor) {
        this.mainView = mainView;
        this.weatherInteractor = weatherInteractor;
    }

    @Override
    public void requestWeatherInformation() {
        mainView.showBusyIndicator();
        weatherInteractor.getWeatherInformation(mainView.getUserNameText(), mainView.getCityText(), this);
    }

    @Override
    public void onUserNameValidationError(int messageID) {
        mainView.hideBusyIndicator();
        mainView.showUserNameError(messageID);
    }

    @Override
    public void onCityValidationError(int messageID) {
        mainView.hideBusyIndicator();
        mainView.showCityNameError(messageID);
    }

    @Override
    public void onSuccess(String data) {
        mainView.hideBusyIndicator();
        mainView.showResult(data);
    }

    @Override
    public void onFailure(int errorMessage) {
        mainView.hideBusyIndicator();
        mainView.showGenericError(errorMessage);
    }
}