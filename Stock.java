public class Stock
{
    /** Ticker symbol or display name of this security. */ 
    
    private String name;
    private double price;
    private double volatility;
    private double volume;
    
    
    public  Stock(String name, double price, int volatility, int volume){
        this.name = name;
        this.price = price;
        this.volatility = volatility;
        this.volume = volume;
    }

    public void update() {
        double max = volatility;
        double min = -(volatility / 2);
        double percentChange = Math.random() * (max - min) + min;
        price += price * percentChange / 100;
        
    }
    
    public double getVolume() {
        return volume;
    }
    
    public double getVolatility() {
        return volatility;
    }
    
    public double getPrice() {
        return price;
    }
    
    public String getName() {
        return name;
    }
    
    
}
