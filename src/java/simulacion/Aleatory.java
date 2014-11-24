
package simulacion;

import java.util.Random;

public class Aleatory {
    
    final private Random rnd;
    
    public Aleatory(){
     rnd = new Random();        
    }
    
    public Aleatory(long seed){
     rnd = new Random(seed);        
    }

    public long Bernoulli(double p){
      return ((rnd.nextDouble() < (1.0 - p)) ? 0 : 1);
    }

    public long Binomial(long n, double p){ 
      long i, x = 0;

      for (i = 0; i < n; i++)
        x += Bernoulli(p);
      return (x);
    }

    public long Equilikely(long a, long b){
      return (a + (long) ((b - a + 1) * rnd.nextDouble()));
    }

    public long Geometric(double p){
      return ((long) (Math.log(1.0 - rnd.nextDouble()) / Math.log(p)));
    }

    public long Pascal(long n, double p){ 
      long i, x = 0;

      for (i = 0; i < n; i++)
        x += Geometric(p);
      return (x);
    }

    public long Poisson(double m){ 
      double t = 0.0;
      long   x = 0;

      while (t < m) {
        t += Exponential(1.0);
        x++;
      }
      return (x - 1);
    }

    public double Uniform(double a, double b){ 
      return (a + (b - a) * rnd.nextDouble());
    }

    public double Exponential(double m){
      return (-m * Math.log(1.0 - rnd.nextDouble()));
    }

    public double Erlang(long n, double b){ 
      long   i;
      double x = 0.0;

      for (i = 0; i < n; i++){ 
        x += Exponential(b);
      }
      return (x);
    }

    public double Normal(double m, double s)
    { 
      double p0 = 0.322232431088;
      double q0 = 0.099348462606;
      double p1 = 1.0;                
      double q1 = 0.588581570495;
      double p2 = 0.342242088547; 
      double q2 = 0.531103462366;
      double p3 = 0.204231210245e-1;
      double q3 = 0.103537752850;
      double p4 = 0.453642210148e-4;
      double q4 = 0.385607006340e-2;
      
      double u, t, p, q, z;

      u   = rnd.nextDouble();
      if(u < 0.5){
        t = Math.sqrt(-2.0 * Math.log(u));
      }
      else{
        t = Math.sqrt(-2.0 * Math.log(1.0 - u));
      }
      
      p   = p0 + t * (p1 + t * (p2 + t * (p3 + t * p4)));
      q   = q0 + t * (q1 + t * (q2 + t * (q3 + t * q4)));
      
      if (u < 0.5){
        z = (p / q) - t;
      }        
      else{
        z = t - (p / q);
      }
      
      return (m + s * z);
    }

    public double Lognormal(double a, double b){
      return (Math.exp(a + b * Normal(0.0, 1.0)));
    }

    public double Chisquare(long n){ 
      long   i;
      double z, x = 0.0;

      for (i = 0; i < n; i++) {
        z  = Normal(0.0, 1.0);
        x += z * z;
      }
      return (x);
    }

    public double Student(long n){
      return (Normal(0.0, 1.0) / Math.sqrt(Chisquare(n) / n));
    }
    
}
