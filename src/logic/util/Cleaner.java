/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

/**
 *
 * @author Mats
 */
public class Cleaner {
    public static void close(Reader arg) {
        if(arg != null) {
            try {
                arg.close();
            } catch(IOException e) {
            }
        }
    }
    
//    public static void close(FileReader arg) {
//        if(arg != null) {
//            try {
//                arg.close();
//            } catch(IOException e) {
//            }
//        }
//    }
}
