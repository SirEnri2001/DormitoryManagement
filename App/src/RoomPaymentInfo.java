package src;

public class RoomPaymentInfo extends PaymentInfo{
    public float Amount;
    public int RoomCapacity;   //房屋容量

    public float getAmount() {
        return Amount;
    }

    public void setAmount(float amount) {
        Amount = amount;
    }

    public int getRoomCapacity() {
        return RoomCapacity;
    }

    public void setRoomCapacity(int roomCapacity) {
        RoomCapacity = roomCapacity;
    }
}
