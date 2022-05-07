package repository;

public interface IDeliveryRepository {


    /**
     * Updates one entry by id in the Delivery table
     * @param id id of the updated Delivery object
     * @param name name param for Delivery object
     */

    public void UpdateDelivery();

    public void SaveDelivery();

    public void DeleteDelivery();

    public List<Delivery> getDelivery();

}
