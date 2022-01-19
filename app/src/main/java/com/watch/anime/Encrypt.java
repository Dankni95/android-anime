package com.watch.anime;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

public class Encrypt {
    private byte[] key;
    private byte[] iv;

    private static final String ALGORITHM="AES";

    public Encrypt(byte[] key, byte[] iv) {
        this.key = key;
        this.iv = iv;
    }

    public static byte[]  encrypt(byte[] plainText) throws Exception{

        byte[] key= DatatypeConverter.parseHexBinary("3235373436353338353932393338333936373634363632383739383333323838");
        byte[] iv= DatatypeConverter.parseHexBinary("34323036393133333738303038313335");

        SecretKeySpec secretKey=new SecretKeySpec(key,ALGORITHM);
        IvParameterSpec ivParameterSpec=new IvParameterSpec(iv);
        Cipher cipher=Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE,secretKey,ivParameterSpec);
        return cipher.doFinal(plainText);
    }

    public byte[] getKey() {
        return key;
    }

    public void setKey(byte[] key) {
        this.key = key;
    }

    public byte[] getIv() {
        return iv;
    }

    public void setIv(byte[] iv) {
        this.iv = iv;
    }
}