package com.example.demo;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Consulta extends Thread{

    private String inicio;
    private String fin;
    private String origen;
    private String destino;

    public Consulta(String inicio, String fin, String origen, String destino){
        this.inicio = inicio;
        this.fin = fin;
        this.origen = origen;
        this.destino = destino;
    }

    public void run(){
        try {
            OkHttpClient client = new OkHttpClient();
            MediaType mediaType = MediaType.parse("text/xml");
            okhttp3.RequestBody body = okhttp3.RequestBody.create(mediaType, "<?xml version=\"1.0\" encoding=\"utf-8\"?><soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
                    "<soap:Body>" +
                    "<GetFlights xmlns=\"http://tempuri.org/\">" +
                    "<departing>" + origen + "</departing>" +
                    "<goingTo>" + destino + "</goingTo>" +
                    "<returnAirport></returnAirport>" +
                    "<destinationFinal></destinationFinal>" +
                    "<tripType>return</tripType>" +
                    "<dateDeparting>" + inicio +" </dateDeparting>" +
                    "<dateReturn>" + fin + "</dateReturn>" +
                    "<passengersADT>1</passengersADT>" +
                    "<passengersCHD>0</passengersCHD>" +
                    "<maxStops>3</maxStops>" +
                    "<bookingClass>Y</bookingClass>" +
                    "<airline></airline>" +
                    "<flexibleDates></flexibleDates>" +
                    "<passengerType></passengerType>" +
                    "<studentFare></studentFare>" +
                    "</GetFlights>" +
                    "</soap:Body>" +
                    "</soap:Envelope>");
            Request request = new Request.Builder()
                    .url("https://balanceador.mundojoven.com/FlightsEngine.asmx?wsdl")
                    .post(body)
                    .addHeader("TripType", "return")
                    .addHeader("DateDeparting", inicio)
                    .addHeader("DateReturn", fin)
                    .addHeader("PassengersADT", "1")
                    .addHeader("MaxStops", "3")
                    .addHeader("BookingClass", "Y")
                    .addHeader("Content-Type", "text/xml")
                    .build();

            Response response = client.newCall(request).execute();
            System.out.println(response.body().string());
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }


    }


}
