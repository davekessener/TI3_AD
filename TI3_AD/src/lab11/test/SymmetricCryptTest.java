package lab11.test;

import lab11.Crypt;
import lab11.SymmetricCrypt;

public class SymmetricCryptTest extends CryptTest
{
    @Override
    protected Crypt getImplementation()
    {
        return new SymmetricCrypt(20151206L);
    }
}
