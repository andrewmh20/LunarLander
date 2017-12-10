package game;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import errors.AltInstrumentError;
import errors.AttInstrumentError;
import errors.ComputerOverloadedError1201;
import errors.ComputerOverloadedError1202;
import errors.FuelLeakError;
import errors.FullThrottleError;
import errors.LeftThrusterError;
import errors.RightThrusterError;
import errors.VwInstrumentError;
import errors.VxInstrumentError;
import errors.VyInstrumentError;

@SuppressWarnings("serial")
public class ErrorButtonPanel extends JPanel{
    
    private GameState gs;
    
    JButton FullThrottle;
    ErrorButtonPanel (GameState gs){
        
        super();
        this.gs = gs;
//Set layout
        setLayout(new GridLayout(5,2));
        
         FullThrottle = new JButton();
        FullThrottle.setText("Full Throttle");
        FullThrottle.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                gs.setErrorToSend(new FullThrottleError());
                // TODO Auto-generated method stub
                
            }
//            
        });
        this.add(FullThrottle);
//        
        JButton StuckLeftThruster = new JButton();
        StuckLeftThruster.setText("Stuck Left Thruster");
        StuckLeftThruster.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                gs.setErrorToSend(new LeftThrusterError());
                // TODO Auto-generated method stub
                
            }
            
        });
        this.add(StuckLeftThruster);
//        
        JButton StuckRightThruster= new JButton();
        StuckRightThruster.setText("Stuck Right Thruster");
        StuckRightThruster.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                gs.setErrorToSend(new RightThrusterError());
                // TODO Auto-generated method stub
                
            }
            
        });
        
        //TODO:reset errors, not game
        this.add(StuckRightThruster);
//       
//        JButton ResetGame= new JButton();
//        ResetGame.setText("Reset Game");
//        ResetGame.addActionListener(new ActionListener() {
//
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                gs.setErrorToSend(new ResetGameError());
//                // TODO Auto-generated method stub
//                
//            }
//            
//        });
//        this.add(ResetGame);
//
        JButton Error1201= new JButton();
        Error1201.setText("Error 1201");
        Error1201.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                gs.setErrorToSend(new ComputerOverloadedError1201());
                // TODO Auto-generated method stub
                
            }
            
        });
        this.add(Error1201);
        
        
        JButton Error1202= new JButton();
        Error1202.setText("Error 1202");
        Error1202.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                gs.setErrorToSend(new ComputerOverloadedError1202());
                // TODO Auto-generated method stub
                
            }
            
        });
        this.add(Error1202);
        
        JButton Fuel= new JButton();
        Fuel.setText("Fuel Leak");
        Fuel.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                gs.setErrorToSend(new FuelLeakError());
                // TODO Auto-generated method stub
                
            }
            
        });
        this.add(Fuel);
        
        JButton VxIns= new JButton();
        VxIns.setText("Horizontal Velocity Failure");
        VxIns.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                gs.setErrorToSend(new VxInstrumentError());
                // TODO Auto-generated method stub
                
            }
            
        });
        this.add(VxIns);
        
        JButton VyIns= new JButton();
        VyIns.setText("Vertical Velocity Failure");
        VyIns.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                gs.setErrorToSend(new VyInstrumentError());
                // TODO Auto-generated method stub
                
            }
            
        });
        this.add(VyIns);

        JButton VwIns= new JButton();
        VwIns.setText("Rotational Velocity Failure");
        VwIns.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                gs.setErrorToSend(new VwInstrumentError());
                // TODO Auto-generated method stub
                
            }
            
        });
        this.add(VwIns);
        
        JButton AttIns= new JButton();
        AttIns.setText("Altitude Indicator Failure");
        AttIns.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                gs.setErrorToSend(new AttInstrumentError());
                // TODO Auto-generated method stub
                
            }
            
        });
        this.add(AttIns);
        
        JButton AltIns= new JButton();
        AltIns.setText("Altitude Indicator Failure");
        AltIns.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                gs.setErrorToSend(new AltInstrumentError());
                // TODO Auto-generated method stub
                
            }
            
        });
        this.add(AltIns);
        
        



        
        
        
    }    
    
    public void paintComponent(Graphics gc) {
       // display the light bulb here
        //TODO:set the distances to constants that I can change?....
        //System.out.println(Game.gameState.getVw());
        System.out.println();
//        if (gs.getErrorFreq(new FullThrottleError()) != null) {
//            FullThrottle.setText("Full Throttle"+ gs.getErrorFreq(new FullThrottleError()));
//
//        }

    }
    
    @Override
    public Dimension getPreferredSize() {
        //TODO:FIx to make correct
        return new Dimension(500,400);
    }



}
