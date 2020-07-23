/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imageuploadtest;



import java.net.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 *
 * @author Administrator
 */
public class ImageUploadTest {

    

    public static String ConvertByteArrayToString(byte[] data, int size)
    {
        String result = "";

        for (int i = 0; i < size; i++)
        {
            result += (char)data[i];
        }

        return result;
    }

    public static String GetFileContents(String filename)
    {
        String result = "";

        try
        {
            
           String path = System.getProperty("user.dir") + "\\src\\imageuploadtest\\" + filename;
           RandomAccessFile f = new RandomAccessFile(path, "rw");

           while (true)
           {
               byte[] data = new byte[1000];
               int size = f.read(data);


               if (size == -1) 
               {
                   break;
               }

               result += ConvertByteArrayToString(data, size);

           }

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return result;
    }
    

    
    public static void StartServer()
    {
        try
        {
                
                                
		ServerSocket server = new ServerSocket(80);
                
                
                while (true)
                {
                    
                    Socket socket = server.accept();
                                        
                    new Thread(() -> 
                    {
                        
                    
                       // System.out.println("socket opened: " + socket.toString() + "  " + format.format(date.getTime()));
                        
                        boolean image_data = false;
                        String string = "";
                        
                        
                        while (true)
                        {

                            try
                            {
                                
                                
                                if (socket.isClosed() == true || socket.isConnected() == false) 
                                {                   
                                    
                                    
                                    
                                    
                               //     System.out.println("socket closed: " + socket.toString() + "  " + format.format(date.getTime()));
                        
                                    break;
                                } 
                                
                                

                                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                                BufferedInputStream bis = new BufferedInputStream(socket.getInputStream());
                                BufferedOutputStream dataOut = new BufferedOutputStream(socket.getOutputStream());
                                PrintWriter out = new PrintWriter(socket.getOutputStream());


                                String input = "";

                                while (true)
                                {
                                    byte[] data = new byte[10000];
                                    int size = bis.read(data);
                                    if (size == -1) break;
                                    
                                    for (int i = 0; i < size; i++)
                                    {
                                        char c = (char)data[i];
                                        
                                        
                                        
                                        if (c != '\n')
                                        {                                            
                                            input += c;
                                        }
                                        else
                                        {
                                            
                                            
//                                                System.out.println(input);
                                                
                                            if (image_data == false)
                                            {
                                             //   System.out.println(input);
                                                
                                               // if (input.contains("Content-Disposition: form-data; name=\"the_file\""))
                                                if (input.contains("Content-Disposition: form-data; name=\"myval\""))
                                                {
                                                    image_data = true;
                                                }
                                            }
                                            else
                                            {
                                                
                                                 if (input.contains("-----"))
                                                {
                                                  //   System.out.println("end of data");
                                                                            
                                                    // String s = new String (string.getBytes ("iso-8859-1"), "UTF-8");
                                                    
                                                   
                                                    
                                                    for (int i2 = 0; i2 < 100; i2++)
                                                    {
                                                        char c2 = string.charAt(i2);
                                                        System.out.print(c2);
                                                    }
                                                    System.out.println();
                                                    
                                                    //System.out.println(string);
                                                    
                                                    image_data = false;
                                                }
                                                 else
                                                 {
                                                     string += input;
                                                 }
                                            }
                                            
                                            
                                            
                                            
                                            if (input.contains("GET / HTTP/1.1"))
                                            {
                                                
                                                String response = GetFileContents("index.html");

                                                int responseLength = response.length();

                                                // send HTTP Headers
                                                out.println("HTTP/1.1 200 OK");
                                                out.println("Server: RBTrading_version4");
                                                out.println("Date: " + new Date());
                                                out.println("Content-type: " + "text/html");
                                                out.println("Content-length: " + responseLength);
                                                out.println(); 
                                                out.flush(); 

                                                dataOut.write(response.getBytes(), 0, responseLength);
                                                dataOut.flush();
                                            }
                                            input = "";
                                        }
                                    }
                                    
                                }

                                

                            }
                            catch (Exception ex)
                            {
                                
                                if (ex.getMessage() != null && ex.getMessage().contains("Connection reset"))
                                {
                                    break;
                                }
                                
                            }

                        }
                        
                        
                        
                        
                    }).start();

                    
                }
                
                
        }
        catch (Exception ex)
        {
            System.out.println("ex 2");
            ex.printStackTrace();
        }
    }

    
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        // **** remember to compile before running..
        
        StartServer();
        
        
    }
    
    
}
