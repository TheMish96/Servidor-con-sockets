/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.extraerxml;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author MIRSHA
 */
public class Conectar {
    
    
    private static Connection conn;
    private static final String driver= "com.mysql.jdbc.Driver";
    private static final String user= ("consulta");
    private static final String password=("123");
    private static final String url= "jdbc:mysql://localhost:3306/facturas_Electronicas";


public Connection conectar() {

    conn= null;
        try{
            
            Class.forName(driver);
            conn= DriverManager.getConnection(url,user,password);
            if (conn !=null){
           
            }
        }catch (Exception e){
            System.out.println("error al conectar ");
        }
        return conn;
    }



public void Insertar_Provedores(String Rfc,String apellidop,String apellidoM,String direccion,String CP,String telefono,String Email){

     Connection conn;   
     PreparedStatement pst;
     ResultSet rs;
    String sql;
     try{
         Class.forName(this.driver);
         conn=DriverManager.getConnection(
         this.url,
         this.user,
         this.password);
         pst=conn.prepareStatement(sql);
         rs=pst.executeQuery();
         
         while(rs.next()){
          if(rs.next()){
              JOptionPane.showMessageDialog(null,"conexion Exitosa");
              
              
         }         
     }}  catch (SQLException ex) {
            Logger.getLogger(Conectar.class.getName()).log(Level.SEVERE, null, ex);
        }
       
  
}
    
    
    


    public static void main(String[] args) {
        Conectar m = new Conectar ();
        m.conectar();
        
        
    }

}
 