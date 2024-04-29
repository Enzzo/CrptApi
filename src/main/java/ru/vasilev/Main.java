package ru.vasilev;

import com.google.gson.GsonBuilder;
import service.CrptApi;
import com.google.gson.Gson;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {
        CrptApi service = new CrptApi(TimeUnit.SECONDS, 1);
        CrptApi.Document jsonDocument = makeDocument("src/main/resources/request.json");
        service.createDocument(jsonDocument, "sign", "https://ismp.crpt.ru/api/v3/lk/documents/create");
    }

    private static CrptApi.Document makeDocument(final String src){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = new String();
        try {
            byte[] buffer = new byte[1024];
            buffer = Files.readAllBytes(Paths.get(src));
            json = new String(buffer);
        }
        catch(IOException e){
            System.out.printf("%s\n",e.getMessage());
        }

        return gson.fromJson(json, CrptApi.Document.class);
    }
}