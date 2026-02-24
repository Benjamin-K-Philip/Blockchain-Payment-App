/**
 * Start - Main entry point for the Blockchain Payment System
 * Launches the Java Swing GUI application
 */
public class Start {
    
    public static void main(String[] args) {
        System.out.println("🚀 BLOCKCHAIN PAYMENT SYSTEM - STARTING GUI\n");
        
        try {
            // Launch the Swing GUI
            BlockchainPaymentGUI.main(args);
            
        } catch (Exception e) {
            System.err.println("❌ Error starting GUI: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
