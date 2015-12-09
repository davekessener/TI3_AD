package lab11.test;

import lab11.Crypt;
import lab11.HybridCrypt;
import lab11.RSA;

public class HybridCryptTest extends CryptTest
{
    @Override
    protected Crypt getImplementation()
    {
        return new HybridCrypt(20151206L, new RSA(20151207L));
    }
}
