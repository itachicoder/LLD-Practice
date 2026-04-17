package pu.com.ay.DesignPatternsPractice.SingleTon;
    class BasicSingleTon {
        private static BasicSingleTon instance;

        private BasicSingleTon(){

        }

        public static BasicSingleTon getInstance(){
            if (instance == null) {
                instance = new BasicSingleTon();
            }
            return instance;
        }
    }

    class BasicThreadSafe {

        private static BasicThreadSafe instance;


        private BasicThreadSafe(){

        }

        public static synchronized BasicThreadSafe getInstance(){
            if (instance == null) {
                instance = new BasicThreadSafe();
            }
            return instance;
        }
    
        
    }

    class BasicThreadSafeDouble {

        private static BasicThreadSafeDouble instance;


        private BasicThreadSafeDouble(){

        }

        public static BasicThreadSafeDouble getInstance(){

            if (instance == null) {
                synchronized(BasicThreadSafeDouble.class) {
                    if(instance == null){
                        instance = new BasicThreadSafeDouble();
                    }
                    
                }
            }
            return instance;
        }
    
        
    }

    public class BasicSingleTonDemo {
        public static void main(String[] args) {

            BasicSingleTon newOne =  BasicSingleTon.getInstance();

        }
    }

