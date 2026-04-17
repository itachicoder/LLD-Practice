package pu.com.ay.Java;

public class StringBuffer {

    public static void main(String[] args) {

        // =============================================
        // 1. String — IMMUTABLE (bad for concatenation in loops)
        // Every += creates a NEW object in heap → O(n²) memory waste
        // =============================================
        String result = "";
        for (int i = 0; i < 1000; i++) {
            result += i; // internally: result = new StringBuilder(result).append(i).toString()
        }
        // Each iteration: old string thrown away, new one created
        // 1000 iterations = ~1000 throwaway objects in heap
        System.out.println("String length: " + result.length());


        // =============================================
        // 2. StringBuilder — MUTABLE, NOT thread-safe
        // Single buffer, grows internally → O(n) total, much faster
        // Use this in single-threaded code (most common case)
        // =============================================
        StringBuilder sb = new StringBuilder();   // default capacity: 16 chars
        for (int i = 0; i < 1000; i++) {
            sb.append(i);   // modifies SAME buffer, no new objects
        }
        String result1 = sb.toString();
        System.out.println("StringBuilder length: " + result1.length());

        // Common StringBuilder methods
        StringBuilder demo = new StringBuilder("Hello");
        demo.append(" World");          // Hello World
        demo.insert(5, ",");            // Hello, World
        demo.replace(7, 12, "Java");    // Hello, Java
        demo.delete(5, 7);              // HelloJava
        demo.reverse();                 // avaJolleH
        System.out.println("After operations: " + demo);
        System.out.println("Char at index 2: " + demo.charAt(2));
        System.out.println("Length: " + demo.length());


        // =============================================
        // 3. StringBuffer — MUTABLE, thread-safe (synchronized)
        // Same API as StringBuilder but every method is synchronized
        // Use ONLY when multiple threads share the same buffer
        // Slower than StringBuilder due to lock overhead
        // =============================================
        java.lang.StringBuffer sb1 = new java.lang.StringBuffer();
        sb1.append("Thread");
        sb1.append("-");
        sb1.append("Safe");
        System.out.println("StringBuffer result: " + sb1.toString()); // Thread-Safe

        // StringBuffer same methods as StringBuilder
        sb1.insert(6, " is");           // Thread is-Safe
        sb1.replace(9, 10, " ");        // Thread is Safe
        sb1.delete(0, 7);               // is Safe
        System.out.println("After StringBuffer ops: " + sb1);


        // =============================================
        // 4. String Pool demo — == vs .equals()
        // =============================================
        String a = "Java";          // goes into String Pool
        String b = "Java";          // reuses SAME pool object
        String c = new String("Java"); // forced NEW heap object
        String d = c.intern();      // intern() → returns pool reference

        System.out.println("\n--- String Pool Demo ---");
        System.out.println(a == b);       // true  — same pool reference
        System.out.println(a == c);       // false — c is a separate heap object
        System.out.println(a == d);       // true  — intern() brought d back to pool
        System.out.println(a.equals(c));  // true  — same value, different objects


        // =============================================
        // 5. Compile-time constant folding (tricky interview question)
        // =============================================
        String x = "Ja" + "va";    // compiler resolves this to "Java" at compile time
        String y = "Java";
        System.out.println("\n--- Constant Folding ---");
        System.out.println(x == y); // true — both point to same pool entry

        String part = "Ja";
        String z = part + "va";    // runtime concatenation → new heap object
        System.out.println(z == y); // false — z built at runtime, not from pool


        // =============================================
        // 6. Performance comparison (conceptual)
        // String:        O(n²) — n objects created
        // StringBuilder: O(n)  — 1 object, buffer resized occasionally
        // StringBuffer:  O(n)  — same as StringBuilder + lock overhead
        // =============================================
        System.out.println("\n--- Summary ---");
        System.out.println("String:        immutable, thread-safe by nature, pool-backed");
        System.out.println("StringBuilder: mutable, NOT thread-safe, fastest");
        System.out.println("StringBuffer:  mutable, thread-safe (synchronized), slower");
    }
}
