import java.util.HashMap;
import java.time.LocalDateTime;

public class ExchangeRateService {
    
    // Singleton pattern
    private static ExchangeRateService instance = null;
    
    private HashMap<String, Double> exchangeRates;
    private HashMap<String, Double> last7DayAverage;
    private LocalDateTime lastUpdate;
    
    private ExchangeRateService() {
        exchangeRates = new HashMap<>();
        last7DayAverage = new HashMap<>();
        updateRates();
        System.out.println("✅ ExchangeRateService initialized");
    }
    
    public static ExchangeRateService getInstance() {
        if (instance == null) {
            instance = new ExchangeRateService();
        }
        return instance;
    }
    
    // Simulate fetching live rates (in real app, call API)
    public void updateRates() {
        // Current rates (USDT to other currencies)
        exchangeRates.put("AED", 3.67 + (Math.random() * 0.1 - 0.05)); // UAE Dirham
        exchangeRates.put("SAR", 3.75 + (Math.random() * 0.1 - 0.05)); // Saudi Riyal
        exchangeRates.put("INR", 83.12 + (Math.random() * 0.5 - 0.25)); // Indian Rupee
        exchangeRates.put("PHP", 56.45 + (Math.random() * 0.5 - 0.25)); // Philippine Peso
        exchangeRates.put("PKR", 278.50 + (Math.random() * 1.0 - 0.5)); // Pakistani Rupee
        exchangeRates.put("EUR", 0.92 + (Math.random() * 0.02 - 0.01)); // Euro
        
        // Simulate 7-day averages (slightly different)
        last7DayAverage.put("AED", 3.68);
        last7DayAverage.put("SAR", 3.76);
        last7DayAverage.put("INR", 83.50);
        last7DayAverage.put("PHP", 56.80);
        last7DayAverage.put("PKR", 279.00);
        last7DayAverage.put("EUR", 0.93);
        
        lastUpdate = LocalDateTime.now();
    }
    
    public double getRate(String currency) {
        return exchangeRates.getOrDefault(currency, 1.0);
    }
    
    public double convert(double amountUSDT, String toCurrency) {
        double rate = getRate(toCurrency);
        return amountUSDT * rate;
    }
    
    // AI recommendation logic
    public String getRateRecommendation(String currency) {
        double currentRate = getRate(currency);
        double avgRate = last7DayAverage.getOrDefault(currency, currentRate);
        
        double percentDiff = ((currentRate - avgRate) / avgRate) * 100;
        
        if (percentDiff >= 2.0) {
            return "EXCELLENT"; // Rate is 2%+ better than average
        } else if (percentDiff >= 0) {
            return "GOOD"; // Rate is better than average
        } else if (percentDiff >= -1.0) {
            return "FAIR"; // Rate is slightly worse
        } else {
            return "WAIT"; // Rate is significantly worse
        }
    }
    
    public double getPotentialSavings(String currency, double amount) {
        double currentRate = getRate(currency);
        double avgRate = last7DayAverage.getOrDefault(currency, currentRate);
        
        double currentAmount = amount * currentRate;
        double avgAmount = amount * avgRate;
        
        return currentAmount - avgAmount; // Positive = you get more, Negative = you get less
    }
    
    public String getLastUpdateTime() {
        return lastUpdate.toString();
    }
    
    public HashMap<String, Double> getAllRates() {
        return new HashMap<>(exchangeRates);
    }
}
