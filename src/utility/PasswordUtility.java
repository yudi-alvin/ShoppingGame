package utility;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import org.apache.commons.codec.binary.Base64;

/**
* <p>This class hashes the password for storage in the database</p>
* @author  BB - BB Coder, BB No Sleep
* @version 1.0
* @since   2018-04
*/
public class PasswordUtility {
    // The higher the number of iterations the more 
    // expensive computing the hash is for us and
    // also for an attacker.
    private static final int iterations = 20*1000;
    private static final int saltLen = 32;
    private static final int desiredKeyLen = 256;

    /** Computes a salted PBKDF2 hash of given plaintext password suitable for storing in a database. Empty passwords are not supported. 
     * @param password plain text password
     * @return a combination of salt and hashed password
    */
    public static String getSaltedHash(String password) {
        String saltedHash = "";
        try {
            byte[] salt = SecureRandom.getInstance("SHA1PRNG").generateSeed(saltLen);
            // store the salt with the password
            saltedHash = Base64.encodeBase64String(salt) + "$" + hash(password, salt);
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Password is unable to be hashed.");
        }
        return saltedHash;
    }

    /** Checks whether given plaintext password corresponds 
        to a stored salted hash of the password. 
    *@param password plain text password
    *@param stored the combination of salt and hashed password in the database
    *@return true if hashing of the given password matches hashed password
    */
    public static boolean check(String password, String stored) {
        String[] saltAndPass = stored.split("\\$");
        if (saltAndPass.length != 2) {
            throw new IllegalStateException(
                "The stored password have the form 'salt$hash'");
        }
        String hashOfInput = hash(password, Base64.decodeBase64(saltAndPass[0]));
        return hashOfInput.equals(saltAndPass[1]);
    }

    
    /** Hashes the password with given salt
     * @param password the plain text password
     * @param salt the salt 
    */
    private static String hash(String password, byte[] salt) {
        String hashedPwd = "";
        try {
            SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            SecretKey key = f.generateSecret(new PBEKeySpec(
                password.toCharArray(), salt, iterations, desiredKeyLen)
            );
            hashedPwd = Base64.encodeBase64String(key.getEncoded());
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            System.out.println("Unable to hash password.");
        }
        return hashedPwd;
    }
}