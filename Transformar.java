/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jematormal91
 */
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
 
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Spring;
import javax.swing.SpringLayout;
import javax.swing.SwingUtilities;
 
public class Transformar extends JPanel {
 
   private static JTextField input = new JTextField();
   private static JTextField output = new JTextField();
    
   public Transformar() {
      super(new BorderLayout());
       
      // Create the form to allow the user to enter:
      // the value to be converted
      // and the converted value
       
      JPanel form = new JPanel();
      JLabel inputLabel = new JLabel("");
      inputLabel.setLabelFor(input);
      JLabel outputLabel = new JLabel("");
      outputLabel.setLabelFor(output);
      output.setEditable(false);
       
      form.add(inputLabel);
      form.add(outputLabel);
      
      form.add(input);
      form.add(output);
      
      
      springLayoutForm(form, 2, 2);
       
      add(BorderLayout.CENTER, form);
      
       
      // Add the buttons
       
      JButton CMaPLG = new JButton("CM ---> PLG");
      JButton PLGaCM = new JButton("PLG ---> CM");
      
      JPanel controls = new JPanel();
      controls.add(CMaPLG);
      controls.add(PLGaCM);
      
      add(BorderLayout.SOUTH, controls);
      
 
      // finally wire up the buttons
      // We do this by adding an ActionListener to each button
      // We use an inner class to call the method we want invoked
      // when the button is pressed
 
      PLGaCM.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            transformarCM();
         }
      });
      CMaPLG.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            transformarPLG();
         }
      });
   }
    
   /**
    * Performs the actual conversion
    */
    
   private void transformarCM() {
      try {
          
         // Get the inches valued entered and convert it to a double
          
         double pulgadas = Double.parseDouble(input.getText());
          
         // Heres where we do the actual conversion from inches to cm's
          
         double centimetros = pulgadas * 2.54;
          
         // Finally display the centimetre value
         // We use the NumberFormat class to format the double value
          
         output.setText(
            NumberFormat.getInstance().format(centimetros));
       
      } catch (Exception ex) {
          
         // If the value entered by the user cannot be parsed
         // then an Exception is thrown by parseDouble()
         // And execution will end up here
          
         JOptionPane.showMessageDialog(this,
            "Error. Numero no es valido.");
      }
          
   }
   private void transformarPLG() {
      try {
          
         // Heres where we do the actual conversion from cm to inches
          
         double centimetros = Double.parseDouble(input.getText());
         
         // Get the inches valued entered and convert it to a double
          
         double pulgadas = centimetros * 0.393701;
         
          
         // Finally display the centimetre value
         // We use the NumberFormat class to format the double value
          
         output.setText(
            NumberFormat.getInstance().format(pulgadas));
       
      } catch (Exception ex) {
          
         // If the value entered by the user cannot be parsed
         // then an Exception is thrown by parseDouble()
         // And execution will end up here
          
         JOptionPane.showMessageDialog(this,
            "Error. Numero no es valido.");
      }
   }
 
   /**
    * Create and setup the main window
    */
    
    private static void createAndShowGUI() {
      JFrame frame = new JFrame("Centimetros <---> Pulgadas");
       
      // We want the application to exit when the window is closed
       
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
      // Add the converter panel to window
       
      frame.getContentPane().add(new Transformar());
       
      // Display the window.
       
      frame.pack();
      frame.setVisible(true);
   }
 
   public static void main(String[] args) {
      SwingUtilities.invokeLater(new Runnable() {
         public void run() {
            createAndShowGUI();
         }
      });
   }
 

//   * Aligns the rows and columns of a form
//   * using a SpringLayout
    
   private static void springLayoutForm(Container form, int rows, int cols) {
       
      SpringLayout layout = new SpringLayout();
      form.setLayout(layout);
    
      // Align labels and text fields in each column
       
      Spring x = Spring.constant(10);
      for (int c = 0; c < cols; c++) {
         Spring width = Spring.constant(0);
         for (int r = 0; r < rows; r++) {
            width = Spring.max(width, 
               getConstraints(r, c, form, cols).getWidth());
         }
         for (int r = 0; r < rows; r++) {
            SpringLayout.Constraints constraints =
               getConstraints(r, c, form, cols);
            constraints.setX(x);
            constraints.setWidth(width);
         }
         x = Spring.sum(x, Spring.sum(width, Spring.constant(5)));
      }
    
      // Align all labels and text fields in each row
      // and make them the same height.
       
      Spring y = Spring.constant(10);
      for (int r = 0; r < rows; r++) {
         Spring height = Spring.constant(0);
         for (int c = 0; c < cols; c++) {
            height = Spring.max(height,
               getConstraints(r, c, form, cols).getHeight());
         }
         for (int c = 0; c < cols; c++) {
            SpringLayout.Constraints constraints =
               getConstraints(r, c, form, cols);
            constraints.setY(y);
            constraints.setHeight(height);
         }
         y = Spring.sum(y, 
               Spring.sum(height, Spring.constant(5)));
      }
    
      //Set the form's size.
    
      SpringLayout.Constraints formConstraints = 
         layout.getConstraints(form);
      formConstraints.setConstraint(SpringLayout.SOUTH, y);
      formConstraints.setConstraint(SpringLayout.EAST, x);
   }
 
   private static SpringLayout.Constraints getConstraints(
            int row, int col, Container parent, int cols) {
      SpringLayout layout = (SpringLayout) parent.getLayout();
      Component c = parent.getComponent(row * cols + col);
      return layout.getConstraints(c);
   }
}