package quri.teelab.api.teelab.analytics.interfaces.rest.resources;

public class ManufacturerAnalyticsResource {
    private String manufacturerId;
    private int totalOrdersReceived;
    private int pendingFulfillments;
    private int producedProjects;
    private double avgFulfillmentTimeDays;

    public ManufacturerAnalyticsResource(String manufacturerId, int totalOrdersReceived, int pendingFulfillments, int producedProjects, double avgFulfillmentTimeDays) {
        this.manufacturerId = manufacturerId;
        this.totalOrdersReceived = totalOrdersReceived;
        this.pendingFulfillments = pendingFulfillments;
        this.producedProjects = producedProjects;
        this.avgFulfillmentTimeDays = avgFulfillmentTimeDays;
    }

    public String getManufacturerId() {
        return manufacturerId;
    }

    public int getTotalOrdersReceived() {
        return totalOrdersReceived;
    }

    public int getPendingFulfillments() {
        return pendingFulfillments;
    }

    public int getProducedProjects() {
        return producedProjects;
    }

    public double getAvgFulfillmentTimeDays() {
        return avgFulfillmentTimeDays;
    }
}

