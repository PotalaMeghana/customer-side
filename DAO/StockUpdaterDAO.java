package eStoreProduct.DAO;

public interface StockUpdaterDAO {
    void updateStocks(int prod_id, int qty);
    public int getProductStock(int productId);
}
