/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package futoshiki;
import javax.swing.*;
import metier.*;
/**
 *
 * @author aymane
 */
public class Futoshiki
{
    public static void main(String[] args)
    {
       Interface game = new Interface("Futoshiki game");
       game.setVisible(true);
    }
}
