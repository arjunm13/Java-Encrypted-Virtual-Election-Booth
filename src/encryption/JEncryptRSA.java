
package encryption;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;


public class JEncryptRSA {
// PUBLIC AND PRIVATE KEYS FOR Host A
//  BigInteger modulus = new BigInteger("4158817544478891990887332699388746060980490741231277290559702504481613466980550442961706478901240858287061074274789302013459087253486188049671010462722451");
//  
//  BigInteger pubExp = new BigInteger("2783863571710165701756366128212716963200397432188587571344045992006907476723469011955324259588982550734021144915835869507295184475525800489326435352933629");
//  
//  BigInteger privExp = new BigInteger("2395546180722602066193082622862328107144358038135554220929904800445279801651718781695642149219208087949758635408245613872208809224050269093597648332110397");
//  
        
    // PUBLIC AND PRIVATE KEYS FOR Host B
//  BigInteger modulus = new BigInteger("4599262798794308708014101451216442903941121046201886206956480723533329439440024060097691229615851392513071323777864307630956530138394825517668034826098437");
//  
//  BigInteger pubExp = new BigInteger("2878420510383165733314211612508276097319619487646498929668095229783127291389903837072535693852639094406307595634344542615532828664237932111912378158009611");
//  
//  BigInteger privExp = new BigInteger("413607170569240106355162130796497957404600590139690077874292496075867173940387286464334397558879911527577289794644345851732962834469537529656069768837211");
//  
	
		public static byte[] encryptRSA(byte[] message, BigInteger pubExp, BigInteger modulus) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
			 byte[] messageBytes = message;
			 Cipher cipher = Cipher.getInstance("RSA/ECB/NoPadding");

             KeyFactory keyFactory = KeyFactory.getInstance("RSA");

             // initialize Public and private Key Objects 
             RSAPublicKeySpec pubKeySpec = new RSAPublicKeySpec(modulus, pubExp);
             //RSAPrivateKeySpec privKeySpec = new RSAPrivateKeySpec(modulus, privExp);


             RSAPublicKey pubKey = (RSAPublicKey) keyFactory.generatePublic(pubKeySpec);
             //RSAPrivateKey privKey = (RSAPrivateKey) keyFactory.generatePrivate(privKeySpec)

             // Initialize Cipher Encrypt mode
             cipher.init(Cipher.ENCRYPT_MODE, pubKey);
             // execute Encrypt
             byte[] cipherText = cipher.doFinal(messageBytes);
             return cipherText;
		}
	
    
     public static byte[] encryptRSA(String message, BigInteger pubExp, BigInteger modulus) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        /// Initialize cipher with RSA Parameters
        byte[] messageBytes = message.getBytes();
        
        return encryptRSA(messageBytes, pubExp, modulus);
     }
         
    public static String decryptRSA(byte[] encryptedMessage, BigInteger privExp, BigInteger modulus) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
                    /// Initialize cipher with RSA Parameters
         Cipher cipher = Cipher.getInstance("RSA/ECB/NoPadding");

         KeyFactory keyFactory = KeyFactory.getInstance("RSA");

         // initialize Public and private Key Objects 
       
         RSAPrivateKeySpec privKeySpec = new RSAPrivateKeySpec(modulus, privExp);


         RSAPrivateKey privKey = (RSAPrivateKey) keyFactory.generatePrivate(privKeySpec);


         // Initialize and execute Cipher Dencrypt mode, and then print in
         // String format
         cipher.init(Cipher.DECRYPT_MODE, privKey);

         byte[] plainText = cipher.doFinal(encryptedMessage);
         String outputText = new String(plainText, "UTF-8");
       
        return outputText;
    }
    
    public static byte[] decryptRSABytes(byte[] encryptedMessage, BigInteger privExp, BigInteger modulus) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
        /// Initialize cipher with RSA Parameters
		Cipher cipher = Cipher.getInstance("RSA/ECB/NoPadding");
		
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		
		// initialize Public and private Key Objects 
		
		RSAPrivateKeySpec privKeySpec = new RSAPrivateKeySpec(modulus, privExp);
		
		
		RSAPrivateKey privKey = (RSAPrivateKey) keyFactory.generatePrivate(privKeySpec);
		
		
		// Initialize and execute Cipher Dencrypt mode, and then print in
		// String format
		cipher.init(Cipher.DECRYPT_MODE, privKey);
		byte[] msg = cipher.doFinal(encryptedMessage);
		
		int lastZeroIndex = 0; 
		//check if message has leading zeros
		if (msg[lastZeroIndex] == 0)
		{
			for (lastZeroIndex = 0; lastZeroIndex < msg.length - 1; lastZeroIndex++)
			{
				//if the next element is non zero, break.
				if (msg[lastZeroIndex + 1] != 0)
				{
					break;
				}
			}
			
			return Arrays.copyOfRange(msg, lastZeroIndex + 1, msg.length);
		}
		
		return msg;
		
		
    }
    
    
    /// SIgned Methods, Encrypt with A's PRivate key, Decrypt with A's Public key etc. 
	public static byte[] encryptRSASigned(byte[] message, BigInteger privExp, BigInteger modulus) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
       byte[] messageBytes = message;
       Cipher cipher = Cipher.getInstance("RSA/ECB/NoPadding");

             KeyFactory keyFactory = KeyFactory.getInstance("RSA");

             // initialize Public and private Key Objects 
              RSAPrivateKeySpec privKeySpec = new RSAPrivateKeySpec(modulus, privExp);
             //RSAPrivateKeySpec privKeySpec = new RSAPrivateKeySpec(modulus, privExp);


             RSAPrivateKey privKey = (RSAPrivateKey) keyFactory.generatePrivate(privKeySpec);
             //RSAPrivateKey privKey = (RSAPrivateKey) keyFactory.generatePrivate(privKeySpec)

             // Initialize Cipher Encrypt mode
             cipher.init(Cipher.ENCRYPT_MODE, privKey);
             // execute Encrypt
             byte[] cipherText = cipher.doFinal(messageBytes);
             return cipherText;
    }

    public static byte[] encryptRSASigned(String message, BigInteger pubExp, BigInteger modulus) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        /// Initialize cipher with RSA Parameters
        byte[] messageBytes = message.getBytes();
        
        return encryptRSA(messageBytes, pubExp, modulus);
     }
	 // Decrypt with Public key, Passing in PubExp
      public static byte[] decryptRSASigned(byte[] encryptedMessage, BigInteger pubExp, BigInteger modulus) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
                    /// Initialize cipher with RSA Parameters
         Cipher cipher = Cipher.getInstance("RSA/ECB/NoPadding");

         KeyFactory keyFactory = KeyFactory.getInstance("RSA");

         // initialize Public and private Key Objects 
       
             RSAPublicKeySpec pubKeySpec = new RSAPublicKeySpec(modulus, pubExp);
             //RSAPrivateKeySpec privKeySpec = new RSAPrivateKeySpec(modulus, privExp);


             RSAPublicKey pubKey = (RSAPublicKey) keyFactory.generatePublic(pubKeySpec);


         // Initialize and execute Cipher Dencrypt mode, and then print in
         // String format
         cipher.init(Cipher.DECRYPT_MODE, pubKey);

         byte[] plainText = cipher.doFinal(encryptedMessage);
        // String outputText = new String(plainText, "UTF-8");
       
 		/*int lastZeroIndex = 0; 
 		//check if message has leading zeros
 		if (plainText[lastZeroIndex] == 0)
 		{
 			for (lastZeroIndex = 0; lastZeroIndex < plainText.length - 1; lastZeroIndex++)
 			{
 				//if the next element is non zero, break.
 				if (plainText[lastZeroIndex + 1] != 0)
 				{
 					break;
 				}
 			}
 			
 			return Arrays.copyOfRange(plainText, lastZeroIndex + 1, plainText.length);
 		}*/
        return plainText;
    }
     
}
