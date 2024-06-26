package service;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.URI;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

//  +-------------------------------------------------------------------+
//  |   class CrptApi                                                   |
//  +-------------------------------------------------------------------+
public class CrptApi {
    private final int RESPONSE_OK = 200;

    private final TimeUnit timeUnit;
    private final int requestLimit;
    private final Semaphore semaphore;
    private final ScheduledExecutorService scheduler;

    //  +-------------------------------------------------------------------+
    //  |   class CrptApi                                                   |
    //  +-------------------------------------------------------------------+
    //  |   ctor                                                            |
    //  +-------------------------------------------------------------------+
    public CrptApi(TimeUnit timeUnit, int requestLimit) {
        this.timeUnit = timeUnit;
        this.requestLimit = requestLimit;
        this.semaphore = new Semaphore(requestLimit);
        this.scheduler = Executors.newScheduledThreadPool(1);
    }

    //  +-------------------------------------------------------------------+
    //  |   class CrptApi                                                   |
    //  +-------------------------------------------------------------------+
    //  |   + createDocument                                                |
    //  +-------------------------------------------------------------------+
    public void createDocument(final Document document, final String sign, final String url){
        try {
            semaphore.acquire();
            scheduler.scheduleAtFixedRate(() -> semaphore.release(), 1, 1, timeUnit);

            HttpRequest request = makeRequest(document, url);
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

            if(response.statusCode() == RESPONSE_OK){
                //  the response was received successfully. processing the response body
            }
            else{
                //  otherwise, error handling
            }
        }
        catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    //  +-------------------------------------------------------------------+
    //  |   class CrptApi                                                   |
    //  +-------------------------------------------------------------------+
    //  |   - makeRequest                                                   |
    //  +-------------------------------------------------------------------+
    private HttpRequest makeRequest(final Document doc, final String url){

        String document = new Gson().toJson(doc);

        return HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(document))
                .build();
    }

    //  +-------------------------------------------------------------------+
    //  |   class Document                                                  |
    //  +-------------------------------------------------------------------+
    public class Document{
        private Description description;
        private String doc_id;
        private String doc_status;
        private String doc_type;
        private boolean importRequest;
        private String owner_inn;
        private String participant_inn;
        private String producer_inn;
        private String production_date;
        private String production_type;
        private List<Product> products;
        private String reg_date;
        private String reg_number;

        public Description getDescription() {
            return description;
        }

        public void setDescription(Description description) {
            this.description = description;
        }

        public String getDocId() {
            return doc_id;
        }

        public void setDocId(String docId) {
            this.doc_id = docId;
        }

        public String getDocStatus() {
            return doc_status;
        }

        public void setDocStatus(String doc_status) {
            this.doc_status = doc_status;
        }

        public String getDocType() {
            return doc_type;
        }

        public void setDocType(String doc_type) {
            this.doc_type = doc_type;
        }

        public boolean isImportRequest() {
            return importRequest;
        }

        public void setImportRequest(boolean importRequest) {
            this.importRequest = importRequest;
        }

        public String getOwnerInn() {
            return owner_inn;
        }

        public void setOwnerInn(String owner_inn) {
            this.owner_inn = owner_inn;
        }

        public String getParticipantInn() {
            return participant_inn;
        }

        public void setParticipantInn(String participant_inn) {
            this.participant_inn = participant_inn;
        }

        public String getProducerInn() {
            return producer_inn;
        }

        public void setProducerInn(String producer_inn) {
            this.producer_inn = producer_inn;
        }

        public String getProductionDate() {
            return production_date;
        }

        public void setProductionDate(String production_date) {
            this.production_date = production_date;
        }

        public String getProductionType() {
            return production_type;
        }

        public void setProductionType(String production_type) {
            this.production_type = production_type;
        }

        public List<Product> getProductsList() {
            return products;
        }

        public void setProductsList(List<Product> products) {
            this.products = products;
        }

        public String getRegDate() {
            return reg_date;
        }

        public void setRegDate(String regDate) {
            this.reg_date = regDate;
        }

        public String getRegNumber() {
            return reg_number;
        }

        public void setRegNumber(String reg_number) {
            this.reg_number = reg_number;
        }
    }

    //  +-------------------------------------------------------------------+
    //  |   class Description                                               |
    //  +-------------------------------------------------------------------+
    private class Description{
        private String participantInn;

        public String getParticipantInn() {
            return participantInn;
        }

        public void setParticipantInn(String participantInn) {
            this.participantInn = participantInn;
        }
    }

    //  +-------------------------------------------------------------------+
    //  |   class Products                                                  |
    //  +-------------------------------------------------------------------+
    private class Product{
        private String certificate_document;
        private String certificate_document_date;
        private String certificate_document_number;
        private String owner_inn;
        private String producer_inn;
        private String production_date;
        private String tnved_code;
        private String uit_code;
        private String uitu_code;

        public String getCertificateDocument() {
            return certificate_document;
        }

        public void setCertificateDocument(String certificate_document) {
            this.certificate_document = certificate_document;
        }

        public String getCertificateDocumentDate() {
            return certificate_document_date;
        }

        public void setCertificateDocumentDate(String certificate_document_date) {
            this.certificate_document_date = certificate_document_date;
        }

        public String getCertificateDocumentNumber() {
            return certificate_document_number;
        }

        public void setCertificateDocumentNumber(String certificate_document_number) {
            this.certificate_document_number = certificate_document_number;
        }

        public String getOwnerInn() {
            return owner_inn;
        }

        public void setOwnerInn(String owner_inn) {
            this.owner_inn = owner_inn;
        }

        public String getProducerInn() {
            return producer_inn;
        }

        public void setProducerInn(String producer_inn) {
            this.producer_inn = producer_inn;
        }

        public String getProductionDate() {
            return production_date;
        }

        public void setProductionDate(String production_date) {
            this.production_date = production_date;
        }

        public String getTnvedCode() {
            return tnved_code;
        }

        public void setTnvedCode(String tnved_code) {
            this.tnved_code = tnved_code;
        }

        public String getUitCode() {
            return uit_code;
        }

        public void setUitCode(String uit_code) {
            this.uit_code = uit_code;
        }

        public String getUituCode() {
            return uitu_code;
        }

        public void setUituCode(String uitu_code) {
            this.uitu_code = uitu_code;
        }
    }
}