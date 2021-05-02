# Let's go Ride
> Aplikacja dla rowerzystów do sprawdzania pogody i wyznaczania tras.
## Spis treści
* [Informacje ogólne](#Informacje-ogólne)
* [Dokumentacja](#dokumentacja)
* [Technologia](#Technologie)
* [Przykład kodu](#przykład-kodu)
* [Funkcjonalności](#Funkcjonalności)
* [Status](#status)
* [Kontakt](#kontakt)
## Informacje ogólne
Add more general information about project. What the purpose of the project is? Motivation?
Aplikacja ma na celu ułatwienie rowerzystom
> Applikacja umożliwia pokazanie aktualnej pogody w Twojej lokalizacji oraz wybranie innej lokalizacji i pokazanie pogody. 
> Aplikacja umożliwia pokazanie trasy rowerowej i naszej obecnej lokalizacji.
## Dokumentacja
![Logo aplikacji](./img/logo.png)
![Ekran z pogodą](./img/pogoda.png)
![Zmiana miasta dla pogody](./img/wpisz-miasto.png)
![Mapa](./img/mapa.png)
## Technologie
* Android Studio 4.1.1
* Android SDK 10.0(Q) API Level 29
* OpenWeatherMap API
* Google maps API
* Google directions API
## Przykład kodu
* Zmiana tła w zależności od aktualnej pogody:
```ruby
private static String updateWeatherIcon(int condition)
    {
        if(condition>=0 && condition<=300)
        {
            return "thunderstorm";
        }
        else if(condition>300 && condition<=500)
        {
            return "lightrain";
        }
        else if(condition>=500 && condition<=600)
        {
            return "shower";
        }
        else if(condition>=600 && condition<=700)
        {
            return "snownight";
        }
        else if(condition>=701 && condition<=771)
        {
            return "fog";
        }
         else if(condition>=772 && condition<=800)
        {
            return "overcast";
        }
        else if(condition==800)
        {
            return "sunny";
        }
        else if(condition>=801 && condition<=804)
        {
            return "cloud";
        }
        else if(condition>=900 && condition<=902)
        {
            return "thunderstorm";
        }
        else if(condition==903)
        {
            return "snow";
        }
        if(condition==904)
        {
            return "sunny";
        }
        if(condition>=905 && condition<=1000)
        {
            return "thunderstormnight";
        }

        return "dunno";

    }

    public String getmTemperature() {
        return mTemperature +"°C";
    }

    public String getMicon() {
        return micon;
    }

    public String getMcity() {
        return mcity;
    }

    public String getmWeatherType() {
        return mWeatherType;
    }
}
```
Załadowanie pogody dla aktualnej lokalizacji:
```ruby
private void getWeatherForCurrentLocation() {
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mLocationLisner = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                String Latitude = String.valueOf(location.getLatitude());
                String Longitude = String.valueOf(location.getLongitude());
                RequestParams params =new RequestParams();
                params.put("lat",Latitude);
                params.put("lon",Longitude);
                params.put("appid",APP_ID);
                letsdoSomeNetworkig(params);

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                //nie mozna uzyskac lokalizacji
            }
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE);
            return;
        }
        mLocationManager.requestLocationUpdates(Location_Provider, MIN_TIME, MIN_DISTANCE, mLocationLisner);
    }
```
Pokazanie aktualnej lokalizacji:
```ruby

```
Zmiana tła w zależności od aktualnej pogody:
`put-your-code-here`
Zmiana tła w zależności od aktualnej pogody:
`put-your-code-here`
## Funkcjonalności
List of features ready and TODOs for future development
* Awesome feature 1
* Awesome feature 2
* Awesome feature 3
To-do list:
* Wow improvement to be done 1
* Wow improvement to be done 2
## Status
Project is: _in progress_, _finished_, _no longer continue_ and why?
## Kontakt
Created by Natalia Gościnna & Damian Jaszewski - feel free to contact with us!
