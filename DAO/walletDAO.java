package eStoreProduct.DAO;

import eStoreProduct.model.wallet;

public interface walletDAO {
	public wallet getWalletAmount(int custid);

	public void updatewallet(double amt, int custid);

}
