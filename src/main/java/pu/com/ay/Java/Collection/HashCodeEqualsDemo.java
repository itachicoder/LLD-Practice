package pu.com.ay.Java.Collection;

import java.util.*;

public class HashCodeEqualsDemo {

    // =========================================================
    // CASE 1 — Only equals() overridden, hashCode() NOT overridden
    // This is the BROKEN class — classic interview trap
    // =========================================================
    static class BrokenEmployee {
        int id;
        String name;

        BrokenEmployee(int id, String name) {
            this.id = id;
            this.name = name;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof BrokenEmployee)) return false;
            BrokenEmployee other = (BrokenEmployee) o;
            return this.id == other.id && this.name.equals(other.name);
        }

        // hashCode() NOT overridden
        // So it uses Object.hashCode() which is based on MEMORY ADDRESS
        // Two different objects with same id+name will have DIFFERENT hashCodes
    }


    // =========================================================
    // CASE 2 — Both equals() AND hashCode() overridden correctly
    // This is the CORRECT class
    // =========================================================
    static class CorrectEmployee {
        int id;
        String name;

        CorrectEmployee(int id, String name) {
            this.id = id;
            this.name = name;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof CorrectEmployee)) return false;
            CorrectEmployee other = (CorrectEmployee) o;
            return this.id == other.id && this.name.equals(other.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, name); // consistent with equals()
        }
    }


    // =========================================================
    // CASE 3 — hashCode() overridden but equals() NOT overridden
    // Also broken — two objects land in same bucket but equals() fails
    // =========================================================
    static class AlsoBrokenEmployee {
        int id;
        String name;

        AlsoBrokenEmployee(int id, String name) {
            this.id = id;
            this.name = name;
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, name);
        }

        // equals() NOT overridden — uses Object.equals() which is reference (==) check
    }


    public static void main(String[] args) {

        System.out.println("=======================================================");
        System.out.println("CASE 1: BrokenEmployee — equals() only, no hashCode()");
        System.out.println("=======================================================");

        BrokenEmployee e1 = new BrokenEmployee(1, "Alice");
        BrokenEmployee e2 = new BrokenEmployee(1, "Alice"); // same data, different object

        System.out.println("e1.equals(e2)     : " + e1.equals(e2));     // true  — equals() works
        System.out.println("e1.hashCode()     : " + e1.hashCode());     // some address-based number
        System.out.println("e2.hashCode()     : " + e2.hashCode());     // DIFFERENT number!
        System.out.println("hashCodes equal?  : " + (e1.hashCode() == e2.hashCode())); // false!

        // Now put e1 in a HashSet and try to find e2 (same data)
        Set<BrokenEmployee> brokenSet = new HashSet<>();
        brokenSet.add(e1);

        System.out.println("\nHashSet contains e2? : " + brokenSet.contains(e2));
        // EXPECTED: true (same data)
        // ACTUAL:   false (different bucket due to different hashCode!)
        // HashMap NEVER even checks equals() if hashCodes don't match

        // Same problem with HashMap
        Map<BrokenEmployee, String> brokenMap = new HashMap<>();
        brokenMap.put(e1, "Engineer");

        System.out.println("HashMap get(e2)?     : " + brokenMap.get(e2));
        // EXPECTED: "Engineer"
        // ACTUAL:   null — key not found!

        // You just created a MEMORY LEAK — adding duplicates thinking they're unique
        brokenSet.add(e2); // adds AGAIN because different hashCode = different bucket
        System.out.println("BrokenSet size (should be 1, is): " + brokenSet.size()); // 2!


        System.out.println("\n=======================================================");
        System.out.println("CASE 2: CorrectEmployee — both equals() and hashCode()");
        System.out.println("=======================================================");

        CorrectEmployee c1 = new CorrectEmployee(1, "Alice");
        CorrectEmployee c2 = new CorrectEmployee(1, "Alice");

        System.out.println("c1.equals(c2)     : " + c1.equals(c2));     // true
        System.out.println("c1.hashCode()     : " + c1.hashCode());     // same number
        System.out.println("c2.hashCode()     : " + c2.hashCode());     // same number
        System.out.println("hashCodes equal?  : " + (c1.hashCode() == c2.hashCode())); // true!

        Set<CorrectEmployee> correctSet = new HashSet<>();
        correctSet.add(c1);

        System.out.println("\nHashSet contains c2? : " + correctSet.contains(c2)); // true!

        Map<CorrectEmployee, String> correctMap = new HashMap<>();
        correctMap.put(c1, "Engineer");

        System.out.println("HashMap get(c2)?     : " + correctMap.get(c2)); // "Engineer"!

        correctSet.add(c2); // correctly identified as duplicate, NOT added again
        System.out.println("CorrectSet size (should be 1, is): " + correctSet.size()); // 1!


        System.out.println("\n=======================================================");
        System.out.println("CASE 3: AlsoBroken — hashCode() only, no equals()");
        System.out.println("=======================================================");

        AlsoBrokenEmployee a1 = new AlsoBrokenEmployee(1, "Alice");
        AlsoBrokenEmployee a2 = new AlsoBrokenEmployee(1, "Alice");

        System.out.println("a1.equals(a2)     : " + a1.equals(a2));     // false! (reference check)
        System.out.println("hashCodes equal?  : " + (a1.hashCode() == a2.hashCode())); // true

        // Both land in SAME bucket (same hashCode)
        // But equals() returns false (different references)
        // So HashSet treats them as DIFFERENT entries
        Set<AlsoBrokenEmployee> alsoBrokenSet = new HashSet<>();
        alsoBrokenSet.add(a1);
        alsoBrokenSet.add(a2);
        System.out.println("AlsoBrokenSet size (should be 1, is): " + alsoBrokenSet.size()); // 2!


        System.out.println("\n=======================================================");
        System.out.println("HOW HASHMAP LOOKUP WORKS — Step by Step");
        System.out.println("=======================================================");
        // When you call map.get(key):
        // Step 1: Compute key.hashCode()
        // Step 2: Find the bucket → index = hashCode % capacity
        // Step 3: Walk the bucket's LinkedList/Tree
        // Step 4: For each node, check node.key.equals(key)
        // Step 5: If equals() returns true → return value
        //
        // If hashCode is WRONG → wrong bucket → equals() never called → null returned
        // If equals() is WRONG → right bucket → equals() fails → null returned
        // BOTH must be correct for HashMap to work!

        System.out.println("HashMap lookup requires BOTH steps:");
        System.out.println("Step 1 → hashCode() → finds the RIGHT BUCKET");
        System.out.println("Step 2 → equals()   → finds the RIGHT ENTRY in bucket");


        System.out.println("\n=======================================================");
        System.out.println("THE CONTRACT — memorize this for interviews");
        System.out.println("=======================================================");
        System.out.println("Rule 1: if a.equals(b) == true  → a.hashCode() MUST == b.hashCode()");
        System.out.println("Rule 2: if a.hashCode() != b.hashCode() → a.equals(b) MUST be false");
        System.out.println("Rule 3: if a.hashCode() == b.hashCode() → a.equals(b) MAY be true or false");
        System.out.println("        (collision is allowed, just bad for performance)");


        System.out.println("\n=======================================================");
        System.out.println("BONUS: String already has both overridden correctly");
        System.out.println("=======================================================");
        String s1 = new String("Alice");
        String s2 = new String("Alice");

        System.out.println("s1 == s2          : " + (s1 == s2));           // false (diff objects)
        System.out.println("s1.equals(s2)     : " + s1.equals(s2));        // true
        System.out.println("hashCodes equal?  : " + (s1.hashCode() == s2.hashCode())); // true

        Set<String> stringSet = new HashSet<>();
        stringSet.add(s1);
        stringSet.add(s2); // correctly deduplicated
        System.out.println("String HashSet size: " + stringSet.size()); // 1 — works perfectly!
    }
}
