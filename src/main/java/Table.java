public class Table {
    private int tableNumber;
    private boolean isReserved;

    public Table(int tableNumber) {
        this.tableNumber = tableNumber;
        this.isReserved = false;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public boolean isReserved() {
        return isReserved;
    }

    public void reserveTable() {
        isReserved = true;
    }

    public void makeAvailable() {
        isReserved = false;
    }
}
