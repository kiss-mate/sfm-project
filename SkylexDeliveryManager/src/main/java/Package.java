public class Package {
    public int Id;
    public int Weight;
    public int DeliveryId;
    public String Content;
    public String Destination;
    public boolean Selected;

    @Override
    public String toString() {
        return Selected ? String.format("%s (%dkg), to %s", Content, Weight, Destination)
                : String.format("NOT IN DELIVERY | %s (%dkg), to %s", Content, Weight, Destination);
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getWeight() {
        return Weight;
    }

    public void setWeight(int weight) {
        Weight = weight;
    }

    public int getDeliveryId() {
        return DeliveryId;
    }

    public void setDeliveryId(int deliveryId) {
        DeliveryId = deliveryId;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getDestination() {
        return Destination;
    }

    public void setDestination(String destination) {
        Destination = destination;
    }

    public boolean isSelected() {
        return Selected;
    }

    public void setSelected(boolean selected) {
        Selected = selected;
    }
}