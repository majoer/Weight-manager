/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic.io;

import entity.WeightEntry;

/**
 *
 * @author Mats
 */
public interface ParserInterface {

    public WeightEntry[] process(String line);
    
}
