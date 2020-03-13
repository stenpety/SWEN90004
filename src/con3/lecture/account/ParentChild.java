package con3.lecture.account;

class Parent extends Thread {
    Account a;
    java.util.Random r = new java.util.Random();

    Parent(Account a) {
        this.a = a;
    }

    public void run() {
        for (int i = 0; i < 50; i++) {
            a.deposit(250);
            System.out.print("deposit 500; ");
            System.out.println("balance = " + a.balance);
            int s = r.nextInt(250);
            try {
                Thread.sleep(s);
            }
            catch (InterruptedException e) {}
        }
    }
}

class Child extends Thread {
    String childName;
    Account a;

    Child(Account a, String childName) {
        this.childName = childName;
        this.a = a;
    }

    public void run() {
        for (int i = 0; i < 50; i++) {
            a.withdraw(100);
            System.out.print(childName + ": withdraw 100; ");
            System.out.println("balance = " + a.balance);
        }
    }
}
