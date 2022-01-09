/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.handycredit.systems.core.utils;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author RayGdhrt
 */
public class AppUtils {

    public static String CLOUDINARY_CLOUD_NAME = "revival-gateway";
    public static String CLOUDINARY_API_KEY = "114516888855596";
    public static String CLOUDINARY_API_SECRET = "gG2NUayJtKGuFcQln1yvxYPTMzQ";

    public static String generateOTP(int len) {
        System.out.println("Generating OTP using random() : ");
        System.out.print("You OTP is : ");

        // Using numeric values
        String numbers = "0123456789";

        // Using random method
        Random rndm_method = new Random();

        char[] otp = new char[len];

        for (int i = 0; i < len; i++) {
            // Use of charAt() method : to get character value
            // Use of nextInt() as it is scanning the value as int
            otp[i]
                    = numbers.charAt(rndm_method.nextInt(numbers.length()));
        }
        return String.valueOf(otp);
    }

    /**
     * Uploads image bytes to cloudinary and returns the public URL to access
     * the image. The public_id represents the name used to store the image. You
     * can also specify the directory by including a "/" in the public_id eg
     * myfolder/pics/myImageName
     *
     * @param contents - the image bytes
     * @param public_id - unique image identifier
     * @return
     */
    public String uploadCloudinaryImage(byte[] contents, String public_id) {
        System.out.println("Started image upload...");
        try {
            Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                    "cloud_name", CLOUDINARY_CLOUD_NAME,
                    "api_key", CLOUDINARY_API_KEY,
                    "api_secret", CLOUDINARY_API_SECRET,
                    "secure", true));
            System.out.println(Arrays.toString(contents));
            Map uploadResult = cloudinary.uploader().upload(contents, ObjectUtils.asMap("public_id", public_id));
            String imageUrl = uploadResult.get("secure_url").toString();
            System.out.println("Image url = " + imageUrl);
            return imageUrl;
        } catch (IOException ex) {
            Logger.getLogger(AppUtils.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

    public static void main(String[] args) {
        int length = 4;
        System.out.println(generateOTP(length));
    }
}
